<template>
  <div class="home">
    <h1>高校融媒体中心技术系统</h1>
    <div class="card-container">
      <div class="card" @click="$router.push('/data')">
        <h2>数据监控</h2>
        <p>实时监控高校融媒体数据分析</p>
      </div>
      <div class="card" @click="$router.push('/content')">
        <h2>内容生产</h2>
        <p>多媒体内容制作与发布管理</p>
      </div>
      <div class="card" @click="$router.push('/forum')">
        <h2>高校论坛</h2>
        <p>校园话题交流与互动平台</p>
      </div>
      <div class="card" @click="$router.push('/interaction')">
        <h2>师生互动</h2>
        <p>教师与学生的在线互动空间</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
// 首页逻辑
</script>

<style scoped>
.home {
  padding: 20px;
  text-align: center;
}

h1 {
  margin-bottom: 40px;
  color: #304156;
}

.card-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 20px;
}

.card {
  width: 250px;
  height: 200px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  padding: 20px;
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

h2 {
  margin-bottom: 10px;
  color: #409eff;
}

p {
  color: #606266;
  margin-top: 10px;
}
</style>
