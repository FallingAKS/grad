<template>
  <div class="interaction-view">
    <h1>师生互动平台</h1>

    <div class="interaction-layout">
      <div class="interaction-sidebar">
        <div class="user-info">
          <div class="avatar">
            <img src="https://via.placeholder.com/60" alt="用户头像" />
          </div>
          <h3>用户名</h3>
          <p>用户角色: 学生</p>
        </div>

        <div class="menu">
          <h3>互动功能</h3>
          <ul>
            <li class="active">课程问答</li>
            <li>作业提交</li>
            <li>在线辅导</li>
            <li>资源共享</li>
            <li>通知公告</li>
          </ul>
        </div>

        <div class="recent-contacts">
          <h3>最近联系</h3>
          <div class="contact-list">
            <div class="contact-item" v-for="i in 5" :key="i">
              <div class="contact-avatar">
                <img src="https://via.placeholder.com/40" alt="联系人头像" />
              </div>
              <div class="contact-info">
                <div class="contact-name">{{ `联系人 ${i}` }}</div>
                <div class="contact-role">
                  {{ i % 2 === 0 ? "教师" : "学生" }}
                </div>
              </div>
              <div class="contact-status online"></div>
            </div>
          </div>
        </div>
      </div>

      <div class="interaction-content">
        <div class="course-qa">
          <h2>课程问答</h2>

          <div class="qa-filter">
            <div class="search-box">
              <input type="text" placeholder="搜索问题..." />
              <button>搜索</button>
            </div>
            <div class="filter-options">
              <select>
                <option value="">全部课程</option>
                <option value="math">高等数学</option>
                <option value="physics">大学物理</option>
                <option value="programming">程序设计</option>
                <option value="english">大学英语</option>
              </select>
              <select>
                <option value="">全部状态</option>
                <option value="unanswered">未回答</option>
                <option value="answered">已回答</option>
                <option value="solved">已解决</option>
              </select>
            </div>
          </div>

          <button class="ask-btn">提问</button>

          <div class="qa-list">
            <div class="qa-item" v-for="i in 6" :key="i">
              <div class="qa-header">
                <h3>{{ `问题示例标题 ${i}` }}</h3>
                <div class="qa-tags">
                  <span class="tag">{{
                    ["高等数学", "大学物理", "程序设计", "大学英语"][i % 4]
                  }}</span>
                  <span class="tag status">{{
                    ["未回答", "已回答", "已解决"][i % 3]
                  }}</span>
                </div>
              </div>
              <div class="qa-body">
                <p>
                  这是一个问题的描述，介绍了学生在学习过程中遇到的问题和疑惑。实际项目中将显示真实的问题内容。
                </p>
              </div>
              <div class="qa-footer">
                <div class="qa-info">
                  <span>提问者: 用户{{ 1000 + i }}</span>
                  <span>提问时间: {{ `2025-05-${10 + i}` }}</span>
                </div>
                <div class="qa-stats">
                  <span><i class="icon">👁️</i> {{ 30 + i * 5 }}</span>
                  <span><i class="icon">💬</i> {{ i % 3 === 0 ? 0 : i }}</span>
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
  </div>
</template>

<script setup lang="ts">
// 师生互动相关逻辑
</script>

<style scoped>
.interaction-view {
  padding: 20px;
}

h1 {
  margin-bottom: 30px;
  color: #304156;
}

.interaction-layout {
  display: flex;
  gap: 20px;
}

.interaction-sidebar {
  width: 280px;
  flex-shrink: 0;
}

.user-info,
.menu,
.recent-contacts {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.user-info {
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

.menu ul {
  list-style: none;
  padding: 0;
  margin: 10px 0 0;
}

.menu li {
  padding: 12px;
  border-radius: 4px;
  cursor: pointer;
  margin-bottom: 5px;
}

.menu li:hover {
  background-color: #f5f7fa;
}

.menu li.active {
  background-color: #ecf5ff;
  color: #409eff;
}

.contact-list {
  margin-top: 10px;
}

.contact-item {
  display: flex;
  align-items: center;
  padding: 10px;
  border-radius: 4px;
  margin-bottom: 5px;
  cursor: pointer;
}

.contact-item:hover {
  background-color: #f5f7fa;
}

.contact-avatar {
  margin-right: 10px;
}

.contact-avatar img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.contact-info {
  flex: 1;
}

.contact-role {
  font-size: 12px;
  color: #909399;
}

.contact-status {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin-left: 10px;
}

.contact-status.online {
  background-color: #67c23a;
}

.interaction-content {
  flex: 1;
}

.course-qa {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

h2 {
  margin-bottom: 20px;
  color: #409eff;
}

.qa-filter {
  display: flex;
  margin-bottom: 20px;
  gap: 15px;
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

.filter-options {
  display: flex;
  gap: 10px;
}

.filter-options select {
  padding: 10px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}

.ask-btn {
  background-color: #409eff;
  color: white;
  border: none;
  padding: 12px 20px;
  border-radius: 4px;
  margin-bottom: 20px;
  cursor: pointer;
}

.qa-list {
  margin-bottom: 20px;
}

.qa-item {
  border: 1px solid #ebeef5;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 15px;
}

.qa-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 10px;
}

.qa-tags {
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

.qa-body {
  margin-bottom: 15px;
  color: #606266;
}

.qa-footer {
  display: flex;
  justify-content: space-between;
  border-top: 1px solid #ebeef5;
  padding-top: 15px;
}

.qa-info {
  font-size: 12px;
  color: #909399;
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.qa-stats {
  display: flex;
  gap: 15px;
  color: #909399;
}

.icon {
  margin-right: 5px;
}

.pagination {
  display: flex;
  justify-content: center;
  gap: 8px;
  margin-top: 20px;
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
