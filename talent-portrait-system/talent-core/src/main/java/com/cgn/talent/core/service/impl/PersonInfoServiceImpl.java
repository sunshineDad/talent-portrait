package com.cgn.talent.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgn.talent.common.page.PageParam;
import com.cgn.talent.common.page.PageResult;
import com.cgn.talent.core.entity.*;
import com.cgn.talent.core.mapper.*;
import com.cgn.talent.core.service.IPersonInfoService;
import com.cgn.talent.system.entity.TeamInfo;
import com.cgn.talent.system.mapper.TeamInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 人员基础信息Service业务层处理
 *
 * @author CGN
 * @date 2024-01-10
 */
@Slf4j
@Service
public class PersonInfoServiceImpl extends ServiceImpl<PersonInfoMapper, PersonInfo> implements IPersonInfoService {

    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Autowired
    private PersonSkillMapper personSkillMapper;

    @Autowired
    private PersonProjectMapper personProjectMapper;

    @Autowired
    private PersonPerformanceMapper personPerformanceMapper;

    @Autowired
    private PersonInfluenceMapper personInfluenceMapper;

    @Autowired
    private PersonInnovationMapper personInnovationMapper;

    @Autowired
    private PersonEvaluationMapper personEvaluationMapper;
    @Autowired
    private TeamInfoMapper teamInfoMapper;
    @Override
    public PageResult<PersonInfo> selectPersonPage(PageParam pageParam, PersonInfo personInfo) {
        Page<PersonInfo> page = new Page<>(pageParam.getCurrent(), pageParam.getSize());
        Page<PersonInfo> resultPage = (Page<PersonInfo>) personInfoMapper.selectPersonPage(page, personInfo);

        return new PageResult<>(
                resultPage.getCurrent(),
                resultPage.getSize(),
                resultPage.getTotal(),
                resultPage.getRecords()
        );
    }

