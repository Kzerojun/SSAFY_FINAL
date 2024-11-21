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
        @click="goToScheduleAttractions(schedule.scheduleId)">
        <h2 class="schedule-title"> {{ schedule.scheduleName }} </h2>
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
  import { useRouter } from 'vue-router';

  // 여행 일정 상태
  const schedules = ref<any[]>([]);

  // 라우터 인스턴스
  const router = useRouter();

  // 서버로부터 일정 데이터를 불러오는 함수
  const fetchSchedules = async () => {
    try {
      // 하드코딩된 테스트용 이메일
      const response = await axios.get('http://localhost:80/enjoytrip/schedule?userEmail=inju@naver.com');
      
      // 응답 데이터가 'schedules' 배열에 있어야 함
      if (response.data && response.data.schedules) {
        schedules.value = response.data.schedules;
      } else {
        console.error('일정 목록을 찾을 수 없습니다.');
      }
    } catch (error) {
      console.error('일정 데이터를 불러오는 데 실패했습니다:', error);
    }
  };

  // 날짜 포맷 함수
  const formatDate = (date: string) => {
    const options: Intl.DateTimeFormatOptions = { year: 'numeric', month: 'short', day: 'numeric' };
    return new Date(date).toLocaleDateString('ko-KR', options);
  };

  // 일정 상세 페이지로 이동
  const goToScheduleAttractions = (scheduleId: number) => {
    router.push({ name: 'schedule-attractions', params: { scheduleId } });
  };

  // 컴포넌트가 마운트될 때 일정 데이터를 불러옴
  onMounted(() => {
    fetchSchedules();
  });
</script>

<style scoped>
.schedule-container {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.schedule-title {
  font-size: 1.5rem;
  font-weight: bold;
}

.schedule-card {
  margin: 10px 0;
  padding: 15px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer; /* 클릭 가능한 카드 */
}

.schedule-date,
.schedule-created {
  color: #555;
  font-size: 0.9rem;
}

.empty-message {
  text-align: center;
  font-size: 1.2rem;
  color: #888;
}
</style>
