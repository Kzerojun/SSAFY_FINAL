<template>
    <div class="attractions-container">
      <h1>{{ scheduleName }}의 관광지 목록</h1>
      <div v-if="attractions.length === 0" class="empty-message">
        등록된 관광지가 없습니다.
      </div>
      <div v-else>
        <div
          v-for="attraction in attractions"
          :key="attraction.contentId"
          class="attraction-card"
        >
          <h2>{{ attraction.title }}</h2>
          <p>{{ attraction.addr1 }} {{ attraction.addr2 }}</p>
        </div>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue';
  import { useRoute } from 'vue-router'; // URL 파라미터 받기
  import axios from 'axios';
  
  // URL 파라미터에서 scheduleId 받기
  const route = useRoute();
  const scheduleId = route.params.scheduleId;
  
  // 관광지 목록 상태
  const attractions = ref<any[]>([]);
  const scheduleName = ref('');
  
  // 일정에 해당하는 관광지 목록을 불러오는 함수
  const fetchAttractions = async () => {
    try {
      const response = await axios.get(`http://localhost:80/enjoytrip/schedule/${scheduleId}/attractions`);
      attractions.value = response.data.attractions || [];
      scheduleName.value = response.data.scheduleName; // 일정 이름 저장
    } catch (error) {
      console.error('관광지 목록을 불러오는 데 실패했습니다:', error);
    }
  };
  
  // 컴포넌트가 마운트될 때 관광지 목록을 불러옴
  onMounted(() => {
    fetchAttractions();
  });
  </script>
  
  <style scoped>
  .attractions-container {
    padding: 20px;
    max-width: 800px;
    margin: 0 auto;
  }
  
  .attraction-card {
    margin: 10px 0;
    padding: 15px;
    background-color: #f9f9f9;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .empty-message {
    text-align: center;
    font-size: 1.2rem;
    color: #888;
  }
  </style>
  