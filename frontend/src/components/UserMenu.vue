<template>
  <div class="profile-container" v-if="isLoggedIn">
    <!-- 사용자 정보 섹션 -->
    <a-card class="user-info-card" bordered @click="toggleMenu">
      <a-avatar></a-avatar>
      <div class="user-profile">
        <div class="user-details">
          <p class="username">{{authStore.user}}</p>
        </div>
      </div>
    </a-card>

    <!-- 메뉴 옵션 섹션 -->
    <transition name="fade">
      <div v-if="isMenuVisible" class="dropdown-menu">
        <ul class="menu-list">
          <li v-for="(item, index) in menuItems" :key="index" class="menu-item" @click="handleMenuClick(item)">
            <component v-if="item.icon" :is="item.icon" class="menu-icon" />
            <span class="menu-label">{{ item.label }}</span>
          </li>
        </ul>
      </div>
    </transition>
  </div>
</template>

<script lang="ts" setup>
import {ref, computed, onMounted} from 'vue';
import { WalletOutlined, FileTextOutlined } from '@ant-design/icons-vue';
import { useAuthStore } from '@/stores/auth'; // Pinia Auth Store import

// Pinia Auth Store 사용
const authStore = useAuthStore();

// 로그인 상태 확인 (accessToken 존재 여부)
const isLoggedIn = computed(() => authStore.accessToken !== null);

// 메뉴 항목 정의
const menuItems = ref([
  { label: '쪽지', icon: FileTextOutlined },
  { label: '내정보', icon: WalletOutlined },
  { label: '로그아웃' },
]);

onMounted(() => {
  if (authStore.accessToken) {
    authStore.fetchUser(); // 사용자 정보 가져오기
  }
});

// 메뉴 표시 여부 상태
const isMenuVisible = ref(false);

// 메뉴 표시 토글 함수
const toggleMenu = () => {
  isMenuVisible.value = !isMenuVisible.value;
};

// 메뉴 클릭 핸들러
const handleMenuClick = (item) => {
  if (item.label === '로그아웃') {
    authStore.clearAuth(); // Pinia의 clearAccessToken 호출
  }
};
</script>

<style scoped>
/* 컨테이너 */
.profile-container {
  max-width: 400px;
  margin: 0 auto;
  position: relative; /* 드롭다운 메뉴의 절대 위치 기준 */
}

/* 사용자 정보 카드 */
.user-info-card {
  margin-bottom: 8px;
  padding: 16px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 16px;
}

.user-profile :hover {
  color: #555555; /* 색상 변경 */
  transform: scale(1.1); /* 확대 */
}

.user-details {
  flex: 1;
  text-align: left;
}

.username {
  font-size: 18px;
  font-weight: bold;
  margin: 0;
}

.user-id {
  font-size: 14px;
  color: #999;
  margin: 0;
}

/* 드롭다운 메뉴 */
.dropdown-menu {
  position: absolute;
  top: 100%; /* 사용자 정보 카드와 동일 높이에서 시작 */
  left: -100px; /* 사용자 정보 카드 오른쪽에 배치 */
  min-width: 150px; /* 메뉴의 최소 너비 설정 */
  background-color: #ffffff;
  border: 1px solid #e8e8e8;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 8px 0;
  z-index: 1000; /* 헤더와 충돌하지 않도록 높은 z-index 설정 */
  overflow: hidden;
}

/* 메뉴 리스트 */
.menu-list {
  list-style: none;
  margin: 0;
  padding: 0;
}

.menu-item {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 10px 16px;
  font-size: 16px;
  color: #333;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.menu-item:hover {
  background-color: #f5f5f5;
}

.menu-icon {
  font-size: 20px;
  color: #555;
  margin-right: 12px;
}

.menu-label {
  font-weight: 500;
}
</style>
