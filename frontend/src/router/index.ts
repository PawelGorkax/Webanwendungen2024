import { createRouter, createWebHistory } from 'vue-router';
import TourenListeView from '@/views/TourenListeView.vue';
import TourView from '@/views/TourView.vue';

const routes = [
  { path: '/', 
  redirect: '/touren' 
  },
  { path: '/touren', component: TourenListeView },
  { path: '/tour/:tourid', component: TourView, props: true }
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
