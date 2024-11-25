<template>
    <div class="place-selector">
      <div class="place-list-container">
        <h2>관광지 검색</h2>

        <!-- 검색 필터 -->
        <div class="filters">
          <!-- 시/도 선택 -->
          <select v-model="selectedSido" @change="fetchGugunOptions">
            <option disabled value="">시/도를 선택하세요</option>
            <option v-for="sido in sidos" :key="sido.code" :value="sido.code">
              {{ sido.name }}
            </option>
          </select>

          <!-- 군/구 선택 -->
          <select v-model="selectedGugun" :disabled="!selectedSido">
            <option disabled value="">군/구를 선택하세요</option>
            <option v-for="gugun in guguns" :key="gugun.code" :value="gugun.code">
              {{ gugun.name }}
            </option>
          </select>

          <select v-model="selectedContentType">
            <option value="0">전체</option>
            <option v-for="type in contentTypes" :key="type.id" :value="type.id">
              {{ type.name }}
            </option>
          </select>


          <!-- 검색 버튼 -->
          <button @click="searchAttractions" :disabled="!isSearchEnabled">
            검색
          </button>
        </div>

        <!-- 기존 예시 카드 및 검색 결과 -->
        <div class="place-list">
          <div
              class="place-card"
              v-for="(place, index) in combinedPlaces"
              :key="index"
              draggable="true"
              @dragstart="onDragStart(place, $event)"
          >
            <img :src="place.firstImage1 || 'https://via.placeholder.com/100x100?text=No+Image'" alt="place" class="place-image" />
            <div class="place-info">
              <h3>{{ place.title }}</h3>
              <p>{{ place.addr1 }}</p>
              <div class="place-meta">
                <button class="save-button">❤️ 좋아요</button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 선택된 장소 -->
      <div class="selected-places-container">
        <h2>선택된 장소</h2>
        <div
            class="selected-places"
            @dragover.prevent
            @drop="onDrop"
        >
          <div
              v-for="(place, index) in selectedPlaces"
              :key="index"
              class="selected-place-card"
          >
            <img :src="place.firstImage1 || 'https://via.placeholder.com/100x100?text=No+Image'" alt="place" class="place-image" />
            <div class="place-info">
              <h3>{{ place.title }}</h3>
              <p>{{ place.addr1 }}</p>
            </div>
            <!-- 삭제 버튼 -->
            <button class="delete-button" @click="removePlace(index)">X</button>
          </div>
          <div v-if="selectedPlaces.length === 0" class="empty-slot">
            드래그하여 장소를 추가하세요
          </div>
        </div>

        <div class="bottom-controls">
          <button 
            class="create-schedule-btn" 
            @click="showCreateScheduleModal"
            :disabled="selectedPlaces.length === 0"
          >
            일정 생성
          </button>
        </div>
      </div>

      <!-- 오른쪽: 지도 영역 -->
      <div class="right-section">
        <KakaoMap :locations="mapLocations" />
      </div>


      <!-- 일정 생성 모달 -->
    <div v-if="showModal" class="modal-overlay" @click="closeModal">
        <div class="modal-content" @click.stop>
          <h2>새 일정 만들기</h2>
          <form @submit.prevent="createSchedule">
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
              <button type="button" @click="closeModal">취소</button>
              <button type="submit">완료</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </template>

  <script>
  import axios from "axios";
  import { reactive, ref, computed, watch, onMounted } from "vue";
  import {message} from "ant-design-vue";
  import apiClient from '@/api/apiClient'
  import { useRouter } from 'vue-router';
  import KakaoMap from "./KakaoMap.vue";

  export default {
    name: "DragDropSchedule",
    components: {
      KakaoMap
    },
    setup(props, { emit }) {
    const sidos = reactive([]);
    const guguns = reactive([]);
    const selectedSido = ref("");
    const selectedGugun = ref("");
    const selectedContentType = ref("0");
    const places = reactive([]);
    const searchResults = reactive([]);
    const selectedPlaces = reactive([]);
    const showModal = ref(false);
    const mapLocations = ref([]); // 추가된 부분
    const scheduleForm = reactive({
      scheduleName: '',
      startDate: '',
      endDate: ''
    });

    const router = useRouter();

    const isSearchEnabled = computed(() => selectedSido.value && selectedGugun.value);

    const fetchSidoOptions = () => {
        const url = "https://apis.data.go.kr/B551011/KorService1/areaCode1";
        const params = {
          serviceKey: "Mub0bm4epPcrhuQFwy%2F1s31j04EOcTy1JGaOAQM1hmRrFOpjrjsm4vdNu5wCojATTsagW%2FlLpZsE3zxyu9YJ4Q%3D%3D",
          numOfRows: 20,
          pageNo: 1,
          MobileOS: "ETC",
          MobileApp: "AppTest",
          _type: "json",
        };

        console.log("fetchSidoOptions 호출됨");

        axios
            .get(url, {
              params,
              paramsSerializer: (params) => {
                return Object.keys(params)
                    .map((key) => `${key}=${params[key]}`)
                    .join("&");
              },
            })
            .then((response) => {
              console.log("API 응답 성공:", response.data);
              sidos.push(...(response.data.response?.body?.items?.item || []));
            })
            .catch((error) => {
              console.error("시/도 데이터 로드 실패:", error);
            });
      };

      const fetchGugunOptions = () => {
        if (!selectedSido.value) {
          console.log("selectedSido가 비어 있음");
          return;
        }

        const url = "https://apis.data.go.kr/B551011/KorService1/areaCode1";
        const params = {
          serviceKey: "Mub0bm4epPcrhuQFwy%2F1s31j04EOcTy1JGaOAQM1hmRrFOpjrjsm4vdNu5wCojATTsagW%2FlLpZsE3zxyu9YJ4Q%3D%3D",
          numOfRows: 20,
          pageNo: 1,
          MobileOS: "ETC",
          MobileApp: "AppTest",
          _type: "json",
          areaCode: selectedSido.value,
        };

        console.log("fetchGugunOptions 호출됨");

        axios
            .get(url, {
              params,
              paramsSerializer: (params) => {
                return Object.keys(params)
                    .map((key) => `${key}=${params[key]}`)
                    .join("&");
              },
            })
            .then((response) => {
              console.log("군/구 데이터 요청 성공:", response.data); // 응답 확인
              guguns.splice(0, guguns.length, ...(response.data.response?.body?.items?.item || []));
            })
            .catch((error) => {
              console.error("군/구 데이터 로드 실패:", error);
            });
      };

      const searchAttractions = () => {
        if (!isSearchEnabled.value) {
          message.warn("시/도와 군/구를 선택해주세요.");
          console.log("검색 조건이 부족합니다: 시/도와 군/구를 선택하지 않음");
          return;
        }

        const url = "http://localhost:80/enjoytrip/trip/search";
        const params = {
          sido: selectedSido.value,
          gugun: selectedGugun.value,
          content: selectedContentType.value,
        };

        console.log("검색 요청 데이터:", params);

        axios
          .get(url, { params })
          .then((response) => {
            console.log("검색 성공:", response.data);
            searchResults.splice(0, searchResults.length, ...(response.data.attractions || []));
            
           // mapLocations 업데이트
            mapLocations.value = response.data.attractions.map(attraction => ({
              latitude: attraction.latitude,
              longitude: attraction.longitude,
              title: attraction.title,
              addr1: attraction.addr1,
              contentId: attraction.contentId
            }));
            
            console.log("지도 위치 업데이트:", mapLocations.value);
          })
          .catch((error) => {
            console.error("관광지 검색 실패:", error);
            message.error("관광지 검색에 실패했습니다.");
            mapLocations.value = [];
          });
      };


      const showCreateScheduleModal = () => {
        showModal.value = true;
      };

      const closeModal = () => {
        showModal.value = false;
        Object.assign(scheduleForm, {
          scheduleName: '',
          startDate: '',
          endDate: ''
        });
      };

      const createSchedule = async () => {
        try {
          // 선택된 관광지 리스트를 일정 생성 요청에 포함
          const payload = {
            scheduleName: scheduleForm.scheduleName,
            startDate: scheduleForm.startDate,
            endDate: scheduleForm.endDate,
            attractions: selectedPlaces.map((place, index) => ({
              no: place.no,
              sequenceOrder: index,
            })),
          };

          // 일정과 관광지 리스트를 함께 전송
          const response = await apiClient.post('http://localhost:80/enjoytrip/schedule/with-attractions', payload);

          if (response.data.code === 'SU') {
            const scheduleId = response.data.scheduleId;  // 수정된 부분: 최상위 레벨에서 scheduleId 접근
            console.log('생성된 일정 ID:', scheduleId);
            
            message.success('일정이 생성되었습니다!');
            selectedPlaces.splice(0, selectedPlaces.length);
            closeModal();

            // 리다이렉트
            router.push(`/schedule/${scheduleId}/attractions`);
          } else {
            throw new Error(response.data.message || '일정 생성에 실패했습니다.');
          }
        } catch (error) {
          console.error('일정 생성 실패:', error);
          message.error('일정 생성에 실패했습니다.');
        }
      };

      const onDragStart = (place, event) => {
        event.dataTransfer.setData("place", JSON.stringify(place));
      };

      const onDrop = (event) => {
        const place = JSON.parse(event.dataTransfer.getData("place"));
        selectedPlaces.push(place);
      };

      const removePlace = (index) => {
        selectedPlaces.splice(index, 1);
      };

      const combinedPlaces = computed(() => [...places, ...searchResults]);

      watch(selectedSido, (newValue) => {
        console.log("selectedSido 변경:", newValue);
        fetchGugunOptions();
      });

      fetchSidoOptions();

      return {
        sidos,
        guguns,
        places,
        searchResults,
        combinedPlaces,
        selectedPlaces,
        selectedSido,
        selectedGugun,
        isSearchEnabled,
        showModal,
        scheduleForm,
        fetchSidoOptions,
        fetchGugunOptions,
        searchAttractions,
        onDragStart,
        onDrop,
        removePlace,
        showCreateScheduleModal,
        closeModal,
        createSchedule,
        mapLocations, // 추가된 부분
        contentTypes: [
          { id: 12, name: "관광지" },
          { id: 14, name: "문화시설" },
          { id: 15, name: "축제/공연/행사" },
          { id: 28, name: "레포츠" },
          { id: 32, name: "숙박" },
          { id: 38, name: "쇼핑" },
          { id: 39, name: "음식점" },
        ],
        selectedContentType: "0",
      };
    },
  };
  </script>


