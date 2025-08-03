package com.cgn.talent.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cgn.talent.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 人员基础信息实体
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
     * 人员ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 人员编号
     */
    @NotBlank(message = "人员编号不能为空")
    @Size(max = 50, message = "人员编号长度不能超过50个字符")
    private String personCode;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Size(max = 50, message = "姓名长度不能超过50个字符")
    private String personName;

    /**
     * 性别（1男 2女）
     */
    private String gender;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthDate;

    /**
     * 身份证号
     */
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$",
            message = "身份证号格式不正确")
    private String idCard;

    /**
     * 手机号码
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号码格式不正确")
    private String phone;

    /**
     * 电子邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    private String email;

    /**
     * 最高学历（参考字典：education）
     */
    private String education;

    /**
     * 最高学位（参考字典：degree）
     */
    private String degree;

    /**
     * 毕业院校
     */
    @Size(max = 100, message = "毕业院校长度不能超过100个字符")
    private String graduateSchool;

    /**
     * 所学专业
     */
    @Size(max = 100, message = "所学专业长度不能超过100个字符")
    private String major;

    /**
     * 参加工作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date workStartDate;

    /**
     * 入职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date joinDate;

    /**
     * 职称（参考字典：job_title）
     */
    private String jobTitle;

    /**
     * 职级（参考字典：job_level）
     */
    private String jobLevel;

    /**
     * 岗位
     */
    @Size(max = 100, message = "岗位长度不能超过100个字符")
    private String position;

    /**
     * 所属团队ID
     */
    private Long teamId;

    /**
     * 工作地点
     */
    @Size(max = 100, message = "工作地点长度不能超过100个字符")
    private String workLocation;

    /**
     * 用工类型（参考字典：employment_type）
     */
    private String employmentType;

    /**
     * 政治面貌（参考字典：political_status）
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

    // ========== 非数据库字段 ==========

    /**
     * 团队名称
     */
    @TableField(exist = false)
    private String teamName;

    /**
     * 年龄
     */
    @TableField(exist = false)
    private Integer age;

    /**
     * 工龄（年）
     */
    @TableField(exist = false)
    private Integer workYears;

    /**
     * 司龄（年）
     */
    @TableField(exist = false)
    private Integer companyYears;

    /**
     * 专业能力列表
     */
    @TableField(exist = false)
    private List<PersonSkill> skillList;

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
}
