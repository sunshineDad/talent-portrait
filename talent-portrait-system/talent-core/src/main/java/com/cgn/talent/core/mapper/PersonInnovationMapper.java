package com.cgn.talent.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgn.talent.core.entity.PersonInnovation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 人员创新产出Mapper接口
 *
 * @author CGN
 * @date 2024-01-10
 */
@Mapper
public interface PersonInnovationMapper extends BaseMapper<PersonInnovation> {

    /**
     * 根据人员ID查询创新产出
     *
     * @param personId 人员ID
     * @return 创新产出列表
     */
    List<PersonInnovation> selectInnovationByPersonId(@Param("personId") Long personId);

    /**
     * 按年度统计创新产出
     *
     * @param personId 人员ID
     * @return 年度统计结果
     */
    List<Map<String, Object>> selectInnovationByYear(@Param("personId") Long personId);

    /**
     * 批量插入创新产出
     *
     * @param innovationList 创新产出列表
     * @return 结果
     */
    int batchInsertInnovation(@Param("list") List<PersonInnovation> innovationList);

    /**
     * 统计团队创新产出
     *
     * @param teamId 团队ID
     * @param year 年度（可选）
     * @return 创新产出统计
     */
    List<Map<String, Object>> selectTeamInnovationStatistics(@Param("teamId") Long teamId,
                                                             @Param("year") Integer year);

    /**
     * 查询高价值创新成果
     *
     * @param teamId 团队ID
     * @param level 成果级别
     * @return 高价值成果列表
     */
    List<PersonInnovation> selectHighValueInnovations(@Param("teamId") Long teamId,
                                                      @Param("level") String level);
}
