<template>
  <div class="attractions-container">
    <h1>{{ scheduleName }}</h1>
    <div v-if="attractions.length === 0" class="empty-message">
      등록된 관광지가 없습니다.
    </div>
    <div v-else>
      <div
        v-for="attraction in attractions"
        :key="attraction.scheduleAttractionId"
        class="attraction-card"
      >
        <button class="delete-button" @click="deleteAttraction(attraction.scheduleAttractionId)">X</button>
        <h2>{{ attraction.title }}</h2>
        <p>{{ attraction.addr1 }} {{ attraction.addr2 }}</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

// URL 파라미터에서 scheduleId 받기
const route = useRoute();
const scheduleId = route.params.scheduleId;

// 관광지 목록 및 일정 이름 상태
const attractions = ref<any[]>([]);
const scheduleName = ref('');

// 관광지 목록 가져오기
const fetchAttractions = async () => {
  try {
    const response = await axios.get(`http://localhost:80/enjoytrip/schedule/${scheduleId}/attractions`);
    // 응답 데이터에서 일정 이름과 관광지 목록 설정
    attractions.value = response.data.scheduleAttractions || [];
    scheduleName.value = response.data.scheduleName;
  } catch (error) {
    console.error('관광지 목록을 불러오는 데 실패했습니다:', error);
  }
};

// 관광지 삭제
const deleteAttraction = async (scheduleAttractionId: number) => {
  try {
    // DELETE 요청 전송
    await axios.delete(`http://localhost:80/enjoytrip/schedule/${scheduleId}/attractions/${scheduleAttractionId}`);
    // 로컬 상태에서 해당 ID를 가진 항목 제거
    attractions.value = attractions.value.filter(
      (attraction) => attraction.scheduleAttractionId !== scheduleAttractionId
    );
  } catch (error) {
    console.error('관광지 삭제 실패:', error);
  }
};

// 컴포넌트 마운트 시 데이터 로드
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
  position: relative; /* 삭제 버튼 위치를 조정하기 위해 설정 */
  margin: 10px 0;
  padding: 15px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.delete-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: red;
  color: white;
  border: none;
  border-radius: 50%;
  width: 25px;
  height: 25px;
  font-size: 16px;
  cursor: pointer;
}

.delete-button:hover {
  background: darkred;
}

.empty-message {
  text-align: center;
  font-size: 1.2rem;
  color: #888;
}
</style>
