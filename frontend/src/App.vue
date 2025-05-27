<script setup lang="ts">
import { User } from "@element-plus/icons-vue";
import { ElAvatar, ElConfigProvider, ElMenu, ElMenuItem } from "element-plus";
import "element-plus/dist/index.css";
import { computed } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const activeRoute = computed(() => route.path);
</script>

<template>
  <el-config-provider>
    <div class="app">
      <!-- 顶部导航栏 -->
      <header class="header">
        <!-- 导航菜单 -->
        <el-menu
          :default-active="activeRoute"
          class="main-menu"
          mode="horizontal"
          :router="true"
          background-color="transparent"
          text-color="#fff"
          active-text-color="#fff"
        >
          <el-menu-item index="/">高校融媒体中心技术系统</el-menu-item>
          <el-menu-item index="/school-forum">高校论坛</el-menu-item>
          <el-menu-item index="/interactive">师生互动</el-menu-item>
        </el-menu>

        <!-- 用户信息 -->
        <div class="user-info">
          <el-avatar :size="40" :icon="User" />
          <span class="user-text">个人中心</span>
        </div>
      </header>

      <!-- 主要内容区域 -->
      <main class="main-content">
        <router-view></router-view>
      </main>
    </div>
  </el-config-provider>
</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: "Microsoft YaHei", sans-serif;
  background-color: #f5f7fa;
}

.app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 顶部导航栏样式 */
.header {
  display: flex;
  align-items: center;
  background: linear-gradient(90deg, #1557c0 0%, #1674da 100%);
  color: white;
  padding: 0;
  height: 60px;
  position: relative;
}

/* Logo 区域 */
.logo-container {
  display: flex;
  align-items: center;
  padding: 0 20px;
  height: 100%;
  background-color: #1557c0;
  width: 300px;
}

.logo {
  width: 36px;
  height: 36px;
  margin-right: 10px;
}

.system-name {
  font-size: 18px;
  font-weight: bold;
  white-space: nowrap;
}

/* 覆盖Element Plus菜单样式 */
.main-menu {
  border-bottom: none !important;
  flex: 1;
  background-color: transparent !important;
}

.el-menu--horizontal .el-menu-item {
  height: 60px !important;
  line-height: 60px !important;
  border-bottom: none !important;
  color: white !important;
  font-size: 16px;
}

.el-menu--horizontal .el-menu-item.is-active {
  background-color: rgba(255, 255, 255, 0.1) !important;
  border-bottom: 3px solid white !important;
}

.el-menu--horizontal .el-menu-item:hover {
  background-color: rgba(255, 255, 255, 0.1) !important;
}

/* 用户信息区域 */
.user-info {
  display: flex;
  align-items: center;
  padding: 0 20px;
  height: 100%;
  cursor: pointer;
}

.user-text {
  margin-left: 10px;
  font-size: 14px;
}

/* 主要内容区域 */
.main-content {
  flex: 1;
  padding: 20px;
  background-color: #f5f7fa;
}
</style>
