<template>
  <div class="team-container">
    <div class="team-wrapper">
      <!-- 左侧团队树 -->
      <div class="team-tree-panel">
        <div class="tree-header">
          <span>团队结构</span>
          <el-button type="primary" size="small" icon="Plus" @click="handleAdd(null)"
            >新增</el-button
          >
        </div>
        <div class="tree-search">
          <el-input v-model="filterText" placeholder="输入团队名称搜索" prefix-icon="Search" />
        </div>
        <div class="tree-content">
          <el-tree
            ref="treeRef"
            :data="teamTree"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            default-expand-all
            highlight-current
            @node-click="handleNodeClick"
          >
            <template #default="{ node, data }">
              <span class="tree-node">
                <el-icon><Folder /></el-icon>
                <span>{{ node.label }}</span>
                <span class="node-count">({{ data.memberCount || 0 }}人)</span>
              </span>
            </template>
          </el-tree>
        </div>
      </div>

      <!-- 右侧详情 -->
      <div class="team-detail-panel" v-loading="detailLoading">
        <template v-if="currentTeam">
          <!-- 团队基本信息 -->
          <el-card class="info-card">
            <template #header>
              <div class="card-header">
                <span>团队信息</span>
                <div>
                  <el-button type="primary" size="small" @click="handleEdit">编辑</el-button>
                  <el-button type="danger" size="small" @click="handleDelete">删除</el-button>
                </div>
              </div>
            </template>

            <el-descriptions :column="2" border>
              <el-descriptions-item label="团队名称">
                {{ currentTeam.teamName }}
              </el-descriptions-item>
              <el-descriptions-item label="团队编码">
                {{ currentTeam.teamCode }}
              </el-descriptions-item>
              <el-descriptions-item label="上级团队">
                {{ currentTeam.parentName || '无' }}
              </el-descriptions-item>
              <el-descriptions-item label="团队负责人">
                {{ currentTeam.leaderName || '-' }}
              </el-descriptions-item>
              <el-descriptions-item label="团队类型">
                <dict-tag :options="teamTypeOptions" :value="currentTeam.teamType" />
              </el-descriptions-item>
              <el-descriptions-item label="团队级别">
                <dict-tag :options="teamLevelOptions" :value="currentTeam.teamLevel" />
              </el-descriptions-item>
              <el-descriptions-item label="成立日期">
                {{ currentTeam.establishDate }}
              </el-descriptions-item>
              <el-descriptions-item label="状态">
                <el-tag :type="currentTeam.status === '0' ? 'success' : 'danger'">
                  {{ currentTeam.status === '0' ? '正常' : '停用' }}
                </el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="团队描述" :span="2">
                {{ currentTeam.description || '-' }}
              </el-descriptions-item>
            </el-descriptions>
          </el-card>

          <!-- 团队统计信息 -->
          <el-card class="statistics-card">
            <template #header>
              <span>团队统计</span>
            </template>

            <el-row :gutter="20">
              <el-col :span="6">
                <div class="stat-item">
                  <div class="stat-value">{{ statistics.totalMembers }}</div>
                  <div class="stat-label">团队总人数</div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="stat-item">
                  <div class="stat-value">{{ statistics.directMembers }}</div>
                  <div class="stat-label">直属成员</div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="stat-item">
                  <div class="stat-value">{{ statistics.subTeams }}</div>
                  <div class="stat-label">下级团队</div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="stat-item">
                  <div class="stat-value">{{ statistics.avgAge }}</div>
                  <div class="stat-label">平均年龄</div>
                </div>
              </el-col>
            </el-row>

            <el-divider />

            <el-row :gutter="20">
              <el-col :span="8">
                <div class="chart-title">学历分布</div>
                <div class="mini-chart" ref="educationChart"></div>
              </el-col>
              <el-col :span="8">
                <div class="chart-title">职称分布</div>
                <div class="mini-chart" ref="titleChart"></div>
              </el-col>
              <el-col :span="8">
                <div class="chart-title">性别分布</div>
                <div class="mini-chart" ref="genderChart"></div>
              </el-col>
            </el-row>
          </el-card>

          <!-- 团队成员列表 -->
          <el-card class="members-card">
            <template #header>
              <div class="card-header">
                <span>团队成员</span>
                <el-button type="primary" size="small" @click="handleAddMember">添加成员</el-button>
              </div>
            </template>

            <el-table :data="memberList" border>
              <el-table-column label="姓名" prop="personName" width="100">
                <template #default="scope">
                  <el-link type="primary" @click="viewPerson(scope.row)">
                    {{ scope.row.personName }}
                  </el-link>
                </template>
              </el-table-column>
              <el-table-column label="编号" prop="personCode" width="120" />
              <el-table-column label="职务" prop="position" />
              <el-table-column label="职称" prop="jobTitle">
                <template #default="scope">
                  <dict-tag :options="jobTitleOptions" :value="scope.row.jobTitle" />
                </template>
              </el-table-column>
              <el-table-column label="加入时间" prop="joinTeamDate" width="120" />
              <el-table-column label="是否负责人" width="100" align="center">
                <template #default="scope">
                  <el-tag v-if="scope.row.id === currentTeam.leaderId" type="danger">
                    负责人
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="100" align="center">
                <template #default="scope">
                  <el-button link type="danger" size="small" @click="handleRemoveMember(scope.row)"
                    >移除</el-button
                  >
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </template>

        <el-empty v-else description="请选择团队查看详情" />
      </div>
    </div>

    <!-- 团队表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      :before-close="handleClose"
    >
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="上级团队" prop="parentId">
          <el-tree-select
            v-model="form.parentId"
            :data="teamTreeOptions"
            :props="{ value: 'id', label: 'teamName', children: 'children' }"
            placeholder="请选择上级团队"
            clearable
            check-strictly
            style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="团队名称" prop="teamName">
          <el-input v-model="form.teamName" placeholder="请输入团队名称" />
        </el-form-item>

        <el-form-item label="团队编码" prop="teamCode">
          <el-input v-model="form.teamCode" placeholder="请输入团队编码" />
        </el-form-item>

        <el-form-item label="团队负责人" prop="leaderId">
          <el-select
            v-model="form.leaderId"
            placeholder="请选择团队负责人"
            clearable
            filterable
            style="width: 100%"
          >
            <el-option
              v-for="person in leaderOptions"
              :key="person.id"
              :label="`${person.personName} - ${person.personCode}`"
              :value="person.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="团队类型" prop="teamType">
          <el-select v-model="form.teamType" placeholder="请选择团队类型" style="width: 100%">
            <el-option
              v-for="dict in teamTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="团队级别" prop="teamLevel">
          <el-select v-model="form.teamLevel" placeholder="请选择团队级别" style="width: 100%">
            <el-option
              v-for="dict in teamLevelOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="成立日期" prop="establishDate">
          <el-date-picker
            v-model="form.establishDate"
            type="date"
            placeholder="选择成立日期"
            value-format="YYYY-MM-DD"
            style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="团队描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="3"
            placeholder="请输入团队描述"
          />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, watch, onMounted, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox, ElTree } from 'element-plus'
