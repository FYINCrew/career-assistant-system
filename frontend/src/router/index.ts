import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'listarFuncionarios',
      component: () => import('@/components/funcionarios/ListagemFuncionarios.vue'),
    },
    {
      path: '/detalhar-funcionarios/:id',
      name: 'detalharFuncionarios',
      component: () => import('@/components/funcionarios/DetalharFuncionario.vue'),
    },
    {
      path: '/listar-cargos',
      name: 'listarCargos',
      component: () => import('@/components/cargos/ListagemCargos.vue'),
    },
    {
      path: '/cadastrar-cargo',
      name: 'cadastrarCargo',
      component: () => import('@/components/cargos/CadastrarCargo.vue'),
    },
    {
      path: '/detalhar-cargo/:id',
      name: 'detalharCargo',
      component: () => import('@/components/cargos/DetalharCargo.vue'),
    },
  ],
})

export default router
