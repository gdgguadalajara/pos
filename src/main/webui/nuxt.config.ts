import tailwindcss from "@tailwindcss/vite";

// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2025-07-15',
  ssr: false,
  devtools: { enabled: true },
  app: {
    head: {
      title: 'Point of Sale',
      link: [
        { rel: 'icon', type: 'image/svg', href: '/favicon.svg' }
      ]
    }
  },
  modules: [
    '@nuxt/icon',
    'nuxt-toast'
  ],
  css: ['./app/assets/css/main.css'],
  vite: {
    plugins: [
      tailwindcss(),
    ],
  },
  toast: {
    settings: {
      timeout: 1500,
    }
  }
})