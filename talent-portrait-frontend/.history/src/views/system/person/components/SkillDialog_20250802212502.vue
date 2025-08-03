<template>
  <el-dialog v-model="visible" title="专业能力信息" width="600px" :before-close="handleClose">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="能力类型" prop="skillType">
        <el-select v-model="form.skillType" placeholder="请选择能力类型" style="width: 100%">
          <el-option
            v-for="dict in dictOptions.skillTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="能力名称" prop="skillName">
        <el-input v-model="form.skillName" placeholder="请输入能力名称" />
      </el-form-item>

      <el-form-item label="能力等级" prop="skillLevel">
        <el-select v-model="form.skillLevel" placeholder="请选择能力等级" style="width: 100%">
          <el-option
            v-for="dict in dictOptions.skillLevelOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="经验年限" prop="experienceYears">
        <el-input-number
          v-model="form.experienceYears"
          :min="0"
          :max="50"
          controls-position="right"
          style="width: 100%"
        />
      </el-form-item>

      <el-form-item label="认证证书" prop="certification">
        <el-input v-model="form.certification" placeholder="请输入认证证书" />
      </el-form-item>

      <el-form-item label="描述说明" prop="description">
        <el-input
          v-model="form.description"
          type="textarea"
          :rows="3"
          placeholder="请输入描述说明"
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
import type { PersonSkill } from '@/types/person'

interface Props {
  modelValue: boolean
  skillData: Partial<PersonSkill>
  dictOptions: {
    skillTypeOptions: any[]
    skillLevelOptions: any[]
  }
}

interface Emits {
  (e: 'update:modelValue', value: boolean): void
  (e: 'confirm', data: PersonSkill): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

const visible = ref(false)
const formRef = ref<FormInstance>()
const form = reactive<PersonSkill>({
  skillType: '',
  skillName: '',
  skillLevel: '',
  experienceYears: 0,
  certification: '',
  description: '',
})

const rules = reactive<FormRules>({
  skillType: [{ required: true, message: '请选择能力类型', trigger: 'change' }],
  skillName: [{ required: true, message: '请输入能力名称', trigger: 'blur' }],
  skillLevel: [{ required: true, message: '请选择能力等级', trigger: 'change' }],
  experienceYears: [{ required: true, message: '请输入经验年限', trigger: 'blur' }],
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
  () => props.skillData,
  (val) => {
    if (val) {
      Object.assign(form, {
        skillType: val.skillType || '',
        skillName: val.skillName || '',
        skillLevel: val.skillLevel || '',
        experienceYears: val.experienceYears || 0,
        certification: val.certification || '',
        description: val.description || '',
      })
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
