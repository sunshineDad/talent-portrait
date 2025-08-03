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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 人员绩效信息实体
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("person_performance")
public class PersonPerformance extends BaseEntity {

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
     * 年度
     */
    @NotNull(message = "年度不能为空")
    @Min(value = 2000, message = "年度不能小于2000")
    @Max(value = 2100, message = "年度不能大于2100")
    private Integer year;

    /**
     * 季度（1-4）
     */
    @Min(value = 1, message = "季度最小值为1")
    @Max(value = 4, message = "季度最大值为4")
    private Integer quarter;

    /**
     * 绩效分数
     */
    private BigDecimal performanceScore;

    /**
     * 绩效等级（参考字典：performance_level）
     */
    private String performanceLevel;

    /**
     * KPI完成率（%）
     */
    private BigDecimal kpiCompletion;

    /**
     * 工作质量评价
     */
    private String workQuality;

    /**
     * 工作态度评价
     */
    private String workAttitude;

    /**
     * 团队贡献评价
     */
    private String teamContribution;

    /**
     * 改进建议
     */
    private String improvementSuggestions;

    /**
     * 评价人
     */
    private String evaluator;

    /**
     * 评价日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date evaluationDate;

    // ========== 非数据库字段 ==========

    /**
     * 绩效等级名称
     */
    @TableField(exist = false)
    private String performanceLevelName;

    /**
     * 期间描述（如：2024年第1季度）
     */
    @TableField(exist = false)
    private String periodDesc;
}
