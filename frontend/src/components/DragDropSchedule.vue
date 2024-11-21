<template>
  <div class="place-selector">
    <div class="place-list-container">
      <h2>우리와 함깨 여행하자</h2>
      <input type="text" placeholder="장소명을 입력하세요" class="search-bar" />
      <div>


      </div>
      <div class="place-list">
        <div
            class="place-card"
            v-for="(place, index) in places"
            :key="index"
            draggable="true"
            @dragstart="onDragStart(place, $event)"
        >
          <img :src="place.image" alt="place" class="place-image" />
          <div class="place-info">
            <h3>{{ place.name }}</h3>
            <p>{{ place.address }}</p>
            <div class="place-meta">
              <button class="save-button">❤️ 좋아요</button>
            </div>
          </div>
        </div>
      </div>
    </div>

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
          <img :src="place.image" alt="place" class="place-image" />
          <div class="place-info">
            <h3>{{ place.name }}</h3>
            <p>{{ place.address }}</p>
          </div>
          <!-- 삭제 버튼 -->
          <button class="delete-button" @click="removePlace(index)">X</button>
        </div>
        <div v-if="selectedPlaces.length === 0" class="empty-slot">
          드래그하여 장소를 추가하세요
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";

export default {
  name: "PlaceSelector",
  setup() {
    const places = reactive([
      {
        name: "도쿄 타워",
        address: "4 Chome-2-8 Shibakoen, Minato City, Tokyo",
        image: "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/17/15/6d/d6/paris.jpg?w=500&h=400&s=1",
        likes: 4195,
        rating: 4.4,
      },
      {
        name: "시부야 스카이",
        address: "T 150-6145 Tokyo, Shibuya City",
        image: "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/17/15/6d/d6/paris.jpg?w=500&h=400&s=1",
        likes: 4047,
        rating: 4.6,
      },
      {
        name: "아키하바라",
        address: "Akihabara, Taito City, Tokyo",
        image:"https://dynamic-media-cdn.tripadvisor.com/media/photo-o/17/15/6d/d6/paris.jpg?w=500&h=400&s=1",
        likes: 3457,
        rating: 4.3,
      },
      {
        name: "센소지",
        address: "2 Chome-3-1 Asakusa, Taito City",
        image: "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/17/15/6d/d6/paris.jpg?w=500&h=400&s=1",
        likes: 3350,
        rating: 4.5,
      }
    ]);

    const selectedPlaces = reactive([]);

    const onDragStart = (place, event) => {
      event.dataTransfer.setData("place", JSON.stringify(place));
    };

    const onDrop = (event) => {
      const place = JSON.parse(event.dataTransfer.getData("place"));
      selectedPlaces.push(place);
    };

    const removePlace = (index) => {
      selectedPlaces.splice(index, 1); // 선택된 장소 배열에서 삭제
    };

    return {
      places,
      selectedPlaces,
      onDragStart,
      onDrop,
      removePlace,
    };
  },
};
</script>

<style scoped>
.place-selector {
  display: flex;
  gap: 30px;
  padding: 20px;
  height: 80vh; /* Ensure full viewport height */
  box-sizing: border-box;
}

.place-list-container,
.selected-places-container {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.place-list-container {
  height: 100%; /* Ensure it takes the full column height */
  display: flex;
  flex-direction: column;
  overflow: hidden; /* Prevent accidental overflow */
}

.place-list {
  flex: 1;
  overflow-y: auto; /* Enable vertical scrolling */
  padding-right: 10px; /* Prevent cut-off scroll bar */
}

.search-bar {
  margin-bottom: 10px;
  padding: 10px;
  font-size: 16px;
  width: 100%;
}

.place-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.place-card {
  display: flex;
  gap: 10px;
  align-items: center;
  padding: 10px;
  width: 100%;
  height: 100%; /* 카드의 고정 높이 */
  border: 1px solid #d9d9d9;
  border-radius: 8px;
  background-color: #ffffff;
  cursor: grab;
  box-sizing: border-box; /* 패딩 포함 크기 계산 */
}

.place-image {
  width: 100px; /* 이미지 고정 너비 */
  height: 100px; /* 이미지 고정 높이 */
  border-radius: 8px;
  object-fit: cover; /* 이미지 비율을 유지하며 크기에 맞게 조정 */
}

.place-info {
  flex: 1;
}

.place-meta {
  font-size: 12px;
  color: #888;
}

.selected-places {
  min-height: 300px;
  border: 1px dashed #d9d9d9;
  border-radius: 8px;
  padding: 10px;
  background-color: #f9f9f9;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  overflow-y: auto; /* Allow scrolling for many selected cards */
}

.selected-place-card {
  display: flex;
  gap: 10px;
  align-items: center;
  width: 300px; /* 선택된 카드 고정 너비 */
  height: 120px; /* 선택된 카드 고정 높이 */
  padding: 10px;
  border: 1px solid #d9d9d9;
  border-radius: 8px;
  background-color: #ffffff;
  box-sizing: border-box; /* 패딩 포함 크기 계산 */
}
.empty-slot {
  flex: 1;
  text-align: center;
  color: #888;
  font-size: 14px;
}

.save-button {
  padding: 8px 12px;
  border: none;
  border-radius: 20px;
}

.place-selector {
  font-family: 'NEXON Lv1 Gothic OTF', sans-serif;
  font-size: 16px;
  color: #333;
}

h2 {
  font-family: 'NEXON Lv1 Gothic OTF', sans-serif;
  font-size: 24px;
  font-weight: bold;
}

.search-bar {
  font-family: 'NEXON Lv1 Gothic OTF', sans-serif;
  font-size: 14px;
}

.place-card h3 {
  font-family: 'NEXON Lv1 Gothic OTF', sans-serif;
  font-size: 18px;
}

.place-card p {
  font-size: 14px;
  color: #666;
}

.selected-place-card h3 {
  font-family: 'NEXON Lv1 Gothic OTF', sans-serif;
  font-size: 18px;
}

.selected-place-card p {
  font-size: 14px;
  color: #444;
}

.selected-place-card .place-image {
  width: 100px; /* 이미지 고정 너비 */
  height: 100px; /* 이미지 고정 높이 */
  border-radius: 8px;
  object-fit: cover; /* 이미지 비율 유지 */
}

.delete-button {
  background-color: #ff4d4f;
  color: white;
  border: none;
  border-radius: 50%;
  width: 30px;
  height: 30px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  align-self: center;
  margin-left: auto;
}

.delete-button:hover {
  background-color: #ff7875;
}


</style>
