<template>
  <div class="app-layout">
    <div class="app-sidebar">
      <div class="logo">
        <img src="/logo.png" alt="Logo" />
        <span>核电人才管理系统</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        class="sidebar-menu"
        background-color="#001529"
        text-color="#fff"
        active-text-color="#1890ff"
        :router="true"
      >
        <template v-for="route in menuRoutes" :key="route.path">
          <el-sub-menu v-if="route.children" :index="route.path">
            <template #title>
              <el-icon><component :is="route.meta?.icon" /></el-icon>
              <span>{{ route.meta?.title }}</span>
            </template>
            <el-menu-item
              v-for="child in route.children"
              :key="child.path"
              :index="`/${route.path}/${child.path}`"
            >
              {{ child.meta?.title }}
            </el-menu-item>
          </el-sub-menu>
          <el-menu-item v-else :index="`/${route.path}`">
            <el-icon><component :is="route.meta?.icon" /></el-icon>
            <span>{{ route.meta?.title }}</span>
          </el-menu-item>
        </template>
      </el-menu>
    </div>
    <div class="app-main">
      <router-view />
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

// 当前激活菜单
const activeMenu = computed(() => {
  const { path } = route
  return path
})

// 菜单路由
const menuRoutes = [
  {
    path: 'talent',
    meta: { title: '人才管理看板', icon: 'User' },
    children: [
      {
        path: 'person-portrait',
        meta: { title: '人才画像' },
      },
      {
        path: 'team-portrait',
        meta: { title: '队伍元画像' },
      },
    ],
  },
  {
    path: 'system',
    meta: { title: '系统管理', icon: 'Setting' },
    children: [
      {
        path: 'dict',
        meta: { title: '码表管理' },
      },
      {
        path: 'person',
        meta: { title: '人员信息管理' },
      },
    ],
  },
]
</script>

<style lang="scss" scoped>
.app-layout {
  display: flex;
  height: 100vh;

  .app-sidebar {
    width: 240px;
    background-color: #001529;
    box-shadow: 2px 0 8px rgba(0, 0, 0, 0.15);

    .logo {
      height: 64px;
      display: flex;
      align-items: center;
      justify-content: center;
      border-bottom: 1px solid rgba(255, 255, 255, 0.1);

      img {
        width: 32px;
        height: 32px;
        margin-right: 10px;
      }

      span {
        color: #fff;
        font-size: 16px;
        font-weight: 500;
      }
    }

    .sidebar-menu {
      border-right: none;

      :deep(.el-menu-item) {
        &:hover {
          background-color: rgba(255, 255, 255, 0.1);
        }
      }
    }
  }

  .app-main {
    flex: 1;
    background-color: #f0f2f5;
    overflow: auto;
  }
}
</style>
