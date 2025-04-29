import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "initialComponent",
      component: () =>
        import("@/components/InitialComponent.vue"),
    },
  ],
})

export default router
