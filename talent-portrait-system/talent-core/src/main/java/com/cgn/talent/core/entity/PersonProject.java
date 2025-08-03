package com.cgn.talent.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cgn.talent.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 人员项目经历实体
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("person_project")
public class PersonProject extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 人员ID
     */
    @NotNull(message = "人员ID不能为空")
    private Long personId;

    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空")
    @Size(max = 200, message = "项目名称长度不能超过200个字符")
    private String projectName;

    /**
     * 项目编号
     */
    @Size(max = 100, message = "项目编号长度不能超过100个字符")
    private String projectCode;

    /**
     * 项目类型（参考字典：project_type）
     */
    private String projectType;

    /**
     * 项目级别（参考字典：project_level）
     */
    private String projectLevel;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    /**
     * 项目角色（参考字典：project_role）
     */
    private String projectRole;

    /**
     * 项目状态（参考字典：project_status）
     */
    private String projectStatus;

    /**
     * 主要成果
     */
    private String achievement;

    /**
     * 技术栈
     */
    @Size(max = 500, message = "技术栈长度不能超过500个字符")
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

    // ========== 非数据库字段 ==========

    /**
     * 项目类型名称
     */
    @TableField(exist = false)
    private String projectTypeName;

    /**
     * 项目级别名称
     */
    @TableField(exist = false)
    private String projectLevelName;

    /**
     * 项目角色名称
     */
    @TableField(exist = false)
    private String projectRoleName;

    /**
     * 项目状态名称
     */
    @TableField(exist = false)
    private String projectStatusName;

    /**
     * 项目周期（月）
     */
    @TableField(exist = false)
    private Integer projectDuration;
}
