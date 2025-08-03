<template>
  <el-dialog v-model="visible" title="项目经历信息" width="800px" :before-close="handleClose">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="项目名称" prop="projectName">
            <el-input v-model="form.projectName" placeholder="请输入项目名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目编号" prop="projectCode">
            <el-input v-model="form.projectCode" placeholder="请输入项目编号" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="项目类型" prop="projectType">
            <el-select v-model="form.projectType" placeholder="请选择项目类型" style="width: 100%">
              <el-option
                v-for="dict in dictOptions.projectTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目级别" prop="projectLevel">
            <el-select v-model="form.projectLevel" placeholder="请选择项目级别" style="width: 100%">
              <el-option
                v-for="dict in dictOptions.projectLevelOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="开始时间" prop="startDate">
            <el-date-picker
              v-model="form.startDate"
              type="date"
              placeholder="选择开始时间"
              value-format="YYYY-MM-DD"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="结束时间" prop="endDate">
            <el-date-picker
              v-model="form.endDate"
              type="date"
              placeholder="选择结束时间"
              value-format="YYYY-MM-DD"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="项目角色" prop="projectRole">
            <el-input v-model="form.projectRole" placeholder="如：项目负责人、技术负责人等" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目状态" prop="projectStatus">
            <el-select
              v-model="form.projectStatus"
              placeholder="请选择项目状态"
              style="width: 100%"
            >
              <el-option label="进行中" value="ongoing" />
              <el-option label="已完成" value="completed" />
              <el-option label="已中止" value="terminated" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="团队规模" prop="teamSize">
            <el-input-number
              v-model="form.teamSize"
              :min="1"
              :max="9999"
              controls-position="right"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目预算" prop="projectBudget">
            <el-input-number
              v-model="form.projectBudget"
              :min="0"
              :precision="2"
              controls-position="right"
              style="width: 100%"
              placeholder="万元"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="项目成果" prop="achievement">
        <el-input
          v-model="form.achievement"
          type="textarea"
          :rows="2"
          placeholder="请输入项目成果"
        />
      </el-form-item>

      <el-form-item label="技术栈" prop="technologyStack">
        <el-input v-model="form.technologyStack" placeholder="请输入使用的技术栈，多个用逗号分隔" />
      </el-form-item>

      <el-form-item label="项目描述" prop="description">
        <el-input
          v-model="form.description"
          type="textarea"
          :rows="3"
          placeholder="请输入项目描述"
        />
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="handleConfirm">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, watch } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import type { PersonProject } from '@/types/person'

interface Props {
  modelValue: boolean
  projectData: Partial<PersonProject>
  dictOptions: {
    projectTypeOptions: any[]
    projectLevelOptions: any[]
  }
}

interface Emits {
  (e: 'update:modelValue', value: boolean): void
  (e: 'confirm', data: PersonProject): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

const visible = ref(false)
const formRef = ref<FormInstance>()
const form = reactive<PersonProject>({
  projectName: '',
  projectCode: '',
  projectType: '',
  projectLevel: '',
  startDate: '',
  endDate: '',
  projectRole: '',
  projectStatus: '',
  achievement: '',
  technologyStack: '',
  teamSize: 0,
  projectBudget: 0,
  description: '',
})

const rules = reactive<FormRules>({
  projectName: [{ required: true, message: '请输入项目名称', trigger: 'blur' }],
  projectType: [{ required: true, message: '请选择项目类型', trigger: 'change' }],
  projectLevel: [{ required: true, message: '请选择项目级别', trigger: 'change' }],
  startDate: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
  projectRole: [{ required: true, message: '请输入项目角色', trigger: 'blur' }],
})

watch(
  () => props.modelValue,
  (val) => {
    visible.value = val
  }
)

watch(visible, (val) => {
  emit('update:modelValue', val)
})

watch(
  () => props.projectData,
  (val) => {
    if (val) {
      Object.assign(form, val)
    }
  },
  { immediate: true }
)

const handleClose = () => {
  visible.value = false
  formRef.value?.resetFields()
}

const handleConfirm = async () => {
  if (!formRef.value) return

  await formRef.value.validate()
  emit('confirm', { ...form })
  handleClose()
}
</script>
