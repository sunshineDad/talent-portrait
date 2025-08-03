package com.cgn.talent.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cgn.talent.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * 人员专业能力实体
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("person_skill")
public class PersonSkill {

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
     * 能力类型（参考字典：skill_type）
     */
    @NotBlank(message = "能力类型不能为空")
    private String skillType;

    /**
     * 能力名称
     */
    @NotBlank(message = "能力名称不能为空")
    @Size(max = 100, message = "能力名称长度不能超过100个字符")
    private String skillName;

    /**
     * 能力等级（参考字典：skill_level）
     */
    private String skillLevel;

    /**
     * 经验年限
     */
    private BigDecimal experienceYears;

    /**
     * 相关证书
     */
    @Size(max = 200, message = "相关证书长度不能超过200个字符")
    private String certification;

    /**
     * 能力描述
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
     * 能力类型名称
     */
    @TableField(exist = false)
    private String skillTypeName;

    /**
     * 能力等级名称
     */
    @TableField(exist = false)
    private String skillLevelName;
}
