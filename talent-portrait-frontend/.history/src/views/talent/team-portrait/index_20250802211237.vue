<template>
  <div class="portrait-container team-portrait">
    <!-- 扫描线效果 -->
    <div class="scan-line"></div>

    <!-- 粒子背景 -->
    <div class="particles-bg" id="particles-js"></div>

    <!-- 页面标题 -->
    <h1 class="page-title">核电科技队伍元画像</h1>

    <!-- 团队选择器 -->
    <div class="team-selector">
      <el-cascader
        v-model="currentTeamId"
        :options="teamTreeData"
        :props="{ value: 'id', label: 'teamName', children: 'children' }"
        placeholder="请选择团队"
        @change="handleTeamChange"
        size="large"
        style="width: 400px"
      />
    </div>

    <!-- 主体内容区域 -->
    <div class="content-wrapper" v-if="portraitData">
      <!-- 顶部区域 - 团队基本信息和能力九宫格 -->
      <div class="top-section">
        <!-- 团队基本信息 -->
        <div class="team-info-card data-panel">
          <h3 class="panel-title">团队信息</h3>
          <div class="info-grid">
            <div class="info-item">
              <span class="label">团队名称</span>
              <span class="value">{{ portraitData.basicInfo.teamName }}</span>
            </div>
            <div class="info-item">
              <span class="label">团队负责人</span>
              <span class="value">{{ portraitData.basicInfo.leaderName }}</span>
            </div>
            <div class="info-item">
              <span class="label">团队人数</span>
              <span class="value">{{ portraitData.basicInfo.memberCount }}人</span>
            </div>
            <div class="info-item">
              <span class="label">下级团队</span>
              <span class="value">{{ portraitData.basicInfo.subTeamCount }}个</span>
            </div>
          </div>
          <div class="team-analysis">
            <p class="analysis-text">{{ analysisText }}</p>
          </div>
        </div>

        <!-- 能力九宫格 -->
        <div class="ability-grid-panel data-panel">
          <h3 class="panel-title">能力九宫格</h3>
          <div class="grid-container" ref="abilityGridChart"></div>
          <div class="grid-legend">
            <div class="legend-item">
              <span class="dot high"></span>
              <span>明星员工</span>
            </div>
            <div class="legend-item">
              <span class="dot medium-high"></span>
              <span>核心骨干</span>
            </div>
            <div class="legend-item">
              <span class="dot medium"></span>
              <span>潜力新星</span>
            </div>
            <div class="legend-item">
              <span class="dot low"></span>
              <span>待提升</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 中间区域 - 专业能力分布 -->
      <div class="middle-section">
        <div class="skill-distribution data-panel">
          <h3 class="panel-title">专业能力分布</h3>
          <div class="skill-cards">
            <div
              v-for="(category, index) in portraitData.skillDistribution.categories"
              :key="index"
              class="skill-card"
              :class="`card-type-${index % 4}`"
            >
              <div class="card-header">
                <span class="category-name">{{ category.categoryName }}</span>
                <span class="person-count">{{ category.personCount }}人</span>
              </div>
              <div class="card-body">
                <div class="avg-experience">平均经验：{{ category.avgExperience }}年</div>
                <div class="skill-tags">
                  <span
                    v-for="(skill, idx) in category.details.slice(0, 5)"
                    :key="idx"
                    class="skill-tag"
                  >
                    {{ skill.skillName }} ({{ skill.count }})
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 底部区域 - 人才结构、队伍结构、产出贡献 -->
      <div class="bottom-section">
        <!-- 人才结构 -->
        <div class="talent-structure data-panel">
          <h3 class="panel-title">人才结构</h3>
          <div class="structure-charts">
            <div class="chart-item" ref="educationChart"></div>
            <div class="chart-item" ref="titleChart"></div>
            <div class="chart-item" ref="ageChart"></div>
          </div>
        </div>

        <!-- 队伍结构 -->
        <div class="team-structure data-panel">
          <h3 class="panel-title">队伍结构</h3>
          <div class="chart-container" ref="teamStructureChart"></div>
        </div>

        <!-- 产出贡献 -->
        <div class="contribution-output data-panel">
          <h3 class="panel-title">产出贡献</h3>
          <div class="chart-container" ref="contributionChart"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, nextTick, computed } from 'vue'
