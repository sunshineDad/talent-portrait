<template>
  <div class="person-portrait">
    <!-- 氛围灯效果 -->
    <div class="ambient-light"></div>

    <!-- 扫描线效果 -->
    <div class="scan-line"></div>

    <!-- 网格背景 -->
    <div class="grid-bg"></div>

    <div class="container">
      <!-- 头部 -->
      <header class="header">
        <div class="logo">某核<span>CGN</span></div>
        <h1 class="title">科技人才&队伍全画像</h1>
        <div class="datetime" id="datetime">{{ currentTime }}</div>
      </header>

      <!-- 主要内容 -->
      <div class="main-content" v-if="portraitData">
        <!-- 左侧面板 -->
        <aside class="left-panel">
          <!-- 发展周期路径 -->
          <section class="module career-path">
            <h3 class="module-title">发展周期路径</h3>
            <div class="path-chart" ref="pathChart"></div>
          </section>

          <!-- 本专业及跨专业能力 -->
          <section class="module abilities">
            <h3 class="module-title">本专业及跨专业能力</h3>
            <div class="ability-grid">
              <div class="ability-category" v-for="(group, index) in portraitData.abilityMatrix.groups" :key="index">
                <h4>{{ group.groupName }}</h4>
                <div class="ability-items">
                  <div class="ability-item" v-for="(item, idx) in group.items" :key="idx">
                    {{ item.name }}
                  </div>
                </div>
              </div>
            </div>
          </section>
        </aside>

        <!-- 中间面板 -->
        <main class="center-panel">
          <!-- 人物画像 -->
          <section class="portrait-section">
            <div class="portrait-container">
              <div class="portrait-glow"></div>
              <img :src="portraitData.basicInfo.photoUrl || defaultAvatar" alt="员工头像" class="portrait-img">
            </div>

            <div class="employee-info">
              <div class="employee-name">{{ portraitData.basicInfo.personName }}【{{ portraitData.basicInfo.personCode }}】</div>
              <div class="employee-title">{{ portraitData.basicInfo.position }} {{ portraitData.basicInfo.jobTitle }}</div>
              <select class="employee-select" v-model="currentPersonId" @change="handlePersonChange">
                <option v-for="person in personList" :key="person.id" :value="person.id">
                  {{ person.personName }}【{{ person.personCode }}】
                </option>
              </select>
            </div>

            <div class="tab-buttons">
              <button class="tab-btn" :class="{ active: activeTab === 'basic' }" @click="switchTab('basic')">基本信息</button>
              <button class="tab-btn" :class="{ active: activeTab === 'talent' }" @click="switchTab('talent')">人才信息</button>
            </div>

            <div class="info-container">
              <div class="basic-info" v-show="activeTab === 'basic'">
                <div class="info-item">
                  <div class="info-label">性别</div>
                  <div class="info-value">{{ portraitData.basicInfo.gender }}</div>
                </div>
                <div class="info-item">
                  <div class="info-label">年龄</div>
                  <div class="info-value">{{ portraitData.basicInfo.age }}岁</div>
                </div>
                <div class="info-item">
                  <div class="info-label">民族</div>
                  <div class="info-value">{{ portraitData.basicInfo.ethnicity }}</div>
                </div>
                <div class="info-item">
                  <div class="info-label">学历</div>
                  <div class="info-value">{{ portraitData.basicInfo.education }}</div>
                </div>
                <div class="info-item">
                  <div class="info-label">籍贯</div>
                  <div class="info-value">{{ portraitData.basicInfo.hometown }}</div>
                </div>
                <div class="info-item">
                  <div class="info-label">政治面貌</div>
                  <div class="info-value">{{ portraitData.basicInfo.politicalStatus }}</div>
                </div>
                <div class="info-item">
                  <div class="info-label">毕业院校</div>
                  <div class="info-value">{{ portraitData.basicInfo.graduateSchool }}</div>
                </div>
                <div class="info-item">
                  <div class="info-label">职称</div>
                  <div class="info-value">{{ portraitData.basicInfo.jobTitle }}</div>
                </div>
                <div class="info-item">
                  <div class="info-label">司龄</div>
                  <div class="info-value">{{ portraitData.basicInfo.companyYears }}年司龄</div>
                </div>
              </div>

              <div class="talent-info" v-show="activeTab === 'talent'">
                <div class="info-item" v-for="(item, index) in portraitData.talentInfo" :key="index">
                  <div class="info-value">{{ item }}</div>
                </div>
              </div>
            </div>
          </section>

          <!-- 绩效情况 -->
          <section class="module performance">
            <h3 class="module-title">绩效情况</h3>
            <div class="performance-years">
              <div class="year-item" v-for="item in portraitData.performanceData.items" :key="item.year">
                <div class="year-label">{{ item.year }}年</div>
                <div class="grade" :class="`grade-${item.level.toLowerCase()}`">{{ item.level }}</div>
              </div>
            </div>
          </section>

          <!-- 创新影响力 -->
          <section class="module innovation">
            <h3 class="module-title">创新影响力</h3>
            <div class="innovation-table">
              <div class="innovation-header">
                <div>正在承担的科研项目</div>
                <div>项目层级</div>
                <div>项目角色</div>
              </div>
              <div class="innovation-row" v-for="(project, index) in portraitData.innovationProjects" :key="index">
                <div>{{ project.name }}</div>
                <div>{{ project.level }}</div>
                <div>{{ project.role }}</div>
              </div>
            </div>
          </section>
        </main>

        <!-- 右侧面板 -->
        <aside class="right-panel">
          <!-- 人才评估 -->
          <section class="module evaluation">
            <h3 class="module-title">
              人才评估
              <button class="switch-btn" @click="switchChart">切换明细</button>
            </h3>

            <!-- 仪表盘视图 -->
            <div class="gauge-container" v-show="!isRadarView">
              <div class="gauge-item" v-for="(item, index) in gaugeData" :key="index">
                <div class="gauge-chart" :ref="el => gaugeRefs[index] = el as HTMLElement"></div>
                <div class="gauge-info">
                  <span><i class="dot dot-actual"></i>实际值</span>
                  <span><i class="dot dot-target"></i>达标值</span>
                  <span><i class="dot dot-benchmark"></i>标杆值</span>
                </div>
              </div>
            </div>

            <!-- 雷达图视图 -->
            <div class="radar-chart" :class="{ active: isRadarView }" ref="radarChart"></div>
          </section>

          <!-- 创新产出 -->
          <section class="module innovation-output">
            <h3 class="module-title">创新产出</h3>
            <div class="output-chart" ref="outputChart"></div>
          </section>
        </aside>

        <!-- 分析评估建议 -->
        <section class="module analysis-section">
          <h3 class="module-title">分析评估建议</h3>
          <div class="analysis-content">
            {{ portraitData.analysisText }}
          </div>
        </section>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, nextTick, computed } from 'vue'
