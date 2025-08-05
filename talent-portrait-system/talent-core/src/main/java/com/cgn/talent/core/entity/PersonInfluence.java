package com.cgn.talent.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cgn.talent.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 人员影响力信息实体
 * 对应表：person_influence
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("person_influence")
public class PersonInfluence extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 人员ID
     */
    private String id;
    /**
     * 人员工号
     */
    private String personCode;

    /**
     * 人才计划
     */
    private String talentPlan;

    /**
     * 人才工程
     */
    private String talentEngineer;

    /**
     * 组织/机构名称
     */
    private String organization;

    /**
     * 外部头衔
     */
    private String externalTitle;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 是否在任（0否 1是）
     */
    private String isCurrent;
}
