package com.cgn.talent.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cgn.talent.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 人员人才评估实体
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("person_evaluation")
public class PersonEvaluation extends BaseEntity {

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
     * 评估年度
     */
    @NotNull(message = "评估年度不能为空")
    @Min(value = 2000, message = "评估年度不能小于2000")
    @Max(value = 2100, message = "评估年度不能大于2100")
    private Integer evaluationYear;

    /**
     * 评估维度（参考字典：evaluation_dimension）
     */
    @NotBlank(message = "评估维度不能为空")
    private String evaluationDimension;

    /**
     * 维度得分
     */
    private BigDecimal dimensionScore;

    /**
     * 维度等级
     */
    private String dimensionLevel;

    /**
     * 评估内容
     */
    private String evaluationContent;

    /**
     * 优势分析
     */
    private String strengths;

    /**
     * 劣势分析
     */
    private String weaknesses;

    /**
     * 发展建议
     */
    private String developmentSuggestions;

    /**
     * 评估人
     */
    private String evaluator;

    /**
     * 评估日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date evaluationDate;

    // ========== 非数据库字段 ==========

    /**
     * 评估维度名称
     */
    @TableField(exist = false)
    private String evaluationDimensionName;

    /**
     * 得分百分比（相对于满分100）
     */
    @TableField(exist = false)
    private BigDecimal scorePercentage;
}
