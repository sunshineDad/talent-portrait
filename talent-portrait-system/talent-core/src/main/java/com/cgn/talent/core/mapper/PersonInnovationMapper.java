package com.cgn.talent.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgn.talent.core.entity.PersonInnovation;
import org.apache.ibatis.annotations.MapKey;
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
     * 根据人员编号查询创新产出
     */
    List<PersonInnovation> selectInnovationByPersonCode(@Param("personCode") String personCode);

    /**
     * 批量插入创新产出
     */
    int batchInsertInnovation(List<PersonInnovation> list);

    /**
     * 删除人员的所有创新产出
     */
    int deleteInnovationByPersonCode(@Param("personCode") String personCode);

    /**
     * 按类型统计创新产出
     */
    @MapKey("person_code")
    List<Map<String, Object>> selectInnovationStatisticsByType(@Param("personCode") String personCode);

    /**
     * 查询团队创新产出统计
     */
    @MapKey("team_code")
    List<Map<String, Object>> selectTeamInnovationStatistics(@Param("teamCode") String teamCode,
                                                             @Param("year") Integer year);

    /**
     * 查询创新产出排名
     */
    @MapKey("team_code")
    List<Map<String, Object>> selectInnovationRanking(@Param("teamCode") String teamCode,
                                                      @Param("innovationType") String innovationType,
                                                      @Param("limit") Integer limit);

    /**
     * 统计个人创新产出得分
     */
    @MapKey("person_code")
    Map<String, Object> selectPersonInnovationScore(@Param("personCode") String personCode);
}
