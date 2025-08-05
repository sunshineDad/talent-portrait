package com.cgn.talent.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cgn.talent.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 人员项目经历实体
 * 对应表：person_project
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("person_project")
public class PersonProject extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 人员编号
     */
    private String personCode;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目编号
     */
    private String projectCode;

    /**
     * 项目类型
     */
    private String projectType;

    /**
     * 项目角色
     */
    private String projectRole;

    /**
     * 项目层级别
     */
    private String projectLevel;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;
}
