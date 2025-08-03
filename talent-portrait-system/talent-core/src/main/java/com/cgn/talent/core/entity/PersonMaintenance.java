package com.cgn.talent.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cgn.talent.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 人才信息维护实体
 * 对应表：t_talent_maintenance
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_talent_maintenance")
public class PersonMaintenance extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 人员ID
     */
    private Long personId;

    /**
     * 人才工程
     */
    private String talentProgram;

    /**
     * 人才计划
     */
    private String talentPlan;

    /**
     * 工程级别
     */
    private String programLevel;

    /**
     * 入选时间
     */
    private Date selectedDate;

    /**
     * 有效期开始
     */
    private Date validStartDate;

    /**
     * 有效期结束
     */
    private Date validEndDate;

    /**
     * 是否在期
     */
    private String isValid;

    /**
     * 证书编号
     */
    private String certificateNo;

    /**
     * 支持措施
     */
    private String supportMeasures;

    /**
     * 培养计划
     */
    private String trainingPlan;

    /**
     * 考核要求
     */
    private String assessmentRequirements;

    /**
     * 描述说明
     */
    private String description;
}
