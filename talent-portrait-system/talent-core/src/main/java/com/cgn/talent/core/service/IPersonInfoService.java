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
     * 根据ID查询人员详细信息（包含所有子表数据）
     *
     * @param personId 人员ID
     * @return 人员信息
     */
    PersonInfo selectPersonDetailById(Long personId);

    /**
     * 根据人员编号查询人员详细信息
     *
     * @param personCode 人员编号
     * @return 人员信息
     */
    PersonInfo selectPersonDetailByCode(String personCode);

    /**
     * 新增人员信息
     *
     * @param personInfo 人员信息
     * @return 人员ID
     */
    Long insertPerson(PersonInfo personInfo);

    /**
     * 修改人员信息
     *
     * @param personInfo 人员信息
     * @return 影响行数
     */
    int updatePerson(PersonInfo personInfo);

    /**
     * 批量删除人员信息
     *
     * @param personIds 人员ID数组
     * @return 影响行数
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
     * 统计人员信息
     *
     * @param teamCode 团队编码（可选）
     * @return 统计结果
     */
    Map<String, Object> selectPersonStatistics(String teamCode);

    /**
     * 校验人员编号是否唯一
     *
     * @param personInfo 人员信息
     * @return true-唯一 false-不唯一
     */
    boolean checkPersonCodeUnique(PersonInfo personInfo);

    /**
     * 校验身份证号是否唯一
     *
     * @param personInfo 人员信息
     * @return true-唯一 false-不唯一
     */
    boolean checkIdCardUnique(PersonInfo personInfo);

    /**
     * 根据团队编码查询人员列表
     *
     * @param teamCode 团队编码
     * @return 人员列表
     */
    List<PersonInfo> selectPersonByTeamCode(String teamCode);

    /**
     * 批量更新人员团队
     *
     * @param personIds 人员ID数组
     * @param teamCode 团队编码
     * @return 影响行数
     */
    int updatePersonTeam(Long[] personIds, String teamCode);

    /**
     * 计算人员年龄、工龄等信息
     *
     * @param personInfo 人员信息
     */
    void calculatePersonInfo(PersonInfo personInfo);

    /**
     * 获取人员能力矩阵数据
     *
     * @param personCode 人员编号
     * @return 能力矩阵数据
     */
    Map<String, Object> getPersonAbilityMatrix(String personCode);

    /**
     * 获取人员发展路径数据
     *
     * @param personCode 人员编号
     * @return 发展路径数据
     */
    Map<String, Object> getPersonDevelopmentPath(String personCode);
}