<style scoped>
.place-selector {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: 20px;
  padding: 20px;
  height: calc(100vh - 150px);
  box-sizing: border-box;
}

.place-list-container,
.selected-places-container,
.right-section {
  background: white;
  border-radius: 12px;
  padding: 20px;
  height: 100%;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.filters {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 10px;
  margin-bottom: 20px;
  width: 100%;
}

.filters select,
.filters button {
  width: 100%;
  height: 40px;
  padding: 8px;
  font-size: 14px;
  border: 1px solid #d9d9d9;
  border-radius: 6px;
  background-color: white;
}

.filters button {
  background-color: #f4d03f;
  color: white;
  border: none;
  cursor: pointer;
  transition: background-color 0.2s;
}

.filters button:disabled {
  background-color: #d9d9d9;
  cursor: not-allowed;
}

.place-list {
  flex: 1;
  overflow-y: auto;
  padding-right: 10px;
}

.selected-places-container {
  display: flex;
  flex-direction: column;
}

.selected-places-container h2 {
  margin-bottom: 10px;
}

.create-schedule-btn {
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  background-color: #f4d03f;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.2s;
}

.create-schedule-btn:disabled {
  background-color: #d9d9d9;
  cursor: not-allowed;
}

.selected-places {
  flex: 1;
  width: 100%;
  border: 1px dashed #d9d9d9;
  border-radius: 8px;
  padding: 10px;
  background-color: #f9f9f9;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.place-card {
  display: flex;
  gap: 10px;
  align-items: center;
  padding: 12px;
  border: 1px solid #e8e8e8;
  border-radius: 8px;
  background-color: white;
  cursor: grab;
  margin-bottom: 10px;
  transition: transform 0.2s, box-shadow 0.2s;
}

.place-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.selected-place-card {
  display: flex;
  gap: 10px;
  align-items: center;
  padding: 12px;
  border: 1px solid #e8e8e8;
  border-radius: 8px;
  background-color: white;
  width: calc(100% - 24px);
}

.place-image {
  width: 80px;
  height: 80px;
  border-radius: 8px;
  object-fit: cover;
}

.place-info {
  flex: 1;
  min-width: 0;
}

.place-info h3 {
  margin: 0 0 5px;
  font-size: 14px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.place-info p {
  margin: 0;
  font-size: 12px;
  color: #666;
}


/* 스크롤바 스타일링 */
.place-list::-webkit-scrollbar,
.selected-places::-webkit-scrollbar {
  width: 6px;
}

.place-list::-webkit-scrollbar-thumb,
.selected-places::-webkit-scrollbar-thumb {
  background-color: #d9d9d9;
  border-radius: 3px;
}

.place-list::-webkit-scrollbar-track,
.selected-places::-webkit-scrollbar-track {
  background-color: #f5f5f5;
}

.delete-button {
  background-color: 1890ff;
}

/* 반응형 디자인 */
@media (max-width: 1200px) {
  .place-selector {
    grid-template-columns: 1fr;
    height: auto;
  }

  .right-section {
    height: 400px;
  }
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
</style>