import { Folder } from '@element-plus/icons-vue'
import * as echarts from 'echarts'
import {
  treeListTeam,
  getTeam,
  addTeam,
  updateTeam,
  delTeam,
  getTeamStatistics,
  getTeamMembers,
} from '@/api/system/team'
import { listPerson } from '@/api/core/person'
import { getDictDataByType } from '@/api/system/dict'
import DictTag from '@/components/DictTag/index.vue'
import type { TeamInfo } from '@/types/system'
import type { FormInstance, FormRules } from 'element-plus'

const router = useRouter()

// 树形组件引用
const treeRef = ref<InstanceType<typeof ElTree>>()

// 团队树相关
const teamTree = ref<TeamInfo[]>([])
const teamTreeOptions = ref<TeamInfo[]>([])
const filterText = ref('')
const defaultProps = {
  children: 'children',
  label: 'teamName',
}

// 当前选中的团队
const currentTeam = ref<TeamInfo>()
const detailLoading = ref(false)

// 统计信息
const statistics = ref({
  totalMembers: 0,
  directMembers: 0,
  subTeams: 0,
  avgAge: 0,
})

// 成员列表
const memberList = ref([])

// 表单相关
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref<FormInstance>()
const form = reactive<TeamInfo>({
  teamName: '',
  teamCode: '',
  teamType: '',
  teamLevel: '',
  status: '0',
})

