package com.cgn.talent.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgn.talent.core.entity.PersonInfluence;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 人员影响力信息Mapper接口
 *
 * @author CGN
 * @date 2024-01-10
 */
@Mapper
public interface PersonInfluenceMapper extends BaseMapper<PersonInfluence> {

    /**
     * 根据人员编号查询影响力信息
     */
    List<PersonInfluence> selectInfluenceByPersonCode(@Param("personCode") String personCode);

    /**
     * 批量插入影响力信息
     */
    int batchInsertInfluence(List<PersonInfluence> list);

    /**
     * 删除人员的所有影响力信息
     */
    int deleteInfluenceByPersonCode(@Param("personCode") String personCode);

    /**
     * 查询当前在任的影响力信息
     */
    @MapKey("person_code")
    List<PersonInfluence> selectCurrentInfluenceByPersonCode(@Param("personCode") String personCode);

    /**
     * 统计团队影响力分布
     */
    @MapKey("team_code")
    List<Map<String, Object>> selectTeamInfluenceStatistics(@Param("teamCode") String teamCode);

    /**
     * 查询人才计划分布统计
     */
    @MapKey("team_code")
    List<Map<String, Object>> selectTalentPlanDistribution(@Param("teamCode") String teamCode);

    /**
     * 更新影响力在任状态
     */
    int updateInfluenceCurrentStatus();
}
