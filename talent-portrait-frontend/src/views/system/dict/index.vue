<template>
  <div class="dict-container">
    <div class="dict-wrapper">
      <!-- 左侧字典类型树 -->
      <div class="dict-type-tree">
        <div class="tree-header">
          <span>字典类型</span>
          <el-button type="primary" size="small" @click="handleAddType">新增</el-button>
        </div>
        <div class="tree-content">
          <el-table
            :data="dictTypeList"
            highlight-current-row
            @current-change="handleTypeChange"
            style="width: 100%"
          >
            <el-table-column prop="dictName" label="字典名称" />
            <el-table-column prop="dictType" label="字典类型" width="120" />
            <el-table-column label="状态" width="80" align="center">
              <template #default="scope">
                <el-tag :type="scope.row.status === '0' ? 'success' : 'danger'">
                  {{ scope.row.status === '0' ? '正常' : '停用' }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>

      <!-- 右侧字典数据 -->
      <div class="dict-data-panel">
        <div class="search-bar">
          <el-form :inline="true" :model="queryParams" class="demo-form-inline">
            <el-form-item label="字典标签">
              <el-input v-model="queryParams.dictLabel" placeholder="请输入字典标签" />
            </el-form-item>
            <el-form-item label="状态">
              <el-select v-model="queryParams.status" placeholder="字典状态">
                <el-option label="全部" value="" />
                <el-option label="正常" value="0" />
                <el-option label="停用" value="1" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleQuery">搜索</el-button>
              <el-button @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
        </div>

        <div class="toolbar">
          <el-button type="primary" @click="handleAddData">新增</el-button>
          <el-button type="danger" :disabled="ids.length === 0" @click="handleDelete"
            >删除</el-button
          >
        </div>

        <el-table
          v-loading="loading"
          :data="dictDataList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column prop="dictLabel" label="字典标签" />
          <el-table-column prop="dictValue" label="字典键值" />
          <el-table-column prop="sortOrder" label="字典排序" width="100" />
          <el-table-column label="状态" width="100" align="center">
            <template #default="scope">
              <el-switch
                v-model="scope.row.status"
                active-value="0"
                inactive-value="1"
                @change="handleStatusChange(scope.row)"
              />
            </template>
          </el-table-column>
          <el-table-column prop="remark" label="备注" />
          <el-table-column label="操作" width="160" align="center">
            <template #default="scope">
              <el-button link type="primary" @click="handleEdit(scope.row)">编辑</el-button>
              <el-button link type="danger" @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          v-model:current-page="queryParams.current"
          v-model:page-size="queryParams.size"
          :page-sizes="[10, 20, 30, 50]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleQuery"
          @current-change="handleQuery"
        />
      </div>
    </div>

    <!-- 字典类型表单对话框 -->
    <el-dialog v-model="typeOpen" :title="typeTitle" width="500px" append-to-body>
      <el-form ref="typeFormRef" :model="typeForm" :rules="typeRules" label-width="80px">
        <el-form-item label="字典名称" prop="dictName">
          <el-input v-model="typeForm.dictName" placeholder="请输入字典名称" />
        </el-form-item>
        <el-form-item label="字典类型" prop="dictType">
          <el-input v-model="typeForm.dictType" placeholder="请输入字典类型" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="typeForm.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="typeForm.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="typeOpen = false">取消</el-button>
        <el-button type="primary" @click="submitTypeForm">确定</el-button>
      </template>
    </el-dialog>

    <!-- 字典数据表单对话框 -->
    <el-dialog v-model="dataOpen" :title="dataTitle" width="500px" append-to-body>
      <el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="80px">
        <el-form-item label="字典类型">
          <el-input v-model="currentDictType" :disabled="true" />
        </el-form-item>
        <el-form-item label="字典标签" prop="dictLabel">
          <el-input v-model="dataForm.dictLabel" placeholder="请输入字典标签" />
        </el-form-item>
        <el-form-item label="字典键值" prop="dictValue">
          <el-input v-model="dataForm.dictValue" placeholder="请输入字典键值" />
        </el-form-item>
        <el-form-item label="字典排序" prop="sortOrder">
          <el-input-number v-model="dataForm.sortOrder" :min="0" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="dataForm.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="dataForm.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dataOpen = false">取消</el-button>
        <el-button type="primary" @click="submitDataForm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import {
  listDictType,
  getDictType,
  addDictType,
  updateDictType,
  delDictType,
  listDictData,
  getDictData,
  addDictData,
  updateDictData,
  delDictData,
} from '@/api/system/dict'
import type { DictType, DictData } from '@/types/system'

// 字典类型相关
const dictTypeList = ref<DictType[]>([])
const currentDictType = ref('')
const typeOpen = ref(false)
const typeTitle = ref('')
const typeFormRef = ref<FormInstance>()
const typeForm = reactive<DictType>({
  dictName: '',
  dictType: '',
  status: '0',
  remark: '',
})
const typeRules = reactive<FormRules>({
  dictName: [{ required: true, message: '字典名称不能为空', trigger: 'blur' }],
  dictType: [{ required: true, message: '字典类型不能为空', trigger: 'blur' }],
})

