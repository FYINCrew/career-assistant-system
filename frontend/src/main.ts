import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import PrimeVue from 'primevue/config'
import Aura from '@primeuix/themes/aura'
import { ConfirmationService, ToastService, useConfirm, useToast } from 'primevue'

const app = createApp(App)
app.use(router)
app.use(PrimeVue, {
  global: {
    components: { useConfirm, useToast },
    plugins: [PrimeVue, ConfirmationService, ToastService],
  },
  semantic: {
    colorScheme: {
      light: {
        primary: {
          color: '#ffffff',
          inverseColor: '#ffffff',
          hoverColor: '{zinc.900}',
          activeColor: '{zinc.800}',
        },
        highlight: {
          background: '{zinc.950}',
          focusBackground: '{zinc.700}',
          color: '#ffffff',
          focusColor: '#ffffff',
        },
      },
    },
  },
  theme: {
    preset: Aura,
    options: {
      darkModeSelector: false,
    },
    custom: {
      primary: '#4CAF50',
      secondary: '#FF9800',
      accent: '#FF5722',
      error: '#F44336',
      warning: '#FFC107',
      info: '#2196F3',
      success: '#4CAF50',
    },
  },
})
app.use(ConfirmationService)
app.use(ToastService)
app.mount('#app')
