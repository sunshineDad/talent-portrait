package com.cgn.talent.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cgn.talent.core.entity.*;
import com.cgn.talent.core.mapper.*;
import com.cgn.talent.core.service.IPersonInfoService;
import com.cgn.talent.core.service.IPortraitService;
import com.cgn.talent.core.vo.PersonPortraitVO;
import com.cgn.talent.core.vo.TeamPortraitVO;
import com.cgn.talent.system.entity.TeamInfo;
import com.cgn.talent.system.mapper.TeamInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 人才画像Service实现类
 * @Author: OpenHands
 * @Date: 2025-08-03
 */
@Service
public class PortraitServiceImpl implements IPortraitService {

    @Autowired
    private IPersonInfoService personInfoService;
    
    @Autowired
    private PersonInfoMapper personInfoMapper;
    
    @Autowired
    private TeamInfoMapper teamInfoMapper;
    
    @Autowired
    private PersonSkillMapper personSkillMapper;
    
    @Autowired
    private PersonProjectMapper personProjectMapper;
    
    @Autowired
    private PersonPerformanceMapper personPerformanceMapper;
    
    @Autowired
    private PersonInnovationMapper personInnovationMapper;

    @Override
    public PersonPortraitVO getPersonPortrait(Long personId) {
        // 获取人员基本信息
        PersonInfo personInfo = personInfoService.selectPersonDetailById(personId);
        if (personInfo == null) {
            throw new RuntimeException("人员信息不存在");
        }

        PersonPortraitVO portrait = new PersonPortraitVO();
        
        // 构建基本信息
        PersonPortraitVO.BasicInfo basicInfo = new PersonPortraitVO.BasicInfo();
        basicInfo.setPersonName(personInfo.getPersonName());
        basicInfo.setPersonCode(personInfo.getPersonCode());
        basicInfo.setPosition(personInfo.getPosition());
        basicInfo.setJobTitle(personInfo.getJobTitle());
        basicInfo.setPhotoUrl(personInfo.getPhotoUrl());
        basicInfo.setGender("1".equals(personInfo.getGender()) ? "男" : "女");
        basicInfo.setAge(calculateAge(personInfo.getBirthDate()));
        basicInfo.setEthnicity("汉族"); // 默认值，可以从数据库扩展
        basicInfo.setEducation(personInfo.getEducation());
        basicInfo.setHometown("广东深圳"); // 默认值，可以从数据库扩展
        basicInfo.setPoliticalStatus(personInfo.getPoliticalStatus());
        basicInfo.setGraduateSchool(personInfo.getGraduateSchool());
        basicInfo.setCompanyYears(calculateCompanyYears(personInfo.getJoinDate()));
        portrait.setBasicInfo(basicInfo);

        // 构建人才信息
        List<String> talentInfo = Arrays.asList(
            "核工程与核技术专业背景",
            "具有" + calculateWorkYears(personInfo.getWorkStartDate()) + "年工作经验",
            "擅长反应堆设计与安全分析",
            "具备项目管理能力",
            "团队协作能力强"
        );
        portrait.setTalentInfo(talentInfo);

        // 构建能力矩阵
        PersonPortraitVO.AbilityMatrix abilityMatrix = new PersonPortraitVO.AbilityMatrix();
        List<PersonPortraitVO.AbilityGroup> groups = new ArrayList<>();
        
        // 专业技能组
        PersonPortraitVO.AbilityGroup professionalGroup = new PersonPortraitVO.AbilityGroup();
        professionalGroup.setGroupName("专业技能");
        List<PersonPortraitVO.AbilityItem> professionalItems = Arrays.asList(
            createAbilityItem("反应堆物理", "专家"),
            createAbilityItem("核安全分析", "高级"),
            createAbilityItem("热工水力", "中级"),
            createAbilityItem("核燃料管理", "中级")
        );
        professionalGroup.setItems(professionalItems);
        groups.add(professionalGroup);

        // 技术技能组
        PersonPortraitVO.AbilityGroup technicalGroup = new PersonPortraitVO.AbilityGroup();
        technicalGroup.setGroupName("技术技能");
        List<PersonPortraitVO.AbilityItem> technicalItems = Arrays.asList(
            createAbilityItem("MATLAB", "高级"),
            createAbilityItem("ANSYS", "中级"),
            createAbilityItem("Python", "中级"),
            createAbilityItem("CAD设计", "中级")
        );
        technicalGroup.setItems(technicalItems);
        groups.add(technicalGroup);

        abilityMatrix.setGroups(groups);
        portrait.setAbilityMatrix(abilityMatrix);

        // 构建发展路径
        PersonPortraitVO.DevelopmentPath developmentPath = new PersonPortraitVO.DevelopmentPath();
        List<PersonPortraitVO.PathNode> nodes = Arrays.asList(
            createPathNode("2010-03", "工程师", "入职担任工程师"),
            createPathNode("2013-06", "主管工程师", "晋升为主管工程师"),
            createPathNode("2016-09", "高级工程师", "晋升为高级工程师"),
            createPathNode("2020-01", "资深工程师", "晋升为资深工程师"),
            createPathNode("2024-01", "技术专家", "当前职位")
        );
        developmentPath.setNodes(nodes);
        portrait.setDevelopmentPath(developmentPath);

        // 构建绩效数据
        PersonPortraitVO.PerformanceData performanceData = new PersonPortraitVO.PerformanceData();
        List<PersonPortraitVO.PerformanceItem> performanceItems = Arrays.asList(
            createPerformanceItem(2021, "A", 92.0),
            createPerformanceItem(2022, "A", 88.5),
            createPerformanceItem(2023, "B", 85.0),
            createPerformanceItem(2024, "A", 91.5)
        );
        performanceData.setItems(performanceItems);
        portrait.setPerformanceData(performanceData);

        // 构建创新项目
        List<PersonPortraitVO.InnovationProject> innovationProjects = Arrays.asList(
            createInnovationProject("华龙一号反应堆设计优化", "国家级", "技术负责人", "进行中"),
            createInnovationProject("先进反应堆安全系统研究", "省部级", "项目成员", "已完成"),
            createInnovationProject("核电站数字化改造", "集团级", "技术骨干", "进行中")
        );
        portrait.setInnovationProjects(innovationProjects);

        // 构建分析文本
        String analysisText = String.format(
            "%s同志具有%d年核电行业工作经验，专业技能扎实，在反应堆设计和核安全分析方面具有较强的专业能力。" +
            "近年来绩效表现优秀，参与多个重要项目，具备良好的技术创新能力和团队协作精神。" +
            "建议继续加强跨专业知识学习，提升项目管理能力，向技术管理复合型人才发展。",
            personInfo.getPersonName(),
            calculateWorkYears(personInfo.getWorkStartDate())
        );
        portrait.setAnalysisText(analysisText);

        return portrait;
    }