import * as echarts from 'echarts'
import { getPersonPortrait } from '@/api/core/portrait'
import { listPerson } from '@/api/core/person'
import type { PersonInfo } from '@/types/person'

// 默认头像
const defaultAvatar = 'data:image/svg+xml,%3Csvg xmlns=\'http://www.w3.org/2000/svg\' width=\'140\' height=\'140\'%3E%3Crect width=\'140\' height=\'140\' fill=\'%231a2f4e\' rx=\'70\'/%3E%3Ctext x=\'50%25\' y=\'50%25\' text-anchor=\'middle\' dy=\'.3em\' fill=\'%23667788\' font-size=\'14\'%3E头像%3C/text%3E%3C/svg%3E'

// 当前选中的人员ID
const currentPersonId = ref<number>()

// 人员列表
const personList = ref<PersonInfo[]>([])

// 画像数据
const portraitData = ref<any>()

// 当前时间
const currentTime = ref('')

// 活动标签
const activeTab = ref('basic')

// 是否雷达图视图
const isRadarView = ref(false)

// 仪表盘数据
const gaugeData = ref([
  { id: 'gauge1', name: '专业能力', value: 85, target: 70, benchmark: 90 },
  { id: 'gauge2', name: '创新能力', value: 78, target: 65, benchmark: 85 },
  { id: 'gauge3', name: '协作能力', value: 92, target: 75, benchmark: 95 },
  { id: 'gauge4', name: '领导力', value: 75, target: 60, benchmark: 80 }
])

