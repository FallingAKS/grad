<template>
  <div class="interaction-view">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span class="title">师生互动平台</span>
          <el-button type="primary" @click="goToNewActivity"
            >创建活动</el-button
          >
        </div>
      </template>

      <div class="interaction-content">
        <el-empty
          v-if="activities.length === 0"
          description="暂无活动"
        ></el-empty>

        <div v-else class="activity-list">
          <div
            v-for="activity in activities"
            :key="activity.id"
            class="activity-item"
          >
            <div class="activity-header">
              <h3 class="activity-title" @click="viewActivityDetail(activity)">
                {{ activity.title }}
              </h3>
              <div class="activity-meta">
                <span class="type">{{ activity.type }}</span>
                <span class="time"
                  >{{ activity.startTime }} - {{ activity.endTime }}</span
                >
              </div>
            </div>
            <div class="activity-content-area">
              <div
                class="activity-description"
                @click="viewActivityDetail(activity)"
              >
                {{ activity.description }}
              </div>
              <el-button
                v-if="activity.author === '教师'"
                type="danger"
                size="small"
                class="delete-btn"
                @click.stop="deleteActivity(activity.id)"
                >删除</el-button
              >
            </div>
          </div>
        </div>
      </div>
    </el-card>

    <!-- 活动详情对话框 -->
    <el-dialog
      v-model="activityDetailVisible"
      :title="currentActivity?.title"
      width="70%"
      destroy-on-close
    >
      <div class="activity-detail">
        <div class="activity-info">
          <span class="type">类型: {{ currentActivity?.type }}</span>
          <span class="time"
            >时间: {{ currentActivity?.startTime }} -
            {{ currentActivity?.endTime }}</span
          >
        </div>
        <div
          class="activity-description"
          v-html="currentActivity?.description"
        ></div>

        <div v-if="currentActivity?.rules" class="rules-section">
          <h4>活动规则</h4>
          <div class="rules-content" v-html="currentActivity?.rules"></div>
        </div>

        <div v-if="currentActivity?.questions" class="questions-section">
          <h4>问题列表</h4>
          <div class="questions-list">
            <div
              v-for="(question, index) in currentActivity?.questions"
              :key="index"
              class="question-item"
            >
              <div class="question-text">{{ question.text }}</div>
              <div class="question-options">
                <div
                  v-for="(option, optIndex) in question.options"
                  :key="optIndex"
                  class="option"
                >
                  {{ option }}
                </div>
              </div>
            </div>
          </div>
        </div>

        <div v-if="currentActivity?.gameDescription" class="game-section">
          <h4>游戏说明</h4>
          <div
            class="game-content"
            v-html="currentActivity?.gameDescription"
          ></div>
        </div>
      </div>
    </el-dialog>

    <!-- 创建活动对话框 -->
    <el-dialog
      v-model="newActivityVisible"
      title="创建新活动"
      width="70%"
      destroy-on-close
    >
      <div class="activity-form">
        <el-form :model="newActivity" label-width="80px">
          <el-form-item label="标题">
            <el-input
              v-model="newActivity.title"
              placeholder="请输入活动标题"
            ></el-input>
          </el-form-item>
          <el-form-item label="类型">
            <el-select v-model="newActivity.type" placeholder="请选择活动类型">
              <el-option label="课程问答" value="课程问答"></el-option>
              <el-option label="在线辅导" value="在线辅导"></el-option>
              <el-option label="游戏互动" value="游戏互动"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="时间">
            <el-date-picker
              v-model="newActivity.timeRange"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="描述">
            <el-input
              v-model="newActivity.description"
              type="textarea"
              :rows="4"
              placeholder="请输入活动描述"
            ></el-input>
          </el-form-item>
          <el-form-item label="规则">
            <el-input
              v-model="newActivity.rules"
              type="textarea"
              :rows="4"
              placeholder="请输入活动规则"
            ></el-input>
          </el-form-item>
          <el-form-item v-if="newActivity.type === '课程问答'" label="问题">
            <div
              v-for="(question, index) in newActivity.questions"
              :key="index"
              class="question-form"
            >
              <el-input
                v-model="question.text"
                placeholder="请输入问题"
                class="question-input"
              ></el-input>
              <div class="options-input">
                <el-input
                  v-for="(option, optIndex) in question.options"
                  :key="optIndex"
                  v-model="question.options[optIndex]"
                  placeholder="请输入选项"
                ></el-input>
                <el-button type="primary" @click="addOption(index)"
                  >添加选项</el-button
                >
              </div>
              <el-button type="danger" @click="removeQuestion(index)"
                >删除问题</el-button
              >
            </div>
            <el-button type="primary" @click="addQuestion">添加问题</el-button>
          </el-form-item>
          <el-form-item v-if="newActivity.type === '游戏互动'" label="游戏说明">
            <el-input
              v-model="newActivity.gameDescription"
              type="textarea"
              :rows="4"
              placeholder="请输入游戏说明"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="createActivity"
              >创建活动</el-button
            >
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
  ElDatePicker,
  ElDialog,
  ElEmpty,
  ElForm,
  ElFormItem,
  ElInput,
  ElNotification,
  ElOption,
  ElSelect,
} from "element-plus";
import { onMounted, ref } from "vue";
import { useInteractionStore } from "../stores/interaction";
import { useUserStore } from "../stores/user";
import type { Activity } from "../types";

