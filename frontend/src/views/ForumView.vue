<template>
  <div class="forum-view">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span class="title">高校论坛</span>
          <el-button type="primary" @click="goToNewPost">发帖</el-button>
        </div>
      </template>

      <div class="forum-content">
        <el-empty v-if="posts.length === 0" description="暂无帖子"></el-empty>

        <div v-else class="post-list">
          <div v-for="post in posts" :key="post.id" class="post-item">
            <div class="post-header">
              <h3 class="post-title" @click="viewPostDetail(post)">
                {{ post.title }}
              </h3>
              <div class="post-meta">
                <span class="author">{{ post.author }}</span>
                <span class="time">{{ post.createTime }}</span>
              </div>
            </div>
            <div class="post-content-area">
              <div class="post-summary" @click="viewPostDetail(post)">
                {{ post.summary }}
              </div>
              <el-button
                v-if="post.author === '教师'"
                type="danger"
                size="small"
                class="delete-btn"
                @click.stop="deletePost(post.id)"
                >删除</el-button
              >
            </div>
          </div>
        </div>
      </div>
    </el-card>

    <!-- 帖子详情对话框 -->
    <el-dialog
      v-model="showPostDialog"
      :title="currentPost?.title"
      width="70%"
      destroy-on-close
    >
      <div class="post-detail">
        <div class="post-info">
          <span class="author">作者: {{ currentPost?.author }}</span>
          <span class="time">发布于: {{ currentPost?.createTime }}</span>
        </div>
        <div class="post-content" v-html="currentPost?.content"></div>

        <!-- 高校融媒体帖子的图片 -->
        <div v-if="currentPost?.hasImage" class="image-container">
          <img
            v-if="showMediaImage"
            src="/blue.png"
            alt="高校融媒体示意图"
            class="post-image"
          />
          <div v-else class="image-placeholder">图片加载中...</div>
        </div>

        <div class="comment-section">
          <h4>评论区 ({{ currentPost?.comments?.length || 0 }}条评论)</h4>

          <div class="comment-list">
            <div
              v-for="(comment, index) in currentPost?.comments"
              :key="index"
              class="comment-item"
            >
              <div class="comment-header">
                <div class="comment-user">{{ comment.user }}:</div>
                <el-button
                  v-if="comment.user === '教师'"
                  type="danger"
                  size="small"
                  @click="deleteComment(currentPost.id, index)"
                  >删除</el-button
                >
              </div>
              <div class="comment-content">{{ comment.content }}</div>
              <div class="comment-time">{{ comment.time }}</div>
            </div>
          </div>

          <div class="comment-form">
            <el-input
              v-model="newComment"
              type="textarea"
              :rows="3"
              placeholder="发表您的评论..."
            ></el-input>
            <div class="form-actions">
              <el-button type="primary" @click="submitComment"
                >提交评论</el-button
              >
            </div>
          </div>
        </div>
      </div>
    </el-dialog>

    <!-- 发帖对话框 -->
    <el-dialog
      v-model="showPostDialog"
      title="发布新帖子"
      width="70%"
      destroy-on-close
    >
      <div class="post-form">
        <el-form :model="newPost" label-width="80px">
          <el-form-item label="标题">
            <el-input
              v-model="newPost.title"
              placeholder="请输入标题"
            ></el-input>
          </el-form-item>
          <el-form-item label="内容">
            <el-input
              v-model="newPost.content"
              type="textarea"
              :rows="10"
              placeholder="请输入帖子内容"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="publishPost">发布帖子</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>

    <!-- 处理中的加载提示 -->
    <el-dialog
      v-model="loadingVisible"
      width="300px"
      :show-close="false"
      center
    >
      <div class="loading-dialog">
        <el-icon class="loading-icon"><Loading /></el-icon>
        <span>处理中...</span>
      </div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { Loading } from "@element-plus/icons-vue";
import {
  ElButton,
  ElCard,
  ElDialog,
  ElEmpty,
  ElForm,
  ElFormItem,
  ElInput,
  ElNotification,
} from "element-plus";
import { onMounted, ref } from "vue";
import { useForumStore } from "../stores/forum";
import { useUserStore } from "../stores/user";
import type { Post } from "../types";

