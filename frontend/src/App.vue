<script setup lang="ts">
// App逻辑
</script>

<template>
  <div class="app">
    <header class="app-header">
      <div class="logo">高校融媒体中心技术系统</div>
      <nav>
        <router-link to="/">首页</router-link>
        <router-link to="/data">数据监控</router-link>
        <router-link to="/content">内容生产</router-link>
        <router-link to="/forum">高校论坛</router-link>
        <router-link to="/interaction">师生互动</router-link>
      </nav>
      <div class="user-menu">
        <span class="username">用户名</span>
        <router-link to="/login" class="logout">退出</router-link>
      </div>
    </header>

    <main>
      <router-view />
    </main>

    <footer class="app-footer">
      <p>© 2025 高校融媒体中心技术系统 | 版权所有</p>
    </footer>
  </div>
</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html,
body {
  font-family: "Microsoft YaHei", Arial, sans-serif;
  color: #333;
  background-color: #f5f7fa;
  height: 100%;
}

.app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.app-header {
  background-color: #304156;
  color: white;
  padding: 0 20px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo {
  font-size: 20px;
  font-weight: bold;
}

nav {
  display: flex;
}

nav a {
  color: #cfd4dc;
  text-decoration: none;
  padding: 0 15px;
  height: 60px;
  display: flex;
  align-items: center;
  transition: all 0.3s;
}

nav a:hover,
nav a.router-link-active {
  color: white;
  background-color: #263445;
}

.user-menu {
  display: flex;
  align-items: center;
}

.username {
  margin-right: 15px;
}

.logout {
  color: #cfd4dc;
  text-decoration: none;
}

.logout:hover {
  color: white;
}

main {
  flex: 1;
}

.app-footer {
  background-color: #304156;
  color: #cfd4dc;
  text-align: center;
  padding: 15px;
  font-size: 14px;
}
</style>
