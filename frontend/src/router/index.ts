import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: () => import("../views/HomeView.vue"),
    },
    {
      path: "/login",
      name: "login",
      component: () => import("../views/LoginView.vue"),
    },
    {
      path: "/data",
      name: "data",
      component: () => import("../views/DataView.vue"),
    },
    {
      path: "/content",
      name: "content",
      component: () => import("../views/ContentView.vue"),
    },
    {
      path: "/forum",
      name: "forum",
      component: () => import("../views/ForumView.vue"),
    },
    {
      path: "/interaction",
      name: "interaction",
      component: () => import("../views/InteractionView.vue"),
    },
  ],
});

export default router;
