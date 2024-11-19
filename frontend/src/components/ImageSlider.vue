<template>
  <div class="slider-container">
    <!-- 슬라이더 -->
    <div class="slider-wrapper" :style="{ transform: `translateX(-${currentGroupIndex * 100}%)` }">
      <div
          class="slider-item"
          v-for="(image, index) in images"
          :key="index"
      >
        <img :src="image.src" :alt="image.alt" />
        <div class="slider-caption">{{ image.caption }}</div>
      </div>
    </div>

    <!-- 네비게이션 버튼 -->
    <button class="nav-button prev" @click="prevGroup">‹</button>
    <button class="nav-button next" @click="nextGroup">›</button>

    <!-- 페이지네이션 -->
    <div class="pagination">
      <span
          v-for="index in visibleGroupCount"
          :key="index"
          :class="['dot', { active: index - 1 === currentGroupIndex }]"
          @click="goToGroup(index - 1)"
      ></span>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, onMounted, onUnmounted } from 'vue';

// 슬라이더 데이터
const images = ref([
  {
    src: 'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2a/c7/90/94/caption.jpg?w=500&h=400&s=1',
    alt: '해변 이미지',
    caption: '끝없이 펼쳐진 아름다운 해변',
  },
  {
    src: 'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/15/33/f5/de/london.jpg?w=500&h=400&s=1',
    alt: '런던',
    caption: '신사들이 가득한 런던',
  },
  {
    src: 'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/03/9b/2f/89/hanoi.jpg?w=500&h=400&s=1',
    alt: '하노이 이미지',
    caption: '오랜 역사가 숨쉬는 하노이',
  },
  {
    src: 'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2c/07/ad/24/caption.jpg?w=500&h=400&s=1',
    alt: '로마 이미지',
    caption: '거대한 실외 박물관 로마',
  },
  {
    src: 'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/17/15/6d/d6/paris.jpg?w=500&h=400&s=1',
    alt: '파리',
    caption: '파리바게트 원조 파리',
  },
  {
    src: 'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2a/c7/90/94/caption.jpg?w=500&h=400&s=1',
    alt: '해변 이미지',
    caption: '끝없이 펼쳐진 아름다운 해변',
  },
  {
    src: 'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/15/33/f5/de/london.jpg?w=500&h=400&s=1',
    alt: '런던',
    caption: '신사들이 가득한 런던',
  }
]);

// 현재 슬라이더 상태
const currentGroupIndex = ref(0);
const imagesPerGroup = 5; // 한 번에 보이는 이미지 수

// 총 그룹 수 계산
const visibleGroupCount = computed(() => Math.ceil(images.value.length / imagesPerGroup));

// 슬라이더 이동
const nextGroup = () => {
  currentGroupIndex.value = (currentGroupIndex.value + 1) % visibleGroupCount.value;
};

const prevGroup = () => {
  currentGroupIndex.value =
      (currentGroupIndex.value - 1 + visibleGroupCount.value) % visibleGroupCount.value;
};

const goToGroup = (index: number) => {
  currentGroupIndex.value = index;
};

// 자동 슬라이더 타이머
let autoSlideTimer: number | null = null;

const startAutoSlide = () => {
  autoSlideTimer = setInterval(nextGroup, 5000); // 3초마다 슬라이드 이동
};

const stopAutoSlide = () => {
  if (autoSlideTimer) {
    clearInterval(autoSlideTimer);
    autoSlideTimer = null;
  }
};

// 컴포넌트 생명주기 관리
onMounted(() => {
  startAutoSlide(); // 컴포넌트가 마운트되면 자동 슬라이더 시작
});

onUnmounted(() => {
  stopAutoSlide(); // 컴포넌트가 언마운트되면 슬라이더 중지
});
</script>

<style scoped>
/* 슬라이더 컨테이너 */
.slider-container {
  position: relative;
  width: 80%;
  max-width: 1800px;
  margin: 40px auto;
  overflow: hidden;
  border-radius: 8px;
}

/* 슬라이더 래퍼 */
.slider-wrapper {
  display: flex;
  width: 100%;
  transition: transform 0.5s ease-in-out;
}

/* 슬라이더 아이템 */
.slider-item {
  flex: 0 0 calc(100% / 5); /* 한 번에 5개씩 표시 */
  margin-right: 20px; /* 카드 간의 간격 */
  border-radius: 8px;
  overflow: hidden;
  text-align: center;
  background: #f5f5f5;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.slider-item img {
  width: 100%;
  height: 300px; /* 고정된 이미지 높이 */
  object-fit: cover;
}

.slider-caption {
  padding: 10px 0;
  font-size: 14px;
  color: #333;
  font-weight: bold;
  background: rgba(255, 255, 255, 0.9);
}

/* 네비게이션 버튼 */
.nav-button {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background: rgba(0, 0, 0, 0.5);
  color: #fff;
  border: none;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  cursor: pointer;
  z-index: 10;
}

.nav-button.prev {
  left: 10px;
}

.nav-button.next {
  right: 10px;
}

/* 페이지네이션 */
.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.dot {
  width: 10px;
  height: 10px;
  background: #ddd;
  border-radius: 50%;
  margin: 0 5px;
  cursor: pointer;
}

.dot.active {
  background: #007bff;
}
</style>
