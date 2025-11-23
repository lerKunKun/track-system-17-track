<template>
  <section class="card">
    <h2>平台/17TRACK 配置</h2>
    <p class="helper">维护店铺 API Key、Secret 与 Access Token，保存后调用后台接口写入配置。</p>
    <form @submit.prevent="handleSave">
      <label>
        平台
        <select v-model="form.platform" required>
          <option value="shopify">Shopify</option>
          <option value="shopline">Shopline</option>
          <option value="tiktokshop">TikTok Shop</option>
        </select>
      </label>
      <label>
        API Key
        <input v-model="form.apiKey" required />
      </label>
      <label>
        API Secret
        <input v-model="form.apiSecret" />
      </label>
      <label>
        Access Token
        <input v-model="form.accessToken" />
      </label>
      <button type="submit" :disabled="loading">{{ loading ? '保存中...' : '保存配置' }}</button>
      <p v-if="message" class="success">{{ message }}</p>
      <p v-if="error" class="error">{{ error }}</p>
    </form>
  </section>
</template>

<script setup>
import { reactive, ref } from 'vue'
import axios from 'axios'

const form = reactive({
  platform: 'shopify',
  apiKey: '',
  apiSecret: '',
  accessToken: ''
})

const loading = ref(false)
const message = ref('')
const error = ref('')

const handleSave = async () => {
  loading.value = true
  message.value = ''
  error.value = ''
  try {
    await axios.put('/api/shops/1/credentials', form)
    message.value = '保存成功（示例 shopId=1，可根据实际店铺 ID 调整）'
  } catch (err) {
    error.value = err.response?.data || err.message
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.card {
  background: #fff;
  padding: 24px;
  border-radius: 12px;
  max-width: 640px;
  margin: 0 auto;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
}
form {
  display: flex;
  flex-direction: column;
  gap: 12px;
}
input, select {
  padding: 10px 12px;
  border: 1px solid #cbd5e1;
  border-radius: 8px;
}
button {
  padding: 10px 12px;
  background: #16a34a;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}
.success {
  color: #16a34a;
}
.error {
  color: #dc2626;
}
.helper {
  color: #475569;
  margin-bottom: 12px;
}
</style>