// 表单验证规则
const rules = reactive<FormRules>({
  teamName: [{ required: true, message: '请输入团队名称', trigger: 'blur' }],
  teamCode: [{ required: true, message: '请输入团队编码', trigger: 'blur' }],
  teamType: [{ required: true, message: '请选择团队类型', trigger: 'change' }],
  teamLevel: [{ required: true, message: '请选择团队级别', trigger: 'change' }],
})

// 字典数据
const teamTypeOptions = ref([])
const teamLevelOptions = ref([])
const jobTitleOptions = ref([])
const leaderOptions = ref([])

// 图表实例
let educationChartInstance: echarts.ECharts
let titleChartInstance: echarts.ECharts
let genderChartInstance: echarts.ECharts

// 获取团队树
const getTeamTree = async () => {
  const res = await treeListTeam()
  teamTree.value = res.data
  teamTreeOptions.value = res.data
}

// 获取字典数据
const getDictData = async () => {
  const [teamType, teamLevel, jobTitle] = await Promise.all([
    getDictDataByType('team_type'),
    getDictDataByType('team_level'),
    getDictDataByType('job_title'),
  ])

  teamTypeOptions.value = teamType.data
  teamLevelOptions.value = teamLevel.data
  jobTitleOptions.value = jobTitle.data
}

// 获取可选负责人列表
const getLeaderOptions = async () => {
  const res = await listPerson({ current: 1, size: 1000 })
  leaderOptions.value = res.data.records
}

// 树节点过滤
const filterNode = (value: string, data: TeamInfo) => {
  if (!value) return true
  return data.teamName.includes(value)
}

// 监听搜索框
watch(filterText, (val) => {
  treeRef.value?.filter(val)
})

// 节点点击
const handleNodeClick = async (data: TeamInfo) => {
  currentTeam.value = data
  await loadTeamDetail(data.id!)
}

// 加载团队详情
const loadTeamDetail = async (teamId: number) => {
  detailLoading.value = true
  try {
    // 获取团队详细信息
    const detailRes = await getTeam(teamId)
    currentTeam.value = detailRes.data

    // 获取统计信息
    const statsRes = await getTeamStatistics(teamId)
    statistics.value = statsRes.data

    // 获取成员列表
    const membersRes = await getTeamMembers(teamId)
    memberList.value = membersRes.data

    // 初始化图表
    await nextTick()
    initCharts()
  } finally {
    detailLoading.value = false
  }
}

// 初始化统计图表
const initCharts = () => {
  // 学历分布
  const educationDom = document.querySelector(
    '.members-card .mini-chart:nth-child(1)'
  ) as HTMLElement
  if (educationDom) {
    if (educationChartInstance) {
      educationChartInstance.dispose()
    }
    educationChartInstance = echarts.init(educationDom)

    const option = {
      tooltip: { trigger: 'item' },
      series: [
        {
          type: 'pie',
          radius: ['40%', '70%'],
          data: statistics.value.educationDistribution || [],
          label: { show: false },
        },
      ],
    }

    educationChartInstance.setOption(option)
  }

  // 职称分布
  const titleDom = document.querySelector('.members-card .mini-chart:nth-child(2)') as HTMLElement
  if (titleDom) {
    if (titleChartInstance) {
      titleChartInstance.dispose()
    }
    titleChartInstance = echarts.init(titleDom)

    const option = {
      tooltip: { trigger: 'item' },
      series: [
        {
          type: 'pie',
          radius: ['40%', '70%'],
          data: statistics.value.titleDistribution || [],
          label: { show: false },
        },
      ],
    }

    titleChartInstance.setOption(option)
  }

  // 性别分布
  const genderDom = document.querySelector('.members-card .mini-chart:nth-child(3)') as HTMLElement
  if (genderDom) {
    if (genderChartInstance) {
      genderChartInstance.dispose()
    }
    genderChartInstance = echarts.init(genderDom)

    const option = {
      tooltip: { trigger: 'item' },
      series: [
        {
          type: 'pie',
          radius: ['40%', '70%'],
          data: statistics.value.genderDistribution || [],
          label: { show: false },
        },
      ],
    }

    genderChartInstance.setOption(option)
  }
}

