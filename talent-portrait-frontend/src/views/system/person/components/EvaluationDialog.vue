<template>
  <el-dialog v-model="visible" title="人才评估信息" width="600px" :before-close="handleClose">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="120px">
      <el-form-item label="评估年度" prop="evaluationYear">
        <el-date-picker
          v-model="form.evaluationYear"
          type="year"
          placeholder="选择评估年度"
          format="YYYY"
          value-format="YYYY"
          style="width: 100%"
        />
      </el-form-item>

      <el-form-item label="评估维度" prop="evaluationDimension">
        <el-select
          v-model="form.evaluationDimension"
          placeholder="请选择评估维度"
          style="width: 100%"
        >
          <el-option
            v-for="dict in dictOptions.evaluationDimensionOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="维度得分" prop="dimensionScore">
            <el-input-number
              v-model="form.dimensionScore"
              :min="0"
              :max="100"
              :precision="1"
              controls-position="right"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="维度等级" prop="dimensionLevel">
            <el-select
              v-model="form.dimensionLevel"
              placeholder="请选择维度等级"
              style="width: 100%"
            >
              <el-option label="优秀" value="优秀" />
              <el-option label="良好" value="良好" />
              <el-option label="合格" value="合格" />
              <el-option label="待提升" value="待提升" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="评估内容" prop="evaluationContent">
        <el-input
          v-model="form.evaluationContent"
          type="textarea"
          :rows="3"
          placeholder="请输入评估内容"
        />
      </el-form-item>

      <el-form-item label="优势特点" prop="strengths">
        <el-input v-model="form.strengths" type="textarea" :rows="2" placeholder="请输入优势特点" />
      </el-form-item>

      <el-form-item label="不足之处" prop="weaknesses">
        <el-input
          v-model="form.weaknesses"
          type="textarea"
          :rows="2"
          placeholder="请输入不足之处"
        />
      </el-form-item>

      <el-form-item label="发展建议" prop="developmentSuggestions">
        <el-input
          v-model="form.developmentSuggestions"
          type="textarea"
          :rows="3"
          placeholder="请输入发展建议"
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
import type { PersonEvaluation } from '@/types/person'

interface Props {
  modelValue: boolean
  evaluationData: Partial<PersonEvaluation>
  dictOptions: {
    evaluationDimensionOptions: any[]
  }
}

interface Emits {
  (e: 'update:modelValue', value: boolean): void
  (e: 'confirm', data: PersonEvaluation): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

const visible = ref(false)
const formRef = ref<FormInstance>()
const form = reactive<PersonEvaluation>({
  evaluationYear: new Date().getFullYear(),
  evaluationDimension: '',
  dimensionScore: 0,
  dimensionLevel: '',
  evaluationContent: '',
  strengths: '',
  weaknesses: '',
  developmentSuggestions: '',
  evaluator: '',
  evaluationDate: '',
})

const rules = reactive<FormRules>({
  evaluationYear: [{ required: true, message: '请选择评估年度', trigger: 'change' }],
  evaluationDimension: [{ required: true, message: '请选择评估维度', trigger: 'change' }],
  dimensionScore: [{ required: true, message: '请输入维度得分', trigger: 'blur' }],
  dimensionLevel: [{ required: true, message: '请选择维度等级', trigger: 'change' }],
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
  () => props.evaluationData,
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
