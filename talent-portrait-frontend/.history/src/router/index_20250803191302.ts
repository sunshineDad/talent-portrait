import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import Layout from '@/layouts/index.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: Layout,
    redirect: '/test',
    children: [
      {
        path: 'test',
        name: 'Test',
        component: () => import('@/views/test.vue'),
        meta: { title: '测试页面' },
      },
      {
        path: 'talent/person-portrait',
        name: 'PersonPortrait',
        component: () => import('@/views/talent/person-portrait/index.vue'),
        meta: { title: '人才画像' },
      },
      {
        path: 'talent/team-portrait',
        name: 'TeamPortrait',
        component: () => import('@/views/talent/team-portrait/index.vue'),
        meta: { title: '队伍元画像' },
      },
      {
        path: 'system/dict',
        name: 'Dict',
        component: () => import('@/views/system/dict/index.vue'),
        meta: { title: '码表管理' },
      },
      {
        path: 'system/person',
        name: 'Person',
        component: () => import('@/views/system/person/index.vue'),
        meta: { title: '人员信息管理' },
      },
      {
        path: 'system/person/add',
        name: 'PersonAdd',
        component: () => import('@/views/system/person/form.vue'),
        meta: { title: '新增人员', hidden: true },
      },
      {
        path: 'system/person/edit/:id',
        name: 'PersonEdit',
        component: () => import('@/views/system/person/form.vue'),
        meta: { title: '编辑人员', hidden: true },
      },
      {
        path: 'system/person/view/:id',
        name: 'PersonView',
        component: () => import('@/views/system/person/form.vue'),
        meta: { title: '查看人员', hidden: true },
      },
      {
        path: 'system/team',
        name: 'Team',
        component: () => import('@/views/system/team/index.vue'),
        meta: { title: '团队管理' },
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
