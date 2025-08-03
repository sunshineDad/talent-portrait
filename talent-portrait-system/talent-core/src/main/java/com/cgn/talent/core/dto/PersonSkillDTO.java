package com.cgn.talent.core.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 人员专业能力DTO
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
public class PersonSkillDTO implements Serializable {

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
     * 技能类型
     */
    @NotBlank(message = "技能类型不能为空")
    private String skillType;

    /**
     * 技能名称
     */
    @NotBlank(message = "技能名称不能为空")
    private String skillName;

    /**
     * 技能等级
     */
    @NotBlank(message = "技能等级不能为空")
    private String skillLevel;

    /**
     * 经验年限
     */
    @NotNull(message = "经验年限不能为空")
    private Integer experienceYears;

    /**
     * 相关认证
     */
    private String certification;

    /**
     * 描述说明
     */
    private String description;
}
