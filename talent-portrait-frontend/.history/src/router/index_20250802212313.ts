import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Layout from '@/layouts/index.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: Layout,
    redirect: '/talent/person-portrait',
    children: [
      {
        path: 'talent',
        name: 'Talent',
        meta: { title: '人才管理看板', icon: 'user' },
        children: [
          {
            path: 'person-portrait',
            name: 'PersonPortrait',
            component: () => import('@/views/talent/person-portrait/index.vue'),
            meta: { title: '人才画像' },
          },
          {
            path: 'team-portrait',
            name: 'TeamPortrait',
            component: () => import('@/views/talent/team-portrait/index.vue'),
            meta: { title: '队伍元画像' },
          },
        ],
      },
      {
        path: 'system',
        name: 'System',
        meta: { title: '系统管理', icon: 'setting' },
        children: [
          {
            path: 'dict',
            name: 'Dict',
            component: () => import('@/views/system/dict/index.vue'),
            meta: { title: '码表管理' },
          },
          {
            path: 'person',
            name: 'Person',
            component: () => import('@/views/system/person/index.vue'),
            meta: { title: '人员信息管理' },
          },
          // 在 system 路由的 children 中添加
          {
            path: 'person',
            name: 'Person',
            component: () => import('@/views/system/person/index.vue'),
            meta: { title: '人员信息管理' },
          },
          {
            path: 'person/add',
            name: 'PersonAdd',
            component: () => import('@/views/system/person/form.vue'),
            meta: { title: '新增人员', hidden: true },
          },
          {
            path: 'person/edit/:id',
            name: 'PersonEdit',
            component: () => import('@/views/system/person/form.vue'),
            meta: { title: '编辑人员', hidden: true },
          },
          {
            path: 'person/view/:id',
            name: 'PersonView',
            component: () => import('@/views/system/person/form.vue'),
            meta: { title: '查看人员', hidden: true },
          },
        ],
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
