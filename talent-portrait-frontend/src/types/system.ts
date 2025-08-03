/**
 * 系统管理类型定义
 * 根据数据库设计文档定义
 */

// 字典类型
export interface DictType {
  id?: number
  dictName: string
  dictType: string
  status: string
  createBy?: string
  createTime?: string
  updateBy?: string
  updateTime?: string
  remark?: string
}

// 字典数据
export interface DictData {
  id?: number
  sortOrder: number
  dictLabel: string
  dictValue: string
  dictType: string
  cssClass?: string
  listClass?: string
  isDefault: string
  status: string
  createBy?: string
  createTime?: string
  updateBy?: string
  updateTime?: string
  remark?: string
}

// 团队信息
export interface TeamInfo {
  id?: number
  teamCode: string
  teamName: string
  teamType: string
  parentId?: number
  ancestors?: string
  orderNum: number
  leaderId?: number
  leaderName?: string
  phone?: string
  email?: string
  status: string
  delFlag?: string
  createBy?: string
  createTime?: string
  updateBy?: string
  updateTime?: string
  remark?: string
  children?: TeamInfo[]
}
