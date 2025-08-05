package com.cgn.talent.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cgn.talent.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 人员创新产出实体
 * 对应表：person_innovation
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("person_innovation")
public class PersonInnovation extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 人员编号
     */
    private Long id;
    /**
     * 人员编号
     */
    private String personCode;

    /**
     * 产出类型
     */
    private String innovationType;

    /**
     * 产出成果名称
     */
    private String innovationName;

    /**
     * 评价分数
     */
    private String evaluationScore;

    /**
     * 标注实际数量
     */
    private String number;

    /**
     * 标杆分数
     */
    private String benchmarkScore;
}
