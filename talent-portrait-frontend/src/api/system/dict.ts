import request from '@/utils/request'
import type { DictType, DictData } from '@/types/system'

/**
 * 字典管理API
 * 对应后端：/api/system/dict
 */

// 查询字典类型列表
export function listDictType(query: any) {
  return request({
    url: '/system/dict/type/list',
    method: 'get',
    params: query,
  })
}

// 查询所有字典类型
export function allDictType() {
  return request({
    url: '/system/dict/type/all',
    method: 'get',
  })
}

// 查询字典类型详细
export function getDictType(dictId: number) {
  return request({
    url: `/system/dict/type/${dictId}`,
    method: 'get',
  })
}

// 新增字典类型
export function addDictType(data: DictType) {
  return request({
    url: '/system/dict/type',
    method: 'post',
    data,
  })
}

// 修改字典类型
export function updateDictType(data: DictType) {
  return request({
    url: '/system/dict/type',
    method: 'put',
    data,
  })
}

// 删除字典类型
export function delDictType(dictIds: number[]) {
  return request({
    url: `/system/dict/type/${dictIds}`,
    method: 'delete',
  })
}

// 清理字典缓存
export function clearDictCache() {
  return request({
    url: '/system/dict/type/clearCache',
    method: 'delete',
  })
}

// 查询字典数据列表
export function listDictData(query: any) {
  return request({
    url: '/system/dict/data/list',
    method: 'get',
    params: query,
  })
}

// 根据字典类型查询字典数据
export function getDictDataByType(dictType: string) {
  return request({
    url: `/system/dict/data/type/${dictType}`,
    method: 'get',
  })
}

// 查询字典数据详细
export function getDictData(dictId: number) {
  return request({
    url: `/system/dict/data/${dictId}`,
    method: 'get',
  })
}

// 新增字典数据
export function addDictData(data: DictData) {
  return request({
    url: '/system/dict/data',
    method: 'post',
    data,
  })
}

// 修改字典数据
export function updateDictData(data: DictData) {
  return request({
    url: '/system/dict/data',
    method: 'put',
    data,
  })
}

// 删除字典数据
export function delDictData(dictIds: number[]) {
  return request({
    url: `/system/dict/data/${dictIds}`,
    method: 'delete',
  })
}
