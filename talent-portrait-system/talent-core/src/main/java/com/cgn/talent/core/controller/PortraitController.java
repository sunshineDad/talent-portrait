package com.cgn.talent.core.controller;

import com.cgn.talent.common.result.Result;
import com.cgn.talent.core.service.IPortraitService;
import com.cgn.talent.core.vo.PersonPortraitVO;
import com.cgn.talent.core.vo.TeamPortraitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 人才画像Controller
 * @Author: OpenHands
 * @Date: 2025-08-03
 */
@RestController
@RequestMapping("/core/portrait")
public class PortraitController {

    @Autowired
    private IPortraitService portraitService;

    /**
     * 获取人员画像
     */
    @GetMapping("/person/{personId}")
    public Result<PersonPortraitVO> getPersonPortrait(@PathVariable Long personId) {
        PersonPortraitVO portrait = portraitService.getPersonPortrait(personId);
        return Result.success(portrait);
    }

    /**
     * 获取团队画像
     */
    @GetMapping("/team/{teamId}")
    public Result<TeamPortraitVO> getTeamPortrait(@PathVariable Long teamId) {
        TeamPortraitVO portrait = portraitService.getTeamPortrait(teamId);
        return Result.success(portrait);
    }
}