<template>
    <div class="schedule-container">
      <h1>내 여행 일정</h1>
      <div v-if="schedules.length === 0" class="empty-message">
        등록된 일정이 없습니다. 새로운 일정을 만들어보세요!
      </div>
      <div v-else>
        <div
          v-for="schedule in schedules"
          :key="schedule.scheduleId"
          class="schedule-card"
        >
          <h2 class="schedule-title">{{ schedule.scheduleName }}</h2>
          <p class="schedule-date">
            일정: {{ formatDate(schedule.startDate) }} ~ {{ formatDate(schedule.endDate) }}
          </p>
          <p class="schedule-created">
            생성일: {{ formatDate(schedule.createdAt) }}
          </p>
        </div>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  
  // 여행 일정 상태
  const schedules = ref([]);
  
  // 서버로부터 일정 데이터를 불러오는 함수
  const fetchSchedules = async () => {
    try {
      const response = await axios.get('http://localhost:80/enjoytrip/schedule'); // 실제 API 경로로 대체
      schedules.value = response.data; // 서버 응답 데이터에 맞게 매핑
    } catch (error) {
      console.error('일정 데이터를 불러오는 데 실패했습니다:', error);
    }
  };
  
  // 날짜 포맷 함수
  const formatDate = (date: string) => {
    const options: Intl.DateTimeFormatOptions = { year: 'numeric', month: 'short', day: 'numeric' };
    return new Date(date).toLocaleDateString('ko-KR', options);
  };
  
  // 컴포넌트 로드 시 일정 데이터 불러오기
  onMounted(fetchSchedules);
  </script>
  
  <style scoped>
  .schedule-container {
    padding: 16px;
    max-width: 800px;
    margin: 0 auto;
  }
  
  h1 {
    text-align: center;
    margin-bottom: 24px;
    font-size: 24px;
    font-weight: bold;
  }
  
  .empty-message {
    text-align: center;
    color: #888;
    font-size: 16px;
    margin-top: 20px;
  }
  
  .schedule-card {
    background-color: #f9f9f9;
    border: 1px solid #e0e0e0;
    border-radius: 12px;
    padding: 16px;
    margin-bottom: 16px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: transform 0.2s, box-shadow 0.2s;
  }
  
  .schedule-card:hover {
    transform: translateY(-4px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  }
  
  .schedule-title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 8px;
  }
  
  .schedule-date,
  .schedule-created {
    font-size: 14px;
    color: #555;
    margin: 4px 0;
  }
  </style>
  