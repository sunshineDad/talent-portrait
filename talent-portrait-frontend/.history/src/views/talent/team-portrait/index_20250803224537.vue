<template>
  <div class="team-portrait">
    <!-- 网格背景 -->
    <div class="grid-bg"></div>

    <!-- 粒子效果 -->
    <div class="particles" id="particles"></div>

    <div class="container">
      <!-- 顶部信息 -->
      <div class="header-info">
        <div class="logo">
          <span class="cn">某核</span>
          <span class="en">CGN</span>
        </div>
        <div class="datetime">{{ currentTime }}</div>
      </div>

      <!-- 主标题 -->
      <div class="main-title">
        <div class="title-wrapper">
          <div class="title-border">
            <div class="title-corner tl"></div>
            <div class="title-corner br"></div>
          </div>
          <h1 class="title-text">科技人才&队伍画像</h1>
        </div>
      </div>

      <!-- 主内容区 -->
      <div class="main-content" v-if="portraitData">
        <!-- 左侧区域 -->
        <div class="left-section">
          <!-- 能力九宫格 -->
          <div class="module ability-grid">
            <div class="scan-line"></div>
            <h3 class="module-title">能力九宫格</h3>
            <div class="grid-container">
              <div class="grid-axis-label">能力分数</div>
              <div class="grid-wrapper">
                <div class="grid-cell"></div>
                <div class="grid-cell"><div class="grid-dot"></div></div>
                <div class="grid-cell"><div class="grid-dot"></div></div>
                <div class="grid-cell"><div class="grid-dot"></div></div>
                <div class="grid-cell"><div class="grid-dot"></div></div>
                <div class="grid-cell"><div class="grid-dot"></div></div>
                <div class="grid-cell"></div>
                <div class="grid-cell"><div class="grid-dot"></div></div>
                <div class="grid-cell"><div class="grid-dot"></div></div>
              </div>
              <div class="grid-x-axis">
                <span>0</span>
                <span>33</span>
                <span>66</span>
                <span>99</span>
              </div>
              <div class="grid-y-axis">
                <span>99</span>
                <span>66</span>
                <span>33</span>
                <span>0</span>
              </div>
            </div>
            <div class="grid-legend">
              <div class="legend-item">
                <div class="legend-dot" style="background: #0099ff"></div>
                <span>领军人才</span>
              </div>
              <div class="legend-item">
                <div class="legend-dot" style="background: #00ff88"></div>
                <span>青优秀</span>
              </div>
            </div>
          </div>

          <!-- 专业能力 -->
          <div class="module professional-ability">
            <div class="scan-line"></div>
            <h3 class="module-title">专业能力</h3>
            <div class="ability-tabs">
              <button
                v-for="(tab, index) in abilityTabs"
                :key="index"
                class="ability-tab"
                :class="{ active: activeAbilityTab === index }"
                @click="switchAbilityTab(index)"
              >
                {{ tab }}
              </button>
            </div>
            <div class="ability-cards">
              <div
                v-for="(skill, index) in currentAbilitySkills"
                :key="index"
                class="ability-card"
              >
                <div class="ability-name">{{ skill.skillName }}</div>
                <div class="ability-count">{{ skill.count }}</div>
              </div>
            </div>
          </div>
        </div>

        <!-- 中间区域 -->
        <div class="center-section">
          <!-- 统计数据 -->
          <div class="stats-row">
            <div class="stat-item">
              <div class="stat-label">总人数</div>
              <div class="stat-value">{{ portraitData.basicInfo.memberCount }}<span class="stat-unit">个</span></div>
            </div>
            <div class="stat-item">
              <div class="stat-label">平均年龄</div>
              <div class="stat-value">34<span class="stat-unit">岁</span></div>
            </div>
            <div class="stat-item">
              <div class="stat-label">硕博比例</div>
              <div class="stat-value">83.3<span class="stat-unit">%</span></div>
            </div>
            <div class="stat-item">
              <div class="stat-label">高级职称</div>
              <div class="stat-value">33.3<span class="stat-unit">%</span></div>
            </div>
          </div>

          <!-- 饼图区域 -->
          <div class="module pie-section">
            <div class="scan-line"></div>
            <div id="pieChart" ref="pieChart"></div>
            <div class="pie-legend">
              <div class="pie-legend-title">
                <div class="pie-legend-box"></div>
                <span>领军人才</span>
              </div>
              <div class="pie-legend-list">
                <div class="pie-legend-item">陈志明</div>
                <div class="pie-legend-item">李雪</div>
                <div class="pie-legend-item">苏政睿</div>
              </div>
            </div>
          </div>

          <!-- 人才分布 -->
          <div class="talent-distribution">
            <div class="talent-item">
              <div class="talent-dot" style="background: #0099ff"></div>
              <span class="talent-label">应用人员研究和<br>科研管理人员</span>
              <span class="talent-value">60%</span>
            </div>
            <div class="talent-item">
              <div class="talent-dot" style="background: #00d4ff"></div>
              <span class="talent-label">科技管理人员</span>
              <span class="talent-value">18%</span>
            </div>
            <div class="talent-item">
              <div class="talent-dot" style="background: #00ff88"></div>
              <span class="talent-label">基础研究人员</span>
              <span class="talent-value">13%</span>
            </div>
            <div class="talent-item">
              <div class="talent-dot" style="background: #ff6600"></div>
              <span class="talent-label">领军人才</span>
              <span class="talent-value">9%</span>
            </div>
          </div>

          <!-- 分析评估建议 -->
          <div class="module">
            <div class="scan-line"></div>
            <h3 class="module-title">分析评估建议</h3>
            <div class="analysis-content">
              根据智慧监督平台提供的数据显示团队机构状况良好管理体系规范财务管理状况发展稳健增长良好呈现良好发展态势<br>
              学科带头综管竞突出领导业的领先核心骨干岗位配置合理组织架构完美已形成完整学科体系发展态势呈现良好上开月度整体发展指标继续领军整数据指标提升显著确道路选择正确
            </div>
          </div>
        </div>

        <!-- 右侧区域 -->
        <div class="right-section">
          <!-- 科研队伍结构 -->
          <div class="module team-structure">
            <div class="scan-line"></div>
            <h3 class="module-title">科研队伍结构</h3>
            <div class="structure-tabs">
              <button
                v-for="(tab, index) in structureTabs"
                :key="index"
                class="structure-tab"
                :class="{ active: activeStructureTab === index }"
                @click="switchStructureTab(index)"
              >
                {{ tab }}
              </button>
            </div>
            <div id="teamChart" ref="teamChart"></div>
          </div>

          <!-- 产出与贡献 -->
          <div class="module contribution">
            <div class="scan-line"></div>
            <h3 class="module-title">产出与贡献</h3>
            <div class="contribution-legend">
              <div class="contribution-legend-item">
                <div class="contribution-dot" style="background: #ff6600"></div>
                <span>实际分数</span>
              </div>
              <div class="contribution-legend-item">
                <div class="contribution-dot" style="background: #0099ff"></div>
                <span>科研分数</span>
              </div>
            </div>
            <div id="contributionChart" ref="contributionChart"></div>
          </div>
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

