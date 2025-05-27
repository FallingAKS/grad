import axios from "axios";
import { ElNotification } from "element-plus";
import { defineStore } from "pinia";
import router from "../router";

interface User {
  id: number;
  username: string;
  role: string;
  avatar?: string;
}

interface LoginForm {
  username: string;
  password: string;
}

export const useUserStore = defineStore("user", {
  state: () => ({
    user: null as User | null,
    token: localStorage.getItem("token") || "",
    loading: false,
  }),

  getters: {
    isLoggedIn: (state) => !!state.token,
    isTeacher: (state) => state.user?.role === "teacher",
    isStudent: (state) => state.user?.role === "student",
  },

  actions: {
    // 登录
    async login(form: LoginForm) {
      try {
        this.loading = true;
        const response = await axios.post("/api/auth/login", form);
        const { token, user } = response.data.data;
        this.token = token;
        this.user = user;
        localStorage.setItem("token", token);
        ElNotification.success({
          title: "成功",
          message: "登录成功",
        });
        router.push("/");
      } catch (error) {
        ElNotification.error({
          title: "错误",
          message: "登录失败",
        });
        throw error;
      } finally {
        this.loading = false;
      }
    },

    // 登出
    logout() {
      this.token = "";
      this.user = null;
      localStorage.removeItem("token");
      router.push("/login");
      ElNotification.success({
        title: "成功",
        message: "已退出登录",
      });
    },

    // 获取用户信息
    async fetchUserInfo() {
      try {
        this.loading = true;
        const response = await axios.get("/api/auth/user");
        this.user = response.data.data;
      } catch (error) {
        ElNotification.error({
          title: "错误",
          message: "获取用户信息失败",
        });
        throw error;
      } finally {
        this.loading = false;
      }
    },
  },
});
