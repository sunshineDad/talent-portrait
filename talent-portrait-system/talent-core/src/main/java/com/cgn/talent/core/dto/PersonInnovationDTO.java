package com.cgn.talent.core.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 人员创新产出DTO
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
public class PersonInnovationDTO implements Serializable {

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
     * 创新类型
     */
    @NotBlank(message = "创新类型不能为空")
    private String innovationType;

    /**
     * 创新成果名称
     */
    @NotBlank(message = "创新成果名称不能为空")
    private String innovationName;

    /**
     * 成果级别
     */
    @NotBlank(message = "成果级别不能为空")
    private String innovationLevel;

    /**
     * 完成日期
     */
    @NotNull(message = "完成日期不能为空")
    private Date completeDate;

    /**
     * 专利号/论文号
     */
    private String patentNo;

    /**
     * 作者列表
     */
    private String authors;

    /**
     * 作者排序
     */
    private Integer authorOrder;

    /**
     * 发表刊物/会议
     */
    private String publication;

    /**
     * 成果状态
     */
    private String innovationStatus;

    /**
     * 经济效益（万元）
     */
    private BigDecimal economicBenefit;

    /**
     * 社会效益描述
     */
    private String socialBenefit;

    /**
     * 成果描述
     */
    private String description;

    /**
     * 附件URL
     */
    private String attachmentUrl;
}
