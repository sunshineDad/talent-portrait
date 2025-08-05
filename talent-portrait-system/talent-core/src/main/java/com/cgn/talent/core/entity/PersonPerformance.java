package com.cgn.talent.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cgn.talent.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 人员绩效信息实体
 * 对应表：person_performance
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
     * 人员编号
     */
    private String personCode;

    /**
     * 年度
     */
    private Integer year;

    /**
     * 绩效等级
     */
    private String performanceLevel;

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
}
