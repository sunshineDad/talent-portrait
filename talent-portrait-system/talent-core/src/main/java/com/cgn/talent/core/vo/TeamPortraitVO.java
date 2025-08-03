package com.cgn.talent.core.vo;

import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 团队画像VO
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
public class TeamPortraitVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 团队基本信息
     */
    private TeamBasicInfo basicInfo;

    /**
     * 能力九宫格数据
     */
    private AbilityGrid abilityGrid;

    /**
     * 专业能力分布
     */
    private SkillDistribution skillDistribution;

    /**
     * 人才结构数据
     */
    private TalentStructure talentStructure;

    /**
     * 队伍结构数据
     */
    private TeamStructure teamStructure;

    /**
     * 产出贡献数据
     */
    private ContributionData contributionData;

    @Data
    public static class TeamBasicInfo {
        private Long teamId;
        private String teamCode;
        private String teamName;
        private String teamType;
        private String leaderName;
        private Integer memberCount;
        private Integer subTeamCount;
    }

    @Data
    public static class AbilityGrid {
        private List<GridItem> items;
        private GridStatistics statistics;

        @Data
        public static class GridItem {
            private Long personId;
            private String personName;
            private Double performance;  // X轴：绩效
            private Double potential;    // Y轴：潜力
            private String quadrant;     // 所在象限
            private String color;        // 显示颜色
        }

        @Data
        public static class GridStatistics {
            private Integer highPerformanceHighPotential;  // 明星员工
            private Integer highPerformanceLowPotential;   // 核心骨干
            private Integer lowPerformanceHighPotential;   // 潜力新星
            private Integer lowPerformanceLowPotential;    // 待提升
        }
    }

    @Data
    public static class SkillDistribution {
        private List<SkillCategory> categories;

        @Data
        public static class SkillCategory {
            private String categoryName;
            private String categoryType;
            private Integer personCount;
            private Double avgExperience;
            private List<SkillDetail> details;
        }

        @Data
        public static class SkillDetail {
            private String skillName;
            private Integer count;
            private List<String> persons;
        }
    }

    @Data
    public static class TalentStructure {
        private List<PieItem> educationDistribution;
        private List<PieItem> titleDistribution;
        private List<PieItem> ageDistribution;
        private List<PieItem> workYearsDistribution;

        @Data
        public static class PieItem {
            private String name;
            private Integer value;
            private Double percentage;
        }
    }

    @Data
    public static class TeamStructure {
        private List<BarItem> levelDistribution;
        private List<BarItem> positionDistribution;

        @Data
        public static class BarItem {
            private String category;
            private Integer maleCount;
            private Integer femaleCount;
            private Integer totalCount;
        }
    }

    @Data
    public static class ContributionData {
        private List<Integer> years;
        private List<ContributionItem> innovations;
        private List<ContributionItem> projects;
        private List<ContributionItem> performance;

        @Data
        public static class ContributionItem {
            private String type;
            private List<Double> values;  // 对应年份的值
            private Double total;
        }
    }
}
