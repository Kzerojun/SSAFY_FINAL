<template>
  <div class="slot-machine">
    <div class="frame">
      <div class="slots">
        <Slot
          v-for="(slot, index) in slots"
          :key="index"
          :title="slot.title"
          :items="slot.items"
          :index="index"
          :isActive="currentSlotIndex === index"
          :ref="el => slotRefs[index] = el"
          @finish="handleSlotFinish"
        />
      </div>
      <!-- 레버 컨테이너 -->
      <div class="lever-container">
        <div 
          class="lever"
          :class="{ 'pulled': isLeverPulled }"
          ref="lever"
          @mousedown="startDrag"
          :style="leverStyle"
        >
          <div class="lever-handle"></div>
          <div class="lever-stem"></div>
          <div class="lever-base"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useRegionStore } from '@/stores/regionStore';
import axios from 'axios';
import Slot from './Slot.vue';

export default {
  components: { Slot },
  
  setup() {
    const regionStore = useRegionStore();
    return { regionStore };
  },

  data() {
    return {
      slots: [
        { title: "시도", items: [], selectedValue: null },
        { title: "구군", items: [], selectedValue: null },
        { title: "유형", items: [], selectedValue: null },
      ],
      currentSlotIndex: 0,
      isStarting: false,
      slotRefs: [],
      // 레버 관련 상태 추가
      isLeverPulled: false,
      isDragging: false,
      startY: 0,
      currentY: 0,
      leverPosition: 0,
    };
  },

  computed: {
    leverStyle() {
      return {
        transform: `translateY(${this.leverPosition}px)`,
        transition: this.isDragging ? 'none' : 'transform 0.5s ease-out'
      };
    }
  },

  methods: {
    startDrag(event) {
      if (this.isStarting) return;
      
      this.isDragging = true;
      this.startY = event.clientY;
      this.currentY = this.leverPosition;

      document.addEventListener('mousemove', this.handleDrag);
      document.addEventListener('mouseup', this.endDrag);
    },

    handleDrag(event) {
      if (!this.isDragging) return;

      const deltaY = event.clientY - this.startY;
      // 레버 이동 범위 제한 (0px ~ 100px)
      this.leverPosition = Math.min(Math.max(this.currentY + deltaY, 0), 100);

      // 레버가 충분히 내려갔을 때 (80% 이상)
      if (this.leverPosition >= 80 && !this.isLeverPulled) {
        this.isLeverPulled = true;
        this.start();
      }
    },

    endDrag() {
      this.isDragging = false;
      document.removeEventListener('mousemove', this.handleDrag);
      document.removeEventListener('mouseup', this.endDrag);

      // 레버 원위치
      setTimeout(() => {
        this.leverPosition = 0;
        this.isLeverPulled = false;
      }, 500);
    },

    // 기존 start 메소드
    async start() {
      if (this.isStarting) return;

      this.isStarting = true;
      this.currentSlotIndex = 0;
      this.slots.forEach(slot => {
        slot.selectedValue = null;
      });

      this.slots[0].items = this.regionStore.sidoOptions;

      this.$nextTick(() => {
        this.startSlot(0);
      });
    },

    startSlot(index) {
        console.log('Starting slot:', index); // 디버깅용
        if (index >= this.slots.length) return;
        const slotComponent = this.slotRefs[index];
        if (slotComponent) {
          slotComponent.start();
        } else {
          console.error('Slot component not found:', index); // 디버깅용
        }
      },
  
      async handleSlotFinish(event) {
        console.log('Slot finish event:', event); // 디버깅용
        if (!event || !event.selectedValue) {
          console.error("Invalid event data", event);
          return;
        }
  
        const { index, selectedValue } = event;
        this.slots[index].selectedValue = selectedValue;
  
        if (index === 0) {
          // 구군 데이터 설정
          const gugunList = this.regionStore.getGugunsBySido(selectedValue.id);
          console.log('Gugun list:', gugunList); // 디버깅용
          this.slots[1].items = gugunList;
        } else if (index === 1) {
          // 관광 유형 데이터 설정 (직접 데이터 할당)
          this.slots[2].items = [
            { id: 12, name: "관광지" },
            { id: 14, name: "문화시설" },
            { id: 15, name: "축제공연행사" },
            { id: 25, name: "여행코스" },
            { id: 28, name: "레포츠" },
            { id: 32, name: "숙박" },
            { id: 38, name: "쇼핑" },
            { id: 39, name: "음식점" },
          ];
          console.log('Content types set:', this.slots[2].items); // 디버깅용
        } else if (index === 2) {
          await this.sendResult();
        }
  
        if (index < this.slots.length - 1) {
          this.currentSlotIndex++;
          console.log('Moving to next slot:', this.currentSlotIndex); // 디버깅용
          this.$nextTick(() => {
            this.startSlot(this.currentSlotIndex);
          });
        } else {
          this.isStarting = false;
        }
      },
  
      async sendResult() {
        const sido = this.slots[0].selectedValue.id;
        const gugun = this.slots[1].selectedValue.id;
        const content = this.slots[2].selectedValue.id;
  
        try {
          const response = await axios.post("http://localhost:80/gatcha", {
            sido,
            gugun,
            content,
          });
          console.log("결과:", response.data);
          alert(`추천 여행지: ${response.data.name}`);
        } catch (error) {
          console.error("서버 요청 실패:", error);
        }
      },
    },
  
    created() {
      this.slotRefs = new Array(this.slots.length).fill(null);
    },
  

  beforeUnmount() {
    // 이벤트 리스너 정리
    document.removeEventListener('mousemove', this.handleDrag);
    document.removeEventListener('mouseup', this.endDrag);
  }
};
</script>

<style scoped>
.slot-machine {
  display: flex;
  justify-content: center;
  align-items: center;
}

.frame {
  display: flex;
  align-items: flex-start;
  background: linear-gradient(to bottom, #b8860b, #f0e68c);
  padding: 30px;
  border-radius: 15px;
  box-shadow: 0 0 50px rgba(0, 0, 0, 0.7);
  border: 5px solid #ffd700;
}

.slots {
  display: flex;
  flex-direction: row;
  gap: 15px;
}

.lever-container {
  margin-left: 30px;
  height: 220px;
  display: flex;
  align-items: flex-start;
  position: relative;
}

.lever {
  cursor: pointer;
  position: relative;
  transform-origin: top center;
  user-select: none;
}

.lever-handle {
  width: 40px;
  height: 40px;
  background: linear-gradient(145deg, #ff4d4d, #cc0000);
  border-radius: 50%;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  border: 3px solid #990000;
}

.lever-stem {
  width: 12px;
  height: 100px;
  background: linear-gradient(90deg, #666666, #999999);
  margin: 0 auto;
  border-radius: 6px;
  box-shadow: 2px 0 4px rgba(0, 0, 0, 0.2);
}

.lever-base {
  width: 30px;
  height: 15px;
  background: #333333;
  border-radius: 15px 15px 0 0;
  margin: 0 auto;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

.lever.pulled .lever-handle {
  background: linear-gradient(145deg, #cc0000, #990000);
}

/* 레버 호버 효과 */
.lever:hover .lever-handle {
  transform: scale(1.05);
}

/* 레버 드래그 중 효과 */
.lever:active .lever-handle {
  transform: scale(0.95);
}
</style>