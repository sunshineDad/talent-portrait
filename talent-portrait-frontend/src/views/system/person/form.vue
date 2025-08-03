<template>
  <div class="person-form-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>{{ title }}</span>
          <el-button type="primary" @click="handleSubmit">保存</el-button>
        </div>
      </template>

      <el-tabs v-model="activeTab">
        <!-- 基本信息 -->
        <el-tab-pane label="基本信息" name="basic">
          <el-form
            ref="basicFormRef"
            :model="form"
            :rules="rules"
            label-width="120px"
            :disabled="isView"
          >
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="姓名" prop="personName">
                  <el-input v-model="form.personName" placeholder="请输入姓名" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="编号" prop="personCode">
                  <el-input v-model="form.personCode" placeholder="请输入编号" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="性别" prop="gender">
                  <el-select v-model="form.gender" placeholder="请选择性别">
                    <el-option
                      v-for="dict in genderOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="出生日期" prop="birthDate">
                  <el-date-picker
                    v-model="form.birthDate"
                    type="date"
                    placeholder="选择日期"
                    value-format="YYYY-MM-DD"
                    style="width: 100%"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="身份证号" prop="idCard">
                  <el-input v-model="form.idCard" placeholder="请输入身份证号" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系电话" prop="phone">
                  <el-input v-model="form.phone" placeholder="请输入联系电话" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="电子邮箱" prop="email">
                  <el-input v-model="form.email" placeholder="请输入电子邮箱" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="学历" prop="education">
                  <el-select v-model="form.education" placeholder="请选择学历">
                    <el-option
                      v-for="dict in educationOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="学位" prop="degree">
                  <el-select v-model="form.degree" placeholder="请选择学位">
                    <el-option
                      v-for="dict in degreeOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="毕业院校" prop="graduateSchool">
                  <el-input v-model="form.graduateSchool" placeholder="请输入毕业院校" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="专业" prop="major">
                  <el-input v-model="form.major" placeholder="请输入专业" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="参工时间" prop="workStartDate">
                  <el-date-picker
                    v-model="form.workStartDate"
                    type="date"
                    placeholder="选择日期"
                    value-format="YYYY-MM-DD"
                    style="width: 100%"
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="入职时间" prop="joinDate">
                  <el-date-picker
                    v-model="form.joinDate"
                    type="date"
                    placeholder="选择日期"
                    value-format="YYYY-MM-DD"
                    style="width: 100%"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="所属团队" prop="teamId">
                  <el-tree-select
                    v-model="form.teamId"
                    :data="teamOptions"
                    :props="{ value: 'id', label: 'teamName', children: 'children' }"
                    placeholder="请选择团队"
                    style="width: 100%"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="职务" prop="position">
                  <el-input v-model="form.position" placeholder="请输入职务" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="职称" prop="jobTitle">
                  <el-select v-model="form.jobTitle" placeholder="请选择职称">
                    <el-option
                      v-for="dict in jobTitleOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="职级" prop="jobLevel">
                  <el-select v-model="form.jobLevel" placeholder="请选择职级">
                    <el-option
                      v-for="dict in jobLevelOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="工作地点" prop="workLocation">
                  <el-input v-model="form.workLocation" placeholder="请输入工作地点" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <!-- 专业能力 -->
        <el-tab-pane label="专业能力" name="skill">
          <el-button v-if="!isView" type="primary" size="small" @click="handleAddSkill"
            >新增能力</el-button
          >
          <el-table :data="form.skillList" border style="margin-top: 10px">
            <el-table-column label="能力类型" prop="skillType">
              <template #default="scope">
                <dict-tag :options="skillTypeOptions" :value="scope.row.skillType" />
              </template>
            </el-table-column>
            <el-table-column label="能力名称" prop="skillName" />
            <el-table-column label="能力等级" prop="skillLevel">
              <template #default="scope">
                <dict-tag :options="skillLevelOptions" :value="scope.row.skillLevel" />
              </template>
            </el-table-column>
            <el-table-column label="经验年限" prop="experienceYears" />
            <el-table-column label="认证证书" prop="certification" />
            <el-table-column label="操作" width="120" align="center" v-if="!isView">
              <template #default="scope">
                <el-button link type="primary" @click="handleEditSkill(scope.row, scope.$index)"
                  >编辑</el-button
                >
                <el-button link type="danger" @click="handleDeleteSkill(scope.$index)"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <!-- 项目经历 -->
        <el-tab-pane label="项目经历" name="project">
          <el-button v-if="!isView" type="primary" size="small" @click="handleAddProject"
            >新增项目</el-button
          >
          <el-table :data="form.projectList" border style="margin-top: 10px">
            <el-table-column label="项目名称" prop="projectName" show-overflow-tooltip />
            <el-table-column label="项目类型" prop="projectType">
              <template #default="scope">
                <dict-tag :options="projectTypeOptions" :value="scope.row.projectType" />
              </template>
            </el-table-column>
            <el-table-column label="项目级别" prop="projectLevel">
              <template #default="scope">
                <dict-tag :options="projectLevelOptions" :value="scope.row.projectLevel" />
              </template>
            </el-table-column>
            <el-table-column label="项目角色" prop="projectRole" />
            <el-table-column label="开始时间" prop="startDate" />
            <el-table-column label="结束时间" prop="endDate" />
            <el-table-column label="操作" width="120" align="center" v-if="!isView">
              <template #default="scope">
                <el-button link type="primary" @click="handleEditProject(scope.row, scope.$index)"
                  >编辑</el-button
                >
                <el-button link type="danger" @click="handleDeleteProject(scope.$index)"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <!-- 绩效信息 -->
        <el-tab-pane label="绩效信息" name="performance">
          <el-button v-if="!isView" type="primary" size="small" @click="handleAddPerformance"
            >新增绩效</el-button
          >
          <el-table :data="form.performanceList" border style="margin-top: 10px">
            <el-table-column label="年度" prop="year" />
            <el-table-column label="季度" prop="quarter" />
            <el-table-column label="绩效得分" prop="performanceScore" />
            <el-table-column label="绩效等级" prop="performanceLevel">
              <template #default="scope">
                <el-tag :type="getPerformanceLevelType(scope.row.performanceLevel)">
                  {{ scope.row.performanceLevel }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="评估人" prop="evaluator" />
            <el-table-column label="评估日期" prop="evaluationDate" />
            <el-table-column label="操作" width="120" align="center" v-if="!isView">
              <template #default="scope">
                <el-button
                  link
                  type="primary"
                  @click="handleEditPerformance(scope.row, scope.$index)"
                  >编辑</el-button
                >
                <el-button link type="danger" @click="handleDeletePerformance(scope.$index)"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <!-- 人才评估 -->
        <el-tab-pane label="人才评估" name="evaluation">
          <el-button v-if="!isView" type="primary" size="small" @click="handleAddEvaluation"
            >新增评估</el-button
          >
          <el-table :data="form.evaluationList" border style="margin-top: 10px">
            <el-table-column label="评估年度" prop="evaluationYear" />
            <el-table-column label="评估维度" prop="evaluationDimension">
              <template #default="scope">
                <dict-tag
                  :options="evaluationDimensionOptions"
                  :value="scope.row.evaluationDimension"
                />
              </template>
            </el-table-column>
            <el-table-column label="维度得分" prop="dimensionScore" />
            <el-table-column label="维度等级" prop="dimensionLevel" />
            <el-table-column label="评估人" prop="evaluator" />
            <el-table-column label="评估日期" prop="evaluationDate" />
            <el-table-column label="操作" width="120" align="center" v-if="!isView">
              <template #default="scope">
                <el-button
                  link
                  type="primary"
                  @click="handleEditEvaluation(scope.row, scope.$index)"
                  >编辑</el-button
                >
                <el-button link type="danger" @click="handleDeleteEvaluation(scope.$index)"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>

    <!-- 专业能力弹窗 -->
    <skill-dialog
      v-model="skillDialogVisible"
      :skill-data="currentSkill"
      :dict-options="{ skillTypeOptions, skillLevelOptions }"
      @confirm="handleSkillConfirm"
    />

    <!-- 项目经历弹窗 -->
    <project-dialog
      v-model="projectDialogVisible"
      :project-data="currentProject"
      :dict-options="{ projectTypeOptions, projectLevelOptions }"
      @confirm="handleProjectConfirm"
    />

    <!-- 绩效信息弹窗 -->
    <performance-dialog
      v-model="performanceDialogVisible"
      :performance-data="currentPerformance"
      @confirm="handlePerformanceConfirm"
    />

    <!-- 人才评估弹窗 -->
    <evaluation-dialog
      v-model="evaluationDialogVisible"
      :evaluation-data="currentEvaluation"
      :dict-options="{ evaluationDimensionOptions }"
      @confirm="handleEvaluationConfirm"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getPerson, addPerson, updatePerson } from '@/api/core/person'
