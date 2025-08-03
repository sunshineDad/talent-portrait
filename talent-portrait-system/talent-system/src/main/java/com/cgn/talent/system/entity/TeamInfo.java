package com.cgn.talent.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cgn.talent.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * 团队信息实体
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("team_info")
public class TeamInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 团队ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父团队ID
     */
    private Long parentId;

    /**
     * 团队编码
     */
    @NotBlank(message = "团队编码不能为空")
    @Size(max = 50, message = "团队编码长度不能超过50个字符")
    private String teamCode;

    /**
     * 团队名称
     */
    @NotBlank(message = "团队名称不能为空")
    @Size(max = 100, message = "团队名称长度不能超过100个字符")
    private String teamName;

    /**
     * 团队类型（参考字典：team_type）
     */
    @NotBlank(message = "团队类型不能为空")
    private String teamType;

    /**
     * 团队负责人ID
     */
    private Long leaderId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0存在 1删除）
     */
    private String delFlag;

    /**
     * 子团队列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<TeamInfo> children = new ArrayList<>();

    /**
     * 负责人姓名（非数据库字段）
     */
    @TableField(exist = false)
    private String leaderName;

    /**
     * 团队人数（非数据库字段）
     */
    @TableField(exist = false)
    private Integer memberCount;
}
