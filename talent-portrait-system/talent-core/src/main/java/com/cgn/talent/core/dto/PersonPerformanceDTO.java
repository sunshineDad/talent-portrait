package com.cgn.talent.core.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 人员绩效信息DTO
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
public class PersonPerformanceDTO implements Serializable {

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
     * 年度
     */
    @NotNull(message = "年度不能为空")
    private Integer year;

    /**
     * 季度
     */
    private Integer quarter;

    /**
     * 绩效得分
     */
    @NotNull(message = "绩效得分不能为空")
    private Double performanceScore;

    /**
     * 绩效等级
     */
    @NotNull(message = "绩效等级不能为空")
    private String performanceLevel;

    /**
     * KPI完成率(%)
     */
    private Double kpiCompletion;

    /**
     * 工作质量评分
     */
    private Double workQuality;

    /**
     * 工作态度评分
     */
    private Double workAttitude;

    /**
     * 团队贡献评分
     */
    private Double teamContribution;

    /**
     * 改进建议
     */
    private String improvementSuggestions;

    /**
     * 评估人
     */
    private String evaluator;

    /**
     * 评估日期
     */
    private Date evaluationDate;
}