// 仪表盘引用
const gaugeRefs = ref<(HTMLElement | null)[]>([])

// 图表实例
let pathChartInstance: echarts.ECharts
let radarChartInstance: echarts.ECharts
let outputChartInstance: echarts.ECharts
let gaugeChartInstances: echarts.ECharts[] = []

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

// 切换标签
const switchTab = (tab: string) => {
  activeTab.value = tab
}

// 切换图表
const switchChart = () => {
  isRadarView.value = !isRadarView.value
  if (isRadarView.value && !radarChartInstance) {
    nextTick(() => {
      initRadarChart()
    })
  }
}

// 获取人员列表
const getPersonList = async () => {
  try {
    const res = await listPerson({ current: 1, size: 1000 })
    personList.value = res.data.records
    if (personList.value.length > 0) {
      currentPersonId.value = personList.value[0].id
      loadPortraitData()
    }
  } catch (error) {
    console.error('获取人员列表失败', error)
  }
}

// 加载画像数据
const loadPortraitData = async () => {
  if (!currentPersonId.value) return

  try {
    const res = await getPersonPortrait(currentPersonId.value)
    portraitData.value = res.data

    // 等待DOM更新后初始化图表
    await nextTick()
    initCharts()
  } catch (error) {
    console.error('获取画像数据失败', error)
  }
}

// 人员切换
const handlePersonChange = () => {
  loadPortraitData()
}

// 初始化图表
const initCharts = () => {
  initPathChart()
  initGauges()
  initOutputChart()
}

// 初始化发展周期路径图
const initPathChart = () => {
  const chartDom = document.querySelector('.path-chart') as HTMLElement
  if (!chartDom) return

  if (pathChartInstance) {
    pathChartInstance.dispose()
  }

  pathChartInstance = echarts.init(chartDom)

  const option = {
    backgroundColor: 'transparent',
    grid: {
      left: '20%',
      right: '10%',
      bottom: '15%',
      top: '10%'
    },
    xAxis: {
      type: 'category',
      data: ['2020年', '2021年', '2022年', '2023年', '2024年'],
      axisLine: {
        lineStyle: {
          color: 'rgba(0, 150, 255, 0.5)',
          width: 1
        }
      },
      axisLabel: {
        color: '#8ca0b3',
        fontSize: 12
      },
      splitLine: {
        show: false
      }
    },
    yAxis: {
      type: 'category',
      data: ['初级', '工程师', '主管', '高级', '资深'],
      axisLine: {
        lineStyle: {
          color: 'rgba(0, 150, 255, 0.5)',
          width: 1
        }
      },
      axisLabel: {
        color: '#8ca0b3',
        fontSize: 12
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(0, 150, 255, 0.1)'
        }
      }
    },
    series: [{
      type: 'line',
      data: portraitData.value.developmentPath.nodes.map((node: any) => [node.date, node.position]),
      lineStyle: {
        color: '#00d4ff',
        width: 3,
        shadowColor: 'rgba(0, 212, 255, 0.5)',
        shadowBlur: 10
      },
      symbol: 'circle',
      symbolSize: 10,
      itemStyle: {
        color: '#00d4ff',
        borderColor: '#fff',
        borderWidth: 2
      },
      step: 'end',
      label: {
        show: true,
        color: '#00d4ff',
        fontSize: 11,
        formatter: function(params: any) {
          return params.value[1]
        }
      }
    }]
  }

  pathChartInstance.setOption(option)
}

