package com.cgn.talent.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cgn.talent.common.page.PageParam;
import com.cgn.talent.common.page.PageResult;
import com.cgn.talent.core.entity.PersonInfo;

import java.util.List;
import java.util.Map;

/**
 * 人员基础信息Service接口
 *
 * @author CGN
 * @date 2024-01-10
 */
public interface IPersonInfoService extends IService<PersonInfo> {

    /**
     * 分页查询人员列表
     *
     * @param pageParam 分页参数
     * @param personInfo 查询条件
     * @return 分页结果
     */
    PageResult<PersonInfo> selectPersonPage(PageParam pageParam, PersonInfo personInfo);

    /**
     * 查询人员详细信息（包含所有子表数据）
     *
     * @param personId 人员ID
     * @return 人员详细信息
     */
    PersonInfo selectPersonDetailById(Long personId);

    /**
     * 新增人员信息
     *
     * @param personInfo 人员信息
     * @return 结果
     */
    Long insertPerson(PersonInfo personInfo);

    /**
     * 修改人员信息
     *
     * @param personInfo 人员信息
     * @return 结果
     */
    int updatePerson(PersonInfo personInfo);

    /**
     * 批量删除人员
     *
     * @param personIds 人员ID数组
     * @return 结果
     */
    int deletePersonByIds(Long[] personIds);

    /**
     * 导入人员数据
     *
     * @param personList 人员列表
     * @param updateSupport 是否支持更新
     * @return 导入结果
     */
    String importPerson(List<PersonInfo> personList, boolean updateSupport);

    /**
     * 导出人员数据
     *
     * @param personInfo 查询条件
     * @return 人员列表
     */
    List<PersonInfo> exportPerson(PersonInfo personInfo);

    /**
     * 统计人员基础信息
     *
     * @param teamId 团队ID（可选）
     * @return 统计结果
     */
    Map<String, Object> selectPersonStatistics(Long teamId);

    /**
     * 校验人员编号是否唯一
     *
     * @param personInfo 人员信息
     * @return 结果
     */
    boolean checkPersonCodeUnique(PersonInfo personInfo);

    /**
     * 校验身份证号是否唯一
     *
     * @param personInfo 人员信息
     * @return 结果
     */
    boolean checkIdCardUnique(PersonInfo personInfo);

    Map<String, Object> getPersonPortrait(Long personId);

    Map<String, Object> getTeamPortrait(Long teamId);

    Map<String, Object> getPersonDevelopmentPath(Long personId);

    Map<String, Object> getPersonAbilityMatrix(Long personId);

    Map<String, Object> getTeamAbilityDistribution(Long teamId);

    Map<String, Object> getTeamStructure(Long teamId);

    Map<String, Object> getTeamContribution(Long teamId, Integer year);

    Map<String, Object> compareTeams(Long[] teamIds);
}
