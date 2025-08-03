package com.cgn.talent.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgn.talent.core.entity.PersonSkill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 人员专业能力Mapper接口
 *
 * @author CGN
 * @date 2024-01-10
 */
@Mapper
public interface PersonSkillMapper extends BaseMapper<PersonSkill> {

    /**
     * 根据人员ID查询能力列表
     *
     * @param personId 人员ID
     * @return 能力列表
     */
    List<PersonSkill> selectSkillByPersonId(@Param("personId") Long personId);

    /**
     * 批量插入人员能力
     *
     * @param skillList 能力列表
     * @return 结果
     */
    int batchInsertSkill(@Param("list") List<PersonSkill> skillList);

    /**
     * 删除人员的所有能力
     *
     * @param personId 人员ID
     * @return 结果
     */
    int deleteSkillByPersonId(@Param("personId") Long personId);

    /**
     * 统计团队能力分布
     *
     * @param teamId 团队ID
     * @return 能力分布统计
     */
    List<Map<String, Object>> selectTeamSkillDistribution(@Param("teamId") Long teamId);

    /**
     * 查询能力等级分布
     *
     * @param teamId 团队ID
     * @param skillType 能力类型
     * @return 等级分布
     */
    List<Map<String, Object>> selectSkillLevelDistribution(@Param("teamId") Long teamId,
                                                           @Param("skillType") String skillType);
}
