/**
 * 人才画像模拟数据
 */

// 人员名称映射
const personNames: Record<number, string> = {
  1: '许丽芹',
  2: '张三',
  3: '李四',
  4: '王五',
  5: '陈静',
  6: '赵军',
  7: '孙丽',
  8: '周明',
  9: '吴敏',
  10: '郑华',
}

// 人员编号映射
const personCodes: Record<number, string> = {
  1: 'P0013',
  2: 'P0014',
  3: 'P0015',
  4: 'P0016',
  5: 'P0017',
  6: 'P0018',
  7: 'P0019',
  8: 'P0020',
  9: 'P0021',
  10: 'P0022',
}

// 团队名称映射
const teamNames: Record<number, string> = {
  1: '核电技术研究院',
  2: '反应堆工程研究所',
  3: '核材料研究所',
  4: '核安全研究所',
  5: '堆芯设计研究室',
  6: '热工水力研究室',
  7: '安全分析研究室',
  8: '燃料元件研究室',
  9: '结构材料研究室',
  10: '辐射防护研究室',
  11: '应急响应研究室',
}

// 职位映射
const positions: Record<number, string> = {
  1: '主管研发工程师',
  2: '技术骨干',
  3: '技术工程师',
  4: '研究室主任',
  5: '技术工程师',
  6: '研究室主任',
  7: '技术骨干',
  8: '技术工程师',
  9: '技术专家',
  10: '助理工程师',
}

// 职称映射
const jobTitles: Record<number, string> = {
  1: '高级工程师',
  2: '高级工程师',
  3: '工程师',
  4: '正高级工程师',
  5: '工程师',
  6: '正高级工程师',
  7: '高级工程师',
  8: '工程师',
  9: '高级工程师',
  10: '助理工程师',
}

