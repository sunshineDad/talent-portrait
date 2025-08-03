package com.cgn.talent.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgn.talent.core.entity.PersonPerformance;
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
     * 根据人员ID查询绩效历史
     *
     * @param personId 人员ID
     * @return 绩效列表
     */
    List<PersonPerformance> selectPerformanceByPersonId(@Param("personId") Long personId);

    /**
     * 查询人员某年度的绩效
     *
     * @param personId 人员ID
     * @param year 年度
     * @return 绩效信息
     */
    PersonPerformance selectPerformanceByPersonAndYear(@Param("personId") Long personId,
                                                       @Param("year") Integer year);

    /**
     * 批量插入绩效记录
     *
     * @param performanceList 绩效列表
     * @return 结果
     */
    int batchInsertPerformance(@Param("list") List<PersonPerformance> performanceList);

    /**
     * 统计团队绩效分布
     *
     * @param teamId 团队ID
     * @param year 年度
     * @return 绩效分布统计
     */
    List<Map<String, Object>> selectTeamPerformanceDistribution(@Param("teamId") Long teamId,
                                                                @Param("year") Integer year);

    /**
     * 查询绩效趋势数据
     *
     * @param personId 人员ID
     * @param startYear 开始年度
     * @param endYear 结束年度
     * @return 绩效趋势
     */
    List<Map<String, Object>> selectPerformanceTrend(@Param("personId") Long personId,
                                                     @Param("startYear") Integer startYear,
                                                     @Param("endYear") Integer endYear);
}
