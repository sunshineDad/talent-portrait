package com.cgn.talent.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cgn.talent.core.entity.PersonInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 人员基础信息Mapper接口
 *
 * @author CGN
 * @date 2024-01-10
 */
@Mapper
public interface PersonInfoMapper extends BaseMapper<PersonInfo> {

    /**
     * 分页查询人员列表（关联团队信息）
     *
     * @param page 分页对象
     * @param personInfo 查询条件
     * @return 人员列表
     */
    IPage<PersonInfo> selectPersonPage(Page<PersonInfo> page,
                                       @Param("person") PersonInfo personInfo);

    /**
     * 查询人员详细信息（包含所有子表数据）
     *
     * @param personId 人员ID
     * @return 人员信息
     */
    PersonInfo selectPersonDetailById(@Param("personId") Long personId);

    /**
     * 校验人员编号是否唯一
     *
     * @param personCode 人员编号
     * @param personId 人员ID（排除自己）
     * @return 结果
     */
    int checkPersonCodeUnique(@Param("personCode") String personCode,
                              @Param("personId") Long personId);

    /**
     * 校验身份证号是否唯一
     *
     * @param idCard 身份证号
     * @param personId 人员ID（排除自己）
     * @return 结果
     */
    int checkIdCardUnique(@Param("idCard") String idCard,
                          @Param("personId") Long personId);

    /**
     * 批量更新人员状态
     *
     * @param ids 人员ID数组
     * @param status 状态
     * @return 结果
     */
    int updatePersonStatusByIds(@Param("ids") Long[] ids,
                                @Param("status") String status);

    /**
     * 统计人员基础信息
     *
     * @param teamId 团队ID（可选）
     * @return 统计结果
     */
    Map<String, Object> selectPersonStatistics(@Param("teamId") Long teamId);

    /**
     * 查询团队下的所有人员
     *
     * @param teamId 团队ID
     * @return 人员列表
     */
    List<PersonInfo> selectPersonByTeamId(@Param("teamId") Long teamId);
}
