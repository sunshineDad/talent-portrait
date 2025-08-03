import request from '@/utils/request'

/**
 * 人才画像API
 * 对应后端：/api/core/portrait
 */

// 获取人员画像数据
export function getPersonPortrait(personId: number) {
  return request({
    url: `/core/portrait/person/${personId}`,
    method: 'get',
  })
}

// 获取团队画像数据
export function getTeamPortrait(teamId: string) {
  return request({
    url: `/core/portrait/team/${teamId}`,
    method: 'get',
  })
}
