import { defineConfig } from 'vite'
import Vue from '@vitejs/plugin-vue'
import PrimeUI from 'tailwindcss-primeui';

import { fileURLToPath, URL } from 'node:url'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    Vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  }
})
