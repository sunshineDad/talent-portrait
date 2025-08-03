package com.cgn.talent.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cgn.talent.system.entity.TeamInfo;

import java.util.List;

/**
 * 团队信息Service接口
 *
 * @author CGN
 * @date 2024-01-10
 */
public interface ITeamInfoService extends IService<TeamInfo> {

    /**
     * 查询团队树形结构列表
     *
     * @param teamInfo 查询条件
     * @return 团队树
     */
    List<TeamInfo> selectTeamTree(TeamInfo teamInfo);

    /**
     * 构建前端所需的树形结构
     *
     * @param teams 团队列表
     * @return 树形结构列表
     */
    List<TeamInfo> buildTeamTree(List<TeamInfo> teams);

    /**
     * 根据ID查询团队信息
     *
     * @param teamId 团队ID
     * @return 团队信息
     */
    TeamInfo selectTeamById(Long teamId);

    /**
     * 新增团队
     *
     * @param teamInfo 团队信息
     * @return 结果
     */
    int insertTeam(TeamInfo teamInfo);

    /**
     * 修改团队
     *
     * @param teamInfo 团队信息
     * @return 结果
     */
    int updateTeam(TeamInfo teamInfo);

    /**
     * 删除团队
     *
     * @param teamId 团队ID
     * @return 结果
     */
    int deleteTeamById(Long teamId);

    /**
     * 校验团队是否有子团队
     *
     * @param teamId 团队ID
     * @return 结果
     */
    boolean hasChildTeam(Long teamId);

    /**
     * 校验团队是否有人员
     *
     * @param teamId 团队ID
     * @return 结果
     */
    boolean hasTeamMember(Long teamId);

    /**
     * 校验团队编码是否唯一
     *
     * @param teamInfo 团队信息
     * @return 结果
     */
    boolean checkTeamCodeUnique(TeamInfo teamInfo);

    /**
     * 查询团队下的所有子团队ID
     *
     * @param teamId 团队ID
     * @return 子团队ID列表
     */
    List<Long> selectChildrenIdsById(Long teamId);
}
