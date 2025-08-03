package com.cgn.talent.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgn.talent.common.page.PageParam;
import com.cgn.talent.common.page.PageResult;
import com.cgn.talent.common.utils.RedisUtils;
import com.cgn.talent.system.entity.SysDictType;
import com.cgn.talent.system.mapper.SysDictDataMapper;
import com.cgn.talent.system.mapper.SysDictTypeMapper;
import com.cgn.talent.system.service.ISysDictTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 字典类型Service业务层处理
 *
 * @author CGN
 * @date 2024-01-10
 */
@Slf4j
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private RedisUtils redisUtils;

    // Redis缓存前缀
    private static final String DICT_CACHE_PREFIX = "dict:";

    @Override
    public PageResult<SysDictType> selectDictTypePage(PageParam pageParam, SysDictType dictType) {
        Page<SysDictType> page = new Page<>(pageParam.getCurrent(), pageParam.getSize());

        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(dictType.getDictName())) {
            wrapper.like(SysDictType::getDictName, dictType.getDictName());
        }
        if (StringUtils.hasText(dictType.getDictType())) {
            wrapper.like(SysDictType::getDictType, dictType.getDictType());
        }
        if (StringUtils.hasText(dictType.getStatus())) {
            wrapper.eq(SysDictType::getStatus, dictType.getStatus());
        }

        // 排序处理
        if (StringUtils.hasText(pageParam.getOrderBy())) {
            wrapper.orderBy(true, pageParam.getIsAsc(),
                    pageParam.getOrderBy().equals("createTime") ? SysDictType::getCreateTime : SysDictType::getId);
        } else {
            wrapper.orderByDesc(SysDictType::getCreateTime);
        }

        Page<SysDictType> resultPage = page(page, wrapper);

        return new PageResult<>(
                resultPage.getCurrent(),
                resultPage.getSize(),
                resultPage.getTotal(),
                resultPage.getRecords()
        );
    }

    @Override
    public List<SysDictType> selectDictTypeAll() {
        // 优先从缓存获取
        String cacheKey = DICT_CACHE_PREFIX + "type:all";
        Object cached = redisUtils.get(cacheKey);
        if (cached != null) {
            return (List<SysDictType>) cached;
        }

        List<SysDictType> dictTypes = dictTypeMapper.selectDictTypeAll();
        // 缓存1小时
        redisUtils.set(cacheKey, dictTypes, 3600);
        return dictTypes;
    }

    @Override
    public SysDictType selectDictTypeByType(String dictType) {
        return dictTypeMapper.selectDictTypeByType(dictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertDictType(SysDictType dictType) {
        // 校验唯一性
        if (!checkDictTypeUnique(dictType)) {
            throw new RuntimeException("字典类型'" + dictType.getDictType() + "'已存在");
        }

        int result = baseMapper.insert(dictType);
        if (result > 0) {
            clearDictCache();
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDictType(SysDictType dictType) {
        // 校验唯一性
        if (!checkDictTypeUnique(dictType)) {
            throw new RuntimeException("字典类型'" + dictType.getDictType() + "'已存在");
        }

        SysDictType oldDict = getById(dictType.getId());
        int result = baseMapper.updateById(dictType);

        if (result > 0) {
            // 如果修改了字典类型编码，需要同步更新字典数据
            if (!oldDict.getDictType().equals(dictType.getDictType())) {
                // 更新字典数据的类型
                // 这里可以添加批量更新逻辑
            }
            clearDictCache();
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDictTypeByIds(Long[] dictIds) {
        int count = 0;
        for (Long dictId : dictIds) {
            SysDictType dictType = getById(dictId);
            if (dictType != null) {
                // 删除字典类型
                baseMapper.deleteById(dictId);
                // 删除对应的字典数据
                LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(SysDictType::getDictType, dictType.getDictType());
                dictTypeMapper.delete(wrapper);
                count++;
            }
        }

        if (count > 0) {
            clearDictCache();
        }
        return count;
    }

    @Override
    public void clearDictCache() {
        // 清除所有字典缓存
        redisUtils.del(DICT_CACHE_PREFIX + "*");
        log.info("字典缓存已清除");
    }

    @Override
    public boolean checkDictTypeUnique(SysDictType dictType) {
        Long dictId = dictType.getId() == null ? -1L : dictType.getId();
        int count = dictTypeMapper.checkDictTypeUnique(dictType.getDictType(), dictId);
        return count == 0;
    }
}