// 获取人员画像数据
export function getMockPersonPortrait(personId: number) {
  const baseData = {
    basicInfo: {
      personId,
      personCode: personCodes[personId] || `P00${20 + personId}`,
      personName: personNames[personId] || `员工${personId}`,
      photoUrl: null,
      teamName: teamNames[personId] || '堆芯设计研究室',
      position: positions[personId] || '技术工程师',
      jobTitle: jobTitles[personId] || '工程师',
      jobLevel: `T${Math.min(4, Math.floor((personId - 1) / 3) + 2)}`,
      education: personId === 1 ? '本科' : personId <= 5 ? '博士研究生' : '硕士研究生',
      degree: personId === 1 ? '学士' : personId <= 5 ? '博士' : '硕士',
      workYears: 15 - personId + Math.floor(personId / 2),
      companyYears: personId === 1 ? 5 : 13 - personId + Math.floor(personId / 2),
      gender: personId % 2 === 1 ? '女' : '男',
      age: personId === 1 ? 32 : 30 + personId * 2,
      ethnicity: '汉族',
      hometown: personId === 1 ? '四川省' : '广东省',
      politicalStatus: '中共党员',
      graduateSchool: personId === 1 ? '山东大学' : personId <= 3 ? '清华大学' : '北京大学',
    },
    talentInfo:
      personId === 1
        ? ['深圳市后备级人才', 'NUSSC国内工作组成员']
        : ['集团级技术专家', '省级创新团队成员'],
    developmentPath: {
      categories: ['2020年', '2021年', '2022年', '2023年', '2024年'],
      nodes: generateDevelopmentPath(personId),
    },
    abilityMatrix: {
      groups: [
        {
          groupName: '本专业',
          items: [
            { name: '组件及单元计算', level: '专家', years: 12 - (personId % 3) },
            { name: '接口电子学参数', level: '专家', years: 10 - (personId % 3) },
            { name: '核数据库分析', level: '精通', years: 10 - (personId % 4) },
            { name: '反应堆控制仿真', level: '精通', years: 8 - (personId % 3) },
            { name: '事故分析-DBC', level: '熟练', years: 6 - (personId % 2) },
            { name: '堆芯源项分析', level: '熟练', years: 5 - (personId % 2) },
          ],
        },
        {
          groupName: '跨专业',
          items: [
            { name: '压水堆设计分析', level: '精通', years: 10 - (personId % 3) },
            { name: '单棒传热实验', level: '精通', years: 8 - (personId % 3) },
            { name: '外部灾害分析', level: '熟练', years: 6 - (personId % 2) },
            { name: '反应性设计', level: '熟练', years: 5 - (personId % 2) },
            { name: '机组运行管理', level: '了解', years: 3 - (personId % 2) },
            { name: '环境监测化学', level: '了解', years: 2 },
          ],
        },
        {
          groupName: '通用专业',
          items: [
            {
              name: '换热器传热实验',
              level: personId <= 4 ? '精通' : '熟练',
              years: 8 - (personId % 4),
            },
            { name: '高级功能配置', level: '精通', years: 10 - (personId % 3) },
            {
              name: '管网流量分析',
              level: personId <= 6 ? '精通' : '熟练',
              years: 7 - (personId % 3),
            },
            { name: '设备选型设计', level: '熟练', years: 6 - (personId % 2) },
            { name: '材料选型', level: '熟练', years: 5 - (personId % 2) },
            { name: '系统集成评价', level: '了解', years: 3 },
          ],
        },
      ],
    },
    performanceData: {
      items: generatePerformanceData(personId),
    },
    evaluationData: {
      radarData: [
        { dimension: '专业能力', score: 85, maxScore: 100 },
        { dimension: '创新能力', score: 78, maxScore: 100 },
        { dimension: '协作能力', score: 92, maxScore: 100 },
        { dimension: '领导力', score: 75, maxScore: 100 },
        { dimension: '执行力', score: 88, maxScore: 100 },
        { dimension: '学习能力', score: 90, maxScore: 100 },
      ],
    },
    innovationProjects: [
      { name: '科学共同体参与度', level: '省部级', role: '参与人员' },
      { name: '核电调峰支撑技术研究', level: '集团级', role: '课题负责人' },
      { name: '华龙一号战略专项', level: '集团级', role: '子课题负责人' },
      { name: '先进反应堆技术研发', level: '国家级', role: '技术骨干' },
      { name: '核安全技术创新', level: '省部级', role: '项目成员' },
    ],
    innovationData: {
      yearData: generateInnovationData(personId),
    },
    influenceData: {
      currentInfluence: generateInfluenceData(personId),
    },
    analysisText:
      '基于人才画像综合分析，该员工在专业技术能力和创新能力方面表现突出，近年来绩效稳定在A级水平。建议：\n1. 继续加强在核心技术领域的深耕，承担更多国家级重点项目\n2. 培养跨专业协作能力，拓展技术视野\n3. 加强团队管理和领导力培训，为晋升做好准备\n4. 鼓励参与国际学术交流，提升行业影响力',
  }

  return baseData
}

// 生成发展路径数据
function generateDevelopmentPath(personId: number) {
  const paths = [
    { date: '2020年', position: '初级', team: '堆芯设计研究室' },
    { date: '2021年', position: '工程师', team: '堆芯设计研究室' },
    { date: '2022年', position: '工程师', team: '堆芯设计研究室' },
    { date: '2023年', position: '主管', team: '堆芯设计研究室' },
    { date: '2024年', position: '主管', team: '反应堆工程研究所' },
  ]

  // 根据personId调整路径
  if (personId <= 3) {
    return paths
  } else if (personId <= 6) {
    return paths.slice(1)
  } else {
    return paths.slice(2)
  }
}

// 生成绩效数据
function generatePerformanceData(personId: number) {
  const data = [
    { year: 2020, level: 'A', score: 95 },
    { year: 2021, level: 'C', score: 75 },
    { year: 2022, level: 'B', score: 85 },
    { year: 2023, level: 'A', score: 93 },
    { year: 2024, level: 'A', score: 95 },
  ]

  // 根据personId调整绩效
  if (personId > 5) {
    data[1].level = 'B'
    data[1].score = 85
    data[2].level = 'A'
    data[2].score = 92
  }

  return data
}