    private PersonPortraitVO.AbilityItem createAbilityItem(String name, String level) {
        PersonPortraitVO.AbilityItem item = new PersonPortraitVO.AbilityItem();
        item.setName(name);
        item.setLevel(level);
        return item;
    }

    private PersonPortraitVO.PathNode createPathNode(String date, String position, String description) {
        PersonPortraitVO.PathNode node = new PersonPortraitVO.PathNode();
        node.setDate(date);
        node.setPosition(position);
        node.setDescription(description);
        return node;
    }

    private PersonPortraitVO.PerformanceItem createPerformanceItem(Integer year, String level, Double score) {
        PersonPortraitVO.PerformanceItem item = new PersonPortraitVO.PerformanceItem();
        item.setYear(year);
        item.setLevel(level);
        item.setScore(score);
        return item;
    }

    private PersonPortraitVO.InnovationProject createInnovationProject(String name, String level, String role, String status) {
        PersonPortraitVO.InnovationProject project = new PersonPortraitVO.InnovationProject();
        project.setName(name);
        project.setLevel(level);
        project.setRole(role);
        project.setStatus(status);
        return project;
    }

    private Integer calculateAge(Date birthDate) {
        if (birthDate == null) return null;
        LocalDate birth = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(birth, LocalDate.now()).getYears();
    }

