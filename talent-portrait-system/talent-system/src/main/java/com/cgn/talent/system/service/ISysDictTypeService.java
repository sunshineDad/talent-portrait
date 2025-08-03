package com.cgn.talent.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cgn.talent.common.page.PageParam;
import com.cgn.talent.common.page.PageResult;
import com.cgn.talent.system.entity.SysDictType;

import java.util.List;

/**
 * 字典类型Service接口
 *
 * @author CGN
 * @date 2024-01-10
 */
public interface ISysDictTypeService extends IService<SysDictType> {

    /**
     * 分页查询字典类型列表
     *
     * @param pageParam 分页参数
     * @param dictType 查询条件
     * @return 分页结果
     */
    PageResult<SysDictType> selectDictTypePage(PageParam pageParam, SysDictType dictType);

    /**
     * 查询所有字典类型
     *
     * @return 字典类型列表
     */
    List<SysDictType> selectDictTypeAll();

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典类型信息
     */
    SysDictType selectDictTypeByType(String dictType);

    /**
     * 新增字典类型
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    int insertDictType(SysDictType dictType);

    /**
     * 修改字典类型
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    int updateDictType(SysDictType dictType);

    /**
     * 批量删除字典类型
     *
     * @param dictIds 需要删除的字典ID数组
     * @return 结果
     */
    int deleteDictTypeByIds(Long[] dictIds);

    /**
     * 清理字典缓存
     */
    void clearDictCache();

    /**
     * 校验字典类型是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    boolean checkDictTypeUnique(SysDictType dictType);
}
