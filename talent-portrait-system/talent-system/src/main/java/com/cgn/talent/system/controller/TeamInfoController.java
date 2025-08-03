package com.cgn.talent.system.controller;

import com.cgn.talent.common.result.Result;
import com.cgn.talent.system.entity.TeamInfo;
import com.cgn.talent.system.service.ITeamInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 团队信息Controller
 *
 * @author CGN
 * @date 2024-01-10
 */
@Slf4j
@Api(tags = "团队管理")
@RestController
@RequestMapping("/system/team")
public class TeamInfoController {

    @Autowired
    private ITeamInfoService teamInfoService;

    /**
     * 查询团队树形列表
     */
    @ApiOperation("查询团队树形列表")
    @GetMapping("/treeList")
    public Result<List<TeamInfo>> treeList(TeamInfo teamInfo) {
        List<TeamInfo> teams = teamInfoService.selectTeamTree(teamInfo);
        return Result.success(teams);
    }

    /**
     * 查询团队列表（不分页）
     */
    @ApiOperation("查询团队列表")
    @GetMapping("/list")
    public Result<List<TeamInfo>> list(TeamInfo teamInfo) {
        List<TeamInfo> teams = teamInfoService.list();
        return Result.success(teams);
    }

    /**
     * 获取团队详细信息
     */
    @ApiOperation("获取团队详细信息")
    @GetMapping("/{teamId}")
    public Result<TeamInfo> getInfo(@PathVariable Long teamId) {
        TeamInfo teamInfo = teamInfoService.selectTeamById(teamId);
        return Result.success(teamInfo);
    }

    /**
     * 新增团队
     */
    @ApiOperation("新增团队")
    @PostMapping
    public Result<Void> add(@Validated @RequestBody TeamInfo teamInfo) {
        try {
            teamInfoService.insertTeam(teamInfo);
            return Result.success("新增成功");
        } catch (Exception e) {
            log.error("新增团队失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 修改团队
     */
    @ApiOperation("修改团队")
    @PutMapping
    public Result<Void> edit(@Validated @RequestBody TeamInfo teamInfo) {
        try {
            teamInfoService.updateTeam(teamInfo);
            return Result.success("修改成功");
        } catch (Exception e) {
            log.error("修改团队失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除团队
     */
    @ApiOperation("删除团队")
    @DeleteMapping("/{teamId}")
    public Result<Void> remove(@PathVariable Long teamId) {
        try {
            teamInfoService.deleteTeamById(teamId);
            return Result.success("删除成功");
        } catch (Exception e) {
            log.error("删除团队失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询团队下拉树结构
     */
    @ApiOperation("查询团队下拉树结构")
    @GetMapping("/treeSelect")
    public Result<List<TeamInfo>> treeSelect() {
        List<TeamInfo> teams = teamInfoService.selectTeamTree(new TeamInfo());
        return Result.success(teams);
    }
}
