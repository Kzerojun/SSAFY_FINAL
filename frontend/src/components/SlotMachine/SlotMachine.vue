<template>
  <div class="slot-machine-container">
    <!-- 초기 안내 모달 -->
    <div v-if="showWelcomeModal" class="modal-overlay" @click="closeWelcomeModal">
      <div class="modal-content welcome-modal" @click.stop>
        <h2>랜덤 여행지 뽑기</h2>
        <p>슬롯 머신으로 랜덤 여행지를 뽑아 특별한 추억을 만들어 보세요!</p>
        <button class="primary-button" @click="closeWelcomeModal">시작하기</button>
      </div>
    </div>

    <!-- 일정 생성 모달 (finalizeSchedule 클릭 시 표시) -->
    <div v-if="showScheduleModal" class="modal-overlay" @click="closeScheduleModal">
      <div class="modal-content schedule-modal" @click.stop>
        <h2>새 랜덤 일정 만들기</h2>
        <form @submit.prevent="createFinalSchedule">
          <div class="form-group">
            <label>일정 이름</label>
            <input v-model="scheduleForm.scheduleName" type="text" required>
          </div>
          <div class="form-group">
            <label>시작 날짜</label>
            <input v-model="scheduleForm.startDate" type="date" required>
          </div>
          <div class="form-group">
            <label>종료 날짜</label>
            <input v-model="scheduleForm.endDate" type="date" required>
          </div>
          <div class="modal-buttons">
            <button type="submit">완료</button>
            <button type="button" @click="closeScheduleModal">취소</button>
          </div>
        </form>
      </div>
    </div>

    <!-- 슬롯 머신 -->
    <div class="slot-machine">

      <!-- 아이콘 추가 -->
      <div class="slot-machine-header">
        <img src="@/assets/slotmachine-icon.png" alt="slot machine icon" class="slot-machine-icon">
      </div>
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
    </div>

    <!-- 선택된 관광지 목록 -->
    <div class="selected-attractions">
      <h3>선택된 관광지</h3>
      <div class="attractions-list">
        <div v-for="(attraction, index) in selectedAttractions" :key="index" class="attraction-item">
          <img :src="attraction.firstImage1 || '/placeholder.jpg'" :alt="attraction.title">
          <div class="attraction-details">
            <h4>{{ attraction.title }}</h4>
            <p>{{ attraction.addr1 }}</p>
          </div>
        </div>
      </div>
      <button 
        class="create-schedule-button" 
        @click="finalizeSchedule"
        :disabled="selectedAttractions.length === 0"
      >
        일정 생성하기
      </button>
    </div>

    <!-- 관광지 상세 모달 -->
    <div v-if="showAttractionModal" class="modal-overlay" @click="closeAttractionModal">
      <div class="modal-content" @click.stop>
        <button class="close-button" @click="closeAttractionModal">&times;</button>
        
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

          <div class="modal-buttons">
            <button class="add-button" @click="addToSchedule">내 일정에 추가</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, reactive, computed } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { message } from 'ant-design-vue';
import Slot from './Slot.vue';
import { useRegionStore } from '@/stores/regionStore';
import apiClient from '@/api/apiClient';

