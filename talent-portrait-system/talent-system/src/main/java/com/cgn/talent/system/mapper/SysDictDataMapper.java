package com.cgn.talent.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgn.talent.system.entity.SysDictData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典数据Mapper接口
 *
 * @author CGN
 * @date 2024-01-10
 */
@Mapper
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    /**
     * 根据字典类型查询字典数据列表
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<SysDictData> selectDictDataByType(@Param("dictType") String dictType);

    /**
     * 根据字典类型和字典键值查询字典数据
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典数据
     */
    SysDictData selectDictDataByTypeAndValue(@Param("dictType") String dictType,
                                             @Param("dictValue") String dictValue);

    /**
     * 校验字典键值是否唯一
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @param dictId 字典ID（排除自己）
     * @return 结果
     */
    int checkDictDataUnique(@Param("dictType") String dictType,
                            @Param("dictValue") String dictValue,
                            @Param("dictId") Long dictId);

    /**
     * 批量删除字典数据
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteDictDataByIds(@Param("ids") Long[] ids);
}
