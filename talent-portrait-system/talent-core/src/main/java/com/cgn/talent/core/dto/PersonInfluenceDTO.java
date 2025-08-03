package com.cgn.talent.core.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 人员影响力信息DTO
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
public class PersonInfluenceDTO implements Serializable {

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
     * 影响力类型
     */
    @NotBlank(message = "影响力类型不能为空")
    private String influenceType;

    /**
     * 职务/头衔名称
     */
    @NotBlank(message = "职务/头衔名称不能为空")
    private String influenceName;

    /**
     * 组织机构
     */
    @NotBlank(message = "组织机构不能为空")
    private String organization;

    /**
     * 职务级别
     */
    private String positionLevel;

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 是否在任
     */
    private String isCurrent;

    /**
     * 影响力范围
     */
    private String influenceScope;

    /**
     * 职责描述
     */
    private String description;
}