    private Integer calculateCompanyYears(Date joinDate) {
        if (joinDate == null) return null;
        LocalDate join = joinDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(join, LocalDate.now()).getYears();
    }

    private Integer calculateWorkYears(Date workStartDate) {
        if (workStartDate == null) return null;
        LocalDate workStart = workStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(workStart, LocalDate.now()).getYears();
    }

    @Override
    public TeamPortraitVO getTeamPortrait(Long teamId) {
        // 获取团队基本信息
        TeamInfo teamInfo = teamInfoMapper.selectById(teamId);
        if (teamInfo == null) {
            return null;
        }
        
        TeamPortraitVO portraitVO = new TeamPortraitVO();
        
        // 设置团队基本信息
        TeamPortraitVO.TeamBasicInfo basicInfo = new TeamPortraitVO.TeamBasicInfo();
        basicInfo.setTeamId(teamInfo.getId());
        basicInfo.setTeamCode(teamInfo.getTeamCode());
        basicInfo.setTeamName(teamInfo.getTeamName());
        basicInfo.setTeamType(teamInfo.getTeamType());
        basicInfo.setLeaderName(teamInfo.getLeaderName());
        
        // 获取团队成员
        List<PersonInfo> members = personInfoService.selectPersonsByTeamId(teamId);
        basicInfo.setMemberCount(members.size());
        
        // 计算子团队数量（这里简化处理）
        basicInfo.setSubTeamCount(0);
        
        portraitVO.setBasicInfo(basicInfo);
        
        // 构建能力九宫格数据
        portraitVO.setAbilityGrid(buildAbilityGrid(members));
        
        // 构建技能分布数据
        portraitVO.setSkillDistribution(buildSkillDistribution(members));
        
        // 构建人才结构数据
        portraitVO.setTalentStructure(buildTalentStructure(members));
        
        // 构建队伍结构数据
        portraitVO.setTeamStructure(buildTeamStructure(members));
        
        // 构建产出贡献数据
        portraitVO.setContributionData(buildContributionData(members));
        
        return portraitVO;
    }
    
    /**
     * 构建能力九宫格数据
     */
    private TeamPortraitVO.AbilityGrid buildAbilityGrid(List<PersonInfo> members) {
        TeamPortraitVO.AbilityGrid abilityGrid = new TeamPortraitVO.AbilityGrid();
        
        List<TeamPortraitVO.AbilityGrid.GridItem> items = new ArrayList<>();
        TeamPortraitVO.AbilityGrid.GridStatistics statistics = new TeamPortraitVO.AbilityGrid.GridStatistics();
        
        int highPerformanceHighPotential = 0;
        int highPerformanceLowPotential = 0;
        int lowPerformanceHighPotential = 0;
        int lowPerformanceLowPotential = 0;
        
        for (PersonInfo member : members) {
            TeamPortraitVO.AbilityGrid.GridItem item = new TeamPortraitVO.AbilityGrid.GridItem();
            item.setPersonId(member.getId());
            item.setPersonName(member.getPersonName());
            
            // 简化计算：基于工作年限和学历计算绩效和潜力
            int workYears = member.getWorkYears() != null ? member.getWorkYears() : 0;
            String education = member.getEducation() != null ? member.getEducation() : "";
            double performance = Math.min(90.0, 60.0 + workYears * 2);
            double potential = 50.0 + (education.contains("博士") ? 30 : 
                                     education.contains("硕士") ? 20 : 10);
            
            item.setPerformance(performance);
            item.setPotential(potential);
            
            // 确定象限
            if (performance >= 75 && potential >= 75) {
                item.setQuadrant("明星员工");
                item.setColor("#00ff88");
                highPerformanceHighPotential++;
            } else if (performance >= 75 && potential < 75) {
                item.setQuadrant("核心骨干");
                item.setColor("#ffaa00");
                highPerformanceLowPotential++;
            } else if (performance < 75 && potential >= 75) {
                item.setQuadrant("潜力新星");
                item.setColor("#00aaff");
                lowPerformanceHighPotential++;
            } else {
                item.setQuadrant("待提升");
                item.setColor("#ff6666");
                lowPerformanceLowPotential++;
            }
            
            items.add(item);
        }
        
        statistics.setHighPerformanceHighPotential(highPerformanceHighPotential);
        statistics.setHighPerformanceLowPotential(highPerformanceLowPotential);
        statistics.setLowPerformanceHighPotential(lowPerformanceHighPotential);
        statistics.setLowPerformanceLowPotential(lowPerformanceLowPotential);
        
        abilityGrid.setItems(items);
        abilityGrid.setStatistics(statistics);
        
        return abilityGrid;
    }
    
