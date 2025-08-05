package com.cgn.talent.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgn.talent.common.page.PageParam;
import com.cgn.talent.common.page.PageResult;
import com.cgn.talent.core.entity.*;
import com.cgn.talent.core.mapper.*;
import com.cgn.talent.core.service.IPersonInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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
    private PersonCapabilityMapper personCapabilityMapper;

    @Autowired
    private PersonPostGradeDurationMapper personPostGradeDurationMapper;

    @Override
    public PageResult<PersonInfo> selectPersonPage(PageParam pageParam, PersonInfo personInfo) {
        Page<PersonInfo> page = new Page<>(pageParam.getCurrent(), pageParam.getSize());
        Page<PersonInfo> resultPage = (Page<PersonInfo>) personInfoMapper.selectPersonPage(page, personInfo);

        // 计算每个人员的年龄、工龄等信息
        resultPage.getRecords().forEach(this::calculatePersonInfo);

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
        PersonInfo personInfo = personInfoMapper.selectById(personId);
        if (personInfo == null) {
            return null;
        }

        // 查询团队名称
        if (StringUtils.hasText(personInfo.getTeamCode())) {
            // 这里可以调用TeamService查询团队名称
            // personInfo.setTeamName(teamService.getTeamNameByCode(personInfo.getTeamCode()));
        }

        // 计算年龄、工龄等
        calculatePersonInfo(personInfo);

        // 查询关联的子表数据
        String personCode = personInfo.getPersonCode();
        personInfo.setProjectList(personProjectMapper.selectProjectByPersonCode(personCode));
        personInfo.setPerformanceList(personPerformanceMapper.selectPerformanceByPersonCode(personCode));
        personInfo.setInfluenceList(personInfluenceMapper.selectInfluenceByPersonCode(personCode));
        personInfo.setInnovationList(personInnovationMapper.selectInnovationByPersonCode(personCode));
        personInfo.setEvaluationList(personEvaluationMapper.selectEvaluationByPersonCode(personCode));
        personInfo.setCapabilityList(personCapabilityMapper.selectCapabilityByPersonCode(personCode));
        personInfo.setPostGradeList(personPostGradeDurationMapper.selectPostGradeByPersonCode(personCode));

        return personInfo;
    }

    @Override
    public PersonInfo selectPersonDetailByCode(String personCode) {
        LambdaQueryWrapper<PersonInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PersonInfo::getPersonCode, personCode)
                .eq(PersonInfo::getDelFlag, "0");
        PersonInfo personInfo = baseMapper.selectOne(wrapper);

        if (personInfo == null) {
            return null;
        }

        // 计算年龄、工龄等
        calculatePersonInfo(personInfo);

        // 查询关联的子表数据
        personInfo.setProjectList(personProjectMapper.selectProjectByPersonCode(personCode));
        personInfo.setPerformanceList(personPerformanceMapper.selectPerformanceByPersonCode(personCode));
        personInfo.setInfluenceList(personInfluenceMapper.selectInfluenceByPersonCode(personCode));
        personInfo.setInnovationList(personInnovationMapper.selectInnovationByPersonCode(personCode));
        personInfo.setEvaluationList(personEvaluationMapper.selectEvaluationByPersonCode(personCode));
        personInfo.setCapabilityList(personCapabilityMapper.selectCapabilityByPersonCode(personCode));
        personInfo.setPostGradeList(personPostGradeDurationMapper.selectPostGradeByPersonCode(personCode));

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

        // 设置默认值
        personInfo.setStatus("0");
        personInfo.setDelFlag("0");
        personInfo.setCreateTime(new Date());

        // 保存基础信息
        int result = baseMapper.insert(personInfo);

        if (result > 0) {
            Long personId = personInfo.getId();
            String personCode = personInfo.getPersonCode();

            // 保存子表数据
            saveSubTableData(personCode, personInfo);

            log.info("新增人员成功，人员ID：{}，人员编号：{}", personId, personCode);

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

        personInfo.setUpdateTime(new Date());
        int result = baseMapper.updateById(personInfo);

        if (result > 0) {
            String personCode = personInfo.getPersonCode();

            // 删除原有子表数据
            deleteSubTableData(personCode);

            // 重新保存子表数据
            saveSubTableData(personCode, personInfo);

            log.info("更新人员成功，人员ID：{}，人员编号：{}", personInfo.getId(), personCode);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePersonByIds(Long[] personIds) {
        int count = 0;
        for (Long personId : personIds) {
            PersonInfo person = baseMapper.selectById(personId);
            if (person != null) {
                // 删除子表数据
                deleteSubTableData(person.getPersonCode());

                // 逻辑删除主表
                person.setDelFlag("1");
                person.setUpdateTime(new Date());
                count += baseMapper.updateById(person);

                log.info("删除人员成功，人员ID：{}，人员编号：{}", personId, person.getPersonCode());
            }
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
        if (StringUtils.hasText(personInfo.getTeamCode())) {
            wrapper.eq(PersonInfo::getTeamCode, personInfo.getTeamCode());
        }
        if (StringUtils.hasText(personInfo.getJobTitle())) {
            wrapper.eq(PersonInfo::getJobTitle, personInfo.getJobTitle());
        }
        if (StringUtils.hasText(personInfo.getJobLevel())) {
            wrapper.eq(PersonInfo::getJobLevel, personInfo.getJobLevel());
        }

        wrapper.orderByDesc(PersonInfo::getCreateTime);

        List<PersonInfo> list = baseMapper.selectList(wrapper);

        // 计算年龄、工龄等信息
        list.forEach(this::calculatePersonInfo);

        return list;
    }

    @Override
    public Map<String, Object> selectPersonStatistics(String teamCode) {
        return personInfoMapper.selectPersonStatistics(teamCode);
    }

    @Override
    public boolean checkPersonCodeUnique(PersonInfo personInfo) {
        Long personId = personInfo.getId() == null ? -1L : personInfo.getId();
        LambdaQueryWrapper<PersonInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PersonInfo::getPersonCode, personInfo.getPersonCode())
                .ne(PersonInfo::getId, personId)
                .eq(PersonInfo::getDelFlag, "0");
        int count = Math.toIntExact(baseMapper.selectCount(wrapper));
        return count == 0;
    }

    @Override
    public boolean checkIdCardUnique(PersonInfo personInfo) {
        Long personId = personInfo.getId() == null ? -1L : personInfo.getId();
        LambdaQueryWrapper<PersonInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PersonInfo::getIdCard, personInfo.getIdCard())
                .ne(PersonInfo::getId, personId)
                .eq(PersonInfo::getDelFlag, "0");
        int count = Math.toIntExact(baseMapper.selectCount(wrapper));
        return count == 0;
    }

    @Override
    public List<PersonInfo> selectPersonByTeamCode(String teamCode) {
        LambdaQueryWrapper<PersonInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PersonInfo::getTeamCode, teamCode)
                .eq(PersonInfo::getDelFlag, "0")
                .eq(PersonInfo::getStatus, "0")
                .orderByAsc(PersonInfo::getPersonCode);

        List<PersonInfo> list = baseMapper.selectList(wrapper);
        list.forEach(this::calculatePersonInfo);

        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePersonTeam(Long[] personIds, String teamCode) {
        int count = 0;
        for (Long personId : personIds) {
            PersonInfo person = new PersonInfo();
            person.setId(personId);
            person.setTeamCode(teamCode);
            person.setUpdateTime(new Date());
            count += baseMapper.updateById(person);
        }
        return count;
    }

    @Override
    public void calculatePersonInfo(PersonInfo personInfo) {
        if (personInfo == null) {
            return;
        }

        // 计算年龄
        if (personInfo.getBirthDate() != null) {
            LocalDate birthDate = personInfo.getBirthDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate now = LocalDate.now();
            Period period = Period.between(birthDate, now);
            personInfo.setAgeValue(period.getYears());
            personInfo.setAge(String.valueOf(period.getYears()));
        }

        // 计算工作年限
        if (StringUtils.hasText(personInfo.getWorkYear())) {
            try {
                personInfo.setWorkYears(Integer.parseInt(personInfo.getWorkYear()));
            } catch (NumberFormatException e) {
                // 如果是范围形式（如"10-15年"），取平均值
                if (personInfo.getWorkYear().contains("-")) {
                    String[] parts = personInfo.getWorkYear().split("-");
                    if (parts.length == 2) {
                        try {
                            int min = Integer.parseInt(parts[0].replaceAll("[^0-9]", ""));
                            int max = Integer.parseInt(parts[1].replaceAll("[^0-9]", ""));
                            personInfo.setWorkYears((min + max) / 2);
                        } catch (NumberFormatException ex) {
                            personInfo.setWorkYears(0);
                        }
                    }
                }
            }
        }

        // 计算司龄
        if (personInfo.getJoinDate() != null) {
            LocalDate joinDate = personInfo.getJoinDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate now = LocalDate.now();
            Period period = Period.between(joinDate, now);
            personInfo.setCompanyYears(period.getYears());
        }
    }

    @Override
    public Map<String, Object> getPersonAbilityMatrix(String personCode) {
        Map<String, Object> matrix = new HashMap<>();

        // 查询人员的能力达成情况
        List<PersonCapability> capabilities = personCapabilityMapper.selectCapabilityByPersonCode(personCode);

        // 按能力类型分组
        Map<String, List<PersonCapability>> groupedCapabilities = capabilities.stream()
                .collect(Collectors.groupingBy(PersonCapability::getCapabilityType));

        List<Map<String, Object>> groups = new ArrayList<>();
        for (Map.Entry<String, List<PersonCapability>> entry : groupedCapabilities.entrySet()) {
            Map<String, Object> group = new HashMap<>();
            group.put("groupName", entry.getKey());
            group.put("groupType", entry.getKey());

            List<Map<String, Object>> items = entry.getValue().stream()
                    .map(cap -> {
                        Map<String, Object> item = new HashMap<>();
                        item.put("code", cap.getCapabilityCode());
                        item.put("name", cap.getCapabilityName());
                        item.put("achieved", "1".equals(cap.getIsAchieved()));
                        return item;
                    })
                    .collect(Collectors.toList());

            group.put("items", items);
            group.put("achievedCount", items.stream().filter(item -> (Boolean) item.get("achieved")).count());
            group.put("totalCount", items.size());

            groups.add(group);
        }

        matrix.put("groups", groups);
        matrix.put("totalCapabilities", capabilities.size());
        matrix.put("achievedCapabilities", capabilities.stream().filter(c -> "1".equals(c.getIsAchieved())).count());

        return matrix;
    }

    @Override
    public Map<String, Object> getPersonDevelopmentPath(String personCode) {
        Map<String, Object> path = new HashMap<>();

        // 查询岗级年限记录
        List<PersonPostGradeDuration> postGrades = personPostGradeDurationMapper.selectPostGradeByPersonCode(personCode);

        // 按时间排序
        postGrades.sort(Comparator.comparing(PersonPostGradeDuration::getStartDate));

        // 构建发展路径时间轴
        List<Map<String, Object>> timeline = postGrades.stream()
                .map(pg -> {
                    Map<String, Object> node = new HashMap<>();
                    node.put("date", pg.getStartDate());
                    node.put("postGrade", pg.getPostGrade());
                    node.put("isCurrent", "1".equals(pg.getIsCurrent()));
                    node.put("duration", pg.getActualYears());
                    return node;
                })
                .collect(Collectors.toList());

        // 查询项目经历补充发展路径
        List<PersonProject> projects = personProjectMapper.selectProjectByPersonCode(personCode);
        List<Map<String, Object>> projectNodes = projects.stream()
                .filter(p -> "project_leader".equals(p.getProjectRole()) || "tech_leader".equals(p.getProjectRole()))
                .map(p -> {
                    Map<String, Object> node = new HashMap<>();
                    node.put("date", p.getStartDate());
                    node.put("type", "project");
                    node.put("name", p.getProjectName());
                    node.put("role", p.getProjectRole());
                    node.put("level", p.getProjectLevel());
                    return node;
                })
                .collect(Collectors.toList());

        path.put("postGradeTimeline", timeline);
        path.put("projectTimeline", projectNodes);
        path.put("currentPostGrade", postGrades.stream()
                .filter(pg -> "1".equals(pg.getIsCurrent()))
                .findFirst()
                .orElse(null));

        return path;
    }

    /**
     * 保存子表数据
     */
    private void saveSubTableData(String personCode, PersonInfo personInfo) {
        // 保存项目经历
        if (!CollectionUtils.isEmpty(personInfo.getProjectList())) {
            personInfo.getProjectList().forEach(project -> {
                project.setPersonCode(personCode);
                project.setCreateBy(personInfo.getCreateBy());
                project.setCreateTime(new Date());
            });
            personProjectMapper.batchInsertProject(personInfo.getProjectList());
        }

        // 保存绩效信息
        if (!CollectionUtils.isEmpty(personInfo.getPerformanceList())) {
            personInfo.getPerformanceList().forEach(performance -> {
                performance.setPersonCode(personCode);
                performance.setCreateBy(personInfo.getCreateBy());
                performance.setCreateTime(new Date());
            });
            personPerformanceMapper.batchInsertPerformance(personInfo.getPerformanceList());
        }

        // 保存影响力信息
        if (!CollectionUtils.isEmpty(personInfo.getInfluenceList())) {
            personInfo.getInfluenceList().forEach(influence -> {
                influence.setPersonCode(personCode);
                influence.setCreateBy(personInfo.getCreateBy());
                influence.setCreateTime(new Date());
            });
            personInfluenceMapper.batchInsertInfluence(personInfo.getInfluenceList());
        }

        // 保存创新产出
        if (!CollectionUtils.isEmpty(personInfo.getInnovationList())) {
            personInfo.getInnovationList().forEach(innovation -> {
                innovation.setPersonCode(personCode);
                innovation.setCreateBy(personInfo.getCreateBy());
                innovation.setCreateTime(new Date());
            });
            personInnovationMapper.batchInsertInnovation(personInfo.getInnovationList());
        }

        // 保存人才评估
        if (!CollectionUtils.isEmpty(personInfo.getEvaluationList())) {
            personInfo.getEvaluationList().forEach(evaluation -> {
                evaluation.setPersonCode(personCode);
                evaluation.setCreateBy(personInfo.getCreateBy());
                evaluation.setCreateTime(new Date());
            });
            personEvaluationMapper.batchInsertEvaluation(personInfo.getEvaluationList());
        }

        // 保存能力达成
        if (!CollectionUtils.isEmpty(personInfo.getCapabilityList())) {
            personInfo.getCapabilityList().forEach(capability -> {
                capability.setPersonCode(personCode);
                capability.setCreateBy(personInfo.getCreateBy());
                capability.setCreateTime(new Date());
            });
            personCapabilityMapper.batchInsertCapability(personInfo.getCapabilityList());
        }

        // 保存岗级年限
        if (!CollectionUtils.isEmpty(personInfo.getPostGradeList())) {
            personInfo.getPostGradeList().forEach(postGrade -> {
                postGrade.setPersonCode(personCode);
                postGrade.setCreateBy(personInfo.getCreateBy());
                postGrade.setCreateTime(new Date());
            });
            personPostGradeDurationMapper.batchInsertPostGrade(personInfo.getPostGradeList());
        }
    }

    /**
     * 删除子表数据
     */
    private void deleteSubTableData(String personCode) {
        personProjectMapper.deleteProjectByPersonCode(personCode);
        personPerformanceMapper.deletePerformanceByPersonCode(personCode);
        personInfluenceMapper.deleteInfluenceByPersonCode(personCode);
        personInnovationMapper.deleteInnovationByPersonCode(personCode);
        personEvaluationMapper.deleteEvaluationByPersonCode(personCode);
        personCapabilityMapper.deleteCapabilityByPersonCode(personCode);
        personPostGradeDurationMapper.deletePostGradeByPersonCode(personCode);
    }
}
