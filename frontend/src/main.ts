import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import PrimeVue from 'primevue/config';
import Material from '@primeuix/themes/material';

const app = createApp(App)
app.use(router)
app.use(PrimeVue, {
    theme: {
        preset: Material,
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
