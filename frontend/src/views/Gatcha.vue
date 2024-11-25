<template>
  <div id="app">
    <div class="slot-container">
      <slot-machine />
    </div>
  </div>
</template>

<script setup>
import SlotMachine from '@/components/SlotMachine/SlotMachine.vue'
</script>

<style scoped>
#app {
width: 100%;
height: calc(100vh - 64px);
background-image: url('../assets/gatcha-background.png');
background-size: cover;
background-position: center;
background-repeat: no-repeat;
position: relative;
/* Flexbox를 사용하여 내부 컨텐츠 중앙 정렬 */
display: flex;
justify-content: center;
align-items: center;
}

.slot-container {
/* 필요한 경우 슬롯 머신 컨테이너의 크기나 여백 조정 */
padding: 20px;
}
</style>