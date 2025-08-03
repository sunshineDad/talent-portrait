package com.cgn.talent.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgn.talent.core.entity.PersonInfluence;
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
     * 根据人员ID查询影响力信息
     *
     * @param personId 人员ID
     * @return 影响力列表
     */
    List<PersonInfluence> selectInfluenceByPersonId(@Param("personId") Long personId);

    /**
     * 查询当前在任的影响力信息
     *
     * @param personId 人员ID
     * @return 在任影响力列表
     */
    List<PersonInfluence> selectCurrentInfluence(@Param("personId") Long personId);

    /**
     * 批量插入影响力记录
     *
     * @param influenceList 影响力列表
     * @return 结果
     */
    int batchInsertInfluence(@Param("list") List<PersonInfluence> influenceList);

    /**
     * 统计团队影响力分布
     *
     * @param teamId 团队ID
     * @return 影响力分布统计
     */
    List<Map<String, Object>> selectTeamInfluenceDistribution(@Param("teamId") Long teamId);

    /**
     * 查询高级别影响力人员
     *
     * @param teamId 团队ID
     * @param scope 影响范围（国际级、国家级等）
     * @return 人员列表
     */
    List<Map<String, Object>> selectHighLevelInfluencePerson(@Param("teamId") Long teamId,
                                                             @Param("scope") String scope);
}
