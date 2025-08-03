package com.cgn.talent.core.service;

import com.cgn.talent.core.vo.PersonPortraitVO;
import com.cgn.talent.core.vo.TeamPortraitVO;

/**
 * 人才画像Service接口
 * @Author: OpenHands
 * @Date: 2025-08-03
 */
public interface IPortraitService {
    
    /**
     * 获取人员画像
     * @param personId 人员ID
     * @return 人员画像数据
     */
    PersonPortraitVO getPersonPortrait(Long personId);
    
    /**
     * 获取团队画像
     * @param teamId 团队ID
     * @return 团队画像数据
     */
    TeamPortraitVO getTeamPortrait(Long teamId);
}