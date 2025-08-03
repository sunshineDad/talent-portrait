/**
 * Mock数据拦截器
 */
import type { AxiosRequestConfig, AxiosResponse } from 'axios'
import { mockPersonList, getMockPersonDetail } from './data/person'
import { mockTeamTree, mockTeamStatistics } from './data/team'
import { getMockPersonPortrait, getMockTeamPortrait } from './data/portrait'

// 模拟延迟
const delay = (ms: number) => new Promise((resolve) => setTimeout(resolve, ms))

// 创建响应包装
function createResponse<T>(data: T, msg = '操作成功', code = 200): AxiosResponse {
  return {
    data: {
      code,
      msg,
      data,
    },
    status: 200,
    statusText: 'OK',
    headers: {},
    config: {} as AxiosRequestConfig,
  }
}

// Mock处理器映射
const mockHandlers: Record<string, (config: AxiosRequestConfig) => Promise<AxiosResponse>> = {
  // 人员列表
  'GET /core/person/list': async (config) => {
    await delay(300)
    const params = config.params || {}
    const current = params.current || 1
    const size = params.size || 10

    // 分页处理
    const start = (current - 1) * size
    const end = start + size
    const records = mockPersonList.slice(start, end)

    return createResponse({
      records,
      total: mockPersonList.length,
      size,
      current,
      pages: Math.ceil(mockPersonList.length / size),
    })
  },

  // 人员详情
  'GET /core/person/:id': async (config) => {
    await delay(300)
    const match = config.url?.match(/\/core\/person\/(\d+)/)
    const personId = match ? parseInt(match[1]) : 1
    const person = getMockPersonDetail(personId)

    if (!person) {
      return createResponse(null, '人员不存在', 404)
    }

    return createResponse(person)
  },

  // 团队树形列表
  'GET /system/team/treeList': async () => {
    await delay(400)
    return createResponse(mockTeamTree)
  },

  // 团队列表
  'GET /system/team/list': async () => {
    await delay(300)
    // 将树形结构扁平化
    const flatList: any[] = []
    const flatten = (teams: any[], parent?: any) => {
      teams.forEach((team) => {
        flatList.push({ ...team, children: undefined })
        if (team.children?.length) {
          flatten(team.children, team)
        }
      })
    }
    flatten(mockTeamTree)

    return createResponse({
      records: flatList,
      total: flatList.length,
    })
  },

  // 团队详情
  'GET /system/team/:id': async (config) => {
    await delay(300)
    const match = config.url?.match(/\/system\/team\/(\d+)/)
    const teamId = match ? parseInt(match[1]) : 1

    // 从树形结构中查找团队
    const findTeam = (teams: any[], id: number): any => {
      for (const team of teams) {
        if (team.id === id) return team
        if (team.children?.length) {
          const found = findTeam(team.children, id)
          if (found) return found
        }
      }
      return null
    }

    const team = findTeam(mockTeamTree, teamId)
    if (!team) {
      return createResponse(null, '团队不存在', 404)
    }

    return createResponse(team)
  },

  // 团队统计信息
  'GET /system/team/:id/statistics': async (config) => {
    await delay(300)
    const match = config.url?.match(/\/system\/team\/(\d+)\/statistics/)
    const teamId = match ? parseInt(match[1]) : 1
    const stats = mockTeamStatistics[teamId as keyof typeof mockTeamStatistics]

    return createResponse(
      stats || {
        memberCount: 30,
        subTeamCount: 0,
        avgAge: 35,
        maleCount: 20,
        femaleCount: 10,
        doctorCount: 5,
        masterCount: 15,
        bachelorCount: 10,
        seniorTitleCount: 3,
        middleTitleCount: 10,
        juniorTitleCount: 17,
      },
    )
  },

  // 团队成员列表
  'GET /system/team/:id/members': async (config) => {
    await delay(300)
    const match = config.url?.match(/\/system\/team\/(\d+)\/members/)
    const teamId = match ? parseInt(match[1]) : 1

    // 筛选属于该团队的成员
    const members = mockPersonList.filter((person) => person.teamId === teamId)

    return createResponse(members)
  },

  // 团队下拉树
  'GET /system/team/treeSelect': async () => {
    await delay(300)
    return createResponse(mockTeamTree)
  },

  // 人员画像
  'GET /core/portrait/person/:id': async (config) => {
    await delay(500)
    const match = config.url?.match(/\/core\/portrait\/person\/(\d+)/)
    const personId = match ? parseInt(match[1]) : 1
    const portrait = getMockPersonPortrait(personId)

    return createResponse(portrait)
  },

  // 团队画像
  'GET /core/portrait/team/:id': async (config) => {
    await delay(500)
    const match = config.url?.match(/\/core\/portrait\/team\/(\d+)/)
    const teamId = match ? parseInt(match[1]) : 1
    const portrait = getMockTeamPortrait(teamId)

    return createResponse(portrait)
  },
}

// 匹配URL和方法
function matchHandler(
  config: AxiosRequestConfig,
): ((config: AxiosRequestConfig) => Promise<AxiosResponse>) | null {
  const method = config.method?.toUpperCase() || 'GET'
  const url = config.url || ''

  // 精确匹配
  const exactKey = `${method} ${url}`
  if (mockHandlers[exactKey]) {
    return mockHandlers[exactKey]
  }

  // 模式匹配（处理带参数的路径）
  for (const [pattern, handler] of Object.entries(mockHandlers)) {
    const [patternMethod, patternPath] = pattern.split(' ')
    if (patternMethod !== method) continue

    // 将模式转换为正则表达式
    const regexPattern = patternPath
      .replace(/:[^/]+/g, '[^/]+') // 替换 :id 为匹配模式
      .replace(/\//g, '\\/') // 转义斜杠

    const regex = new RegExp(`^${regexPattern}$`)
    if (regex.test(url)) {
      return handler
    }
  }

  return null
}

// 导出拦截器安装函数
export function setupMockInterceptor(axiosInstance: any) {
  // 请求拦截器
  axiosInstance.interceptors.request.use(
    (config: AxiosRequestConfig) => {
      // 检查是否启用mock
      const useMock = import.meta.env.VITE_USE_MOCK === 'true'
      if (!useMock) return config

      // 标记为mock请求
      const handler = matchHandler(config)
      if (handler) {
        ;(config as any).__mockHandler = handler
      }

      return config
    },
    (error: any) => Promise.reject(error),
  )

  // 响应拦截器
  axiosInstance.interceptors.response.use(
    async (response: AxiosResponse) => {
      // 如果是真实响应，直接返回
      if (!(response.config as any).__mockHandler) {
        return response
      }

      // 使用mock处理器
      const handler = (response.config as any).__mockHandler
      try {
        const mockResponse = await handler(response.config)
        return mockResponse
      } catch (error) {
        console.error('Mock handler error:', error)
        return response
      }
    },
    async (error: any) => {
      // 检查是否有mock处理器
      const config = error.config
      if (config && (config as any).__mockHandler) {
        const handler = (config as any).__mockHandler
        try {
          const mockResponse = await handler(config)
          return mockResponse
        } catch (mockError) {
          console.error('Mock handler error:', mockError)
        }
      }

      return Promise.reject(error)
    },
  )
}

// 导出一个函数用于动态启用/禁用mock
export function toggleMock(enabled: boolean) {
  ;(window as any).__USE_MOCK__ = enabled
}
