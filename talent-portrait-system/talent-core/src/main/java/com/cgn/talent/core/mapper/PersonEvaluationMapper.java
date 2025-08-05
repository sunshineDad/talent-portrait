package com.cgn.talent.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgn.talent.core.entity.PersonEvaluation;
import org.apache.ibatis.annotations.MapKey;
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
     * 根据人员编号查询评估信息
     */
    List<PersonEvaluation> selectEvaluationByPersonCode(@Param("personCode") String personCode);

    /**
     * 批量插入评估信息
     */
    int batchInsertEvaluation(List<PersonEvaluation> list);

    /**
     * 删除人员的所有评估信息
     */
    int deleteEvaluationByPersonCode(@Param("personCode") String personCode);

    /**
     * 查询人员指定年度的评估信息
     */
    List<PersonEvaluation> selectEvaluationByPersonAndYear(@Param("personCode") String personCode,
                                                           @Param("year") Integer year);

    /**
     * 查询评估雷达图数据
     */
    @MapKey("person_code")
    List<Map<String, Object>> selectEvaluationRadarData(@Param("personCode") String personCode,
                                                        @Param("year") Integer year);

    /**
     * 查询团队评估对比数据
     */
    @MapKey("team_code")
    List<Map<String, Object>> selectTeamEvaluationComparison(@Param("teamCode") String teamCode,
                                                             @Param("year") Integer year,
                                                             @Param("dimensionLevel") String dimensionLevel);

    /**
     * 删除指定年度的评估信息
     */
    int deleteEvaluationByPersonAndYear(@Param("personCode") String personCode,
                                        @Param("year") Integer year);

    /**
     * 查询评估维度树形结构
     */
    @MapKey("person_code")
    List<Map<String, Object>> selectEvaluationDimensionTree(@Param("personCode") String personCode,
                                                            @Param("year") Integer year);
}
