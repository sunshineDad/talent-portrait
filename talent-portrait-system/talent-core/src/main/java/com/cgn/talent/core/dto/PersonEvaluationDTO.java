package com.cgn.talent.core.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 人员人才评估DTO
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
public class PersonEvaluationDTO implements Serializable {

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
     * 评估年度
     */
    @NotNull(message = "评估年度不能为空")
    private Integer evaluationYear;

    /**
     * 评估维度
     */
    @NotBlank(message = "评估维度不能为空")
    private String evaluationDimension;

    /**
     * 维度得分
     */
    @NotNull(message = "维度得分不能为空")
    private Double dimensionScore;

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
    private Date evaluationDate;
}
