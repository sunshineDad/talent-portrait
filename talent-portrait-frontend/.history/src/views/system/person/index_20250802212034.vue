<template>
  <div class="person-container">
    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :model="queryParams" ref="queryRef" :inline="true">
        <el-form-item label="姓名" prop="personName">
          <el-input
            v-model="queryParams.personName"
            placeholder="请输入姓名"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="编号" prop="personCode">
          <el-input
            v-model="queryParams.personCode"
            placeholder="请输入编号"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="团队" prop="teamId">
          <el-tree-select
            v-model="queryParams.teamId"
            :data="teamOptions"
            :props="{ value: 'id', label: 'teamName', children: 'children' }"
            placeholder="请选择团队"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="职称" prop="jobTitle">
          <el-select v-model="queryParams.jobTitle" placeholder="请选择职称" clearable>
            <el-option
              v-for="dict in jobTitleOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
          <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 操作工具栏 -->
    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>人员列表</span>
          <div>
            <el-button type="primary" icon="Plus" @click="handleAdd">新增</el-button>
            <el-button type="success" icon="Download" @click="handleExport">导出</el-button>
            <el-button type="warning" icon="Upload" @click="handleImport">导入</el-button>
            <el-button type="danger" icon="Delete" :disabled="multiple" @click="handleDelete"
              >删除</el-button
            >
          </div>
        </div>
      </template>

      <!-- 人员列表表格 -->
      <el-table
        v-loading="loading"
        :data="personList"
        @selection-change="handleSelectionChange"
        border
        stripe
      >
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column label="序号" type="index" width="60" align="center" />
        <el-table-column label="姓名" prop="personName" width="100">
          <template #default="scope">
            <el-link type="primary" @click="handleView(scope.row)">
              {{ scope.row.personName }}
            </el-link>
          </template>
        </el-table-column>
        <el-table-column label="编号" prop="personCode" width="120" />
        <el-table-column label="性别" prop="gender" width="80" align="center">
          <template #default="scope">
            <dict-tag :options="genderOptions" :value="scope.row.gender" />
          </template>
        </el-table-column>
        <el-table-column label="团队" prop="teamName" min-width="150" show-overflow-tooltip />
        <el-table-column label="职务" prop="position" width="120" />
        <el-table-column label="职称" prop="jobTitle" width="100">
          <template #default="scope">
            <dict-tag :options="jobTitleOptions" :value="scope.row.jobTitle" />
          </template>
        </el-table-column>
        <el-table-column label="职级" prop="jobLevel" width="100">
          <template #default="scope">
            <dict-tag :options="jobLevelOptions" :value="scope.row.jobLevel" />
          </template>
        </el-table-column>
        <el-table-column label="学历" prop="education" width="100">
          <template #default="scope">
            <dict-tag :options="educationOptions" :value="scope.row.education" />
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" width="80" align="center">
          <template #default="scope">
            <el-switch
              v-model="scope.row.status"
              active-value="0"
              inactive-value="1"
              @change="handleStatusChange(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center" fixed="right">
          <template #default="scope">
            <el-button link type="primary" icon="View" @click="handleView(scope.row)"
              >查看</el-button
            >
            <el-button link type="primary" icon="Edit" @click="handleEdit(scope.row)"
              >编辑</el-button
            >
            <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="queryParams.current"
        v-model:page-size="queryParams.size"
        :page-sizes="[10, 20, 30, 50]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="getList"
        @current-change="getList"
      />
    </el-card>

    <!-- 导入对话框 -->
    <el-dialog v-model="importOpen" title="导入人员" width="400px" append-to-body>
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx,.xls"
        :auto-upload="false"
        :on-change="handleFileChange"
        drag
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip">
            <el-link type="primary" @click="downloadTemplate">下载模板</el-link>
            只能上传 xlsx/xls 文件
          </div>
        </template>
      </el-upload>
      <template #footer>
        <el-button @click="importOpen = false">取消</el-button>
        <el-button type="primary" @click="submitImport">确定导入</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { UploadFilled } from '@element-plus/icons-vue'
import {
  listPerson,
  delPerson,
  updatePerson,
  exportPerson,
  importPerson,
  downloadTemplate,
} from '@/api/core/person'
import { treeSelectTeam } from '@/api/system/team'
import { getDictDataByType } from '@/api/system/dict'
import DictTag from '@/components/DictTag/index.vue'
import type { PersonInfo } from '@/types/person'

