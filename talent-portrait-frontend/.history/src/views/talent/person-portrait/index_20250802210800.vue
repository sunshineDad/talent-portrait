<template>
  <div class="portrait-container person-portrait">
    <!-- 扫描线效果 -->
    <div class="scan-line"></div>

    <!-- 粒子背景 -->
    <div class="particles-bg" id="particles-js"></div>

    <!-- 页面标题 -->
    <h1 class="page-title">核电科技人才画像</h1>

    <!-- 人员选择器 -->
    <div class="person-selector">
      <el-select
        v-model="currentPersonId"
        placeholder="请选择人员"
        @change="handlePersonChange"
        size="large"
        style="width: 300px"
      >
        <el-option
          v-for="person in personList"
          :key="person.id"
          :label="`${person.personName} - ${person.personCode}`"
          :value="person.id"
        />
      </el-select>
    </div>

    <!-- 主体内容区域 -->
    <div class="content-wrapper" v-if="portraitData">
      <!-- 左侧区域 -->
      <div class="left-section">
        <!-- 基本信息卡片 -->
        <div class="basic-info-card data-panel">
          <h3 class="panel-title">基本信息</h3>
          <div class="info-content">
            <div class="avatar-box">
              <img :src="portraitData.basicInfo.photoUrl || defaultAvatar" alt="头像" />
            </div>
            <div class="info-details">
              <div class="info-item">
                <span class="label">姓名：</span>
                <span class="value">{{ portraitData.basicInfo.personName }}</span>
              </div>
              <div class="info-item">
                <span class="label">编号：</span>
                <span class="value">{{ portraitData.basicInfo.personCode }}</span>
              </div>
              <div class="info-item">
                <span class="label">团队：</span>
                <span class="value">{{ portraitData.basicInfo.teamName }}</span>
              </div>
              <div class="info-item">
                <span class="label">职务：</span>
                <span class="value">{{ portraitData.basicInfo.position }}</span>
              </div>
              <div class="info-item">
                <span class="label">职称：</span>
                <span class="value">{{ portraitData.basicInfo.jobTitle }}</span>
              </div>
              <div class="info-item">
                <span class="label">职级：</span>
                <span class="value">{{ portraitData.basicInfo.jobLevel }}</span>
              </div>
            </div>
          </div>
          <div class="info-tags">
            <span class="tag-item">{{ portraitData.basicInfo.education }}</span>
            <span class="tag-item">{{ portraitData.basicInfo.degree }}</span>
            <span class="tag-item">工龄{{ portraitData.basicInfo.workYears }}年</span>
            <span class="tag-item">司龄{{ portraitData.basicInfo.companyYears }}年</span>
          </div>
        </div>

        <!-- 发展路径图表 -->
        <div class="development-path data-panel">
          <h3 class="panel-title">发展路径</h3>
          <div class="chart-container" ref="pathChart"></div>
        </div>
      </div>

      <!-- 中间区域 -->
      <div class="middle-section">
        <!-- 能力矩阵 -->
        <div class="ability-matrix data-panel">
          <h3 class="panel-title">能力矩阵</h3>
          <div class="matrix-grid">
            <div
              v-for="(group, index) in portraitData.abilityMatrix.groups"
              :key="index"
              class="ability-group"
            >
              <h4 class="group-title">{{ group.groupName }}</h4>
              <div class="ability-items">
                <div v-for="(item, idx) in group.items" :key="idx" class="ability-item">
                  <div class="item-name">{{ item.name }}</div>
                  <div class="item-level">{{ item.level }}</div>
                  <div class="item-years">{{ item.years }}年</div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 绩效展示 -->
        <div class="performance-display data-panel">
          <h3 class="panel-title">绩效表现</h3>
          <div class="performance-grid">
            <div
              v-for="item in portraitData.performanceData.items"
              :key="item.year"
              class="performance-item"
              :class="`level-${item.level.toLowerCase()}`"
            >
              <div class="year">{{ item.year }}</div>
              <div class="level">{{ item.level }}</div>
              <div class="score">{{ item.score }}</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧区域 -->
      <div class="right-section">
        <!-- 人才评估雷达图 -->
        <div class="evaluation-radar data-panel">
          <h3 class="panel-title">人才评估</h3>
          <div class="chart-container" ref="radarChart"></div>
        </div>

        <!-- 创新产出图表 -->
        <div class="innovation-output data-panel">
          <h3 class="panel-title">创新产出</h3>
          <div class="chart-container" ref="innovationChart"></div>
        </div>

        <!-- 个人影响力 -->
        <div class="personal-influence data-panel">
          <h3 class="panel-title">个人影响力</h3>
          <div class="influence-list">
            <div class="influence-section">
              <h4>当前任职</h4>
              <div
                v-for="item in portraitData.influenceData.currentInfluence"
                :key="item.id"
                class="influence-item"
              >
                <span class="influence-name">{{ item.name }}</span>
                <span class="influence-org">{{ item.organization }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, nextTick } from 'vue'
