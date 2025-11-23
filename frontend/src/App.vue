<template>
  <div class="app-shell">
    <header class="topbar">
      <h1>17TRACK 物流监控控制台</h1>
      <nav>
        <RouterLink to="/settings">平台配置</RouterLink>
        <RouterLink to="/trackings">运单列表</RouterLink>
      </nav>
    </header>
    <main>
      <RouterView />
    </main>
  </div>
</template>

<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<style scoped>
.app-shell {
  font-family: 'Inter', system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  color: #1f2937;
  background: #f8fafc;
  min-height: 100vh;
}
.topbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 24px;
  background: #0f172a;
  color: #e2e8f0;
}
.topbar nav a {
  margin-left: 16px;
  color: #e2e8f0;
  text-decoration: none;
}
.topbar nav a.router-link-active {
  font-weight: 600;
  text-decoration: underline;
}
main {
  padding: 24px;
}
</style>