import * as echarts from 'echarts'
import { getTeamPortrait } from '@/api/core/portrait'
import { treeListTeam } from '@/api/system/team'
import type { TeamInfo } from '@/types/system'

// 当前选中的团队ID
const currentTeamId = ref<number[]>([])

// 团队树形数据
const teamTreeData = ref<TeamInfo[]>([])

// 画像数据
const portraitData = ref<any>()

// 图表实例
let abilityGridChartInstance: echarts.ECharts
let educationChartInstance: echarts.ECharts
let titleChartInstance: echarts.ECharts
let ageChartInstance: echarts.ECharts
let teamStructureChartInstance: echarts.ECharts
let contributionChartInstance: echarts.ECharts

// 分析文本
const analysisText = computed(() => {
  return '根据智慧监督平台提供的数据显示团队机构状况良好管理体系规范财务管理状况发展稳健增长良好呈现良好发展态势。学科带头综管竞突出领导业的领先核心骨干岗位配置合理'[
    [3]
  ][doc_3]
})

// 获取团队树
const getTeamTree = async () => {
  try {
    const res = await treeListTeam()
    teamTreeData.value = res.data
    if (teamTreeData.value.length > 0) {
      currentTeamId.value = [teamTreeData.value[0].id!]
      loadPortraitData()
    }
  } catch (error) {
    console.error('获取团队树失败', error)
  }
}

// 加载画像数据
const loadPortraitData = async () => {
  const teamId = currentTeamId.value[currentTeamId.value.length - 1]
  if (!teamId) return

  try {
    const res = await getTeamPortrait(teamId)
    portraitData.value = res.data

    // 等待DOM更新后初始化图表
    await nextTick()
    initCharts()
  } catch (error) {
    console.error('获取画像数据失败', error)
  }
}

// 团队切换
const handleTeamChange = () => {
  loadPortraitData()
}

// 初始化所有图表
const initCharts = () => {
  initAbilityGridChart()
  initEducationChart()
  initTitleChart()
  initAgeChart()
  initTeamStructureChart()
  initContributionChart()
}

// 初始化能力九宫格图表
const initAbilityGridChart = () => {
  const chartDom = document.querySelector('.ability-grid-panel .grid-container') as HTMLElement
  if (!chartDom) return

  if (abilityGridChartInstance) {
    abilityGridChartInstance.dispose()
  }

  abilityGridChartInstance = echarts.init(chartDom)

  const gridData = portraitData.value.abilityGrid.items

  const option = {
    backgroundColor: 'transparent',
    grid: {
      left: '15%',
      right: '10%',
      bottom: '15%',
      top: '10%',
    },
    xAxis: {
      type: 'value',
      name: '绩效',
      nameLocation: 'middle',
      nameGap: 30,
      nameTextStyle: {
        color: '#52d6f4',
        fontSize: 14,
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(32, 168, 216, 0.2)',
        },
      },
      axisLine: {
        lineStyle: {
          color: 'rgba(32, 168, 216, 0.6)',
        },
      },
      axisLabel: {
        color: '#52d6f4',
      },
    },
    yAxis: {
      type: 'value',
      name: '潜力',
      nameLocation: 'middle',
      nameGap: 50,
      nameTextStyle: {
        color: '#52d6f4',
        fontSize: 14,
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(32, 168, 216, 0.2)',
        },
      },
      axisLine: {
        lineStyle: {
          color: 'rgba(32, 168, 216, 0.6)',
        },
      },
      axisLabel: {
        color: '#52d6f4',
      },
    },
    tooltip: {
      trigger: 'item',
      backgroundColor: 'rgba(0, 20, 40, 0.8)',
      borderColor: 'rgba(32, 168, 216, 0.3)',
      textStyle: {
        color: '#52d6f4',
      },
      formatter: (params: any) => {
        const data = params.data
        return `${data.name}<br/>绩效：${data.value[0]}<br/>潜力：${data.value[1]}`
      },
    },
    series: [
      {
        type: 'scatter',
        symbolSize: 20,
        data: gridData.map((item: any) => ({
          name: item.personName,
          value: [item.performance, item.potential],
          itemStyle: {
            color: getQuadrantColor(item.quadrant),
          },
        })),
        emphasis: {
          scale: 1.5,
        },
      },
    ],
  }

  abilityGridChartInstance.setOption(option)
}

