<template>
  <el-dialog v-model="visible" title="绩效信息" width="600px" :before-close="handleClose">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="年度" prop="year">
            <el-date-picker
              v-model="form.year"
              type="year"
              placeholder="选择年度"
              format="YYYY"
              value-format="YYYY"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="季度" prop="quarter">
            <el-select v-model="form.quarter" placeholder="请选择季度" style="width: 100%">
              <el-option label="第一季度" :value="1" />
              <el-option label="第二季度" :value="2" />
              <el-option label="第三季度" :value="3" />
              <el-option label="第四季度" :value="4" />
              <el-option label="年度" :value="0" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="绩效得分" prop="performanceScore">
            <el-input-number
              v-model="form.performanceScore"
              :min="0"
              :max="100"
              :precision="1"
              controls-position="right"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="绩效等级" prop="performanceLevel">
            <el-select
              v-model="form.performanceLevel"
              placeholder="请选择绩效等级"
              style="width: 100%"
            >
              <el-option label="S" value="S" />
              <el-option label="A" value="A" />
              <el-option label="B" value="B" />
              <el-option label="C" value="C" />
              <el-option label="D" value="D" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="KPI完成率" prop="kpiCompletion">
            <el-input-number
              v-model="form.kpiCompletion"
              :min="0"
              :max="200"
              :precision="1"
              controls-position="right"
              style="width: 100%"
            >
              <template #append>%</template>
            </el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="工作质量" prop="workQuality">
            <el-input-number
              v-model="form.workQuality"
              :min="0"
              :max="100"
              controls-position="right"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="工作态度" prop="workAttitude">
            <el-input-number
              v-model="form.workAttitude"
              :min="0"
              :max="100"
              controls-position="right"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="团队贡献" prop="teamContribution">
            <el-input-number
              v-model="form.teamContribution"
              :min="0"
              :max="100"
              controls-position="right"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="改进建议" prop="improvementSuggestions">
        <el-input
          v-model="form.improvementSuggestions"
          type="textarea"
          :rows="3"
          placeholder="请输入改进建议"
        />
      </el-form-item>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="评估人" prop="evaluator">
            <el-input v-model="form.evaluator" placeholder="请输入评估人" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="评估日期" prop="evaluationDate">
            <el-date-picker
              v-model="form.evaluationDate"
              type="date"
              placeholder="选择评估日期"
              value-format="YYYY-MM-DD"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
      </el-row>
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
import type { PersonPerformance } from '@/types/person'

interface Props {
  modelValue: boolean
  performanceData: Partial<PersonPerformance>
}

interface Emits {
  (e: 'update:modelValue', value: boolean): void
  (e: 'confirm', data: PersonPerformance): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

const visible = ref(false)
const formRef = ref<FormInstance>()
const form = reactive<PersonPerformance>({
  year: new Date().getFullYear(),
  quarter: 0,
  performanceScore: 0,
  performanceLevel: '',
  kpiCompletion: 0,
  workQuality: 0,
  workAttitude: 0,
  teamContribution: 0,
  improvementSuggestions: '',
  evaluator: '',
  evaluationDate: '',
})

const rules = reactive<FormRules>({
  year: [{ required: true, message: '请选择年度', trigger: 'change' }],
  performanceScore: [{ required: true, message: '请输入绩效得分', trigger: 'blur' }],
  performanceLevel: [{ required: true, message: '请选择绩效等级', trigger: 'change' }],
  evaluator: [{ required: true, message: '请输入评估人', trigger: 'blur' }],
  evaluationDate: [{ required: true, message: '请选择评估日期', trigger: 'change' }],
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
  () => props.performanceData,
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
