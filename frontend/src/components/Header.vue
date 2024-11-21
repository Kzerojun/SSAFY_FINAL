<template>
  <a-layout-header class="header">
    <!-- 로고 -->
    <div class="left-section" @click = "main">
      <div class="logo">
         <img src="@/assets/logo.png" alt="Logo" />
      </div>
      <span class="brand-name">강원랜트</span>
    </div>

    <!-- 커스텀 검색창 -->
    <div class="center-section">
      <div class="custom-search-bar">
        <input
            type="text"
            class="search-input"
            placeholder="여행 떠나고, 잭팟 뽑자!"
        />
        <button class="search-button">
          <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
              class="search-icon"
          >
            <circle cx="11" cy="11" r="8"></circle>
            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
          </svg>
        </button>
      </div>
    </div>

    <!-- 우측 메뉴 -->
    <div class="right-section">
      <nav class="menu">
        <span @click = "gatcha">여행 뽑기</span>
        <span @click = "schedule">일정 확인</span>
        <span @click = "search">여행 일정 만들기</span>
        <UserOutlined class="icon" @click = "login"/>
        <ClockCircleOutlined class="icon" />
      </nav>
    </div>
  </a-layout-header>
</template>

<script lang="ts" setup>
import { Layout } from 'ant-design-vue';
import { UserOutlined, ClockCircleOutlined } from '@ant-design/icons-vue';
import { useRouter } from 'vue-router';

// Ant Design 컴포넌트 명시적 등록
const ALayoutHeader = Layout.Header;

const router = useRouter();

const login = () => {
  router.push({ name: 'login' });
};

const main = () =>{
  router.push({ name: 'home' });
}

const gatcha = () => {
  router.push({ name: 'gatcha' })
}

const schedule = () => {
  router.push({ name : 'schedule' })
}


const search = () => {
  router.push({ name : 'search' })
}



</script>

<style scoped>
/* 글로벌 폰트 */
@font-face {
  font-family: 'LINESeedKR-Bd';
  src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_11-01@1.0/LINESeedKR-Bd.woff2')
  format('woff2');
  font-weight: 700;
  font-style: normal;
}

@font-face {
  font-family: 'NanumSquareNeoLight';
  src: url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareNeo/NanumSquareNeoTTF-aLt.eot);
  src: url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareNeo/NanumSquareNeoTTF-aLt.eot?#iefix) format("embedded-opentype"), url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareNeo/NanumSquareNeoTTF-aLt.woff) format("woff"), url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareNeo/NanumSquareNeoTTF-aLt.ttf) format("truetype");
}

@font-face {
  font-family: 'S-CoreDream-3Light';
  src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: 'NEXON Lv1 Gothic OTF';
  src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

/* 헤더 */
.header {
  margin-top: 10px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  background: #ffffff;
  border-bottom: 2px solid #f4d03f;
  height: 64px;
}

/* 왼쪽 로고 섹션 */
.left-section {
  display: flex;
  align-items: center;
  cursor: pointer;
}


.logo img {
  width: 150px;
  height: 100px;
  margin-right: 8px;
  transition: transform 0.3s ease;
}

.brand-name {
  font-size: 30px;
  transition: color 0.3s ease;
  font-family: 'NEXON Lv1 Gothic OTF', sans-serif;
}


/* 가운데 커스텀 검색창 섹션 */
.center-section {
  flex: 1;
  display: flex;
  justify-content: center;
}

/* 커스텀 검색창 */
.custom-search-bar {
  display: flex;
  align-items: center;
  width: 400px;
  border: 2px solid #f4d03f;
  border-radius: 24px;
  padding: 4px 12px;
  background-color: #fff;
  transition: all 0.3s ease;
}

.custom-search-bar:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15); /* 그림자 추가 */
  transform: scale(1.02); /* 약간 확대 */
}

/* 검색창 입력 필드 */
.search-input {
  flex: 1;
  border: none;
  outline: none;
  font-size: 16px;
  font-family: 'LINESeedKR-Bd', sans-serif;
}

.search-input::placeholder {
  color: #999;
}

/* 검색 버튼 */
.search-button {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
  margin: 0;
  transition: transform 0.3s ease;
}

.search-button:hover {
  transform: scale(1.2); /* 아이콘 확대 */
}

.search-icon {
  width: 20px;
  height: 20px;
  color: #555;
  transition: color 0.3s ease;
}

.search-icon:hover {
  color: #f4d03f; /* 색상 변경 */
}

/* 우측 메뉴 */
.right-section {
  display: flex;
  align-items: center;
  font-family: 'S-CoreDream-3Light', sans-serif;
}

.menu {
  display: flex;
  align-items: center;
  gap: 20px;
  font-size: 20px;
  transition: all 0.3s ease;
}

/* 메뉴 호버 효과 */
.menu span {
  cursor: pointer;
  transition: color 0.3s ease, transform 0.3s ease;
}

.menu span:hover {
  color: #555555; /* 색상 변경 */
  transform: scale(1.1); /* 확대 */
}

/* 아이콘 스타일 */
.icon {
  font-size: 25px;
  color: #555;
  cursor: pointer;
  transition: color 0.3s ease, transform 0.3s ease;
}

.icon:hover {
  color: #f4d03f; /* 색상 변경 */
  transform: scale(1.2); /* 확대 */
}
</style>