// 字典数据相关
const loading = ref(false)
const ids = ref<number[]>([])
const dictDataList = ref<DictData[]>([])
const total = ref(0)
const dataOpen = ref(false)
const dataTitle = ref('')
const dataFormRef = ref<FormInstance>()
const dataForm = reactive<DictData>({
  sortOrder: 0,
  dictLabel: '',
  dictValue: '',
  dictType: '',
  isDefault: 'N',
  status: '0',
  remark: '',
})
const dataRules = reactive<FormRules>({
  dictLabel: [{ required: true, message: '字典标签不能为空', trigger: 'blur' }],
  dictValue: [{ required: true, message: '字典键值不能为空', trigger: 'blur' }],
  sortOrder: [{ required: true, message: '字典排序不能为空', trigger: 'blur' }],
})

// 查询参数
const queryParams = reactive({
  current: 1,
  size: 10,
  dictType: '',
  dictLabel: '',
  status: '',
})

// 获取字典类型列表
const getDictTypeList = async () => {
  const res = await listDictType({ current: 1, size: 1000 })
  dictTypeList.value = res.data.records
  if (dictTypeList.value.length > 0 && !currentDictType.value) {
    handleTypeChange(dictTypeList.value[0])
  }
}

// 获取字典数据列表
const getDictDataList = async () => {
  if (!currentDictType.value) return
  loading.value = true
  try {
    queryParams.dictType = currentDictType.value
    const res = await listDictData(queryParams)
    dictDataList.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

// 字典类型切换
const handleTypeChange = (row: DictType) => {
  currentDictType.value = row.dictType
  getDictDataList()
}

// 查询
const handleQuery = () => {
  queryParams.current = 1
  getDictDataList()
}

// 重置查询
const resetQuery = () => {
  queryParams.dictLabel = ''
  queryParams.status = ''
  handleQuery()
}

// 多选
const handleSelectionChange = (selection: DictData[]) => {
  ids.value = selection.map((item) => item.id!)
}

// 状态修改
const handleStatusChange = async (row: DictData) => {
  try {
    await updateDictData(row)
    ElMessage.success('状态修改成功')
  } catch (error) {
    row.status = row.status === '0' ? '1' : '0'
  }
}

// 新增字典类型
const handleAddType = () => {
  Object.assign(typeForm, {
    id: undefined,
    dictName: '',
    dictType: '',
    status: '0',
    remark: '',
  })
  typeOpen.value = true
  typeTitle.value = '添加字典类型'
}

// 新增字典数据
const handleAddData = () => {
  if (!currentDictType.value) {
    ElMessage.warning('请先选择字典类型')
    return
  }
  Object.assign(dataForm, {
    id: undefined,
    sortOrder: 0,
    dictLabel: '',
    dictValue: '',
    dictType: currentDictType.value,
    isDefault: 'N',
    status: '0',
    remark: '',
  })
  dataOpen.value = true
  dataTitle.value = '添加字典数据'
}

// 编辑
const handleEdit = async (row: DictData) => {
  const res = await getDictData(row.id!)
  Object.assign(dataForm, res.data)
  dataOpen.value = true
  dataTitle.value = '修改字典数据'
}

// 删除
const handleDelete = async (row?: DictData) => {
  const dictIds = row?.id ? [row.id] : ids.value
  await ElMessageBox.confirm('是否确认删除选中的数据项？', '系统提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })

  await delDictData(dictIds)
  ElMessage.success('删除成功')
  getDictDataList()
}

// 提交字典类型表单
const submitTypeForm = async () => {
  if (!typeFormRef.value) return
  await typeFormRef.value.validate()

  if (typeForm.id) {
    await updateDictType(typeForm)
    ElMessage.success('修改成功')
  } else {
    await addDictType(typeForm)
    ElMessage.success('新增成功')
  }

  typeOpen.value = false
  getDictTypeList()
}

// 提交字典数据表单
const submitDataForm = async () => {
  if (!dataFormRef.value) return
  await dataFormRef.value.validate()

  if (dataForm.id) {
    await updateDictData(dataForm)
    ElMessage.success('修改成功')
  } else {
    await addDictData(dataForm)
    ElMessage.success('新增成功')
  }

  dataOpen.value = false
  getDictDataList()
}

onMounted(() => {
  getDictTypeList()
})
</script>

<style lang="scss" scoped>
.dict-container {
  height: 100%;
  padding: 20px;

  .dict-wrapper {
    display: flex;
    gap: 20px;
    height: 100%;
    background: #fff;
    border-radius: 4px;

    .dict-type-tree {
      width: 400px;
      border-right: 1px solid #e8e8e8;
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

      .tree-content {
        flex: 1;
        overflow: auto;
      }
    }

    .dict-data-panel {
      flex: 1;
      padding: 16px;
      display: flex;
      flex-direction: column;

      .search-bar {
        margin-bottom: 16px;
      }

      .toolbar {
        margin-bottom: 16px;
      }

      .el-table {
        flex: 1;
      }

      .el-pagination {
        margin-top: 16px;
      }
    }
  }
}
</style>
