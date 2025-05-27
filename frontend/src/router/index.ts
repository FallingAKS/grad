import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    name: "Home",
    component: () => import("../views/HomeView.vue"),
  },
  {
    path: "/school-forum",
    name: "SchoolForum",
    component: () => import("../views/ForumView.vue"),
  },
  {
    path: "/interactive",
    name: "Interactive",
    component: () => import("../views/InteractionView.vue"),
  },
  {
    path: "/data",
    name: "Data",
    component: () => import("../views/DataView.vue"),
  },
  {
    path: "/content",
    name: "Content",
    component: () => import("../views/ContentView.vue"),
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("../views/LoginView.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