// 生成创新产出数据
function generateInnovationData(personId: number) {
  const baseYear = 2019
  const years = []

  for (let i = 0; i < 5; i++) {
    years.push({
      year: baseYear + i,
      patent: Math.max(0, 3 - Math.floor(personId / 4) + (i % 2)),
      paper: Math.max(1, 5 - Math.floor(personId / 3) - (i % 3)),
      standard: Math.max(0, 2 - Math.floor(personId / 5) + (i % 4)),
      software: Math.max(0, 1 + (i % 2) - Math.floor(personId / 6)),
    })
  }

  return years
}

// 生成影响力数据
function generateInfluenceData(personId: number) {
  const influences = []

  if (personId <= 4) {
    influences.push({
      id: 1,
      name: '中国核学会反应堆物理分会委员',
      organization: '中国核学会',
    })
  }

  if (personId <= 6) {
    influences.push({
      id: 2,
      name: '《核动力工程》编委',
      organization: '核动力工程编辑部',
    })
  }

  if (personId <= 3) {
    influences.push({
      id: 3,
      name: '国家核安全专家委员会委员',
      organization: '国家核安全局',
    })
  }

  return influences
}

// 获取团队画像数据
export function getMockTeamPortrait(teamId: number) {
  const teamData = {
    basicInfo: {
      teamId,
      teamName: teamNames[teamId] || '核电技术研究院',
      leaderName: getTeamLeader(teamId),
      memberCount: getMemberCount(teamId),
      subTeamCount: getSubTeamCount(teamId),
    },
    abilityGrid: {
      items: generateAbilityGridData(teamId),
    },
    skillDistribution: {
      categories: generateSkillCategories(teamId),
    },
    talentStructure: {
      educationDistribution: [
        { name: '博士', value: 15 + (teamId % 10) },
        { name: '硕士', value: 45 + (teamId % 15) },
        { name: '本科', value: 40 - (teamId % 10) },
      ],
      titleDistribution: [
        { name: '正高级', value: 10 + (teamId % 8) },
        { name: '高级', value: 30 + (teamId % 10) },
        { name: '中级', value: 40 - (teamId % 8) },
        { name: '初级', value: 20 + (teamId % 5) },
      ],
      ageDistribution: [
        { name: '30岁以下', value: 25 + (teamId % 10) },
        { name: '30-40岁', value: 45 - (teamId % 10) },
        { name: '40-50岁', value: 20 + (teamId % 8) },
        { name: '50岁以上', value: 10 + (teamId % 5) },
      ],
    },
    teamStructure: {
      levelDistribution: [
        { category: '青年', maleCount: 1000, femaleCount: -800 },
        { category: '骨干', maleCount: 700, femaleCount: -600 },
        { category: '领军', maleCount: 400, femaleCount: -300 },
      ],
    },
    contributionData: {
      years: ['2019', '2020', '2021', '2022', '2023'],
      innovations: [{ name: '创新产出', values: [100, 130, 110, 80, 70] }],
      projects: [{ name: '项目完成', values: [80, 110, 90, 60, 50] }],
      performance: [{ name: '绩效指标', values: [85, 88, 90, 92, 95] }],
    },
  }

  return teamData
}

// 辅助函数
function getTeamLeader(teamId: number): string {
  const leaders: Record<number, string> = {
    1: '陈志明',
    2: '李雪',
    3: '苏政睿',
    4: '刘所长',
    5: '王主任',
    6: '赵主任',
    7: '钱主任',
    8: '孙主任',
    9: '周主任',
    10: '吴主任',
    11: '郑主任',
  }
  return leaders[teamId] || '负责人'
}

