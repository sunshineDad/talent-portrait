package com.cgn.talent.core.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 人员信息DTO
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
public class PersonInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 人员ID
     */
    private Long id;

    /**
     * 人员编号
     */
    @NotBlank(message = "人员编号不能为空")
    private String personCode;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String personName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 出生日期
     */
    private Date birthDate;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 学历
     */
    private String education;

    /**
     * 学位
     */
    private String degree;

    /**
     * 毕业院校
     */
    private String graduateSchool;

    /**
     * 专业
     */
    private String major;

    /**
     * 参加工作时间
     */
    private Date workStartDate;

    /**
     * 入职日期
     */
    private Date joinDate;

    /**
     * 职称
     */
    private String jobTitle;

    /**
     * 职级
     */
    private String jobLevel;

    /**
     * 职务
     */
    private String position;

    /**
     * 所属团队ID
     */
    @NotNull(message = "所属团队不能为空")
    private Long teamId;

    /**
     * 工作地点
     */
    private String workLocation;

    /**
     * 用工类型
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
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    // 子表数据
    /**
     * 人才信息维护列表
     */
    private List<PersonMaintenanceDTO> maintenanceList;

    /**
     * 人才技能列表
     */
    private List<PersonSkillDTO> skillList;
    private List<PersonProjectDTO> projectList;
    private List<PersonPerformanceDTO> performanceList;
    private List<PersonInfluenceDTO> influenceList;
    private List<PersonInnovationDTO> innovationList;
    private List<PersonEvaluationDTO> evaluationList;
}
