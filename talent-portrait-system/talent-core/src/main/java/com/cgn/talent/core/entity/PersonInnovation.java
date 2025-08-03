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
 * 人员创新产出实体
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("person_innovation")
public class PersonInnovation {

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
     * 创新类型（参考字典：innovation_type）
     */
    @NotBlank(message = "创新类型不能为空")
    private String innovationType;

    /**
     * 创新成果名称
     */
    @NotBlank(message = "创新成果名称不能为空")
    @Size(max = 200, message = "创新成果名称长度不能超过200个字符")
    private String innovationName;

    /**
     * 成果级别（参考字典：innovation_level）
     */
    private String innovationLevel;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date completeDate;

    /**
     * 专利号/论文号/标准号
     */
    @Size(max = 100, message = "编号长度不能超过100个字符")
    private String patentNo;

    /**
     * 作者/发明人
     */
    @Size(max = 500, message = "作者信息长度不能超过500个字符")
    private String authors;

    /**
     * 作者排序
     */
    private Integer authorOrder;

    /**
     * 发表刊物/授权机构
     */
    @Size(max = 200, message = "发表刊物长度不能超过200个字符")
    private String publication;

    /**
     * 成果状态（参考字典：innovation_status）
     */
    private String innovationStatus;

    /**
     * 经济效益（万元）
     */
    private BigDecimal economicBenefit;

    /**
     * 社会效益
     */
    private String socialBenefit;

    /**
     * 成果描述
     */
    private String description;

    /**
     * 附件URL
     */
    private String attachmentUrl;
    
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
     * 创新类型名称
     */
    @TableField(exist = false)
    private String innovationTypeName;

    /**
     * 成果级别名称
     */
    @TableField(exist = false)
    private String innovationLevelName;

    /**
     * 成果状态名称
     */
    @TableField(exist = false)
    private String innovationStatusName;

    /**
     * 年份
     */
    @TableField(exist = false)
    private Integer year;
}
