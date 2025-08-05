package com.cgn.talent.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgn.talent.core.entity.PersonPerformance;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 人员绩效信息Mapper接口
 *
 * @author CGN
 * @date 2024-01-10
 */
@Mapper
public interface PersonPerformanceMapper extends BaseMapper<PersonPerformance> {

    /**
     * 根据人员编号查询绩效信息
     *
     * @param personCode 人员编号
     * @return 绩效信息列表
     */
    List<PersonPerformance> selectPerformanceByPersonCode(@Param("personCode") String personCode);

    /**
     * 批量插入绩效信息
     *
     * @param list 绩效信息列表
     * @return 影响行数
     */
    int batchInsertPerformance(List<PersonPerformance> list);

    /**
     * 删除人员的所有绩效信息
     *
     * @param personCode 人员编号
     * @return 影响行数
     */
    int deletePerformanceByPersonCode(@Param("personCode") String personCode);

    /**
     * 查询人员指定年度的绩效信息
     *
     * @param personCode 人员编号
     * @param year 年度
     * @return 绩效信息
     */
    PersonPerformance selectPerformanceByPersonAndYear(@Param("personCode") String personCode,
                                                       @Param("year") Integer year);

    /**
     * 查询团队绩效统计
     *
     * @param teamCode 团队编码
     * @param year 年度（可选）
     * @return 统计结果
     */
    @MapKey("team_code")
    List<Map<String, Object>> selectTeamPerformanceStatistics(@Param("teamCode") String teamCode,
                                                              @Param("year") Integer year);

    /**
     * 查询人员绩效趋势
     *
     * @param personCode 人员编号
     * @param startYear 开始年度
     * @param endYear 结束年度
     * @return 绩效趋势数据
     */
    @MapKey("person_code")
    List<Map<String, Object>> selectPerformanceTrend(@Param("personCode") String personCode,
                                                     @Param("startYear") Integer startYear,
                                                     @Param("endYear") Integer endYear);

    /**
     * 查询团队绩效分布
     *
     * @param teamCode 团队编码
     * @param year 年度
     * @return 绩效分布数据
     */
    @MapKey("team_code")
    List<Map<String, Object>> selectTeamPerformanceDistribution(@Param("teamCode") String teamCode,
                                                                @Param("year") Integer year);

    /**
     * 查询连续优秀人员
     *
     * @param teamCode 团队编码（可选）
     * @param continuousYears 连续年数
     * @param performanceLevel 绩效等级
     * @return 人员列表
     */
    @MapKey("team_code")
    List<Map<String, Object>> selectContinuousExcellentPersons(@Param("teamCode") String teamCode,
                                                               @Param("continuousYears") Integer continuousYears,
                                                               @Param("performanceLevel") String performanceLevel);

    /**
     * 批量删除指定年度的绩效信息
     *
     * @param personCode 人员编号
     * @param years 年度列表
     * @return 影响行数
     */
    int deletePerformanceByPersonAndYears(@Param("personCode") String personCode,
                                          @Param("years") List<Integer> years);

    /**
     * 查询绩效评价维度统计
     *
     * @param teamCode 团队编码
     * @param year 年度
     * @return 维度统计数据
     */
    @MapKey("team_code")
    Map<String, Object> selectPerformanceDimensionStatistics(@Param("teamCode") String teamCode,
                                                             @Param("year") Integer year);
}
