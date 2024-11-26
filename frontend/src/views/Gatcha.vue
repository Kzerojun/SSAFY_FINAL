<template>
  <div class="gatcha-page">
    <div class="content-container">
      <!-- 슬롯 머신 섹션 -->
      <div class="slot-section">
        <SlotMachine />
      </div>
    </div>
  </div>
</template>

<script setup>
import SlotMachine from '@/components/SlotMachine/SlotMachine.vue';
import { useRouter } from 'vue-router';
import { onMounted } from 'vue';
import { useAuthStore } from '@/stores/auth';

const router = useRouter();
const authStore = useAuthStore();

// 로그인 체크
onMounted(() => {
  if (!authStore.accessToken) {
    router.push('/login');
  }
});
</script>

<style scoped>
.gatcha-page {
  width: 100%;
  min-height: calc(100vh - 64px); /* 헤더 높이를 제외한 전체 높이 */
  background-image: url('../assets/gatcha-background.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 2rem;
}

.content-container {
  width: 100%;
  max-width: 1400px; /* 최대 너비 설정 */
  margin: 0 auto;
}

.slot-section {
  background: rgba(255, 255, 255, 0.5); /* 반투명 흰색 배경 */
  border-radius: 16px;
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(5px);
  padding: 2rem;
}

/* 반응형 디자인 */
@media (max-width: 1200px) {
  .content-container {
    max-width: 95%;
  }
}

@media (max-width: 768px) {
  .gatcha-page {
    padding: 1rem;
  }
  
  .slot-section {
    padding: 1rem;
  }
}
</style>