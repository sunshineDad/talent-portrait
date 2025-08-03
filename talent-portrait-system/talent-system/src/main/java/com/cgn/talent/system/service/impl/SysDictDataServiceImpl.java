package com.cgn.talent.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgn.talent.common.page.PageParam;
import com.cgn.talent.common.page.PageResult;
import com.cgn.talent.common.utils.RedisUtils;
import com.cgn.talent.system.entity.SysDictData;
import com.cgn.talent.system.mapper.SysDictDataMapper;
import com.cgn.talent.system.service.ISysDictDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 字典数据Service业务层处理
 *
 * @author CGN
 * @date 2024-01-10
 */
@Slf4j
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private RedisUtils redisUtils;

    // Redis缓存前缀
    private static final String DICT_CACHE_PREFIX = "dict:data:";

    @Override
    public PageResult<SysDictData> selectDictDataPage(PageParam pageParam, SysDictData dictData) {
        Page<SysDictData> page = new Page<>(pageParam.getCurrent(), pageParam.getSize());

        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(dictData.getDictType())) {
            wrapper.eq(SysDictData::getDictType, dictData.getDictType());
        }
        if (StringUtils.hasText(dictData.getDictLabel())) {
            wrapper.like(SysDictData::getDictLabel, dictData.getDictLabel());
        }
        if (StringUtils.hasText(dictData.getStatus())) {
            wrapper.eq(SysDictData::getStatus, dictData.getStatus());
        }

        wrapper.orderByAsc(SysDictData::getSortOrder);

        Page<SysDictData> resultPage = page(page, wrapper);

        return new PageResult<>(
                resultPage.getCurrent(),
                resultPage.getSize(),
                resultPage.getTotal(),
                resultPage.getRecords()
        );
    }

    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        // 优先从缓存获取
        String cacheKey = DICT_CACHE_PREFIX + dictType;
        Object cached = redisUtils.get(cacheKey);
        if (cached != null) {
            return (List<SysDictData>) cached;
        }

        List<SysDictData> dictDataList = dictDataMapper.selectDictDataByType(dictType);

        // 缓存1小时
        if (!CollectionUtils.isEmpty(dictDataList)) {
            redisUtils.set(cacheKey, dictDataList, 3600);
        }

        return dictDataList;
    }

    @Override
    public SysDictData selectDictDataByTypeAndValue(String dictType, String dictValue) {
        return dictDataMapper.selectDictDataByTypeAndValue(dictType, dictValue);
    }

    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        // 优先从缓存的列表中查找
        List<SysDictData> dictDataList = selectDictDataByType(dictType);
        if (!CollectionUtils.isEmpty(dictDataList)) {
            for (SysDictData dictData : dictDataList) {
                if (dictData.getDictValue().equals(dictValue)) {
                    return dictData.getDictLabel();
                }
            }
        }
        return dictValue;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertDictData(SysDictData dictData) {
        // 校验唯一性
        if (!checkDictDataUnique(dictData)) {
            throw new RuntimeException("字典键值已存在");
        }

        // 设置默认值
        if (dictData.getIsDefault() == null) {
            dictData.setIsDefault("N");
        }
        if (dictData.getStatus() == null) {
            dictData.setStatus("0");
        }

        int result = baseMapper.insert(dictData);

        if (result > 0) {
            clearDictCache(dictData.getDictType());
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDictData(SysDictData dictData) {
        // 校验唯一性
        if (!checkDictDataUnique(dictData)) {
            throw new RuntimeException("字典键值已存在");
        }

        int result = baseMapper.updateById(dictData);

        if (result > 0) {
            clearDictCache(dictData.getDictType());
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDictDataByIds(Long[] dictIds) {
        // 查询要删除的字典数据
        List<SysDictData> dictDataList = listByIds(Arrays.asList(dictIds));

        int count = dictDataMapper.deleteDictDataByIds(dictIds);

        if (count > 0) {
            // 清除相关缓存
            dictDataList.stream()
                    .map(SysDictData::getDictType)
                    .distinct()
                    .forEach(this::clearDictCache);
        }

        return count;
    }

    @Override
    public boolean checkDictDataUnique(SysDictData dictData) {
        Long dictId = dictData.getId() == null ? -1L : dictData.getId();
        int count = dictDataMapper.checkDictDataUnique(
                dictData.getDictType(),
                dictData.getDictValue(),
                dictId
        );
        return count == 0;
    }

    /**
     * 清除字典缓存
     */
    private void clearDictCache(String dictType) {
        String cacheKey = DICT_CACHE_PREFIX + dictType;
        redisUtils.del(cacheKey);
        log.info("字典数据缓存已清除: {}", dictType);
    }
}