// 新增团队
const handleAdd = (parentData: TeamInfo | null) => {
  Object.assign(form, {
    id: undefined,
    parentId: parentData?.id || 0,
    teamName: '',
    teamCode: '',
    teamType: '',
    teamLevel: '',
    leaderId: undefined,
    establishDate: undefined,
    status: '0',
    description: '',
  })

  dialogTitle.value = '新增团队'
  dialogVisible.value = true
}

// 编辑团队
const handleEdit = () => {
  if (!currentTeam.value) return

  Object.assign(form, currentTeam.value)
  dialogTitle.value = '编辑团队'
  dialogVisible.value = true
}

// 删除团队
const handleDelete = async () => {
  if (!currentTeam.value) return

  await ElMessageBox.confirm('是否确认删除团队"' + currentTeam.value.teamName + '"？', '系统提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })

  await delTeam(currentTeam.value.id!)
  ElMessage.success('删除成功')
  currentTeam.value = undefined
  getTeamTree()
}

// 提交表单
const submitForm = async () => {
  if (!formRef.value) return

  await formRef.value.validate()

  if (form.id) {
    await updateTeam(form)
    ElMessage.success('修改成功')
  } else {
    await addTeam(form)
    ElMessage.success('新增成功')
  }

  dialogVisible.value = false
  getTeamTree()

  // 如果编辑的是当前团队，刷新详情
  if (currentTeam.value && form.id === currentTeam.value.id) {
    loadTeamDetail(form.id)
  }
}

// 关闭对话框
const handleClose = () => {
  dialogVisible.value = false
  formRef.value?.resetFields()
}

// 添加成员
const handleAddMember = () => {
  // TODO: 实现添加成员功能
  ElMessage.info('添加成员功能待实现')
}

// 移除成员
const handleRemoveMember = async (row: any) => {
  await ElMessageBox.confirm('确认将该成员移出团队吗？', '系统提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })

  // TODO: 调用移除成员接口
  ElMessage.success('移除成功')
  loadTeamDetail(currentTeam.value!.id!)
}

// 查看人员详情
const viewPerson = (row: any) => {
  router.push(`/system/person/view/${row.id}`)
}

// 监听窗口大小变化
window.addEventListener('resize', () => {
  educationChartInstance?.resize()
  titleChartInstance?.resize()
  genderChartInstance?.resize()
})

onMounted(() => {
  getDictData()
  getTeamTree()
  getLeaderOptions()
})
</script>

<style lang="scss" scoped>
.team-container {
  height: 100%;
  padding: 20px;

  .team-wrapper {
    display: flex;
    gap: 20px;
    height: 100%;

    .team-tree-panel {
      width: 350px;
      background: #fff;
      border-radius: 4px;
      display: flex;
      flex-direction: column;

      .tree-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 16px;
        border-bottom: 1px solid #e8e8e8;

        span {
          font-size: 16px;
          font-weight: 500;
        }
      }

      .tree-search {
        padding: 16px;
        border-bottom: 1px solid #e8e8e8;
      }

      .tree-content {
        flex: 1;
        overflow: auto;
        padding: 16px;

        .tree-node {
          display: flex;
          align-items: center;
          gap: 5px;

          .node-count {
            color: #999;
            font-size: 12px;
            margin-left: 5px;
          }
        }
      }
    }

    .team-detail-panel {
      flex: 1;
      overflow: auto;

      .el-card {
        margin-bottom: 20px;
      }

      .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
      }

      .statistics-card {
        .stat-item {
          text-align: center;
          padding: 20px 0;

          .stat-value {
            font-size: 28px;
            font-weight: bold;
            color: #409eff;
          }

          .stat-label {
            font-size: 14px;
            color: #666;
            margin-top: 5px;
          }
        }

        .chart-title {
          text-align: center;
          font-size: 14px;
          color: #666;
          margin-bottom: 10px;
        }

        .mini-chart {
          height: 150px;
        }
      }
    }
  }
}
</style>
