package com.cgn.talent.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cgn.talent.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 人员人才评估实体
 * 对应表：person_evaluation
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
     * 人员编号
     */
    private String personCode;

    /**
     * 评估年度
     */
    private Integer evaluationYear;

    /**
     * 维度级别（1一级 2二级）
     */
    private String dimensionLevel;

    /**
     * 评估维度代码
     */
    private String dimensionCode;

    /**
     * 评估维度名称
     */
    private String dimensionName;

    /**
     * 父级维度代码（二级维度时必填）
     */
    private String parentDimensionCode;

    /**
     * 达标值
     */
    private BigDecimal standardValue;

    /**
     * 标杆值
     */
    private BigDecimal benchmarkValue;

    /**
     * 实际值
     */
    private BigDecimal actualValue;
}
