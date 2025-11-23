import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import SettingsView from '../views/SettingsView.vue'
import TrackingListView from '../views/TrackingListView.vue'

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: LoginView },
  { path: '/settings', component: SettingsView },
  { path: '/trackings', component: TrackingListView }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