const router = useRouter()

// 列表相关
const loading = ref(false)
const personList = ref<PersonInfo[]>([])
const total = ref(0)
const ids = ref<number[]>([])
const multiple = ref(true)

// 查询参数
const queryParams = reactive({
  current: 1,
  size: 10,
  personName: '',
  personCode: '',
  teamId: undefined,
  jobTitle: '',
})

// 字典数据
const teamOptions = ref([])
const genderOptions = ref([])
const jobTitleOptions = ref([])
const jobLevelOptions = ref([])
const educationOptions = ref([])

// 导入相关
const importOpen = ref(false)
const uploadRef = ref()
const importFile = ref()

// 获取列表
const getList = async () => {
  loading.value = true
  try {
    const res = await listPerson(queryParams)
    personList.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

// 获取字典数据
const getDictData = async () => {
  const [teams, gender, jobTitle, jobLevel, education] = await Promise.all([
    treeSelectTeam(),
    getDictDataByType('sys_user_sex'),
    getDictDataByType('job_title'),
    getDictDataByType('job_level'),
    getDictDataByType('education'),
  ])

  teamOptions.value = teams.data
  genderOptions.value = gender.data
  jobTitleOptions.value = jobTitle.data
  jobLevelOptions.value = jobLevel.data
  educationOptions.value = education.data
}

// 搜索
const handleQuery = () => {
  queryParams.current = 1
  getList()
}

// 重置
const resetQuery = () => {
  queryParams.personName = ''
  queryParams.personCode = ''
  queryParams.teamId = undefined
  queryParams.jobTitle = ''
  handleQuery()
}

// 多选
const handleSelectionChange = (selection: PersonInfo[]) => {
  ids.value = selection.map((item) => item.id!)
  multiple.value = !selection.length
}

// 新增
const handleAdd = () => {
  router.push('/system/person/add')
}

// 查看
const handleView = (row: PersonInfo) => {
  router.push(`/system/person/view/${row.id}`)
}

// 编辑
const handleEdit = (row: PersonInfo) => {
  router.push(`/system/person/edit/${row.id}`)
}

// 删除
const handleDelete = async (row?: PersonInfo) => {
  const personIds = row?.id ? [row.id] : ids.value

  await ElMessageBox.confirm('是否确认删除选中的人员数据？', '系统提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })

  await delPerson(personIds)
  ElMessage.success('删除成功')
  getList()
}

// 状态修改
const handleStatusChange = async (row: PersonInfo) => {
  try {
    await updatePerson(row)
    ElMessage.success('状态修改成功')
  } catch (error) {
    row.status = row.status === '0' ? '1' : '0'
  }
}

// 导出
const handleExport = async () => {
  await ElMessageBox.confirm('确认导出所有人员数据吗？', '系统提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })

  loading.value = true
  try {
    const res = await exportPerson(queryParams)
    const blob = new Blob([res.data])
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = `人员信息_${new Date().getTime()}.xlsx`
    link.click()
    window.URL.revokeObjectURL(url)
  } finally {
    loading.value = false
  }
}

// 导入
const handleImport = () => {
  importOpen.value = true
}

// 下载模板
const downloadTemplate = async () => {
  const res = await downloadTemplate()
  const blob = new Blob([res.data])
  const url = window.URL.createObjectURL(blob)
  const link = document.createElement('a')
  link.href = url
  link.download = '人员信息导入模板.xlsx'
  link.click()
  window.URL.revokeObjectURL(url)
}

// 文件变化
const handleFileChange = (file: any) => {
  importFile.value = file.raw
}

// 提交导入
const submitImport = async () => {
  if (!importFile.value) {
    ElMessage.warning('请选择要导入的文件')
    return
  }

  const formData = new FormData()
  formData.append('file', importFile.value)

  loading.value = true
  try {
    await importPerson(formData)
    ElMessage.success('导入成功')
    importOpen.value = false
    getList()
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  getDictData()
  getList()
})
</script>

<style lang="scss" scoped>
.person-container {
  padding: 20px;

  .search-card {
    margin-bottom: 20px;
  }

  .table-card {
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .el-table {
      margin-bottom: 20px;
    }
  }
}
</style>