export default {
  components: { Slot },
  
  setup() {
    const router = useRouter();
    const regionStore = useRegionStore();
    
    return { 
      regionStore,
      router 
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


  data() {
    return {
      showWelcomeModal: true,
      showScheduleModal: false,
      showAttractionModal: false,
      scheduleForm: {
        scheduleName: '',
        startDate: '',
        endDate: ''
      },
      selectedAttractions: [],
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
      attractionData: null,
    };
  },

  methods: {
    // 웰컴 모달 관련 메소드 수정
    closeWelcomeModal() {
      this.showWelcomeModal = false;
      this.slots[0].items = this.regionStore.sidoOptions; // 시도 옵션만 초기화
    },

    async start() {
      if (this.isStarting) return;

      this.isStarting = true;
      this.currentSlotIndex = 0;
      this.slots.forEach(slot => {
        slot.selectedValue = null;
      });

      if (this.slots[0].items.length === 0) {
        this.slots[0].items = this.regionStore.sidoOptions;
      }

      this.$nextTick(() => {
        this.startSlot(0);
      });
    },

    // 슬롯 머신 활성화
    enableSlotMachine() {
      this.slots[0].items = this.regionStore.sidoOptions;
      // 필요한 초기화 작업 수행
    },

    // 일정 생성 관련 메소드
    async finalizeSchedule() {
      // 일정 생성 모달 표시
      this.showScheduleModal = true;
    },

    // 일정 최종 생성 메소드 (모달에서 완료 버튼 클릭 시)
    async createFinalSchedule() {
      try {
        const response = await apiClient.post('http://localhost:80/enjoytrip/schedule/with-attractions', {
          scheduleName: this.scheduleForm.scheduleName,
          startDate: this.scheduleForm.startDate,
          endDate: this.scheduleForm.endDate,
          attractions: this.selectedAttractions.map((attraction, index) => ({
            no: attraction.no,
            sequenceOrder: index
          }))
        });

        if (response.data.code === 'SU') {
          message.success('일정이 성공적으로 생성되었습니다!');
          this.router.push(`/schedule/${response.data.scheduleId}/attractions`);
        }
      } catch (error) {
        console.error('일정 생성 실패:', error);
        message.error('일정 생성에 실패했습니다.');
      }
    },

    closeAttractionModal() {
      this.showAttractionModal = false;
      this.attractionData = null;
    },

    closeScheduleModal() {
      this.showScheduleModal = false;
      this.scheduleForm = {
        scheduleName: '',
        startDate: '',
        endDate: ''
      };
    },

    // 관광지 추가 메소드 수정
    addToSchedule() {
      if (this.attractionData) {
        this.selectedAttractions.push(this.attractionData);
        message.success('관광지가 추가되었습니다!');
      }
      this.closeAttractionModal();
    },

    // async finalizeSchedule() {
    //   try {
    //     const response = await axios.post('http://localhost:80/enjoytrip/schedule/with-attractions', {
    //       scheduleId: this.currentScheduleId,
    //       scheduleName: this.scheduleForm.scheduleName,
    //       startDate: this.scheduleForm.startDate,
    //       endDate: this.scheduleForm.endDate,
    //       attractions: this.selectedAttractions.map((attraction, index) => ({
    //         contentId: attraction.contentId,
    //         sequenceOrder: index
    //       }))
    //     });

    //     if (response.data.code === 'SU') {
    //       message.success('일정이 성공적으로 생성되었습니다!');
    //       this.router.push(`/schedule/${response.data.scheduleId}/attractions`);
    //     }
    //   } catch (error) {
    //     console.error('최종 일정 생성 실패:', error);
    //     message.error('일정 생성에 실패했습니다.');
    //   }
    // },

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
        
        if (!response.data.attraction) {
          // 관광지가 없는 경우
          message.warning('해당하는 여행지가 없습니다!');
          this.isStarting = false;
          return;
        }

        // 관광지가 있는 경우 기존 로직 실행
        this.attractionData = response.data.attraction;
        this.showAttractionModal = true;
      } catch (error) {
        console.error("서버 요청 실패:", error);
        message.error('서버 요청에 실패했습니다.');
      } finally {
        this.isStarting = false;
      }
    },

    closeModal() {
      this.showModal = false;
      this.attractionData = null;
    },
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

/* 전체 컨테이너 */
.slot-machine-container {
  display: flex;
  gap: 2rem;
  align-items: flex-start; /* 상단 정렬 */
  position: relative;
}

/* 슬롯 머신 영역 */
.slot-machine {
  flex: 0 0 800px;
  position: relative;
  margin-top: 220px; /* 아이콘 높이만큼 여백 */
}

.slot-machine-header {
  position: absolute;
  bottom: 100%; /* 슬롯 머신의 상단에 딱 맞게 */
  left: 50%;
  transform: translateX(-50%);
  z-index: 2;
}

.slot-machine-icon { 
  width: 480px;
  height: auto;
  object-fit: contain;
  filter: drop-shadow(0 6px 12px rgba(0, 0, 0, 0.25));
}

.frame {
  position: relative;
  z-index: 1;
  display: flex;
  align-items: flex-start;
  background-image: url('../../assets/slot-background.jpg');
  padding: 30px;
  border-radius: 15px;
  box-shadow: 0 0 50px rgba(0, 0, 0, 0.7);
  border: 5px solid #f4d03f;
  min-width: 750px;
}

.slots {
  display: flex;
  flex-direction: row;
  gap: 15px;
  min-width: 600px; /* 슬롯들의 최소 너비 */
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

/* 모달 관련 */
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
  z-index: 9999; /* 더 높은 z-index 값 설정 */
}

.modal-content {
  background: white;
  padding: 24px;
  border-radius: 12px;
  min-width: 400px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  position: relative; /* position 추가 */
  z-index: 10000; /* modal-overlay보다 높은 z-index */
}

.modal-content h2 {
  margin: 0 0 20px 0;
  font-size: 20px;
  font-weight: bold;
}

.modal-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 24px;
}

.modal-buttons button {
  padding: 8px 16px;
  border-radius: 6px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s;
}

.modal-buttons button[type="submit"] {
  background-color: #f4d03f;
  color: white;
  border: none;
}

.modal-buttons button[type="submit"]:hover {
  background-color: #f4d03f;
}

.modal-buttons button[type="button"] {
  background-color: white;
  border: 1px solid #d9d9d9;
  color: #666;
}

.modal-buttons button[type="button"]:hover {
  color: #f4d03f;
  border-color: #f4d03f;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #333;
}

.form-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #d9d9d9;
  border-radius: 6px;
  font-size: 14px;
  transition: border-color 0.3s;
}

