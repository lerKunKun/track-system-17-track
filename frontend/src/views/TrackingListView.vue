<template>
  <section class="card">
    <header class="card-header">
      <div>
        <h2>运单列表</h2>
        <p class="helper">支持批量导入单号并查看基础状态，后端可对接 17TRACK 同步。</p>
      </div>
      <form class="import-form" @submit.prevent="handleImport">
        <input v-model="importInput" placeholder="输入单号，用逗号分隔" />
        <button type="submit">导入</button>
      </form>
    </header>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>单号</th>
          <th>承运商</th>
          <th>来源</th>
          <th>最近同步</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in trackings" :key="item.id">
          <td>{{ item.id }}</td>
          <td>{{ item.trackingNumber }}</td>
          <td>{{ item.carrierCode || '-' }}</td>
          <td>{{ item.source }}</td>
          <td>{{ item.updatedAt || '-' }}</td>
        </tr>
      </tbody>
    </table>
    <p v-if="error" class="error">{{ error }}</p>
  </section>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'

const trackings = ref([])
const importInput = ref('')
const error = ref('')

const fetchData = async () => {
  try {
    const { data } = await axios.get('/api/trackings')
    trackings.value = data
  } catch (err) {
    error.value = err.response?.data || err.message
  }
}

const handleImport = async () => {
  try {
    const numbers = importInput.value.split(',').map((i) => i.trim()).filter(Boolean)
    if (!numbers.length) return
    await axios.post('/api/trackings/import', { trackingNumbers: numbers })
    importInput.value = ''
    fetchData()
  } catch (err) {
    error.value = err.response?.data || err.message
  }
}

onMounted(fetchData)
</script>

<style scoped>
.card {
  background: #fff;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}
.import-form {
  display: flex;
  gap: 8px;
}
.import-form input {
  padding: 10px 12px;
  border: 1px solid #cbd5e1;
  border-radius: 8px;
}
.import-form button {
  padding: 10px 12px;
  background: #2563eb;
  color: #fff;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 16px;
}
th, td {
  text-align: left;
  padding: 10px;
  border-bottom: 1px solid #e2e8f0;
}
.helper {
  color: #475569;
}
.error {
  color: #dc2626;
}
</style>
