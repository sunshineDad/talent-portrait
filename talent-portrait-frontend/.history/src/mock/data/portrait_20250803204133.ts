/**
 * 人才画像模拟数据
 */

// 人员名称映射
const personNames: Record<number, string> = {
  1: '张伟',
  2: '李娜',
  3: '王强',
  4: '刘洋',
  5: '陈静',
  6: '赵军',
  7: '孙丽',
  8: '周明',
  9: '吴敏',
  10: '郑华',
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
  1: '首席专家',
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
  1: '正高级工程师',
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
      personCode: `NP00${personId}`,
      personName: personNames[personId] || `员工${personId}`,
      photoUrl: `/images/avatar/avatar${personId}.jpg`,
      teamName: teamNames[personId] || '堆芯设计研究室',
      position: positions[personId] || '技术工程师',
      jobTitle: jobTitles[personId] || '工程师',
      jobLevel: `T${Math.min(4, Math.floor((personId - 1) / 3) + 2)}`,
      education: personId <= 5 ? '博士研究生' : '硕士研究生',
      degree: personId <= 5 ? '博士' : '硕士',
      workYears: 15 - personId + Math.floor(personId / 2),
      companyYears: 13 - personId + Math.floor(personId / 2),
    },
    developmentPath: {
      categories: ['2010', '2012', '2014', '2016', '2018', '2020', '2022', '2024'],
      nodes: generateDevelopmentPath(personId),
    },
    abilityMatrix: {
      groups: [
        {
          groupName: '专业技能',
          items: [
            { name: '反应堆物理', level: '专家', years: 12 - (personId % 3) },
            { name: '堆芯设计', level: '专家', years: 10 - (personId % 3) },
            { name: '中子学计算', level: '精通', years: 10 - (personId % 4) },
            { name: '临界安全', level: '精通', years: 8 - (personId % 3) },
          ],
        },
        {
          groupName: '软件技能',
          items: [
            { name: 'MCNP', level: '精通', years: 10 - (personId % 3) },
            { name: 'RELAP5', level: '精通', years: 8 - (personId % 3) },
            { name: 'COBRA', level: '熟练', years: 6 - (personId % 2) },
            { name: 'TRACE', level: '熟练', years: 5 - (personId % 2) },
          ],
        },
        {
          groupName: '管理技能',
          items: [
            { name: '项目管理', level: personId <= 4 ? '精通' : '熟练', years: 8 - (personId % 4) },
            { name: '团队协作', level: '精通', years: 10 - (personId % 3) },
            { name: '技术指导', level: personId <= 6 ? '精通' : '熟练', years: 7 - (personId % 3) },
          ],
        },
      ],
    },
    performanceData: {
      items: generatePerformanceData(personId),
    },
    evaluationData: {
      radarData: [
        { dimension: '专业能力', score: 90 + (personId % 10), maxScore: 100 },
        { dimension: '创新能力', score: 85 + (personId % 8), maxScore: 100 },
        { dimension: '团队协作', score: 88 + (personId % 7), maxScore: 100 },
        { dimension: '学习能力', score: 92 + (personId % 5), maxScore: 100 },
        { dimension: '执行力', score: 90 + (personId % 6), maxScore: 100 },
        {
          dimension: '领导力',
          score: personId <= 4 ? 85 + (personId % 10) : 75 + (personId % 10),
          maxScore: 100,
        },
      ],
    },
    innovationData: {
      yearData: generateInnovationData(personId),
    },
    influenceData: {
      currentInfluence: generateInfluenceData(personId),
    },
  }

  return baseData
}

// 生成发展路径数据
function generateDevelopmentPath(personId: number) {
  const paths = [
    { date: '2010-07', position: '助理工程师', team: '堆芯设计研究室' },
    { date: '2012-12', position: '工程师', team: '堆芯设计研究室' },
    { date: '2014-06', position: '高级工程师', team: '堆芯设计研究室' },
    { date: '2016-12', position: '技术骨干', team: '堆芯设计研究室' },
    { date: '2018-06', position: '技术专家', team: '反应堆工程研究所' },
    { date: '2020-12', position: '首席专家', team: '反应堆工程研究所' },
    { date: '2022-06', position: '首席专家', team: '核电技术研究院' },
    { date: '2024-01', position: '首席专家/学科带头人', team: '核电技术研究院' },
  ]

  // 根据personId调整路径
  const adjustedPaths = paths.slice(0, Math.max(4, 8 - Math.floor(personId / 3)))
  return adjustedPaths
}

