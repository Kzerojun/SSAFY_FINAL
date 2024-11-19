<!-- App.vue -->
<template>
  <div id="app">
    <Header /> <!-- Header 컴포넌트를 렌더링합니다 -->
    <router-view /> <!-- 이 부분은 라우팅이 있을 경우에만 필요합니다 -->
  </div>
</template>

<script lang="ts" setup>
import Header from './components/Header.vue';
</script>

<style>
@font-face {
  font-family: 'Danjo-bold-Regular';
  src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_2307-1@1.1/Danjo-bold-Regular.woff2') format('woff2');
  font-weight: normal;
  font-style: normal;
}

body {
  font-family: 'Danjo-bold-Regular', sans-serif;
}
</style>