<template>
    <div class="slot">
      <h2>{{ title }}</h2>
      <div class="slot__window">
        <div class="slot__wrap">
          <div 
            v-for="(item, idx) in items" 
            :key="idx" 
            class="slot__item"
          >
            {{ item.name }}
          </div>
          <div class="slot__item slot__item--copy">{{ items[0]?.name }}</div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  const requestAnimationFrame = window.requestAnimationFrame ||
    window.webkitRequestAnimationFrame ||
    window.mozRequestAnimationFrame ||
    window.msRequestAnimationFrame ||
    window.oRequestAnimationFrame ||
    function(cb) { window.setTimeout(cb, 1000/60) };
  
  export default {
    props: {
      title: String,
      items: Array,
      index: Number,
      isActive: Boolean,
    },
  
    data() {
      return {
        opts: null,
        startedAt: null,
        selectedValue: null,
      };
    },
  
    methods: {
      start() {
        if (this.opts || this.items.length === 0) return;
  
        const choice = Math.floor(Math.random() * this.items.length);
        this.selectedValue = this.items[choice];
        
        this.opts = [{
          el: this.$el.querySelector('.slot__wrap'),
          finalPos: choice * 180,
          startOffset: 2000 + Math.random() * 500 + this.index * 500,
          height: this.items.length * 180,
          duration: 3000 + this.index * 700,
          isFinished: false,
        }];
  
        requestAnimationFrame(this.animate);
      },
  
      animate(timestamp) {
        if (this.startedAt == null) {
          this.startedAt = timestamp;
        }
  
        const timeDiff = timestamp - this.startedAt;
  
        this.opts.forEach(opt => {
          if (opt.isFinished) return;
  
          const timeRemaining = Math.max(opt.duration - timeDiff, 0);
          const power = 3;
          const offset = (Math.pow(timeRemaining, power) / Math.pow(opt.duration, power)) * opt.startOffset;
          const pos = -1 * Math.floor((offset + opt.finalPos) % opt.height);
          
          opt.el.style.transform = `translateY(${pos}px)`;
  
          if (timeDiff > opt.duration) {
            opt.isFinished = true;
          }
        });
  
        if (this.opts.every(o => o.isFinished)) {
          this.$emit('finish', {
            index: this.index,
            selectedValue: this.selectedValue
          });
          this.opts = null;
          this.startedAt = null;
        } else {
          requestAnimationFrame(this.animate);
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .slot {
    width: 220px;
    height: 220px;
    background-color: orange;
    border: 3px solid #f4d03f;
    border-radius: 15px;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.6);
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 10px;
    font-size: 18px;
  }

  .slot h2 {
    font-weight: bold;
    color: #2a0346;
  }
  
  .slot__window {
    width: 90%;
    height: 180px;
    overflow: hidden;
    background-color: #8A2BE2;
    border-radius: 10px;
    position: relative;
  }
  
  .slot__wrap {
    position: absolute; /* relative에서 absolute로 변경 */
    top: 0;
    left: 0;
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    will-change: transform;
    backface-visibility: hidden; /* 추가 */
    transform-style: preserve-3d; /* 추가 */
  }
  
  .slot__item {
    flex-shrink: 0; /* 추가 */
    height: 180px;
    width: 100%;
    margin: 0;
    padding: 0 15px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #4B0082;
    color: white;
    font-size: 24px;
    font-weight: bold;
    text-align: center;
    border-radius: 10px;
    box-shadow: 0 0 15px rgba(0, 0, 0, 0.4);
    box-sizing: border-box; /* 추가 */
  }
  
  .slot__item--copy {
    position: absolute;
    top: 100%;
    left: 0; /* 추가 */
  }
  </style>