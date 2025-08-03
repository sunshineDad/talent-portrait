package com.cgn.talent.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgn.talent.system.entity.SysDictType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典类型Mapper接口
 *
 * @author CGN
 * @date 2024-01-10
 */
@Mapper
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典类型信息
     */
    SysDictType selectDictTypeByType(@Param("dictType") String dictType);

    /**
     * 校验字典类型是否唯一
     *
     * @param dictType 字典类型
     * @param dictId 字典ID（排除自己）
     * @return 结果
     */
    int checkDictTypeUnique(@Param("dictType") String dictType, @Param("dictId") Long dictId);

    /**
     * 查询所有字典类型列表
     *
     * @return 字典类型列表
     */
    List<SysDictType> selectDictTypeAll();
}
