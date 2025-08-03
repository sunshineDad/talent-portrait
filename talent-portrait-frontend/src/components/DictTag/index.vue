<template>
  <el-tag v-if="displayValue" :type="tagType" :size="size">
    {{ displayValue }}
  </el-tag>
  <span v-else>-</span>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  options: Array<{
    dictLabel: string
    dictValue: string
    listClass?: string
  }>
  value: string | number
  size?: 'large' | 'default' | 'small'
}

const props = withDefaults(defineProps<Props>(), {
  size: 'default',
})

// 显示值
const displayValue = computed(() => {
  const option = props.options.find((item) => item.dictValue === String(props.value))
  return option?.dictLabel || ''
})

// 标签类型
const tagType = computed(() => {
  const option = props.options.find((item) => item.dictValue === String(props.value))
  const listClass = option?.listClass || 'default'

  const typeMap: Record<string, any> = {
    default: '',
    primary: 'primary',
    success: 'success',
    warning: 'warning',
    danger: 'danger',
    info: 'info',
  }

  return typeMap[listClass] || ''
})
</script>