import { treeSelectTeam } from '@/api/system/team'
import { getDictDataByType } from '@/api/system/dict'
import DictTag from '@/components/DictTag/index.vue'
import SkillDialog from './components/SkillDialog.vue'
import ProjectDialog from './components/ProjectDialog.vue'
import PerformanceDialog from './components/PerformanceDialog.vue'
import EvaluationDialog from './components/EvaluationDialog.vue'
import type { PersonInfo } from '@/types/person'
import type { FormInstance, FormRules } from 'element-plus'

const route = useRoute()
const router = useRouter()

// 表单类型
const formType = computed(() => {
  if (route.path.includes('add')) return 'add'
  if (route.path.includes('edit')) return 'edit'
  return 'view'
})

const isView = computed(() => formType.value === 'view')
const title = computed(() => {
  const titleMap = {
    add: '新增人员',
    edit: '编辑人员',
    view: '查看人员',
  }
  return titleMap[formType.value]
})

// 表单相关
const basicFormRef = ref<FormInstance>()
const activeTab = ref('basic')
const form = reactive<PersonInfo>({
  personCode: '',
  personName: '',
  teamId: 0,
  skillList: [],
  projectList: [],
  performanceList: [],
  influenceList: [],
  innovationList: [],
  evaluationList: [],
  maintenanceList: [],
})