// 当前时间
const currentTime = ref('')

// 画像数据
const portraitData = ref<any>()

// 能力标签页
const abilityTabs = ref(['安全分析', '堆芯设计', '辐射与屏蔽', '热工水力', '严重事故'])
const activeAbilityTab = ref(0)

// 结构标签页
const structureTabs = ref(['实际', '规划'])
const activeStructureTab = ref(0)

// 图表实例
let pieChartInstance: echarts.ECharts
let teamChartInstance: echarts.ECharts
let contributionChartInstance: echarts.ECharts

// 当前显示的专业能力
const currentAbilitySkills = computed(() => {
  if (!portraitData.value) return []
  const category = portraitData.value.skillDistribution.categories[activeAbilityTab.value]
  return category ? category.details : []
})

// 更新时间
const updateDateTime = () => {
  const now = new Date()
  currentTime.value = now.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    hour12: false
  }).replace(/\//g, '-')
}

// 切换能力标签
const switchAbilityTab = (index: number) => {
  activeAbilityTab.value = index
}

// 切换结构标签
const switchStructureTab = (index: number) => {
  activeStructureTab.value = index
  updateTeamChart()
}

// 加载团队画像数据
const loadPortraitData = async () => {
  try {
    // 默认加载第一个团队的数据
    const res = await getTeamPortrait('1')
    portraitData.value = res.data

    await nextTick()
    initCharts()
  } catch (error) {
    console.error('获取团队画像数据失败', error)
  }
}

// 初始化所有图表
const initCharts = () => {
  initPieChart()
  initTeamChart()
  initContributionChart()
}

// 初始化饼图
const initPieChart = () => {
  const chartDom = document.getElementById('pieChart')
  if (!chartDom) return

  if (pieChartInstance) {
    pieChartInstance.dispose()
  }

  pieChartInstance = echarts.init(chartDom)

  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c}%',
      backgroundColor: 'rgba(0, 0, 0, 0.8)',
      borderColor: '#0099ff',
      borderWidth: 1,
      textStyle: {
        color: '#fff'
      }
    },
    series: [{
      type: 'pie',
      radius: ['45%', '75%'],
      center: ['50%', '50%'],
      data: [
        {
          value: 60,
          name: '领军人才',
          itemStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: '#0099ff' },
              { offset: 1, color: '#004488' }
            ])
          }
        },
        {
          value: 26,
          name: '青优秀',
          itemStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: '#00d4ff' },
              { offset: 1, color: '#0066aa' }
            ])
          }
        },
        {
          value: 13,
          name: '骨干',
          itemStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: '#00ff88' },
              { offset: 1, color: '#00aa55' }
            ])
          }
        },
        {
          value: 9,
          name: '新人才',
          itemStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: '#ff6600' },
              { offset: 1, color: '#cc3300' }
            ])
          }
        }
      ],
      label: {
        show: true,
        position: 'inside',
        formatter: '{d}%',
        color: '#fff',
        fontSize: 16,
        fontWeight: 'bold'
      },
      emphasis: {
        scale: true,
        scaleSize: 10,
        label: {
          fontSize: 18
        }
      },
      itemStyle: {
        borderRadius: 10,
        shadowBlur: 30,
        shadowColor: 'rgba(0, 0, 0, 0.5)',
        shadowOffsetY: 10
      }
    }]
  }

  pieChartInstance.setOption(option)
}