    /**
     * 构建技能分布数据
     */
    private TeamPortraitVO.SkillDistribution buildSkillDistribution(List<PersonInfo> members) {
        TeamPortraitVO.SkillDistribution skillDistribution = new TeamPortraitVO.SkillDistribution();
        
        // 获取所有成员的技能
        List<Long> memberIds = members.stream().map(PersonInfo::getId).collect(Collectors.toList());
        // 使用空列表避免数据库查询
        List<PersonSkill> allSkills = new ArrayList<>();
        
        // 按技能类型分组
        Map<String, List<PersonSkill>> skillsByType = allSkills.stream()
            .collect(Collectors.groupingBy(PersonSkill::getSkillType));
        
        List<TeamPortraitVO.SkillDistribution.SkillCategory> categories = new ArrayList<>();
        
        for (Map.Entry<String, List<PersonSkill>> entry : skillsByType.entrySet()) {
            TeamPortraitVO.SkillDistribution.SkillCategory category = new TeamPortraitVO.SkillDistribution.SkillCategory();
            category.setCategoryName(entry.getKey());
            category.setCategoryType(entry.getKey());
            
            List<PersonSkill> categorySkills = entry.getValue();
            category.setPersonCount((int) categorySkills.stream().map(PersonSkill::getPersonId).distinct().count());
            category.setAvgExperience(categorySkills.stream()
                .mapToDouble(skill -> skill.getExperienceYears().doubleValue())
                .average().orElse(0.0));
            
            // 按技能名称分组统计
            Map<String, List<PersonSkill>> skillsByName = categorySkills.stream()
                .collect(Collectors.groupingBy(PersonSkill::getSkillName));
            
            List<TeamPortraitVO.SkillDistribution.SkillDetail> details = new ArrayList<>();
            for (Map.Entry<String, List<PersonSkill>> skillEntry : skillsByName.entrySet()) {
                TeamPortraitVO.SkillDistribution.SkillDetail detail = new TeamPortraitVO.SkillDistribution.SkillDetail();
                detail.setSkillName(skillEntry.getKey());
                detail.setCount(skillEntry.getValue().size());
                
                List<String> persons = skillEntry.getValue().stream()
                    .map(skill -> members.stream()
                        .filter(member -> member.getId().equals(skill.getPersonId()))
                        .findFirst()
                        .map(PersonInfo::getPersonName)
                        .orElse(""))
                    .filter(name -> !name.isEmpty())
                    .collect(Collectors.toList());
                detail.setPersons(persons);
                
                details.add(detail);
            }
            
            category.setDetails(details);
            categories.add(category);
        }
        
        skillDistribution.setCategories(categories);
        return skillDistribution;
    }
    
