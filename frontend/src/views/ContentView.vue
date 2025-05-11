<template>
  <div class="content-view">
    <h1>内容生产平台</h1>

    <div class="toolbar">
      <button class="primary-btn">新建内容</button>
      <div class="search-box">
        <input type="text" placeholder="搜索内容..." />
        <button>搜索</button>
      </div>
      <div class="filter">
        <select>
          <option value="">全部类型</option>
          <option value="news">新闻</option>
          <option value="announcement">公告</option>
          <option value="event">活动</option>
          <option value="teaching">教学</option>
          <option value="research">研究</option>
        </select>
      </div>
    </div>

    <div class="content-list">
      <div class="content-item" v-for="i in 6" :key="i">
        <div class="content-header">
          <h3>{{ `示例内容标题 ${i}` }}</h3>
          <div class="content-tags">
            <span class="tag">{{
              ["新闻", "公告", "活动", "教学", "研究"][i % 5]
            }}</span>
            <span class="tag status">{{
              ["已发布", "草稿", "审核中"][i % 3]
            }}</span>
          </div>
        </div>
        <div class="content-body">
          <p>
            这是一段示例内容描述，展示了内容的摘要信息。实际项目中这里将显示真实的内容摘要，帮助用户快速了解内容的主要信息。
          </p>
        </div>
        <div class="content-footer">
          <div class="content-info">
            <span>作者: 用户{{ 1000 + i }}</span>
            <span>创建时间: {{ `2025-05-${10 + i}` }}</span>
            <span>阅读量: {{ 100 + i * 12 }}</span>
          </div>
          <div class="content-actions">
            <button>编辑</button>
            <button>预览</button>
            <button>删除</button>
          </div>
        </div>
      </div>
    </div>

    <div class="pagination">
      <button>&lt;</button>
      <button class="active">1</button>
      <button>2</button>
      <button>3</button>
      <button>&gt;</button>
    </div>
  </div>
</template>

<script setup lang="ts">
// 内容生产相关逻辑
</script>

<style scoped>
.content-view {
  padding: 20px;
}

h1 {
  margin-bottom: 30px;
  color: #304156;
}

.toolbar {
  display: flex;
  gap: 15px;
  margin-bottom: 20px;
  padding: 15px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.primary-btn {
  background-color: #409eff;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
}

.search-box {
  display: flex;
  flex: 1;
}

.search-box input {
  flex: 1;
  padding: 10px;
  border: 1px solid #dcdfe6;
  border-right: none;
  border-radius: 4px 0 0 4px;
}

.search-box button {
  padding: 10px 20px;
  background-color: #606266;
  color: white;
  border: none;
  border-radius: 0 4px 4px 0;
  cursor: pointer;
}

.filter select {
  padding: 10px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}

.content-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.content-item {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.content-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 10px;
}

.content-tags {
  display: flex;
  gap: 8px;
}

.tag {
  padding: 4px 8px;
  background-color: #ecf5ff;
  color: #409eff;
  font-size: 12px;
  border-radius: 4px;
}

.tag.status {
  background-color: #f0f9eb;
  color: #67c23a;
}

.content-body {
  margin-bottom: 15px;
  color: #606266;
}

.content-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-top: 1px solid #ebeef5;
  padding-top: 15px;
}

.content-info {
  font-size: 12px;
  color: #909399;
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.content-actions {
  display: flex;
  gap: 8px;
}

.content-actions button {
  padding: 6px 12px;
  background-color: #f4f4f5;
  border: none;
  border-radius: 4px;
  color: #606266;
  cursor: pointer;
}

.pagination {
  display: flex;
  justify-content: center;
  gap: 8px;
}

.pagination button {
  width: 32px;
  height: 32px;
  border: 1px solid #e4e7ed;
  background-color: white;
  border-radius: 4px;
  cursor: pointer;
}

.pagination button.active {
  background-color: #409eff;
  color: white;
  border-color: #409eff;
}
</style>