// 初始化仪表盘
const initGauges = () => {
  gaugeChartInstances.forEach(instance => instance?.dispose())
  gaugeChartInstances = []

  gaugeData.value.forEach((item, index) => {
    const chartDom = gaugeRefs.value[index]
    if (!chartDom) return

    const chart = echarts.init(chartDom)
    gaugeChartInstances.push(chart)

    const option = {
      backgroundColor: 'transparent',
      grid: {
        top: '10%',
        bottom: '25%',
        left: '5%',
        right: '5%'
      },
      series: [
        // 背景圆环
        {
          type: 'gauge',
          startAngle: 180,
          endAngle: 0,
          center: ['50%', '75%'],
          radius: '90%',
          min: 0,
          max: 100,
          splitNumber: 0,
          axisLine: {
            lineStyle: {
              width: 25,
              color: [[1, 'rgba(0, 150, 255, 0.1)']]
            }
          },
          pointer: { show: false },
          axisTick: { show: false },
          axisLabel: { show: false },
          splitLine: { show: false },
          detail: { show: false }
        },
        // 达标值标记
        {
          type: 'gauge',
          startAngle: 180,
          endAngle: 0,
          center: ['50%', '75%'],
          radius: '90%',
          min: 0,
          max: 100,
          splitNumber: 0,
          axisLine: {
            lineStyle: {
              width: 25,
              color: [
                [item.target / 100, 'transparent'],
                [item.target / 100 + 0.002, '#ffa500'],
                [1, 'transparent']
              ]
            }
          },
          pointer: { show: false },
          axisTick: { show: false },
          axisLabel: { show: false },
          splitLine: { show: false },
          detail: { show: false }
        },
        // 标杆值标记
        {
          type: 'gauge',
          startAngle: 180,
          endAngle: 0,
          center: ['50%', '75%'],
          radius: '90%',
          min: 0,
          max: 100,
          splitNumber: 0,
          axisLine: {
            lineStyle: {
              width: 25,
              color: [
                [item.benchmark / 100, 'transparent'],
                [item.benchmark / 100 + 0.002, '#4caf50'],
                [1, 'transparent']
              ]
            }
          },
          pointer: { show: false },
          axisTick: { show: false },
          axisLabel: { show: false },
          splitLine: { show: false },
          detail: { show: false }
        },
        // 实际值
        {
          type: 'gauge',
          startAngle: 180,
          endAngle: 0,
          center: ['50%', '75%'],
          radius: '90%',
          min: 0,
          max: 100,
          splitNumber: 10,
          axisLine: {
            lineStyle: {
              width: 25,
              color: [
                [0.3, '#ff4500'],
                [0.7, '#ffa500'],
                [1, '#00d4ff']
              ]
            }
          },
          progress: {
            show: true,
            width: 25,
            itemStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 1,
                y2: 0,
                colorStops: [
                  { offset: 0, color: 'rgba(0, 212, 255, 0.3)' },
                  { offset: 1, color: 'rgba(0, 212, 255, 0.8)' }
                ]
              }
            }
          },
          pointer: {
            show: true,
            length: '70%',
            width: 4,
            itemStyle: {
              color: '#00d4ff'
            }
          },
          axisTick: {
            distance: -30,
            length: 5,
            lineStyle: {
              color: 'rgba(255, 255, 255, 0.3)',
              width: 1
            }
          },
          splitLine: {
            distance: -35,
            length: 8,
            lineStyle: {
              color: 'rgba(255, 255, 255, 0.5)',
              width: 1
            }
          },
          axisLabel: {
            distance: -45,
            color: '#8ca0b3',
            fontSize: 10,
            formatter: function(value: number) {
              if (value % 20 === 0) {
                return value
              }
              return ''
            }
          },
          title: {
            offsetCenter: [0, '-10%'],
            fontSize: 14,
            color: '#8ca0b3',
            fontWeight: 'normal'
          },
          detail: {
            valueAnimation: true,
            formatter: function(value: number) {
              return '{value|' + value + '}{unit|%}'
            },
            rich: {
              value: {
                fontSize: 24,
                fontWeight: 'bold',
                color: '#00d4ff'
              },
              unit: {
                fontSize: 14,
                color: '#00d4ff'
              }
            },
            offsetCenter: [0, '10%']
          },
          data: [{
            value: item.value,
            name: item.name
          }]
        }
      ]
    }

    chart.setOption(option)
  })
}