// 初始化学历分布图表
const initEducationChart = () => {
  const chartDom = document.querySelector(
    '.talent-structure .chart-item:nth-child(1)'
  ) as HTMLElement
  if (!chartDom) return

  if (educationChartInstance) {
    educationChartInstance.dispose()
  }

  educationChartInstance = echarts.init(chartDom)

  const data = portraitData.value.talentStructure.educationDistribution

  const option = {
    backgroundColor: 'transparent',
    title: {
      text: '学历分布',
      textStyle: {
        color: '#52d6f4',
        fontSize: 14,
      },
      left: 'center',
    },
    tooltip: {
      trigger: 'item',
      backgroundColor: 'rgba(0, 20, 40, 0.8)',
      borderColor: 'rgba(32, 168, 216, 0.3)',
      textStyle: {
        color: '#52d6f4',
      },
    },
    series: [
      {
        type: 'pie',
        radius: ['40%', '70%'],
        center: ['50%', '60%'],
        data: data.map((item: any) => ({
          name: item.name,
          value: item.value,
          itemStyle: {
            color: getPieColor(item.name),
          },
        })),
        label: {
          color: '#52d6f4',
          fontSize: 12,
        },
      },
    ],
  }

  educationChartInstance.setOption(option)
}

// 初始化职称分布图表
const initTitleChart = () => {
  const chartDom = document.querySelector(
    '.talent-structure .chart-item:nth-child(2)'
  ) as HTMLElement
  if (!chartDom) return

  if (titleChartInstance) {
    titleChartInstance.dispose()
  }

  titleChartInstance = echarts.init(chartDom)

  const data = portraitData.value.talentStructure.titleDistribution

  const option = {
    backgroundColor: 'transparent',
    title: {
      text: '职称分布',
      textStyle: {
        color: '#52d6f4',
        fontSize: 14,
      },
      left: 'center',
    },
    tooltip: {
      trigger: 'item',
      backgroundColor: 'rgba(0, 20, 40, 0.8)',
      borderColor: 'rgba(32, 168, 216, 0.3)',
      textStyle: {
        color: '#52d6f4',
      },
    },
    series: [
      {
        type: 'pie',
        radius: ['40%', '70%'],
        center: ['50%', '60%'],
        data: data.map((item: any) => ({
          name: item.name,
          value: item.value,
          itemStyle: {
            color: getPieColor(item.name),
          },
        })),
        label: {
          color: '#52d6f4',
          fontSize: 12,
        },
      },
    ],
  }

  titleChartInstance.setOption(option)
}

// 初始化年龄分布图表
const initAgeChart = () => {
  const chartDom = document.querySelector(
    '.talent-structure .chart-item:nth-child(3)'
  ) as HTMLElement
  if (!chartDom) return

  if (ageChartInstance) {
    ageChartInstance.dispose()
  }

  ageChartInstance = echarts.init(chartDom)

  const data = portraitData.value.talentStructure.ageDistribution

  const option = {
    backgroundColor: 'transparent',
    title: {
      text: '年龄分布',
      textStyle: {
        color: '#52d6f4',
        fontSize: 14,
      },
      left: 'center',
    },
    tooltip: {
      trigger: 'item',
      backgroundColor: 'rgba(0, 20, 40, 0.8)',
      borderColor: 'rgba(32, 168, 216, 0.3)',
      textStyle: {
        color: '#52d6f4',
      },
    },
    series: [
      {
        type: 'pie',
        radius: ['40%', '70%'],
        center: ['50%', '60%'],
        data: data.map((item: any) => ({
          name: item.name,
          value: item.value,
          itemStyle: {
            color: getPieColor(item.name),
          },
        })),
        label: {
          color: '#52d6f4',
          fontSize: 12,
        },
      },
    ],
  }

  ageChartInstance.setOption(option)
}

