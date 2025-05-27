import axios from "axios";
import { ElNotification } from "element-plus";
import { defineStore } from "pinia";
import type { Activity, ApiResponse } from "../types";

export const useInteractionStore = defineStore("interaction", {
  state: () => ({
    activities: [] as Activity[],
    currentActivity: {
      id: 0,
      title: "",
      type: "",
      startTime: "",
      endTime: "",
      description: "",
      rules: "",
      participants: 0,
    } as Activity,
    loading: false,
  }),

  actions: {
    // 获取活动列表
    async fetchActivities() {
      try {
        this.loading = true;
        const response = await axios.get<ApiResponse<Activity[]>>(
          "/api/interaction/activities"
        );
        this.activities = response.data.data;
      } catch (error) {
        ElNotification.error({
          title: "错误",
          message: "获取活动列表失败",
        });
      } finally {
        this.loading = false;
      }
    },

    // 获取活动详情
    async fetchActivityDetail(id: number) {
      try {
        this.loading = true;
        const response = await axios.get<ApiResponse<Activity>>(
          `/api/interaction/activities/${id}`
        );
        this.currentActivity = response.data.data;
        return response.data.data;
      } catch (error) {
        ElNotification.error({
          title: "错误",
          message: "获取活动详情失败",
        });
        throw error;
      } finally {
        this.loading = false;
      }
    },

    // 创建新活动
    async createActivity(activity: {
      title: string;
      type: string;
      startTime: string;
      endTime: string;
      description: string;
      rules?: string;
      questions?: { text: string; options: string[] }[];
      gameDescription?: string;
    }) {
      try {
        this.loading = true;
        const response = await axios.post<ApiResponse<Activity>>(
          "/api/interaction/activities",
          {
            ...activity,
            author: "教师", // 这里应该从用户状态获取
          }
        );
        this.activities.unshift(response.data.data);
        return response.data.data;
      } catch (error) {
        ElNotification.error({
          title: "错误",
          message: "创建活动失败",
        });
        throw error;
      } finally {
        this.loading = false;
      }
    },

    // 删除活动
    async deleteActivity(id: number) {
      try {
        this.loading = true;
        await axios.delete<ApiResponse<boolean>>(
          `/api/interaction/activities/${id}`
        );
        this.activities = this.activities.filter(
          (activity) => activity.id !== id
        );
      } catch (error) {
        ElNotification.error({
          title: "错误",
          message: "删除活动失败",
        });
        throw error;
      } finally {
        this.loading = false;
      }
    },
  },
});