const forumStore = useForumStore();
const userStore = useUserStore();

const posts = ref<Post[]>([]);
const showPostDialog = ref(false);
const showCommentDialog = ref(false);
const currentPost = ref<Post | null>(null);
const newPost = ref({
  title: "",
  content: "",
  hasImage: false,
});
const newComment = ref("");
const loadingVisible = ref(false);
const showMediaImage = ref(true);

onMounted(() => {
  forumStore.fetchPosts();
});

const viewPostDetail = async (post: Post) => {
  try {
    await forumStore.fetchPostDetail(post.id);
    currentPost.value = post;
    showPostDialog.value = true;
  } catch (error) {
    console.error("获取帖子详情失败:", error);
  }
};

const deletePost = async (id: number) => {
  try {
    await forumStore.deletePost(id);
    ElNotification.success({
      title: "成功",
      message: "帖子已删除",
    });
  } catch (error) {
    console.error("删除帖子失败:", error);
  }
};

const publishPost = async () => {
  try {
    await forumStore.publishPost({
      ...newPost.value,
      author: userStore.user?.username || "匿名用户",
    });
    showPostDialog.value = false;
    newPost.value = {
      title: "",
      content: "",
      hasImage: false,
    };
    ElNotification.success({
      title: "成功",
      message: "帖子发布成功",
    });
  } catch (error) {
    console.error("发布帖子失败:", error);
  }
};

const submitComment = async () => {
  if (!currentPost.value) return;
  try {
    await forumStore.submitComment(currentPost.value.id, {
      user: userStore.user?.username || "匿名用户",
      content: newComment.value,
      time: new Date().toISOString(),
    });
    newComment.value = "";
    ElNotification.success({
      title: "成功",
      message: "评论发布成功",
    });
  } catch (error) {
    console.error("提交评论失败:", error);
  }
};

const deleteComment = async (postId: number, commentIndex: number) => {
  try {
    await forumStore.deleteComment(postId, commentIndex);
    ElNotification.success({
      title: "成功",
      message: "评论已删除",
    });
  } catch (error) {
    console.error("删除评论失败:", error);
  }
};

const goToNewPost = () => {
  showPostDialog.value = true;
};
</script>

<style scoped>
.forum-view {
  padding: 20px;
}

.box-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  font-size: 18px;
  font-weight: bold;
}

.post-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.post-item {
  border-bottom: 1px solid #eee;
  padding-bottom: 20px;
}

.post-header {
  margin-bottom: 10px;
}

.post-title {
  font-size: 18px;
  color: #333;
  cursor: pointer;
  margin: 0;
}

.post-title:hover {
  color: #409eff;
}

.post-meta {
  font-size: 14px;
  color: #999;
  margin-top: 5px;
}

.author {
  margin-right: 15px;
}

.post-content-area {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.post-summary {
  flex: 1;
  color: #666;
  cursor: pointer;
}

.post-summary:hover {
  color: #409eff;
}

.delete-btn {
  margin-left: 10px;
}

.post-detail {
  padding: 20px;
}

.post-info {
  margin-bottom: 20px;
  color: #666;
}

.post-content {
  margin-bottom: 30px;
  line-height: 1.6;
}

.image-container {
  margin: 20px 0;
  text-align: center;
}

.post-image {
  max-width: 100%;
  height: auto;
}

.image-placeholder {
  padding: 40px;
  background-color: #f5f7fa;
  color: #909399;
  text-align: center;
}

.comment-section {
  margin-top: 30px;
  border-top: 1px solid #eee;
  padding-top: 20px;
}

.comment-list {
  margin: 20px 0;
}

.comment-item {
  margin-bottom: 15px;
  padding: 10px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 5px;
}

.comment-user {
  font-weight: bold;
  color: #333;
}

.comment-content {
  color: #666;
  margin: 5px 0;
}

.comment-time {
  font-size: 12px;
  color: #999;
}

.comment-form {
  margin-top: 20px;
}

.form-actions {
  margin-top: 10px;
  text-align: right;
}

.loading-dialog {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.loading-icon {
  font-size: 24px;
  margin-bottom: 10px;
  animation: rotating 2s linear infinite;
}

@keyframes rotating {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>
