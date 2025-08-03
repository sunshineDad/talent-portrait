package com.cgn.talent.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgn.talent.core.entity.PersonProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 人员项目经历Mapper接口
 *
 * @author CGN
 * @date 2024-01-10
 */
@Mapper
public interface PersonProjectMapper extends BaseMapper<PersonProject> {

    /**
     * 根据人员ID查询项目经历
     *
     * @param personId 人员ID
     * @return 项目经历列表
     */
    List<PersonProject> selectProjectByPersonId(@Param("personId") Long personId);

    /**
     * 批量插入项目经历
     *
     * @param projectList 项目列表
     * @return 结果
     */
    int batchInsertProject(@Param("list") List<PersonProject> projectList);

    /**
     * 删除人员的所有项目经历
     *
     * @param personId 人员ID
     * @return 结果
     */
    int deleteProjectByPersonId(@Param("personId") Long personId);

    /**
     * 统计项目类型分布
     *
     * @param teamId 团队ID
     * @return 项目类型统计
     */
    List<Map<String, Object>> selectProjectTypeStatistics(@Param("teamId") Long teamId);

    /**
     * 查询重要项目列表（国家级、省部级）
     *
     * @param teamId 团队ID
     * @return 重要项目列表
     */
    List<PersonProject> selectImportantProjects(@Param("teamId") Long teamId);
}