// 初始化团队结构图
const initTeamChart = () => {
  const chartDom = document.getElementById('teamChart')
  if (!chartDom) return

  if (teamChartInstance) {
    teamChartInstance.dispose()
  }

  teamChartInstance = echarts.init(chartDom)
  updateTeamChart()
}

// 更新团队结构图
const updateTeamChart = () => {
  if (!teamChartInstance) return

  const isActual = activeStructureTab.value === 0

  const option = {
    grid: {
      left: '20%',
      right: '15%',
      top: '10%',
      bottom: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'value',
      min: -1200,
      max: 1200,
      axisLine: {
        show: true,
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.2)'
        }
      },
      axisTick: {
        show: false
      },
      axisLabel: {
        show: false
      },
      splitLine: {
        show: false
      }
    },
    yAxis: {
      type: 'category',
      data: ['青年', '骨干', '领军'],
      axisLine: {
        show: false
      },
      axisTick: {
        show: false
      },
      axisLabel: {
        color: '#8899aa',
        fontSize: 16,
        margin: 20
      }
    },
    series: [
      {
        name: '实际',
        type: 'bar',
        stack: 'total',
        barWidth: 25,
        data: isActual ? [1000, 700, 400] : [1200, 900, 500],
        itemStyle: {
          color: '#ff6600',
          borderRadius: [0, 15, 15, 0]
        },
        label: {
          show: true,
          position: 'right',
          formatter: '{c}',
          color: '#ff6600',
          fontSize: 14,
          fontWeight: 'bold'
        }
      },
      {
        name: '规划',
        type: 'bar',
        stack: 'total',
        barWidth: 25,
        data: isActual ? [-800, -600, -300] : [-1000, -800, -400],
        itemStyle: {
          color: '#0099ff',
          borderRadius: [15, 0, 0, 15]
        },
        label: {
          show: true,
          position: 'left',
          formatter: function(params: any) {
            return Math.abs(params.value)
          },
          color: '#0099ff',
          fontSize: 14,
          fontWeight: 'bold'
        }
      }
    ]
  }

  teamChartInstance.setOption(option)
}

// 初始化贡献图表
const initContributionChart = () => {
  const chartDom = document.getElementById('contributionChart')
  if (!chartDom) return

  if (contributionChartInstance) {
    contributionChartInstance.dispose()
  }

  contributionChartInstance = echarts.init(chartDom)

  const option = {
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(0, 0, 0, 0.8)',
      borderColor: '#0099ff',
      borderWidth: 1,
      textStyle: {
        color: '#fff'
      }
    },
    grid: {
      left: '5%',
      right: '5%',
      bottom: '25%',
      top: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: ['国市级科研计划\n科技工程类', '反成堆心物理\n安全设安', '国家级标准制定', '学术著作奖量', '国家美展特科技\n奖章'],
      axisLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.2)'
        }
      },
      axisLabel: {
        color: '#6688aa',
        fontSize: 12,
        interval: 0,
        rotate: -20
      }
    },
    yAxis: {
      type: 'value',
      max: 140,
      axisLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.2)'
        }
      },
      axisLabel: {
        color: '#6688aa'
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.1)'
        }
      }
    },
    series: [
      {
        name: '科研分数',
        type: 'bar',
        data: [80, 110, 90, 60, 50],
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#0099ff' },
            { offset: 1, color: '#004488' }
          ]),
          borderRadius: [6, 6, 0, 0]
        }
      },
      {
        name: '实际分数',
        type: 'line',
        data: [100, 130, 110, 80, 70],
        smooth: true,
        symbol: 'circle',
        symbolSize: 8,
        lineStyle: {
          color: '#ff6600',
          width: 3,
          shadowBlur: 10,
          shadowColor: '#ff6600'
        },
        itemStyle: {
          color: '#ff6600',
          borderWidth: 2,
          borderColor: '#fff'
        }
      }
    ]
  }

  contributionChartInstance.setOption(option)
}

// 生成粒子效果
const createParticles = () => {
  const particlesContainer = document.getElementById('particles')
  if (!particlesContainer) return

  for (let i = 0; i < 50; i++) {
    const particle = document.createElement('div')
    particle.className = 'particle'
    particle.style.left = Math.random() * 100 + '%'
    particle.style.animationDelay = Math.random() * 10 + 's'
    particle.style.animationDuration = (Math.random() * 10 + 10) + 's'
    particlesContainer.appendChild(particle)
  }
}

// 监听窗口大小变化
window.addEventListener('resize', () => {
  pieChartInstance?.resize()
  teamChartInstance?.resize()
  contributionChartInstance?.resize()
})

onMounted(() => {
  updateDateTime()
  setInterval(updateDateTime, 60000)
  createParticles()
  loadPortraitData()
})
</script>

<style lang="scss" scoped>
@import './style.scss';
</style>
