<!-- components/CoinRain.vue -->
<template>
    <div class="coin-container" v-if="isActive">
      <div 
        v-for="coin in coins" 
        :key="coin.id"
        class="coin"
        :style="coin.style"
      >
        <img src="@/assets/coin.png" alt="coin" />
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, watch } from 'vue';
  
  const props = defineProps({
    isActive: Boolean
  });
  
  const coins = ref([]);
  const maxCoins = 50; // 동전 개수 증가
  
  function createCoin() {
    const id = Date.now() + Math.random();
    const xPos = Math.random() * window.innerWidth;
    const delay = Math.random() * 2;
    const rotation = Math.random() * 360;
    const size = 30 + Math.random() * 20; // 30px~50px 랜덤 크기
    
    return {
      id,
      style: {
        left: `${xPos}px`,
        animationDelay: `${delay}s`,
        transform: `rotate(${rotation}deg)`,
        width: `${size}px`,
        height: `${size}px`
      }
    };
  }
  
  function startRain() {
    coins.value = Array(maxCoins).fill(null).map(createCoin);
  }
  
  watch(() => props.isActive, (newValue) => {
    if (newValue) {
      startRain();
    } else {
      coins.value = [];
    }
  });
  </script>
  
  <style scoped>
  .coin-container {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    pointer-events: none;
    z-index: 1000;
  }
  
  .coin {
    position: absolute;
    top: -50px;
    animation: fall 3s linear forwards;
  }
  
  .coin img {
    width: 100%;
    height: 100%;
    filter: drop-shadow(0 0 5px gold);
  }
  
  @keyframes fall {
    0% {
      transform: translateY(0) rotate(0deg);
      opacity: 1;
    }
    20% {
      transform: translateY(20vh) rotate(120deg);
    }
    40% {
      transform: translateY(40vh) rotate(240deg);
    }
    60% {
      transform: translateY(60vh) rotate(360deg);
    }
    80% {
      transform: translateY(80vh) rotate(480deg);
      opacity: 1;
    }
    100% {
      transform: translateY(110vh) rotate(600deg);
      opacity: 0;
    }
  }
  </style>