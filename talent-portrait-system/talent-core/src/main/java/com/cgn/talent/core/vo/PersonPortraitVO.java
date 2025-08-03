package com.cgn.talent.core.vo;

import lombok.Data;
import java.util.List;

/**
 * 人员画像VO
 * @Author: OpenHands
 * @Date: 2025-08-03
 */
@Data
public class PersonPortraitVO {
    
    /**
     * 基本信息
     */
    private BasicInfo basicInfo;
    
    /**
     * 人才信息
     */
    private List<String> talentInfo;
    
    /**
     * 能力矩阵
     */
    private AbilityMatrix abilityMatrix;
    
    /**
     * 发展路径
     */
    private DevelopmentPath developmentPath;
    
    /**
     * 绩效数据
     */
    private PerformanceData performanceData;
    
    /**
     * 创新项目
     */
    private List<InnovationProject> innovationProjects;
    
    /**
     * 分析文本
     */
    private String analysisText;
    
    @Data
    public static class BasicInfo {
        private String personName;
        private String personCode;
        private String position;
        private String jobTitle;
        private String photoUrl;
        private String gender;
        private Integer age;
        private String ethnicity;
        private String education;
        private String hometown;
        private String politicalStatus;
        private String graduateSchool;
        private Integer companyYears;
    }
    
    @Data
    public static class AbilityMatrix {
        private List<AbilityGroup> groups;
    }
    
    @Data
    public static class AbilityGroup {
        private String groupName;
        private List<AbilityItem> items;
    }
    
    @Data
    public static class AbilityItem {
        private String name;
        private String level;
    }
    
    @Data
    public static class DevelopmentPath {
        private List<PathNode> nodes;
    }
    
    @Data
    public static class PathNode {
        private String date;
        private String position;
        private String description;
    }
    
    @Data
    public static class PerformanceData {
        private List<PerformanceItem> items;
    }
    
    @Data
    public static class PerformanceItem {
        private Integer year;
        private String level;
        private Double score;
    }
    
    @Data
    public static class InnovationProject {
        private String name;
        private String level;
        private String role;
        private String status;
    }
}

