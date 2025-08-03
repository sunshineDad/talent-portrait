package com.cgn.talent.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cgn.talent.common.page.PageParam;
import com.cgn.talent.common.page.PageResult;
import com.cgn.talent.system.entity.SysDictData;

import java.util.List;

/**
 * 字典数据Service接口
 *
 * @author CGN
 * @date 2024-01-10
 */
public interface ISysDictDataService extends IService<SysDictData> {

    /**
     * 分页查询字典数据列表
     *
     * @param pageParam 分页参数
     * @param dictData 查询条件
     * @return 分页结果
     */
    PageResult<SysDictData> selectDictDataPage(PageParam pageParam, SysDictData dictData);

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 根据字典类型和字典键值查询字典数据
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典数据
     */
    SysDictData selectDictDataByTypeAndValue(String dictType, String dictValue);

    /**
     * 根据字典类型和字典键值查询字典标签
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    String selectDictLabel(String dictType, String dictValue);

    /**
     * 新增字典数据
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    int insertDictData(SysDictData dictData);

    /**
     * 修改字典数据
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    int updateDictData(SysDictData dictData);

    /**
     * 批量删除字典数据
     *
     * @param dictIds 需要删除的字典数据ID数组
     * @return 结果
     */
    int deleteDictDataByIds(Long[] dictIds);

    /**
     * 校验字典数据是否唯一
     *
     * @param dictData 字典数据
     * @return 结果
     */
    boolean checkDictDataUnique(SysDictData dictData);
}
