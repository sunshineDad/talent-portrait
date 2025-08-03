package com.cgn.talent.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgn.talent.system.entity.TeamInfo;
import com.cgn.talent.system.mapper.TeamInfoMapper;
import com.cgn.talent.system.service.ITeamInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 团队信息Service业务层处理
 *
 * @author CGN
 * @date 2024-01-10
 */
@Slf4j
@Service
public class TeamInfoServiceImpl extends ServiceImpl<TeamInfoMapper, TeamInfo> implements ITeamInfoService {

    @Autowired
    private TeamInfoMapper teamInfoMapper;

    @Override
    public List<TeamInfo> selectTeamTree(TeamInfo teamInfo) {
        List<TeamInfo> teams = teamInfoMapper.selectTeamTree(teamInfo);
        return buildTeamTree(teams);
    }

    @Override
    public List<TeamInfo> buildTeamTree(List<TeamInfo> teams) {
        if (CollectionUtils.isEmpty(teams)) {
            return new ArrayList<>();
        }

        // 构建树形结构
        List<TeamInfo> result = new ArrayList<>();

        // 获取所有根节点
        List<TeamInfo> rootTeams = teams.stream()
                .filter(team -> team.getParentId() == null || team.getParentId() == 0L)
                .collect(Collectors.toList());

        // 递归构建子节点
        for (TeamInfo rootTeam : rootTeams) {
            buildChildren(rootTeam, teams);
            result.add(rootTeam);
        }

        return result;
    }

    /**
     * 递归构建子节点
     */
    private void buildChildren(TeamInfo parent, List<TeamInfo> allTeams) {
        List<TeamInfo> children = allTeams.stream()
                .filter(team -> parent.getId().equals(team.getParentId()))
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(children)) {
            parent.setChildren(children);

            // 递归处理子节点
            for (TeamInfo child : children) {
                buildChildren(child, allTeams);
            }
        }
    }

    @Override
    public TeamInfo selectTeamById(Long teamId) {
        TeamInfo teamInfo = getById(teamId);
        if (teamInfo != null && teamInfo.getLeaderId() != null) {
            // 可以在这里查询负责人姓名
        }
        return teamInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertTeam(TeamInfo teamInfo) {
        // 校验团队编码唯一性
        if (!checkTeamCodeUnique(teamInfo)) {
            throw new RuntimeException("团队编码已存在");
        }

        // 设置祖级列表
        if (teamInfo.getParentId() != null && teamInfo.getParentId() > 0) {
            TeamInfo parentTeam = getById(teamInfo.getParentId());
            if (parentTeam == null) {
                throw new RuntimeException("父团队不存在");
            }

            // 设置祖级列表
            String ancestors = parentTeam.getAncestors() + "," + teamInfo.getParentId();
            teamInfo.setAncestors(ancestors);
        } else {
            teamInfo.setAncestors("0");
            teamInfo.setParentId(0L);
        }

        teamInfo.setStatus("0");
        teamInfo.setDelFlag("0");

        return baseMapper.insert(teamInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateTeam(TeamInfo teamInfo) {
        // 校验团队编码唯一性
        if (!checkTeamCodeUnique(teamInfo)) {
            throw new RuntimeException("团队编码已存在");
        }

        TeamInfo oldTeam = getById(teamInfo.getId());
        if (oldTeam == null) {
            throw new RuntimeException("团队不存在");
        }

        // 如果修改了父节点，需要更新祖级列表
        if (!oldTeam.getParentId().equals(teamInfo.getParentId())) {
            // 更新祖级列表
            updateTeamAncestors(teamInfo);
        }

        return baseMapper.updateById(teamInfo);
    }

    /**
     * 更新团队祖级列表
     */
    private void updateTeamAncestors(TeamInfo teamInfo) {
        String newAncestors = "0";
        if (teamInfo.getParentId() != null && teamInfo.getParentId() > 0) {
            TeamInfo parentTeam = getById(teamInfo.getParentId());
            if (parentTeam != null) {
                newAncestors = parentTeam.getAncestors() + "," + teamInfo.getParentId();
            }
        }
        teamInfo.setAncestors(newAncestors);

        // 更新所有子团队的祖级列表
        List<TeamInfo> children = getChildrenTeams(teamInfo.getId());
        if (!CollectionUtils.isEmpty(children)) {
            teamInfoMapper.updateTeamAncestors(children, newAncestors + "," + teamInfo.getId());
        }
    }

    /**
     * 获取所有子团队
     */
    private List<TeamInfo> getChildrenTeams(Long teamId) {
        LambdaQueryWrapper<TeamInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(TeamInfo::getAncestors, "," + teamId + ",");
        return list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteTeamById(Long teamId) {
        // 检查是否有子团队
        if (hasChildTeam(teamId)) {
            throw new RuntimeException("存在子团队，不允许删除");
        }

        // 检查是否有人员
        if (hasTeamMember(teamId)) {
            throw new RuntimeException("团队下存在人员，不允许删除");
        }

        // 逻辑删除
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setId(teamId);
        teamInfo.setDelFlag("1");

        return baseMapper.updateById(teamInfo);
    }

    @Override
    public boolean hasChildTeam(Long teamId) {
        int count = teamInfoMapper.selectChildrenCountById(teamId);
        return count > 0;
    }

    @Override
    public boolean hasTeamMember(Long teamId) {
        int count = teamInfoMapper.selectTeamMemberCount(teamId);
        return count > 0;
    }

    @Override
    public boolean checkTeamCodeUnique(TeamInfo teamInfo) {
        Long teamId = teamInfo.getId() == null ? -1L : teamInfo.getId();
        int count = teamInfoMapper.checkTeamCodeUnique(teamInfo.getTeamCode(), teamId);
        return count == 0;
    }

    @Override
    public List<Long> selectChildrenIdsById(Long teamId) {
        return teamInfoMapper.selectChildrenIdsById(teamId);
    }
}