    @Override
    public PersonInfo selectPersonDetailById(Long personId) {
        // 查询基础信息
        PersonInfo personInfo = personInfoMapper.selectPersonDetailById(personId);
        if (personInfo == null) {
            return null;
        }

        // 查询关联的子表数据
        personInfo.setSkillList(personSkillMapper.selectSkillByPersonId(personId));
        personInfo.setProjectList(personProjectMapper.selectProjectByPersonId(personId));
        personInfo.setPerformanceList(personPerformanceMapper.selectPerformanceByPersonId(personId));
        personInfo.setInfluenceList(personInfluenceMapper.selectInfluenceByPersonId(personId));
        personInfo.setInnovationList(personInnovationMapper.selectInnovationByPersonId(personId));
        personInfo.setEvaluationList(personEvaluationMapper.selectEvaluationByPersonId(personId));

        return personInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long insertPerson(PersonInfo personInfo) {
        // 校验唯一性
        if (!checkPersonCodeUnique(personInfo)) {
            throw new RuntimeException("人员编号已存在");
        }
        if (StringUtils.hasText(personInfo.getIdCard()) && !checkIdCardUnique(personInfo)) {
            throw new RuntimeException("身份证号已存在");
        }

        // 保存基础信息
        personInfo.setStatus("0");
        personInfo.setDelFlag("0");
        int result = baseMapper.insert(personInfo);

        if (result > 0) {
            Long personId = personInfo.getId();

            // 保存子表数据
            saveSubTableData(personId, personInfo);

            return personId;
        }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePerson(PersonInfo personInfo) {
        // 校验唯一性
        if (!checkPersonCodeUnique(personInfo)) {
            throw new RuntimeException("人员编号已存在");
        }
        if (StringUtils.hasText(personInfo.getIdCard()) && !checkIdCardUnique(personInfo)) {
            throw new RuntimeException("身份证号已存在");
        }

        int result = baseMapper.updateById(personInfo);

        if (result > 0) {
            // 更新子表数据
            Long personId = personInfo.getId();

            // 删除原有子表数据
            deleteSubTableData(personId);

            // 重新保存子表数据
            saveSubTableData(personId, personInfo);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePersonByIds(Long[] personIds) {
        int count = 0;
        for (Long personId : personIds) {
            // 删除子表数据
            deleteSubTableData(personId);

            // 逻辑删除主表
            PersonInfo person = new PersonInfo();
            person.setId(personId);
            person.setDelFlag("1");
            count += baseMapper.updateById(person);
        }
        return count;
    }

    @Override
    public String importPerson(List<PersonInfo> personList, boolean updateSupport) {
        if (CollectionUtils.isEmpty(personList)) {
            throw new RuntimeException("导入数据不能为空");
        }

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (PersonInfo person : personList) {
            try {
                // 根据人员编号查询是否存在
                LambdaQueryWrapper<PersonInfo> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(PersonInfo::getPersonCode, person.getPersonCode())
                        .eq(PersonInfo::getDelFlag, "0");
                PersonInfo existPerson = baseMapper.selectOne(wrapper);

                if (existPerson == null) {
                    // 新增
                    insertPerson(person);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、人员 ")
                            .append(person.getPersonName()).append(" 导入成功");
                } else if (updateSupport) {
                    // 更新
                    person.setId(existPerson.getId());
                    updatePerson(person);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、人员 ")
                            .append(person.getPersonName()).append(" 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、人员 ")
                            .append(person.getPersonName()).append(" 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、人员 " + person.getPersonName() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }

        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new RuntimeException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }

        return successMsg.toString();
    }

    @Override
    public List<PersonInfo> exportPerson(PersonInfo personInfo) {
        LambdaQueryWrapper<PersonInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PersonInfo::getDelFlag, "0");

        if (StringUtils.hasText(personInfo.getPersonCode())) {
            wrapper.like(PersonInfo::getPersonCode, personInfo.getPersonCode());
        }
        if (StringUtils.hasText(personInfo.getPersonName())) {
            wrapper.like(PersonInfo::getPersonName, personInfo.getPersonName());
        }
        if (personInfo.getTeamId() != null) {
            wrapper.eq(PersonInfo::getTeamId, personInfo.getTeamId());
        }

        wrapper.orderByDesc(PersonInfo::getCreateTime);

        return baseMapper.selectList(wrapper);
    }

    @Override
    public Map<String, Object> selectPersonStatistics(Long teamId) {
        return personInfoMapper.selectPersonStatistics(teamId);
    }

    @Override
    public boolean checkPersonCodeUnique(PersonInfo personInfo) {
        Long personId = personInfo.getId() == null ? -1L : personInfo.getId();
        int count = personInfoMapper.checkPersonCodeUnique(personInfo.getPersonCode(), personId);
        return count == 0;
    }

    @Override
    public boolean checkIdCardUnique(PersonInfo personInfo) {
        Long personId = personInfo.getId() == null ? -1L : personInfo.getId();
        int count = personInfoMapper.checkIdCardUnique(personInfo.getIdCard(), personId);
        return count == 0;
    }

    /**
     * 保存子表数据
     */
    private void saveSubTableData(Long personId, PersonInfo personInfo) {
        // 保存专业能力
        if (!CollectionUtils.isEmpty(personInfo.getSkillList())) {
            personInfo.getSkillList().forEach(skill -> {
                skill.setPersonId(personId);
                skill.setCreateBy(personInfo.getCreateBy());
            });
            personSkillMapper.batchInsertSkill(personInfo.getSkillList());
        }

        // 保存项目经历
        if (!CollectionUtils.isEmpty(personInfo.getProjectList())) {
            personInfo.getProjectList().forEach(project -> {
                project.setPersonId(personId);
                project.setCreateBy(personInfo.getCreateBy());
            });
            personProjectMapper.batchInsertProject(personInfo.getProjectList());
        }

        // 保存绩效信息
        if (!CollectionUtils.isEmpty(personInfo.getPerformanceList())) {
            personInfo.getPerformanceList().forEach(performance -> {
                performance.setPersonId(personId);
                performance.setCreateBy(personInfo.getCreateBy());
            });
            personPerformanceMapper.batchInsertPerformance(personInfo.getPerformanceList());
        }

        // 保存影响力信息
        if (!CollectionUtils.isEmpty(personInfo.getInfluenceList())) {
            personInfo.getInfluenceList().forEach(influence -> {
                influence.setPersonId(personId);
                influence.setCreateBy(personInfo.getCreateBy());
            });
            personInfluenceMapper.batchInsertInfluence(personInfo.getInfluenceList());
        }

        // 保存创新产出
        if (!CollectionUtils.isEmpty(personInfo.getInnovationList())) {
            personInfo.getInnovationList().forEach(innovation -> {
                innovation.setPersonId(personId);
                innovation.setCreateBy(personInfo.getCreateBy());
            });
            personInnovationMapper.batchInsertInnovation(personInfo.getInnovationList());
        }

        // 保存人才评估
        if (!CollectionUtils.isEmpty(personInfo.getEvaluationList())) {
            personInfo.getEvaluationList().forEach(evaluation -> {
                evaluation.setPersonId(personId);
                evaluation.setCreateBy(personInfo.getCreateBy());
            });
            personEvaluationMapper.batchInsertEvaluation(personInfo.getEvaluationList());
        }
    }

    /**
     * 删除子表数据
     */
    private void deleteSubTableData(Long personId) {
        personSkillMapper.deleteSkillByPersonId(personId);
        personProjectMapper.deleteProjectByPersonId(personId);

        // 删除其他子表数据
        LambdaQueryWrapper<PersonPerformance> performanceWrapper = new LambdaQueryWrapper<>();
        performanceWrapper.eq(PersonPerformance::getPersonId, personId);
        personPerformanceMapper.delete(performanceWrapper);

        LambdaQueryWrapper<PersonInfluence> influenceWrapper = new LambdaQueryWrapper<>();
        influenceWrapper.eq(PersonInfluence::getPersonId, personId);
        personInfluenceMapper.delete(influenceWrapper);

        LambdaQueryWrapper<PersonInnovation> innovationWrapper = new LambdaQueryWrapper<>();
        innovationWrapper.eq(PersonInnovation::getPersonId, personId);
        personInnovationMapper.delete(innovationWrapper);

        LambdaQueryWrapper<PersonEvaluation> evaluationWrapper = new LambdaQueryWrapper<>();
        evaluationWrapper.eq(PersonEvaluation::getPersonId, personId);
        personEvaluationMapper.delete(evaluationWrapper);
    }
    @Override
    public Map<String, Object> getPersonPortrait(Long personId) {
        Map<String, Object> portrait = new HashMap<>();

        // 1. 基本信息
        PersonInfo personInfo = personInfoMapper.selectPersonDetailById(personId);
        if (personInfo == null) {
            throw new RuntimeException("人员不存在");
        }

        portrait.put("basicInfo", buildBasicInfo(personInfo));

        // 2. 发展路径
        portrait.put("developmentPath", buildDevelopmentPath(personId));

        // 3. 能力矩阵
//        portrait.put("abilityMatrix", buildAbilityMatrix(personId));

        // 4. 绩效数据
        portrait.put("performanceData", buildPerformanceData(personId));

        // 5. 创新产出
        portrait.put("innovationData", buildInnovationData(personId));

        // 6. 人才评估
        portrait.put("evaluationData", buildEvaluationData(personId));

        // 7. 个人影响力
        portrait.put("influenceData", buildInfluenceData(personId));

        return portrait;
    }

    @Override
    public Map<String, Object> getTeamPortrait(Long teamId) {
        Map<String, Object> portrait = new HashMap<>();

        // 1. 团队基本信息
        TeamInfo teamInfo = teamInfoMapper.selectById(teamId);
        if (teamInfo == null) {
            throw new RuntimeException("团队不存在");
        }

        portrait.put("basicInfo", buildTeamBasicInfo(teamInfo));

        // 2. 能力九宫格
        portrait.put("abilityGrid", buildAbilityGrid(teamId));

        // 3. 专业能力分布
        portrait.put("skillDistribution", buildSkillDistribution(teamId));

        // 4. 人才结构
        portrait.put("talentStructure", buildTalentStructure(teamId));

        // 5. 队伍结构
        portrait.put("teamStructure", buildTeamStructure(teamId));

        // 6. 产出贡献
        portrait.put("contributionData", buildContributionData(teamId));

        return portrait;
    }

    @Override
    public Map<String, Object> getPersonDevelopmentPath(Long personId) {
        Map<String, Object> path = new HashMap<>();

        // 查询项目经历作为发展路径
        List<PersonProject> projects = personProjectMapper.selectProjectByPersonId(personId);

        // 构建时间轴数据
        List<Map<String, Object>> timeline = projects.stream()
                .sorted(Comparator.comparing(PersonProject::getStartDate))
                .map(project -> {
                    Map<String, Object> node = new HashMap<>();
                    node.put("date", project.getStartDate());
                    node.put("title", project.getProjectName());
                    node.put("role", project.getProjectRole());
                    node.put("level", project.getProjectLevel());
                    node.put("description", project.getDescription());
                    return node;
                })
                .collect(Collectors.toList());

        path.put("timeline", timeline);
        path.put("total", timeline.size());

        return path;
    }

    @Override
    public Map<String, Object> getPersonAbilityMatrix(Long personId) {
        Map<String, Object> matrix = new HashMap<>();

        // 查询专业能力
        List<PersonSkill> skills = personSkillMapper.selectSkillByPersonId(personId);

        // 按技能类型分组
        Map<String, List<PersonSkill>> groupedSkills = skills.stream()
                .collect(Collectors.groupingBy(PersonSkill::getSkillType));

        List<Map<String, Object>> groups = new ArrayList<>();
        for (Map.Entry<String, List<PersonSkill>> entry : groupedSkills.entrySet()) {
            Map<String, Object> group = new HashMap<>();
            group.put("groupName", entry.getKey());
            group.put("groupType", entry.getKey());
            group.put("items", entry.getValue().stream()
                    .map(skill -> {
                        Map<String, Object> item = new HashMap<>();
                        item.put("name", skill.getSkillName());
                        item.put("level", skill.getSkillLevel());
                        item.put("years", skill.getExperienceYears());
                        item.put("certification", skill.getCertification());
                        return item;
                    })
                    .collect(Collectors.toList())
            );
            groups.add(group);
        }

        matrix.put("groups", groups);
        matrix.put("totalSkills", skills.size());

        return matrix;
    }

    @Override
    public Map<String, Object> getTeamAbilityDistribution(Long teamId) {
        Map<String, Object> distribution = new HashMap<>();

        // 查询团队能力分布
        List<Map<String, Object>> skillDist = personSkillMapper.selectTeamSkillDistribution(teamId);

        distribution.put("distribution", skillDist);
        distribution.put("categories", skillDist.stream()
                .map(item -> item.get("skill_type_name"))
                .collect(Collectors.toList())
        );

        return distribution;
    }

    @Override
    public Map<String, Object> getTeamStructure(Long teamId) {
        Map<String, Object> structure = new HashMap<>();

        // 查询团队人员统计
        Map<String, Object> statistics = personInfoMapper.selectPersonStatistics(teamId);

        structure.put("statistics", statistics);

        // TODO: 添加更多结构分析数据

        return structure;
    }

    @Override
    public Map<String, Object> getTeamContribution(Long teamId, Integer year) {
        Map<String, Object> contribution = new HashMap<>();

        // 查询团队创新产出
        List<Map<String, Object>> innovations = personInnovationMapper.selectTeamInnovationStatistics(teamId, year);

        contribution.put("innovations", innovations);

        // TODO: 添加项目贡献、绩效贡献等数据

        return contribution;
    }

    @Override
    public Map<String, Object> compareTeams(Long[] teamIds) {
        Map<String, Object> comparison = new HashMap<>();

        List<Map<String, Object>> teamDataList = new ArrayList<>();

        for (Long teamId : teamIds) {
            Map<String, Object> teamData = new HashMap<>();
            TeamInfo team = teamInfoMapper.selectById(teamId);
            teamData.put("teamInfo", team);
            teamData.put("statistics", personInfoMapper.selectPersonStatistics(teamId));
            teamDataList.add(teamData);
        }

        comparison.put("teams", teamDataList);

        return comparison;
    }

    // ==================== 私有方法 ====================

    private Map<String, Object> buildBasicInfo(PersonInfo personInfo) {
        Map<String, Object> basicInfo = new HashMap<>();
        basicInfo.put("personId", personInfo.getId());
        basicInfo.put("personCode", personInfo.getPersonCode());
        basicInfo.put("personName", personInfo.getPersonName());
        basicInfo.put("photoUrl", personInfo.getPhotoUrl());
        basicInfo.put("teamName", personInfo.getTeamName());
        basicInfo.put("position", personInfo.getPosition());
        basicInfo.put("jobTitle", personInfo.getJobTitle());
        basicInfo.put("jobLevel", personInfo.getJobLevel());
        basicInfo.put("age", personInfo.getAge());
        basicInfo.put("workYears", personInfo.getWorkYears());
        basicInfo.put("companyYears", personInfo.getCompanyYears());
        basicInfo.put("education", personInfo.getEducation());
        basicInfo.put("degree", personInfo.getDegree());
        return basicInfo;
    }

    private Map<String, Object> buildDevelopmentPath(Long personId) {
        Map<String, Object> path = new HashMap<>();

        // 查询项目经历
        List<PersonProject> projects = personProjectMapper.selectProjectByPersonId(personId);

        // 构建路径节点
        List<Map<String, Object>> nodes = projects.stream()
                .sorted(Comparator.comparing(PersonProject::getStartDate))
                .map(project -> {
                    Map<String, Object> node = new HashMap<>();
                    node.put("date", project.getStartDate());
                    node.put("position", project.getProjectRole());
                    node.put("team", project.getProjectName());
                    node.put("description", project.getDescription());
                    return node;
                })
                .collect(Collectors.toList());

        path.put("nodes", nodes);
        path.put("categories", nodes.stream()
                .map(node -> node.get("date").toString())
                .collect(Collectors.toList())
        );

        return path;
    }

    private Map<String, Object> buildPerformanceData(Long personId) {
        Map<String, Object> performance = new HashMap<>();

        // 查询绩效历史
        List<PersonPerformance> performances = personPerformanceMapper.selectPerformanceByPersonId(personId);

        List<Map<String, Object>> items = performances.stream()
                .map(p -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("year", p.getYear());
                    item.put("level", p.getPerformanceLevel());
                    item.put("score", p.getPerformanceScore());
                    return item;
                })
                .collect(Collectors.toList());

        performance.put("items", items);
        performance.put("years", items.stream()
                .map(item -> item.get("year"))
                .collect(Collectors.toList())
        );

        return performance;
    }

    private Map<String, Object> buildInnovationData(Long personId) {
        Map<String, Object> innovation = new HashMap<>();

        // 按年度统计创新产出
        List<Map<String, Object>> yearData = personInnovationMapper.selectInnovationByYear(personId);

        innovation.put("yearData", yearData);

        return innovation;
    }

    private Map<String, Object> buildEvaluationData(Long personId) {
        Map<String, Object> evaluation = new HashMap<>();

        // 获取最新年度的评估数据
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);

        List<Map<String, Object>> radarData = personEvaluationMapper.selectEvaluationRadarData(personId, currentYear);

        evaluation.put("radarData", radarData);

        return evaluation;
    }

    private Map<String, Object> buildInfluenceData(Long personId) {
        Map<String, Object> influence = new HashMap<>();

        // 查询影响力信息
        List<PersonInfluence> influences = personInfluenceMapper.selectInfluenceByPersonId(personId);

        // 分为当前和历史
        Map<Boolean, List<PersonInfluence>> grouped = influences.stream()
                .collect(Collectors.partitioningBy(i -> "1".equals(i.getIsCurrent())));

        influence.put("currentInfluence", grouped.get(true));
        influence.put("historyInfluence", grouped.get(false));

        return influence;
    }

    private Map<String, Object> buildTeamBasicInfo(TeamInfo teamInfo) {
        Map<String, Object> basicInfo = new HashMap<>();
        basicInfo.put("teamId", teamInfo.getId());
        basicInfo.put("teamCode", teamInfo.getTeamCode());
        basicInfo.put("teamName", teamInfo.getTeamName());
        basicInfo.put("teamType", teamInfo.getTeamType());
        basicInfo.put("leaderName", teamInfo.getLeaderName());
        basicInfo.put("memberCount", teamInfo.getMemberCount());
        basicInfo.put("subTeamCount", teamInfoMapper.selectChildrenCountById(teamInfo.getId()));
        return basicInfo;
    }

    private Map<String, Object> buildAbilityGrid(Long teamId) {
        Map<String, Object> grid = new HashMap<>();

        // TODO: 实现能力九宫格逻辑
        // 需要根据绩效和潜力评分将人员分配到九宫格中

        return grid;
    }

    private Map<String, Object> buildSkillDistribution(Long teamId) {
        Map<String, Object> distribution = new HashMap<>();

        // 查询团队技能分布
        List<Map<String, Object>> skills = personSkillMapper.selectTeamSkillDistribution(teamId);

        distribution.put("categories", skills);

        return distribution;
    }

    private Map<String, Object> buildTalentStructure(Long teamId) {
        Map<String, Object> structure = new HashMap<>();

        // TODO: 实现人才结构分析
        // 包括学历分布、职称分布、年龄分布、工龄分布等

        return structure;
    }

    private Map<String, Object> buildTeamStructure(Long teamId) {
        Map<String, Object> structure = new HashMap<>();

        // TODO: 实现队伍结构分析
        // 包括职级分布、岗位分布等

        return structure;
    }

    private Map<String, Object> buildContributionData(Long teamId) {
        Map<String, Object> contribution = new HashMap<>();

        // 查询团队产出贡献
        List<Map<String, Object>> innovations = personInnovationMapper.selectTeamInnovationStatistics(teamId, null);

        contribution.put("innovations", innovations);

        // TODO: 添加项目贡献、绩效贡献等

        return contribution;
    }
    
    @Override
    public List<PersonInfo> selectPersonsByTeamId(Long teamId) {
        return personInfoMapper.selectList(
                new LambdaQueryWrapper<PersonInfo>()
                        .eq(PersonInfo::getTeamId, teamId)
                        .eq(PersonInfo::getDelFlag, "0")
                        .orderByAsc(PersonInfo::getPersonCode)
        );
    }
}
