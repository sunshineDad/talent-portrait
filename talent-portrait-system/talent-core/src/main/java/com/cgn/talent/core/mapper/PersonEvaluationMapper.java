package com.cgn.talent.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgn.talent.core.entity.PersonEvaluation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 人员人才评估Mapper接口
 *
 * @author CGN
 * @date 2024-01-10
 */
@Mapper
public interface PersonEvaluationMapper extends BaseMapper<PersonEvaluation> {

    /**
     * 根据人员ID查询评估记录
     *
     * @param personId 人员ID
     * @return 评估列表
     */
    List<PersonEvaluation> selectEvaluationByPersonId(@Param("personId") Long personId);

    /**
     * 查询人员某年度的评估记录
     *
     * @param personId 人员ID
     * @param year 年度
     * @return 评估记录列表
     */
    List<PersonEvaluation> selectEvaluationByPersonAndYear(@Param("personId") Long personId,
                                                           @Param("year") Integer year);

    /**
     * 批量插入评估记录
     *
     * @param evaluationList 评估列表
     * @return 结果
     */
    int batchInsertEvaluation(@Param("list") List<PersonEvaluation> evaluationList);

    /**
     * 查询人员评估雷达图数据
     *
     * @param personId 人员ID
     * @param year 年度
     * @return 雷达图数据
     */
    List<Map<String, Object>> selectEvaluationRadarData(@Param("personId") Long personId,
                                                        @Param("year") Integer year);

    /**
     * 统计团队人才评估情况
     *
     * @param teamId 团队ID
     * @param year 年度
     * @return 团队评估统计
     */
    List<Map<String, Object>> selectTeamEvaluationStatistics(@Param("teamId") Long teamId,
                                                             @Param("year") Integer year);
}
