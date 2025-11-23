<template>
  <section class="card">
    <h2>登录</h2>
    <form @submit.prevent="handleLogin">
      <label>
        用户名
        <input v-model="form.username" placeholder="admin" required />
      </label>
      <label>
        密码
        <input type="password" v-model="form.password" placeholder="••••••" required />
      </label>
      <button type="submit" :disabled="loading">{{ loading ? '登录中...' : '登录' }}</button>
      <p class="hint">默认凭据在后端 application.yml 中配置。</p>
      <p v-if="error" class="error">{{ error }}</p>
    </form>
  </section>
</template>

<script setup>
import { reactive, ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()
const loading = ref(false)
const error = ref('')
const form = reactive({ username: '', password: '' })

const handleLogin = async () => {
  loading.value = true
  error.value = ''
  try {
    const { data } = await axios.post('/api/auth/login', form)
    localStorage.setItem('token', data.token)
    router.push('/settings')
  } catch (err) {
    error.value = err.response?.data || err.message
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.card {
  max-width: 400px;
  margin: 40px auto;
  background: white;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
}
form {
  display: flex;
  flex-direction: column;
  gap: 12px;
}
input {
  width: 100%;
  padding: 10px 12px;
  border-radius: 8px;
  border: 1px solid #cbd5e1;
}
button {
  padding: 10px 12px;
  border: none;
  background: #2563eb;
  color: #fff;
  border-radius: 8px;
  cursor: pointer;
}
button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}
.error {
  color: #dc2626;
}
.hint {
  color: #64748b;
  font-size: 13px;
}
</style>
