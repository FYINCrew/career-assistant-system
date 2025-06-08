import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "initialComponent",
      component: () =>
        import("@/components/funcionarios/ListagemFuncionarios.vue"),
    },
    {
      path: "/detalhar-funcionarios/:id",
      name: "detalharFuncionarios",
      component: () =>
        import("@/components/funcionarios/DetalharFuncionario.vue"),
    },
  ],
})

export default router