// 生成绩效数据
function generatePerformanceData(personId: number) {
  const years = [2019, 2020, 2021, 2022, 2023]
  const levels = ['A', 'A', 'B+', 'A', 'A']
  const scores = [95, 92, 88, 93, 95]

  return years.map((year, index) => ({
    year,
    level: levels[(index + personId) % levels.length],
    score: scores[(index + personId) % scores.length] - (personId % 5),
  }))
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
        { category: '管理层', maleCount: 3 + (teamId % 3), femaleCount: 1 + (teamId % 2) },
        { category: '技术专家', maleCount: 8 + (teamId % 5), femaleCount: 4 + (teamId % 3) },
        { category: '技术骨干', maleCount: 15 + (teamId % 8), femaleCount: 10 + (teamId % 5) },
        { category: '一般员工', maleCount: 20 + (teamId % 10), femaleCount: 15 + (teamId % 8) },
      ],
    },
    contributionData: {
      years: ['2019', '2020', '2021', '2022', '2023'],
      innovations: [{ name: '创新产出', values: [15, 18, 22, 25, 30] }],
      projects: [{ name: '项目完成', values: [8, 10, 12, 15, 18] }],
      performance: [{ name: '绩效指标', values: [85, 88, 90, 92, 95] }],
    },
  }

  return teamData
}

// 辅助函数
function getTeamLeader(teamId: number): string {
  const leaders: Record<number, string> = {
    1: '张院长',
    2: '李所长',
    3: '陈所长',
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
    1: 156,
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

  for (let i = 0; i < count; i++) {
    const performance = 60 + Math.random() * 40
    const potential = 60 + Math.random() * 40

    let quadrant = 'medium'
    if (performance > 85 && potential > 85) quadrant = 'high'
    else if (performance > 75 && potential > 75) quadrant = 'medium-high'
    else if (performance < 70 || potential < 70) quadrant = 'low'

    members.push({
      personName: `成员${i + 1}`,
      performance: Math.round(performance),
      potential: Math.round(potential),
      quadrant,
    })
  }

  return members
}

function generateSkillCategories(teamId: number) {
  const categories = [
    {
      categoryName: '反应堆物理',
      personCount: 12 + (teamId % 8),
      avgExperience: 8.5,
      details: [
        { skillName: '堆芯设计', count: 8 },
        { skillName: '临界安全', count: 6 },
        { skillName: '中子学计算', count: 5 },
        { skillName: '屏蔽设计', count: 4 },
        { skillName: '燃料管理', count: 3 },
      ],
    },
    {
      categoryName: '热工水力',
      personCount: 10 + (teamId % 6),
      avgExperience: 7.2,
      details: [
        { skillName: '传热分析', count: 7 },
        { skillName: '流体力学', count: 6 },
        { skillName: '事故分析', count: 5 },
        { skillName: '系统建模', count: 4 },
        { skillName: '热工设计', count: 3 },
      ],
    },
    {
      categoryName: '核材料',
      personCount: 8 + (teamId % 5),
      avgExperience: 6.8,
      details: [
        { skillName: '燃料元件', count: 5 },
        { skillName: '结构材料', count: 4 },
        { skillName: '腐蚀防护', count: 4 },
        { skillName: '材料测试', count: 3 },
        { skillName: '辐照效应', count: 3 },
      ],
    },
    {
      categoryName: '核安全',
      personCount: 15 + (teamId % 7),
      avgExperience: 9.2,
      details: [
        { skillName: '安全分析', count: 10 },
        { skillName: '辐射防护', count: 8 },
        { skillName: '应急响应', count: 6 },
        { skillName: '法规标准', count: 5 },
        { skillName: '风险评估', count: 4 },
      ],
    },
  ]

  return categories
}