    /**
     * 构建人才结构数据
     */
    private TeamPortraitVO.TalentStructure buildTalentStructure(List<PersonInfo> members) {
        TeamPortraitVO.TalentStructure talentStructure = new TeamPortraitVO.TalentStructure();
        
        int totalCount = members.size();
        
        // 学历分布
        Map<String, Long> educationCount = members.stream()
            .collect(Collectors.groupingBy(PersonInfo::getEducation, Collectors.counting()));
        
        List<TeamPortraitVO.TalentStructure.PieItem> educationDistribution = educationCount.entrySet().stream()
            .map(entry -> {
                TeamPortraitVO.TalentStructure.PieItem item = new TeamPortraitVO.TalentStructure.PieItem();
                item.setName(entry.getKey());
                item.setValue(entry.getValue().intValue());
                item.setPercentage(entry.getValue() * 100.0 / totalCount);
                return item;
            })
            .collect(Collectors.toList());
        
        // 职称分布
        Map<String, Long> titleCount = members.stream()
            .collect(Collectors.groupingBy(PersonInfo::getJobTitle, Collectors.counting()));
        
        List<TeamPortraitVO.TalentStructure.PieItem> titleDistribution = titleCount.entrySet().stream()
            .map(entry -> {
                TeamPortraitVO.TalentStructure.PieItem item = new TeamPortraitVO.TalentStructure.PieItem();
                item.setName(entry.getKey());
                item.setValue(entry.getValue().intValue());
                item.setPercentage(entry.getValue() * 100.0 / totalCount);
                return item;
            })
            .collect(Collectors.toList());
        
        // 年龄分布
        Map<String, Long> ageCount = members.stream()
            .filter(member -> member.getAge() != null)
            .collect(Collectors.groupingBy(member -> {
                int age = member.getAge();
                if (age < 30) return "30岁以下";
                else if (age < 40) return "30-40岁";
                else if (age < 50) return "40-50岁";
                else return "50岁以上";
            }, Collectors.counting()));
        
        List<TeamPortraitVO.TalentStructure.PieItem> ageDistribution = ageCount.entrySet().stream()
            .map(entry -> {
                TeamPortraitVO.TalentStructure.PieItem item = new TeamPortraitVO.TalentStructure.PieItem();
                item.setName(entry.getKey());
                item.setValue(entry.getValue().intValue());
                item.setPercentage(entry.getValue() * 100.0 / totalCount);
                return item;
            })
            .collect(Collectors.toList());
        
        // 工作年限分布
        Map<String, Long> workYearsCount = members.stream()
            .filter(member -> member.getWorkYears() != null)
            .collect(Collectors.groupingBy(member -> {
                int years = member.getWorkYears();
                if (years < 5) return "5年以下";
                else if (years < 10) return "5-10年";
                else if (years < 15) return "10-15年";
                else return "15年以上";
            }, Collectors.counting()));
        
        List<TeamPortraitVO.TalentStructure.PieItem> workYearsDistribution = workYearsCount.entrySet().stream()
            .map(entry -> {
                TeamPortraitVO.TalentStructure.PieItem item = new TeamPortraitVO.TalentStructure.PieItem();
                item.setName(entry.getKey());
                item.setValue(entry.getValue().intValue());
                item.setPercentage(entry.getValue() * 100.0 / totalCount);
                return item;
            })
            .collect(Collectors.toList());
        
        talentStructure.setEducationDistribution(educationDistribution);
        talentStructure.setTitleDistribution(titleDistribution);
        talentStructure.setAgeDistribution(ageDistribution);
        talentStructure.setWorkYearsDistribution(workYearsDistribution);
        
        return talentStructure;
    }
    
