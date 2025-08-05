package com.cgn.talent.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgn.talent.core.entity.PersonProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 人员项目经历Mapper接口
 *
 * @author CGN
 * @date 2024-01-10
 */
@Mapper
public interface PersonProjectMapper extends BaseMapper<PersonProject> {

    /**
     * 根据人员编号查询项目经历
     */
    List<PersonProject> selectProjectByPersonCode(@Param("personCode") String personCode);

    /**
     * 批量插入项目经历
     */
    int batchInsertProject(List<PersonProject> list);

    /**
     * 删除人员的所有项目经历
     */
    int deleteProjectByPersonCode(@Param("personCode") String personCode);
}
