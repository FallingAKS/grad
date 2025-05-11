<template>
  <div class="forum-view">
    <h1>高校论坛</h1>
    
    <div class="forum-container">
      <div class="forum-sidebar">
        <div class="forum-user">
          <div class="avatar">
            <img src="https://via.placeholder.com/60" alt="用户头像" />
          </div>
          <h3>用户名</h3>
          <p>用户角色: 学生</p>
        </div>
        
        <div class="forum-categories">
          <h3>讨论区分类</h3>
          <ul>
            <li class="active">全部话题</li>
            <li>校园生活</li>
            <li>学术交流</li>
            <li>活动公告</li>
            <li>就业创业</li>
            <li>资源共享</li>
          </ul>
        </div>
        
        <div class="forum-tags">
          <h3>热门标签</h3>
          <div class="tags-container">
            <span class="tag">期末考试</span>
            <span class="tag">校园活动</span>
            <span class="tag">学生会</span>
            <span class="tag">社团招新</span>
            <span class="tag">实习</span>
            <span class="tag">考研</span>
            <span class="tag">竞赛</span>
          </div>
        </div>
      </div>
      
      <div class="forum-content">
        <div class="forum-toolbar">
          <button class="primary-btn">发布新话题</button>
          <div class="search-box">
            <input type="text" placeholder="搜索话题..." />
            <button>搜索</button>
          </div>
        </div>
        
        <div class="forum-topics">
          <div class="topic-item" v-for="i in 8" :key="i">
            <div class="topic-avatar">
              <img src="https://via.placeholder.com/40" alt="用户头像" />
            </div>
            <div class="topic-content">
              <h3>{{ `话题示例标题 ${i}` }}</h3>
              <div class="topic-info">
                <span>作者: 用户{{ 1000 + i }}</span>
                <span>发布于: {{ `2025-05-${10 + i}` }}</span>
                <span>{{ ['校园生活', '学术交流', '活动公告', '就业创业', '资源共享'][i % 5] }}</span>
              </div>
              <p class="topic-summary">这是一段话题的简要描述，介绍了话题的主要内容和讨论方向。实际项目中将显示真实的话题内容摘要。</p>
              <div class="topic-stats">
                <span><i class="icon">👁️</i> {{ 100 + i * 15 }}</span>
                <span><i class="icon">💬</i> {{ 5 + i * 2 }}</span>
                <span><i class="icon">👍</i> {{ 10 + i * 3 }}</span>
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
    </div>
  </div>
</template>

<script setup lang="ts">
// 高校论坛相关逻辑
</script>

<style scoped>
.forum-view {
  padding: 20px;
}

h1 {
  margin-bottom: 30px;
  color: #304156;
}

.forum-container {
  display: flex;
  gap: 20px;
}

.forum-sidebar {
  width: 250px;
  flex-shrink: 0;
}

.forum-user,
.forum-categories,
.forum-tags {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.forum-user {
  text-align: center;
}

.avatar {
  margin-bottom: 10px;
}

.avatar img {
  width: 60px;
  height: 60px;
  border-radius: 50%;
}

.forum-categories ul {
  list-style: none;
  padding: 0;
  margin: 10px 0 0;
}

.forum-categories li {
  padding: 10px;
  border-radius: 4px;
  cursor: pointer;
  margin-bottom: 5px;
}

.forum-categories li:hover {
  background-color: #f5f7fa;
}

.forum-categories li.active {
  background-color: #ecf5ff;
  color: #409EFF;
}

.tags-container {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 10px;
}

.tag {
  padding: 6px 10px;
  background-color: #f5f7fa;
  color: #606266;
  border-radius: 4px;
  font-size: 12px;
  cursor: pointer;
}

.forum-content {
  flex: 1;
}

.forum-toolbar {
  display: flex;
  gap: 15px;
  margin-bottom: 20px;
  padding: 15px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.primary-btn {
  background-color: #409EFF;
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

.forum-topics {
  margin-bottom: 20px;
}

.topic-item {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  margin-bottom: 15px;
  display: flex;
  gap: 15px;
}

.topic-avatar img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.topic-content {
  flex: 1;
}

.topic-info {
  font-size: 12px;
  color: #909399;
  margin-bottom: 8px;
  display: flex;
  gap: 15px;
}

.topic-summary {
  color: #606266;
  margin-bottom: 10px;
}

.topic-stats {
  display: flex;
  gap: 20px;
  color: #909399;
  font-size: 14px;
}

.icon {
  margin-right: 5px;
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
  background-color: #409EFF;
  color: white;
  border-color: #409EFF;
}
</style>
</rewritten_file>