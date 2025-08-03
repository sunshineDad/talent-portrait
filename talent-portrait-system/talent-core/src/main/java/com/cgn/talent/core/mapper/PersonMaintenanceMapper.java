package com.cgn.talent.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgn.talent.core.entity.PersonMaintenance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 人才信息维护Mapper接口
 *
 * @author CGN
 * @date 2024-01-10
 */
@Mapper
public interface PersonMaintenanceMapper extends BaseMapper<PersonMaintenance> {

    /**
     * 根据人员ID查询人才信息维护记录
     *
     * @param personId 人员ID
     * @return 人才信息维护列表
     */
    List<PersonMaintenance> selectMaintenanceByPersonId(@Param("personId") Long personId);

    /**
     * 批量插入人才信息维护记录
     *
     * @param list 人才信息维护列表
     * @return 影响行数
     */
    int batchInsertMaintenance(List<PersonMaintenance> list);

    /**
     * 删除人员的所有人才信息维护记录
     *
     * @param personId 人员ID
     * @return 影响行数
     */
    int deleteMaintenanceByPersonId(@Param("personId") Long personId);

    /**
     * 统计团队人才工程分布
     *
     * @param teamId 团队ID
     * @return 统计结果
     */
    List<Map<String, Object>> selectTeamProgramDistribution(@Param("teamId") Long teamId);
}