const interactionStore = useInteractionStore();
const userStore = useUserStore();

const showActivityDialog = ref(false);
const showCreateDialog = ref(false);
const currentActivity = ref<Activity | null>(null);
const newActivity = ref({
  title: "",
  type: "",
  startTime: "",
  endTime: "",
  description: "",
  rules: "",
  questions: [] as { text: string; options: string[] }[],
  gameDescription: "",
});

const loadingVisible = ref(false);

onMounted(() => {
  interactionStore.fetchActivities();
});

const viewActivityDetail = async (activity: Activity) => {
  try {
    await interactionStore.fetchActivityDetail(activity.id);
    currentActivity.value = activity;
    showActivityDialog.value = true;
  } catch (error) {
    console.error("获取活动详情失败:", error);
  }
};

const deleteActivity = async (id: number) => {
  try {
    await interactionStore.deleteActivity(id);
    ElNotification.success({
      title: "成功",
      message: "活动已删除",
    });
  } catch (error) {
    if (error !== "cancel") {
      ElNotification.error({
        title: "错误",
        message: "删除活动失败",
      });
    }
  }
};

const createActivity = async () => {
  if (
    !newActivity.value.title ||
    !newActivity.value.type ||
    !newActivity.value.timeRange ||
    !newActivity.value.description
  ) {
    ElNotification.warning({
      title: "提示",
      message: "请填写完整的活动信息",
    });
    return;
  }

  try {
    await interactionStore.createActivity({
      ...newActivity.value,
      author: userStore.user?.username || "匿名用户",
    });
    showCreateDialog.value = false;
    newActivity.value = {
      title: "",
      type: "",
      startTime: "",
      endTime: "",
      description: "",
      rules: "",
      questions: [],
      gameDescription: "",
    };
    ElNotification.success({
      title: "成功",
      message: "活动创建成功",
    });
  } catch (error) {
    console.error("创建活动失败:", error);
  }
};

// 添加问题
const addQuestion = () => {
  newActivity.value.questions.push({
    text: "",
    options: [""],
  });
};

// 删除问题
const removeQuestion = (index: number) => {
  newActivity.value.questions.splice(index, 1);
};

// 添加选项
const addOption = (questionIndex: number) => {
  newActivity.value.questions[questionIndex].options.push("");
};

const goToNewActivity = () => {
  showCreateDialog.value = true;
};
</script>

<style scoped>
.interaction-view {
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

.activity-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.activity-item {
  border-bottom: 1px solid #eee;
  padding-bottom: 20px;
}

.activity-header {
  margin-bottom: 10px;
}

.activity-title {
  font-size: 18px;
  color: #333;
  cursor: pointer;
  margin: 0;
}

.activity-title:hover {
  color: #409eff;
}

.activity-meta {
  font-size: 14px;
  color: #999;
  margin-top: 5px;
}

.type {
  margin-right: 15px;
}

.activity-content-area {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.activity-description {
  flex: 1;
  color: #666;
  cursor: pointer;
}

.activity-description:hover {
  color: #409eff;
}

.delete-btn {
  margin-left: 10px;
}

.activity-detail {
  padding: 20px;
}

.activity-info {
  margin-bottom: 20px;
  color: #666;
}

.rules-section,
.questions-section,
.game-section {
  margin-top: 30px;
  border-top: 1px solid #eee;
  padding-top: 20px;
}

.questions-list {
  margin: 20px 0;
}

.question-item {
  margin-bottom: 15px;
  padding: 10px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.question-text {
  font-weight: bold;
  margin-bottom: 10px;
}

.question-options {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.option {
  padding: 5px 10px;
  background-color: white;
  border-radius: 4px;
}

.question-form {
  margin-bottom: 20px;
  padding: 15px;
  border: 1px solid #eee;
  border-radius: 4px;
}

.question-input {
  margin-bottom: 10px;
}

.options-input {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 10px;
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
