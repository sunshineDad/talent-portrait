package com.cgn.talent.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgn.talent.system.entity.TeamInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 团队信息Mapper接口
 *
 * @author CGN
 * @date 2024-01-10
 */
@Mapper
public interface TeamInfoMapper extends BaseMapper<TeamInfo> {

    /**
     * 查询团队树形结构列表
     *
     * @param teamInfo 查询条件
     * @return 团队列表
     */
    List<TeamInfo> selectTeamTree(TeamInfo teamInfo);

    /**
     * 根据团队ID查询子团队数量
     *
     * @param teamId 团队ID
     * @return 子团队数量
     */
    int selectChildrenCountById(@Param("teamId") Long teamId);

    /**
     * 根据团队ID查询所有子团队ID
     *
     * @param teamId 团队ID
     * @return 子团队ID列表
     */
    List<Long> selectChildrenIdsById(@Param("teamId") Long teamId);

    /**
     * 修改子团队的祖级列表
     *
     * @param children 子团队列表
     * @param ancestors 祖级列表
     * @return 结果
     */
    int updateTeamAncestors(@Param("children") List<TeamInfo> children,
                            @Param("ancestors") String ancestors);

    /**
     * 查询团队下的人员数量
     *
     * @param teamId 团队ID
     * @return 人员数量
     */
    int selectTeamMemberCount(@Param("teamId") Long teamId);

    /**
     * 校验团队编码是否唯一
     *
     * @param teamCode 团队编码
     * @param teamId 团队ID（排除自己）
     * @return 结果
     */
    int checkTeamCodeUnique(@Param("teamCode") String teamCode,
                            @Param("teamId") Long teamId);
}