// 表单验证规则
const rules = reactive<FormRules>({
  personName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  personCode: [{ required: true, message: '请输入编号', trigger: 'blur' }],
  teamId: [{ required: true, message: '请选择团队', trigger: 'change' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  phone: [
    { required: true, message: '请输入联系电话', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' },
  ],
  email: [
    { required: true, message: '请输入电子邮箱', trigger: 'blur' },
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' },
  ],
})

// 字典数据
const teamOptions = ref([])
const genderOptions = ref([])
const educationOptions = ref([])
const degreeOptions = ref([])
const jobTitleOptions = ref([])
const jobLevelOptions = ref([])
const skillTypeOptions = ref([])
const skillLevelOptions = ref([])
const projectTypeOptions = ref([])
const projectLevelOptions = ref([])
const evaluationDimensionOptions = ref([])

// 子表弹窗相关
const skillDialogVisible = ref(false)
const currentSkill = ref({})
const currentSkillIndex = ref(-1)

const projectDialogVisible = ref(false)
const currentProject = ref({})
const currentProjectIndex = ref(-1)

const performanceDialogVisible = ref(false)
const currentPerformance = ref({})
const currentPerformanceIndex = ref(-1)

const evaluationDialogVisible = ref(false)
const currentEvaluation = ref({})
const currentEvaluationIndex = ref(-1)

// 获取字典数据
const getDictData = async () => {
  const dictTypes = [
    { key: 'teamOptions', type: 'team', api: treeSelectTeam },
    { key: 'genderOptions', type: 'sys_user_sex' },
    { key: 'educationOptions', type: 'education' },
    { key: 'degreeOptions', type: 'degree' },
    { key: 'jobTitleOptions', type: 'job_title' },
    { key: 'jobLevelOptions', type: 'job_level' },
    { key: 'skillTypeOptions', type: 'skill_type' },
    { key: 'skillLevelOptions', type: 'skill_level' },
    { key: 'projectTypeOptions', type: 'project_type' },
    { key: 'projectLevelOptions', type: 'project_level' },
    { key: 'evaluationDimensionOptions', type: 'evaluation_dimension' },
  ]

  for (const dict of dictTypes) {
    if (dict.api) {
      const res = await dict.api()
      eval(`${dict.key}.value = res.data`)
    } else {
      const res = await getDictDataByType(dict.type)
      eval(`${dict.key}.value = res.data`)
    }
  }
}

// 获取人员详情
const getPersonDetail = async () => {
  if (formType.value === 'add') return

  const personId = route.params.id as string
  const res = await getPerson(Number(personId))
  Object.assign(form, res.data)
}

// 提交表单
const handleSubmit = async () => {
  if (!basicFormRef.value) return
  await basicFormRef.value.validate()

  try {
    if (formType.value === 'add') {
      await addPerson(form)
      ElMessage.success('新增成功')
    } else {
      await updatePerson(form)
      ElMessage.success('修改成功')
    }
    router.push('/system/person')
  } catch (error) {
    console.error(error)
  }
}

// 专业能力相关操作
const handleAddSkill = () => {
  currentSkill.value = {}
  currentSkillIndex.value = -1
  skillDialogVisible.value = true
}

const handleEditSkill = (row: any, index: number) => {
  currentSkill.value = { ...row }
  currentSkillIndex.value = index
  skillDialogVisible.value = true
}

const handleDeleteSkill = (index: number) => {
  form.skillList!.splice(index, 1)
}

const handleSkillConfirm = (data: any) => {
  if (currentSkillIndex.value === -1) {
    form.skillList!.push(data)
  } else {
    form.skillList![currentSkillIndex.value] = data
  }
}

// 项目经历相关操作
const handleAddProject = () => {
  currentProject.value = {}
  currentProjectIndex.value = -1
  projectDialogVisible.value = true
}

const handleEditProject = (row: any, index: number) => {
  currentProject.value = { ...row }
  currentProjectIndex.value = index
  projectDialogVisible.value = true
}

const handleDeleteProject = (index: number) => {
  form.projectList!.splice(index, 1)
}

const handleProjectConfirm = (data: any) => {
  if (currentProjectIndex.value === -1) {
    form.projectList!.push(data)
  } else {
    form.projectList![currentProjectIndex.value] = data
  }
}

// 绩效信息相关操作
const handleAddPerformance = () => {
  currentPerformance.value = {}
  currentPerformanceIndex.value = -1
  performanceDialogVisible.value = true
}

const handleEditPerformance = (row: any, index: number) => {
  currentPerformance.value = { ...row }
  currentPerformanceIndex.value = index
  performanceDialogVisible.value = true
}

const handleDeletePerformance = (index: number) => {
  form.performanceList!.splice(index, 1)
}

const handlePerformanceConfirm = (data: any) => {
  if (currentPerformanceIndex.value === -1) {
    form.performanceList!.push(data)
  } else {
    form.performanceList![currentPerformanceIndex.value] = data
  }
}

// 人才评估相关操作
const handleAddEvaluation = () => {
  currentEvaluation.value = {}
  currentEvaluationIndex.value = -1
  evaluationDialogVisible.value = true
}

const handleEditEvaluation = (row: any, index: number) => {
  currentEvaluation.value = { ...row }
  currentEvaluationIndex.value = index
  evaluationDialogVisible.value = true
}

const handleDeleteEvaluation = (index: number) => {
  form.evaluationList!.splice(index, 1)
}

const handleEvaluationConfirm = (data: any) => {
  if (currentEvaluationIndex.value === -1) {
    form.evaluationList!.push(data)
  } else {
    form.evaluationList![currentEvaluationIndex.value] = data
  }
}

// 获取绩效等级类型
const getPerformanceLevelType = (level: string) => {
  const typeMap: Record<string, string> = {
    S: 'danger',
    A: 'warning',
    B: 'primary',
    C: 'info',
  }
  return typeMap[level] || 'info'
}

onMounted(() => {
  getDictData()
  getPersonDetail()
})
</script>

<style lang="scss" scoped>
.person-form-container {
  padding: 20px;

  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  :deep(.el-tabs__content) {
    padding: 20px 0;
  }
}
</style>
