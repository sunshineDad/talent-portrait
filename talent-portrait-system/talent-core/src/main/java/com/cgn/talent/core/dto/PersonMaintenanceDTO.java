package com.cgn.talent.core.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 人才信息维护DTO
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
public class PersonMaintenanceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 人员ID
     */
    private Long personId;

    /**
     * 人才工程
     */
    @NotBlank(message = "人才工程不能为空")
    private String talentProgram;

    /**
     * 人才计划
     */
    @NotBlank(message = "人才计划不能为空")
    private String talentPlan;

    /**
     * 工程级别
     */
    @NotBlank(message = "工程级别不能为空")
    private String programLevel;

    /**
     * 入选时间
     */
    @NotNull(message = "入选时间不能为空")
    private Date selectedDate;

    /**
     * 有效期开始
     */
    @NotNull(message = "有效期开始不能为空")
    private Date validStartDate;

    /**
     * 有效期结束
     */
    @NotNull(message = "有效期结束不能为空")
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
