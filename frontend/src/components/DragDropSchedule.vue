  <template>
    <div class="main-container">
      <!-- 왼쪽: 검색 및 드래그 드롭 영역 -->
      <div class="left-section">
        <div class="place-list-container">
          <h2>우리와 함께 여행하자</h2>
          
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

          <!-- 장소 목록 -->
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
      </div>

      <!-- 오른쪽: 지도 영역 -->
      <div class="right-section">
        <KakaoMap :locations="mapLocations" />
      </div>

      <!-- 모달은 그대로 유지 -->
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
.main-container {
  display: flex;
  gap: 20px;
  padding: 20px;
  height: 100vh;
  box-sizing: border-box;
}

.left-section {
  flex: 0 0 400px; /* 고정 너비 */
  display: flex;
  flex-direction: column;
  gap: 20px;
  height: 100%;
  overflow-y: auto;
}

.right-section {
  flex: 1; /* 남은 공간 모두 차지 */
  height: 100%;
}

.place-list-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 0; /* 스크롤을 위해 필요 */
}

.selected-places-container {
  flex: 0 0 auto;
  padding: 15px;
  background-color: #f8f9fa;
  border-radius: 8px;
}

.place-list {
  flex: 1;
  overflow-y: auto;
  padding-right: 10px;
}

.selected-places {
  min-height: 300px;
  border: 2px dashed #d9d9d9;
  border-radius: 8px;
  padding: 15px;
  background-color: #ffffff;
  display: flex;
  flex-direction: column;
  gap: 10px;
  overflow-y: auto;
}

/* 기존 스타일 중에서 수정이 필요한 부분 */
/* 기존 스타일에 추가/수정 */
.place-card {
  display: flex;
  gap: 10px;
  align-items: center;
  padding: 10px;
  border: 1px solid #d9d9d9;
  border-radius: 8px;
  background-color: #ffffff;
  cursor: grab;
  margin-bottom: 10px;
  width: 100%; /* 컨테이너에 맞추기 */
}

.place-card .place-image {
  width: 100px;  /* 이미지 크기 고정 */
  height: 100px;
  border-radius: 8px;
  object-fit: cover;
  flex-shrink: 0; /* 이미지 크기 고정 유지 */
}

.place-card .place-info {
  flex: 1;
  min-width: 0; /* 텍스트 오버플로우 방지 */
}

.place-card .place-info h3 {
  margin: 0 0 5px 0;
  font-size: 16px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.place-card .place-info p {
  margin: 0;
  font-size: 14px;
  color: #666;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.selected-place-card {
  display: flex;
  gap: 10px;
  align-items: center;
  padding: 10px;
  border: 1px solid #d9d9d9;
  border-radius: 8px;
  background-color: #ffffff;
  width: 100%;
  margin-bottom: 10px;
}

.selected-place-card .place-image {
  width: 100px;
  height: 100px;
  border-radius: 8px;
  object-fit: cover;
  flex-shrink: 0;
}

.selected-place-card .place-info {
  flex: 1;
  min-width: 0;
}

.filters {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
  flex-wrap: wrap; /* 필요시 줄바꿈 */
}

.filters select,
.filters button {
  flex: 1;
  min-width: 80px;
  max-width: 120px;
}

/* 나머지 기존 스타일은 유지 */
.modal-overlay {
  /* 기존 스타일 유지 */
}

/* 추가적인 반응형 스타일 */
@media (max-width: 1200px) {
  .main-container {
    flex-direction: column;
  }

  .left-section {
    flex: 0 0 auto;
    width: 100%;
  }

  .right-section {
    height: 600px;
  }
}
</style>