// 初始化雷达图
const initRadarChart = () => {
  const chartDom = document.querySelector('.radar-chart') as HTMLElement
  if (!chartDom) return

  if (radarChartInstance) {
    radarChartInstance.dispose()
  }

  radarChartInstance = echarts.init(chartDom)

  const option = {
    backgroundColor: 'transparent',
    radar: {
      indicator: [
        { name: '专业能力', max: 100 },
        { name: '创新能力', max: 100 },
        { name: '协作能力', max: 100 },
        { name: '领导力', max: 100 },
        { name: '执行力', max: 100 },
        { name: '学习能力', max: 100 }
      ],
      center: ['50%', '50%'],
      radius: '70%',
      shape: 'polygon',
      splitNumber: 5,
      axisName: {
        color: '#8ca0b3',
        fontSize: 12
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(0, 150, 255, 0.2)'
        }
      },
      splitArea: {
        areaStyle: {
          color: ['rgba(0, 150, 255, 0.05)', 'rgba(0, 150, 255, 0.1)']
        }
      },
      axisLine: {
        lineStyle: {
          color: 'rgba(0, 150, 255, 0.3)'
        }
      }
    },
    series: [{
      type: 'radar',
      data: [{
        value: [85, 78, 92, 75, 88, 90],
        name: '能力评估',
        lineStyle: {
          color: '#00d4ff',
          width: 2
        },
        areaStyle: {
          color: 'rgba(0, 212, 255, 0.3)'
        },
        symbol: 'circle',
        symbolSize: 8,
        itemStyle: {
          color: '#00d4ff',
          borderColor: '#fff',
          borderWidth: 2
        }
      }]
    }]
  }

  radarChartInstance.setOption(option)
}

// 初始化创新产出图表
const initOutputChart = () => {
  const chartDom = document.querySelector('.output-chart') as HTMLElement
  if (!chartDom) return

  if (outputChartInstance) {
    outputChartInstance.dispose()
  }

  outputChartInstance = echarts.init(chartDom)

  const option = {
    backgroundColor: 'transparent',
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      },
      backgroundColor: 'rgba(0, 0, 0, 0.8)',
      borderColor: '#00d4ff',
      borderWidth: 1,
      textStyle: {
        color: '#fff'
      }
    },
    grid: {
      left: '15%',
      right: '10%',
      bottom: '15%',
      top: '10%'
    },
    xAxis: {
      type: 'category',
      data: ['专利', '论文', '标准', '软著', '获奖'],
      axisLine: {
        lineStyle: {
          color: 'rgba(0, 150, 255, 0.5)',
          width: 1
        }
      },
      axisLabel: {
        color: '#8ca0b3',
        fontSize: 12,
        interval: 0
      },
      splitLine: {
        show: false
      }
    },
    yAxis: {
      type: 'value',
      axisLine: {
        lineStyle: {
          color: 'rgba(0, 150, 255, 0.5)',
          width: 1
        }
      },
      axisLabel: {
        color: '#8ca0b3',
        fontSize: 12
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(0, 150, 255, 0.1)'
        }
      }
    },
    series: [
      {
        name: '2023年',
        type: 'bar',
        data: [12, 8, 5, 3, 2],
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#00d4ff' },
            { offset: 1, color: '#0066cc' }
          ])
        },
        barWidth: '30%'
      },
      {
        name: '2024年',
        type: 'bar',
        data: [15, 10, 6, 4, 3],
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#ffa500' },
            { offset: 1, color: '#ff6347' }
          ])
        },
        barWidth: '30%'
      }
    ],
    legend: {
      data: ['2023年', '2024年'],
      textStyle: {
        color: '#8ca0b3',
        fontSize: 12
      },
      bottom: 0,
      icon: 'rect'
    }
  }

  outputChartInstance.setOption(option)
}

// 监听窗口大小变化
window.addEventListener('resize', () => {
  pathChartInstance?.resize()
  radarChartInstance?.resize()
  outputChartInstance?.resize()
  gaugeChartInstances.forEach(instance => instance?.resize())
})

// 添加页面加载动画
const animatePageLoad = () => {
  const modules = document.querySelectorAll('.module')
  modules.forEach((module, index) => {
    const el = module as HTMLElement
    el.style.opacity = '0'
    el.style.transform = 'translateY(20px)'
    setTimeout(() => {
      el.style.transition = 'all 0.6s ease-out'
      el.style.opacity = '1'
      el.style.transform = 'translateY(0)'
    }, index * 100)
  })
}

onMounted(() => {
  updateDateTime()
  setInterval(updateDateTime, 60000)
  getPersonList()
  animatePageLoad()
})
</script>

<style lang="scss" scoped>
@import './style.scss';
</style>
