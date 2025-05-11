<template>
  <div class="data-view">
    <h1>数据监控中心</h1>
    <div class="dashboard">
      <div class="stat-card">
        <h3>今日访问量</h3>
        <div class="stat-value">1,245</div>
        <div class="stat-change up">+12.5%</div>
      </div>
      <div class="stat-card">
        <h3>内容发布量</h3>
        <div class="stat-value">378</div>
        <div class="stat-change up">+5.2%</div>
      </div>
      <div class="stat-card">
        <h3>用户互动量</h3>
        <div class="stat-value">896</div>
        <div class="stat-change down">-2.3%</div>
      </div>
      <div class="stat-card">
        <h3>新增用户</h3>
        <div class="stat-value">64</div>
        <div class="stat-change up">+8.7%</div>
      </div>
    </div>

    <div class="charts-container">
      <div class="chart">
        <h3>访问量趋势</h3>
        <div class="chart-placeholder">图表区域 - 访问量趋势</div>
      </div>
      <div class="chart">
        <h3>内容分类分布</h3>
        <div class="chart-placeholder">图表区域 - 内容分类分布</div>
      </div>
    </div>

    <div class="data-table">
      <h3>最新数据列表</h3>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>类型</th>
            <th>标题</th>
            <th>发布时间</th>
            <th>阅读量</th>
            <th>点赞量</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="i in 5" :key="i">
            <td>{{ 10000 + i }}</td>
            <td>{{ ["新闻", "公告", "活动", "教学", "研究"][i % 5] }}</td>
            <td>{{ `示例内容标题 ${i}` }}</td>
            <td>{{ `2025-05-${10 + i}` }}</td>
            <td>{{ 100 + i * 12 }}</td>
            <td>{{ 50 + i * 5 }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
// 数据监控相关逻辑
</script>

<style scoped>
.data-view {
  padding: 20px;
}

h1 {
  margin-bottom: 30px;
  color: #304156;
}

.dashboard {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  margin: 10px 0;
}

.stat-change {
  font-size: 14px;
}

.stat-change.up {
  color: #67c23a;
}

.stat-change.down {
  color: #f56c6c;
}

.charts-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.chart {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.chart-placeholder {
  height: 300px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f5f7fa;
  border-radius: 4px;
  color: #909399;
}

.data-table {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

th,
td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #ebeef5;
}

th {
  font-weight: bold;
  color: #606266;
  background-color: #f5f7fa;
}

tbody tr:hover {
  background-color: #f5f7fa;
}
</style>