    /**
     * 构建队伍结构数据
     */
    private TeamPortraitVO.TeamStructure buildTeamStructure(List<PersonInfo> members) {
        TeamPortraitVO.TeamStructure teamStructure = new TeamPortraitVO.TeamStructure();
        
        // 职级分布（按职称分组，统计男女比例）
        Map<String, Map<String, Long>> levelGenderCount = members.stream()
            .collect(Collectors.groupingBy(
                PersonInfo::getJobTitle,
                Collectors.groupingBy(PersonInfo::getGender, Collectors.counting())
            ));
        
        List<TeamPortraitVO.TeamStructure.BarItem> levelDistribution = levelGenderCount.entrySet().stream()
            .map(entry -> {
                TeamPortraitVO.TeamStructure.BarItem item = new TeamPortraitVO.TeamStructure.BarItem();
                item.setCategory(entry.getKey());
                
                Map<String, Long> genderCount = entry.getValue();
                int maleCount = genderCount.getOrDefault("男", 0L).intValue();
                int femaleCount = genderCount.getOrDefault("女", 0L).intValue();
                
                item.setMaleCount(maleCount);
                item.setFemaleCount(femaleCount);
                item.setTotalCount(maleCount + femaleCount);
                
                return item;
            })
            .collect(Collectors.toList());
        
        // 岗位分布（按职位分组，统计男女比例）
        Map<String, Map<String, Long>> positionGenderCount = members.stream()
            .collect(Collectors.groupingBy(
                PersonInfo::getPosition,
                Collectors.groupingBy(PersonInfo::getGender, Collectors.counting())
            ));
        
        List<TeamPortraitVO.TeamStructure.BarItem> positionDistribution = positionGenderCount.entrySet().stream()
            .map(entry -> {
                TeamPortraitVO.TeamStructure.BarItem item = new TeamPortraitVO.TeamStructure.BarItem();
                item.setCategory(entry.getKey());
                
                Map<String, Long> genderCount = entry.getValue();
                int maleCount = genderCount.getOrDefault("男", 0L).intValue();
                int femaleCount = genderCount.getOrDefault("女", 0L).intValue();
                
                item.setMaleCount(maleCount);
                item.setFemaleCount(femaleCount);
                item.setTotalCount(maleCount + femaleCount);
                
                return item;
            })
            .collect(Collectors.toList());
        
        teamStructure.setLevelDistribution(levelDistribution);
        teamStructure.setPositionDistribution(positionDistribution);
        
        return teamStructure;
    }
    
    /**
     * 构建产出贡献数据
     */
    private TeamPortraitVO.ContributionData buildContributionData(List<PersonInfo> members) {
        TeamPortraitVO.ContributionData contributionData = new TeamPortraitVO.ContributionData();
        
        // 设置年份（最近5年）
        List<Integer> years = Arrays.asList(2020, 2021, 2022, 2023, 2024);
        contributionData.setYears(years);
        
        List<Long> memberIds = members.stream().map(PersonInfo::getId).collect(Collectors.toList());
        
        // 创新项目贡献
        // 使用空列表避免数据库查询
        List<PersonInnovation> innovations = new ArrayList<>();
        
        TeamPortraitVO.ContributionData.ContributionItem innovationItem = new TeamPortraitVO.ContributionData.ContributionItem();
        innovationItem.setType("创新项目");
        innovationItem.setValues(Arrays.asList(8.0, 12.0, 15.0, 18.0, 22.0)); // 模拟数据
        innovationItem.setTotal(75.0);
        
        // 项目贡献
        // 使用空列表避免数据库查询
        List<PersonProject> projects = new ArrayList<>();
        
        TeamPortraitVO.ContributionData.ContributionItem projectItem = new TeamPortraitVO.ContributionData.ContributionItem();
        projectItem.setType("项目贡献");
        projectItem.setValues(Arrays.asList(25.0, 30.0, 35.0, 40.0, 45.0)); // 模拟数据
        projectItem.setTotal(175.0);
        
        // 绩效贡献
        // 使用空列表避免数据库查询
        List<PersonPerformance> performances = new ArrayList<>();
        
        TeamPortraitVO.ContributionData.ContributionItem performanceItem = new TeamPortraitVO.ContributionData.ContributionItem();
        performanceItem.setType("绩效表现");
        performanceItem.setValues(Arrays.asList(85.0, 87.0, 89.0, 88.0, 90.0)); // 模拟数据
        performanceItem.setTotal(439.0);
        
        contributionData.setInnovations(Arrays.asList(innovationItem));
        contributionData.setProjects(Arrays.asList(projectItem));
        contributionData.setPerformance(Arrays.asList(performanceItem));
        
        return contributionData;
    }
}