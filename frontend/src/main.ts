import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import PrimeVue from 'primevue/config';
import Aura from '@primeuix/themes/aura';
import ToastPlugin from 'vue-toast-notification'




const app = createApp(App)
app.use(router)
app.use(ToastPlugin)
app.use(PrimeVue, {
    semantic:{
        colorScheme: {
            light: {
                primary: {
                    color: '#ffffff',
                    inverseColor: '#ffffff',
                    hoverColor: '{zinc.900}',
                    activeColor: '{zinc.800}'
                },
                highlight: {
                    background: '{zinc.950}',
                    focusBackground: '{zinc.700}',
                    color: '#ffffff',
                    focusColor: '#ffffff'
                }
            },
        }
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
            success: '#4CAF50'
        }
    }
})
app.mount('#app')
