import request from '@/utils/request'
import type { TeamInfo } from '@/types/system'

/**
 * 团队管理API
 * 对应后端：/api/system/team
 */

// 查询团队树形列表
export function treeListTeam(query?: any) {
  return request({
    url: '/system/team/treeList',
    method: 'get',
    params: query,
  })
}

// 查询团队列表
export function listTeam(query?: any) {
  return request({
    url: '/system/team/list',
    method: 'get',
    params: query,
  })
}

// 查询团队详细
export function getTeam(teamId: number) {
  return request({
    url: `/system/team/${teamId}`,
    method: 'get',
  })
}

// 新增团队
export function addTeam(data: TeamInfo) {
  return request({
    url: '/system/team',
    method: 'post',
    data,
  })
}

// 修改团队
export function updateTeam(data: TeamInfo) {
  return request({
    url: '/system/team',
    method: 'put',
    data,
  })
}

// 删除团队
export function delTeam(teamId: number) {
  return request({
    url: `/system/team/${teamId}`,
    method: 'delete',
  })
}

// 查询团队下拉树结构
export function treeSelectTeam() {
  return request({
    url: '/system/team/treeSelect',
    method: 'get',
  })
}

// 查询团队统计信息
export function getTeamStatistics(teamId: number) {
  return request({
    url: `/system/team/${teamId}/statistics`,
    method: 'get',
  })
}

// 查询团队成员列表
export function getTeamMembers(teamId: number) {
  return request({
    url: `/system/team/${teamId}/members`,
    method: 'get',
  })
}
