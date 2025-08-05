package com.cgn.talent.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cgn.talent.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 人员基础信息实体
 * 对应表：person_info
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("person_info")
public class PersonInfo extends BaseEntity {

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
     * 姓名
     */
    private String personName;

    /**
     * 性别（1男 2女）
     */
    private String gender;

    /**
     * 出生日期
     */
    private Date birthDate;

    /**
     * 年龄
     */
    private String age;

    /**
     * 民族
     */
    private String nationality;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 最高学历
     */
    private String education;

    /**
     * 最高学位
     */
    private String degree;

    /**
     * 毕业院校
     */
    private String graduateSchool;

    /**
     * 所学专业
     */
    private String major;

    /**
     * 工作年限
     */
    private String workYear;

    /**
     * 入职时间
     */
    private Date joinDate;

    /**
     * 职称
     */
    private String jobTitle;

    /**
     * 岗级
     */
    private String jobLevel;

    /**
     * 职务
     */
    private String position;

    /**
     * 团队编码
     */
    private String teamCode;

    /**
     * 工作地点
     */
    private String workLocation;

    /**
     * 人才标签
     */
    private String talentTag;

    /**
     * 人员类别
     */
    private String employmentType;

    /**
     * 政治面貌
     */
    private String politicalStatus;

    /**
     * 照片URL
     */
    private String photoUrl;

    /**
     * 状态（0在职 1离职）
     */
    private String status;

    /**
     * 删除标志（0存在 1删除）
     */
    private String delFlag;

    // ========== 扩展字段 ==========

    /**
     * 团队名称
     */
    @TableField(exist = false)
    private String teamName;

    /**
     * 年龄（计算值）
     */
    @TableField(exist = false)
    private Integer ageValue;

    /**
     * 工作年限（计算值）
     */
    @TableField(exist = false)
    private Integer workYears;

    /**
     * 司龄（计算值）
     */
    @TableField(exist = false)
    private Integer companyYears;

    // ========== 子表数据 ==========

    /**
     * 项目经历列表
     */
    @TableField(exist = false)
    private List<PersonProject> projectList;

    /**
     * 绩效信息列表
     */
    @TableField(exist = false)
    private List<PersonPerformance> performanceList;

    /**
     * 影响力信息列表
     */
    @TableField(exist = false)
    private List<PersonInfluence> influenceList;

    /**
     * 创新产出列表
     */
    @TableField(exist = false)
    private List<PersonInnovation> innovationList;

    /**
     * 人才评估列表
     */
    @TableField(exist = false)
    private List<PersonEvaluation> evaluationList;

    /**
     * 能力达成列表
     */
    @TableField(exist = false)
    private List<PersonCapability> capabilityList;

    /**
     * 岗级年限列表
     */
    @TableField(exist = false)
    private List<PersonPostGradeDuration> postGradeList;
}