import * as echarts from 'echarts'
import { getPersonPortrait } from '@/api/core/portrait'
import { listPerson } from '@/api/core/person'
import type { PersonInfo } from '@/types/person'

// 默认头像
const defaultAvatar = '/images/default-avatar.png'

// 当前选中的人员ID
const currentPersonId = ref<number>()

// 人员列表
const personList = ref<PersonInfo[]>([])

// 画像数据
const portraitData = ref<any>()

// 图表实例
let pathChartInstance: echarts.ECharts
let radarChartInstance: echarts.ECharts
let innovationChartInstance: echarts.ECharts

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
  initRadarChart()
  initInnovationChart()
}

// 初始化发展路径图表
const initPathChart = () => {
  const chartDom = document.querySelector('.development-path .chart-container') as HTMLElement
  if (!chartDom) return

  if (pathChartInstance) {
    pathChartInstance.dispose()
  }

  pathChartInstance = echarts.init(chartDom)

  const option = {
    backgroundColor: 'transparent',
    grid: {
      left: '10%',
      right: '5%',
      bottom: '10%',
      top: '10%'
    },
    xAxis: {
      type: 'category',
      data: portraitData.value.developmentPath.categories,
      axisLine: {
        lineStyle: {
          color: 'rgba(32, 168, 216, 0.6)'
        }
      },
      axisLabel: {
        color: '#52d6f4',
        rotate: 45
      }
    },
    yAxis: {
      type: 'value',
      splitLine: {
        lineStyle: {
          color: 'rgba(32, 168, 216, 0.2)'
        }
      },
      axisLine: {
        lineStyle: {
          color: 'rgba(32, 168, 216, 0.6)'
        }
      },
      axisLabel: {
        color: '#52d6f4'
      }
    },
    series: [{
      type: 'line',
      smooth: true,
      symbol: 'circle',
      symbolSize: 8,
      itemStyle: {
        color: '#20a8d8'
      },
      lineStyle: {
        color: '#20a8d8',
        width: 3,
        shadowBlur: 10,
        shadowColor: 'rgba(32, 168, 216, 0.5)'
      },
      areaStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: 'rgba(32, 168, 216, 0.3)' },
          { offset: 1, color: 'rgba(32, 168, 216, 0.05)' }
        ])
      },
      data: portraitData.value.developmentPath.nodes.map((node: any) => ({
        value: [node.date, 1],
        name: node.position,
        team: node.team
      }))
    }]
  }

  pathChartInstance.setOption(option)
}

// 初始化雷达图
const initRadarChart = () => {
  const chartDom = document.querySelector('.evaluation-radar .chart-container') as HTMLElement
  if (!chartDom) return

  if (radarChartInstance) {
    radarChartInstance.dispose()
  }

  radarChartInstance = echarts.init(chartDom)

  const radarData = portraitData.value.evaluationData.radarData

  const option = {
    backgroundColor: 'transparent',
    radar: {
      indicator: radarData.map((item: any) => ({
        name: item.dimension,
        max: item.maxScore
      })),
      shape: 'polygon',
      splitNumber: 5,
      axisName: {
        color: '#52d6f4'
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(32, 168, 216, 0.2)'
        }
      },