// 初始化队伍结构图表
const initTeamStructureChart = () => {
  const chartDom = document.querySelector('.team-structure .chart-container') as HTMLElement
  if (!chartDom) return

  if (teamStructureChartInstance) {
    teamStructureChartInstance.dispose()
  }

  teamStructureChartInstance = echarts.init(chartDom)

  const data = portraitData.value.teamStructure.levelDistribution

  const option = {
    backgroundColor: 'transparent',
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(0, 20, 40, 0.8)',
      borderColor: 'rgba(32, 168, 216, 0.3)',
      textStyle: {
        color: '#52d6f4',
      },
    },
    legend: {
      data: ['男性', '女性'],
      textStyle: {
        color: '#52d6f4',
      },
    },
    grid: {
      left: '3%',
      right: '10%',
      bottom: '3%',
      top: '15%',
      containLabel: true,
    },
    xAxis: {
      type: 'value',
      axisLine: {
        lineStyle: {
          color: 'rgba(32, 168, 216, 0.6)',
        },
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(32, 168, 216, 0.2)',
        },
      },
      axisLabel: {
        color: '#52d6f4',
      },
    },
    yAxis: {
      type: 'category',
      data: data.map((item: any) => item.category),
      axisLine: {
        lineStyle: {
          color: 'rgba(32, 168, 216, 0.6)',
        },
      },
      axisLabel: {
        color: '#52d6f4',
      },
    },
    series: [
      {
        name: '男性',
        type: 'bar',
        stack: 'total',
        itemStyle: {
          color: '#20a8d8',
        },
        data: data.map((item: any) => item.maleCount),
      },
      {
        name: '女性',
        type: 'bar',
        stack: 'total',
        itemStyle: {
          color: '#ff6b6b',
        },
        data: data.map((item: any) => item.femaleCount),
      },
    ],
  }

  teamStructureChartInstance.setOption(option)
}

// 初始化产出贡献图表
const initContributionChart = () => {
  const chartDom = document.querySelector('.contribution-output .chart-container') as HTMLElement
  if (!chartDom) return

  if (contributionChartInstance) {
    contributionChartInstance.dispose()
  }

  contributionChartInstance = echarts.init(chartDom)

  const data = portraitData.value.contributionData

  const option = {
    backgroundColor: 'transparent',
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(0, 20, 40, 0.8)',
      borderColor: 'rgba(32, 168, 216, 0.3)',
      textStyle: {
        color: '#52d6f4',
      },
    },
    legend: {
      data: ['创新产出', '项目完成', '绩效指标'],
      textStyle: {
        color: '#52d6f4',
      },
    },
    grid: {
      left: '10%',
      right: '5%',
      bottom: '10%',
      top: '15%',
    },
    xAxis: {
      type: 'category',
      data: data.years,
      axisLine: {
        lineStyle: {
          color: 'rgba(32, 168, 216, 0.6)',
        },
      },
      axisLabel: {
        color: '#52d6f4',
      },
    },
    yAxis: {
      type: 'value',
      splitLine: {
        lineStyle: {
          color: 'rgba(32, 168, 216, 0.2)',
        },
      },
      axisLine: {
        lineStyle: {
          color: 'rgba(32, 168, 216, 0.6)',
        },
      },
      axisLabel: {
        color: '#52d6f4',
      },
    },
    series: [
      {
        name: '创新产出',
        type: 'line',
        smooth: true,
        itemStyle: {
          color: '#20a8d8',
        },
        data: data.innovations[0]?.values || [],
      },
      {
        name: '项目完成',
        type: 'bar',
        itemStyle: {
          color: '#52d6f4',
        },
        data: data.projects[0]?.values || [],
      },
      {
        name: '绩效指标',
        type: 'line',
        smooth: true,
        itemStyle: {
          color: '#ffc107',
        },
        data: data.performance[0]?.values || [],
      },
    ],
  }

  contributionChartInstance.setOption(option)
}

// 获取象限颜色
const getQuadrantColor = (quadrant: string) => {
  const colorMap: Record<string, string> = {
    high: '#ff6b6b',
    'medium-high': '#ffc107',
    medium: '#20a8d8',
    low: '#6c757d',
  }
  return colorMap[quadrant] || '#20a8d8'
}

// 获取饼图颜色
const getPieColor = (name: string) => {
  const colors = ['#20a8d8', '#52d6f4', '#7cb5ec', '#90ed7d', '#f7a35c', '#ff6b6b']
  const hash = name.split('').reduce((acc, char) => acc + char.charCodeAt(0), 0)
  return colors[hash % colors.length]
}

// 监听窗口大小变化
window.addEventListener('resize', () => {
  abilityGridChartInstance?.resize()
  educationChartInstance?.resize()
  titleChartInstance?.resize()
  ageChartInstance?.resize()
  teamStructureChartInstance?.resize()
  contributionChartInstance?.resize()
})

onMounted(() => {
  getTeamTree()
})
</script>

<style lang="scss" scoped>
@import './style.scss';
</style>
