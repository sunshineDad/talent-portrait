package com.cgn.talent.core.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 人员项目经历DTO
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
public class PersonProjectDTO implements Serializable {

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
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空")
    private String projectName;

    /**
     * 项目编号
     */
    private String projectCode;

    /**
     * 项目类型
     */
    @NotBlank(message = "项目类型不能为空")
    private String projectType;

    /**
     * 项目级别
     */
    @NotBlank(message = "项目级别不能为空")
    private String projectLevel;

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 项目角色
     */
    @NotBlank(message = "项目角色不能为空")
    private String projectRole;

    /**
     * 项目状态
     */
    private String projectStatus;

    /**
     * 项目成果
     */
    private String achievement;

    /**
     * 技术栈
     */
    private String technologyStack;

    /**
     * 团队规模
     */
    private Integer teamSize;

    /**
     * 项目预算（万元）
     */
    private BigDecimal projectBudget;

    /**
     * 项目描述
     */
    private String description;
}
