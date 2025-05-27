import axios from "axios";
import { ElNotification } from "element-plus";
import { defineStore } from "pinia";
import type { ApiResponse, Post } from "../types";

export const useForumStore = defineStore("forum", {
  state: () => ({
    posts: [] as Post[],
    currentPost: {
      id: 0,
      title: "",
      author: "",
      createTime: "",
      summary: "",
      content: "",
      comments: [],
    } as Post,
    loading: false,
  }),

  actions: {
    // 获取帖子列表
    async fetchPosts() {
      try {
        this.loading = true;
        const response = await axios.get<ApiResponse<{ records: Post[] }>>(
          "/api/forum/posts"
        );
        this.posts = response.data.data.records;
      } catch (error) {
        ElNotification.error({
          title: "错误",
          message: "获取帖子列表失败",
        });
      } finally {
        this.loading = false;
      }
    },

    // 获取帖子详情
    async fetchPostDetail(id: number) {
      try {
        this.loading = true;
        const response = await axios.get<ApiResponse<Post>>(
          `/api/forum/posts/${id}`
        );
        this.currentPost = response.data.data;
        return response.data.data;
      } catch (error) {
        ElNotification.error({
          title: "错误",
          message: "获取帖子详情失败",
        });
        throw error;
      } finally {
        this.loading = false;
      }
    },

    // 发布新帖子
    async publishPost(post: { title: string; content: string }) {
      try {
        this.loading = true;
        const response = await axios.post<ApiResponse<Post>>(
          "/api/forum/posts",
          {
            ...post,
            author: "教师", // 这里应该从用户状态获取
            createTime: new Date().toISOString().split("T")[0],
          }
        );
        this.posts.unshift(response.data.data);
        return response.data.data;
      } catch (error) {
        ElNotification.error({
          title: "错误",
          message: "发布帖子失败",
        });
        throw error;
      } finally {
        this.loading = false;
      }
    },

    // 删除帖子
    async deletePost(id: number) {
      try {
        this.loading = true;
        await axios.delete<ApiResponse<boolean>>(`/api/forum/posts/${id}`);
        this.posts = this.posts.filter((post) => post.id !== id);
      } catch (error) {
        ElNotification.error({
          title: "错误",
          message: "删除帖子失败",
        });
        throw error;
      } finally {
        this.loading = false;
      }
    },

    // 提交评论
    async submitComment(postId: number, comment: { content: string }) {
      try {
        this.loading = true;
        const response = await axios.post(
          `/api/forum/posts/${postId}/comments`,
          {
            ...comment,
            user: "教师", // 这里应该从用户状态获取
            time: new Date().toISOString().split("T")[0],
          }
        );
        this.currentPost.comments.push(response.data);
        return response.data;
      } catch (error) {
        ElNotification.error({
          title: "错误",
          message: "发布评论失败",
        });
        throw error;
      } finally {
        this.loading = false;
      }
    },

    // 删除评论
    async deleteComment(postId: number, commentIndex: number) {
      try {
        this.loading = true;
        await axios.delete(
          `/api/forum/posts/${postId}/comments/${commentIndex}`
        );
        this.currentPost.comments.splice(commentIndex, 1);
      } catch (error) {
        ElNotification.error({
          title: "错误",
          message: "删除评论失败",
        });
        throw error;
      } finally {
        this.loading = false;
      }
    },
  },
});
