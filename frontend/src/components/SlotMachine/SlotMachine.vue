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

    <!-- 모달 컴포넌트 -->
    <div v-if="showModal" class="modal-overlay" @click="closeModal">
      <div class="modal-content" @click.stop>
        <button class="close-button" @click="closeModal">&times;</button>
        
        <div class="attraction-info">
          <h2>{{ attractionData.title }}</h2>
          
          <img 
            v-if="attractionData.firstImage1" 
            :src="attractionData.firstImage1" 
            :alt="attractionData.title"
            class="attraction-image"
          >
          
          <div class="info-grid">
            <div class="info-row">
              <strong>주소:</strong>
              <span>{{ attractionData.addr1 }} {{ attractionData.addr2 }}</span>
            </div>
            <div v-if="attractionData.tel" class="info-row">
              <strong>전화번호:</strong>
              <span>{{ attractionData.tel }}</span>
            </div>
            <div v-if="attractionData.homepage" class="info-row">
              <strong>홈페이지:</strong>
              <a :href="attractionData.homepage" target="_blank">바로가기</a>
            </div>
          </div>

          <div v-if="attractionData.overview" class="overview">
            <h3>개요</h3>
            <p>{{ attractionData.overview }}</p>
          </div>

          <button class="add-button" @click="addToSchedule">내 일정에 추가</button>
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
      isLeverPulled: false,
      isDragging: false,
      startY: 0,
      currentY: 0,
      leverPosition: 0,
      showModal: false,
      attractionData: null,
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
      this.leverPosition = Math.min(Math.max(this.currentY + deltaY, 0), 100);

      if (this.leverPosition >= 80 && !this.isLeverPulled) {
        this.isLeverPulled = true;
        this.start();
      }
    },

    endDrag() {
      this.isDragging = false;
      document.removeEventListener('mousemove', this.handleDrag);
      document.removeEventListener('mouseup', this.endDrag);

      setTimeout(() => {
        this.leverPosition = 0;
        this.isLeverPulled = false;
      }, 500);
    },

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
      if (index >= this.slots.length) return;
      const slotComponent = this.slotRefs[index];
      if (slotComponent) {
        slotComponent.start();
      }
    },

    async handleSlotFinish(event) {
      if (!event || !event.selectedValue) {
        console.error("Invalid event data", event);
        return;
      }

      const { index, selectedValue } = event;
      this.slots[index].selectedValue = selectedValue;

      if (index === 0) {
        const gugunList = this.regionStore.getGugunsBySido(selectedValue.id);
        this.slots[1].items = gugunList;
      } else if (index === 1) {
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
      } else if (index === 2) {
        await this.sendResult();
      }

      if (index < this.slots.length - 1) {
        this.currentSlotIndex++;
        this.$nextTick(() => {
          this.startSlot(this.currentSlotIndex);
        });
      } else {
        this.isStarting = false;
      }
    },

    async sendResult() {
      try {
        const response = await axios.post("http://localhost:80/enjoytrip/gatcha", {
          sido: this.slots[0].selectedValue.id,
          gugun: this.slots[1].selectedValue.id,
          content: this.slots[2].selectedValue.id,
        });
        
        this.attractionData = response.data.attraction;
        this.showModal = true;
      } catch (error) {
        console.error("서버 요청 실패:", error);
      }
    },

    closeModal() {
      this.showModal = false;
      this.attractionData = null;
    },

    async addToSchedule() {
      try {
        await axios.post("http://localhost:80/enjoytrip/schedule/attraction", {
          contentId: this.attractionData.contentId,
          scheduleId: 1 // TODO: 실제 일정 ID로 교체 필요
        });
        alert("일정에 추가되었습니다!");
        this.closeModal();
      } catch (error) {
        console.error("일정 추가 실패:", error);
        alert("일정 추가에 실패했습니다.");
      }
    }
  },

  created() {
    this.slotRefs = new Array(this.slots.length).fill(null);
  },

  beforeUnmount() {
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
  /*background: linear-gradient(to bottom, #b8860b, #f0e68c);*/
  background-image: url('../../assets/slot-background.jpg');
  padding: 30px;
  border-radius: 15px;
  box-shadow: 0 0 50px rgba(0, 0, 0, 0.7);
  border: 5px solid #f4d03f;
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

.lever:hover .lever-handle {
  transform: scale(1.05);
}

.lever:active .lever-handle {
  transform: scale(0.95);
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 8px;
  max-width: 600px;
  width: 90%;
  max-height: 80vh;
  overflow-y: auto;
  position: relative;
}

.close-button {
  position: absolute;
  top: 1rem;
  right: 1rem;
  font-size: 1.5rem;
  border: none;
  background: none;
  cursor: pointer;
}

.attraction-image {
  width: 100%;
  height: 300px;
  object-fit: cover;
  border-radius: 4px;
  margin: 1rem 0;
}

.info-grid {
  display: grid;
  gap: 1rem;
  margin: 1rem 0;
}

.info-row {
  display: grid;
  grid-template-columns: 100px 1fr;
  gap: 1rem;
}

.overview {
  margin: 1rem 0;
}

.add-button {
  width: 100%;
  padding: 1rem;
  background: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  margin-top: 1rem;
}

.add-button:hover {
  background: #45a049;
}
</style>