function getMemberCount(teamId: number): number {
  const counts: Record<number, number> = {
    1: 24,
    2: 48,
    3: 35,
    4: 42,
    5: 15,
    6: 18,
    7: 15,
    8: 12,
    9: 10,
    10: 20,
    11: 22,
  }
  return counts[teamId] || 30
}

function getSubTeamCount(teamId: number): number {
  const counts: Record<number, number> = {
    1: 11,
    2: 3,
    3: 2,
    4: 2,
    5: 0,
    6: 0,
    7: 0,
    8: 0,
    9: 0,
    10: 0,
    11: 0,
  }
  return counts[teamId] || 0
}

function generateAbilityGridData(teamId: number) {
  const members = []
  const count = Math.min(20, getMemberCount(teamId))

  // 生成九宫格数据，确保有不同象限的人员
  const positions = [
    { x: 20, y: 80, quadrant: 'high' },
    { x: 50, y: 85, quadrant: 'high' },
    { x: 80, y: 90, quadrant: 'high' },
    { x: 30, y: 60, quadrant: 'medium-high' },
    { x: 60, y: 65, quadrant: 'medium-high' },
    { x: 85, y: 70, quadrant: 'medium-high' },
    { x: 40, y: 40, quadrant: 'medium' },
    { x: 70, y: 45, quadrant: 'medium' },
    { x: 90, y: 50, quadrant: 'medium' },
  ]

  positions.forEach((pos, i) => {
    if (i < count) {
      members.push({
        personName: `成员${i + 1}`,
        performance: pos.x,
        potential: pos.y,
        quadrant: pos.quadrant,
      })
    }
  })

  return members
}

function generateSkillCategories(teamId: number) {
  const categories = [
    {
      categoryName: '安全分析',
      personCount: 15,
      avgExperience: 8.5,
      details: [
        { skillName: 'LOCA类事故分析', count: 3 },
        { skillName: '蒸汽发生器传热管破裂事故分析', count: 2 },
        { skillName: '辐射剂量及屏蔽设计程序应用', count: 1 },
        { skillName: '蒸汽发生器热工水力', count: 1 },
        { skillName: '程序源项与屏蔽计算方法', count: 1 },
      ],
    },
    {
      categoryName: '堆芯设计',
      personCount: 13,
      avgExperience: 7.2,
      details: [
        { skillName: '安全系统及控制相关功能设计', count: 1 },
        { skillName: '失电及暂态运行分析', count: 1 },
        { skillName: '堆外相对中子注量率设计', count: 1 },
        { skillName: '堆芯功率分布与燃耗设计', count: 1 },
        { skillName: '反应堆压力容器热工水力', count: 1 },
      ],
    },
    {
      categoryName: '辐射与屏蔽',
      personCount: 10,
      avgExperience: 6.8,
      details: [
        { skillName: '事故经验反馈及分析', count: 2 },
        { skillName: '事故处理和应急设计', count: 2 },
        { skillName: '燃料组件及堆芯机械设计', count: 2 },
        { skillName: '反应堆控制功能设计', count: 2 },
        { skillName: 'DEC事故管理与控制', count: 2 },
      ],
    },
    {
      categoryName: '热工水力',
      personCount: 8,
      avgExperience: 9.2,
      details: [
        { skillName: '传热分析', count: 2 },
        { skillName: '流体力学', count: 2 },
        { skillName: '事故分析', count: 1 },
        { skillName: '系统建模', count: 1 },
        { skillName: '热工设计', count: 1 },
      ],
    },
    {
      categoryName: '严重事故',
      personCount: 6,
      avgExperience: 5.5,
      details: [
        { skillName: '严重事故分析', count: 2 },
        { skillName: '事故缓解措施', count: 1 },
        { skillName: '氢气控制', count: 1 },
        { skillName: '熔融物冷却', count: 1 },
        { skillName: '安全壳完整性', count: 1 },
      ],
    },
  ]

  return categories
}
