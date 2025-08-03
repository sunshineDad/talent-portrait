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
import java.util.Date;

/**
 * 人员影响力信息实体
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("person_influence")
public class PersonInfluence {

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
     * 影响力类型（参考字典：influence_type）
     */
    @NotBlank(message = "影响力类型不能为空")
    private String influenceType;

    /**
     * 影响力名称（如：职务名称、委员会名称等）
     */
    @NotBlank(message = "影响力名称不能为空")
    @Size(max = 200, message = "影响力名称长度不能超过200个字符")
    private String influenceName;

    /**
     * 组织/机构名称
     */
    @Size(max = 200, message = "组织名称长度不能超过200个字符")
    private String organization;

    /**
     * 职位级别
     */
    private String positionLevel;

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
     * 是否在任（0否 1是）
     */
    private String isCurrent;

    /**
     * 影响范围（参考字典：influence_scope）
     */
    private String influenceScope;

    /**
     * 职责描述
     */
    private String description;
    
    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private java.util.Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private java.util.Date updateTime;

    // ========== 非数据库字段 ==========

    /**
     * 影响力类型名称
     */
    @TableField(exist = false)
    private String influenceTypeName;

    /**
     * 影响范围名称
     */
    @TableField(exist = false)
    private String influenceScopeName;

    /**
     * 任职时长（月）
     */
    @TableField(exist = false)
    private Integer tenureMonths;
}
