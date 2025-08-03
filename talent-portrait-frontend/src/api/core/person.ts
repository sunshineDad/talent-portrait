import request from '@/utils/request'
import type { PersonInfo } from '@/types/person'

/**
 * 人员信息管理API
 * 对应后端：/api/core/person
 */

// 查询人员列表
export function listPerson(query: any) {
  return request({
    url: '/core/person/list',
    method: 'get',
    params: query,
  })
}

// 查询人员详细信息（包含子表）
export function getPerson(personId: number) {
  return request({
    url: `/core/person/${personId}`,
    method: 'get',
  })
}

// 新增人员
export function addPerson(data: PersonInfo) {
  return request({
    url: '/core/person',
    method: 'post',
    data,
  })
}

// 修改人员
export function updatePerson(data: PersonInfo) {
  return request({
    url: '/core/person',
    method: 'put',
    data,
  })
}

// 删除人员
export function delPerson(personIds: number[]) {
  return request({
    url: `/core/person/${personIds}`,
    method: 'delete',
  })
}

// 导出人员Excel
export function exportPerson(query: any) {
  return request({
    url: '/core/person/export',
    method: 'get',
    params: query,
    responseType: 'blob',
  })
}

// 下载导入模板
export function downloadTemplate() {
  return request({
    url: '/core/person/importTemplate',
    method: 'get',
    responseType: 'blob',
  })
}

// 导入人员Excel
export function importPerson(formData: FormData) {
  return request({
    url: '/core/person/importData',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
}