.form-group input:focus {
  outline: none;
  border-color: #f4d03f;
  box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
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

.attraction-details {
  flex: 1;
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
  background: #f4d03f;
}

/* 선택된 관광지 목록 영역 */
.selected-attractions {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(3px);
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  min-width: 400px;
  max-width: 500px;
  margin-top: 0; /* 아이콘과 같은 높이에서 시작 */
  height: calc(100% + 220px); /* 슬롯 머신 높이 + 아이콘 높이 */
}

.selected-attractions h3 {
  margin: 0 0 1rem 0;
  font-size: 1.25rem;
  font-weight: bold;
}


/* 관광지 목록 스크롤 영역 
   전체 높이(800px)에서 다음을 제외:
   - 상하 패딩(2rem * 2 = 64px)
   - 제목 영역(약 40px)
   - 하단 버튼(약 56px)
   - 여유 공간(20px)
*/
/* 관광지 목록 스크롤 영역 */
.attractions-list {
  height: 520px; /* 700px - (패딩 + 제목 + 버튼 높이) */
  overflow-y: auto;
  margin-bottom: 1rem;
  border: 1px solid #eee;
  border-radius: 6px;
  padding: 0.5rem;
}

/* 스크롤바 스타일링 */
.attractions-list::-webkit-scrollbar {
  width: 8px;
}

.attractions-list::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 4px;
}

.attractions-list::-webkit-scrollbar-thumb {
  background: #8b5cf6;  /* 보라색 스크롤바 */
  border-radius: 4px;
}

.attractions-list::-webkit-scrollbar-thumb:hover {
  background: #7c3aed;  /* 호버 시 더 진한 보라색 */
}

.attraction-item {
  display: flex;
  gap: 1rem;
  padding: 1rem;
  border-bottom: 1px solid #eee;
  background: white;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 4px;
}


.attraction-item img {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 4px;
}

.attraction-item:last-child {
  border-bottom: none;
}

.attraction-item img {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 4px;
}

.welcome-modal {
  text-align: center;
  padding: 2rem;
}

.primary-button {
  background: #f4d03f;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
}



.create-schedule-button {
  padding: 1rem;
  background: #7c3aed;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: auto; /* 버튼을 항상 하단에 고정 */
}

.create-schedule-button:disabled {
  background: #ccc;
  cursor: not-allowed;
}

</style>