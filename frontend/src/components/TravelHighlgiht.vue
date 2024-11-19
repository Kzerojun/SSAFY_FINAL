<template>
  <hgroup class="sectionTitleWrap">
    <h2 class="sectionTitle">우리와 함께 가요 강원랜트</h2>
    <p class="sectionSubTitle">랜덤 여행뿐 아니라 가장 유명한 여행지까지!!</p>
  </hgroup>

  <div class="travel-container">
    <!-- 상단 탭 메뉴 -->
    <div class="tab-menu">
      <button
          v-for="(tab, index) in tabs"
          :key="index"
          :class="['tab', { active: activeTab === index }]"
          @click="activeTab = index"
      >
        {{ tab }}
      </button>
    </div>

    <!-- 여행 카드 리스트 -->
    <div class="travel-card-list">
      <div
          class="travel-card"
          v-for="(travel, index) in filteredTravels"
          :key="index"
      >
        <img :src="travel.image" :alt="travel.name" class="card-image" />
        <div class="card-content">
          <h3 class="card-title">{{ travel.name }}</h3>
          <p class="card-description">{{ travel.description }}</p>

          <!-- 옵션 버튼 그룹 -->
          <div class="travel-options">
            <button class="option-btn">📖 역사 투어</button>
            <button class="option-btn">🍣 음식 투어</button>
            <button class="option-btn">🎎 전통 체험 투어</button>
            <button class="option-btn">🛒 쇼핑 투어</button>
          </div>

          <!-- 저장 및 여행 스토리 -->
          <div class="save-section">
            <button class="save-button">❤ 저장</button>
            <a href="#" class="travel-story">여행 스토리</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "TravelCategories",
  data() {
    return {
      tabs: ["뜨는 여행지", "인기 여행지", "자연 여행지", "맛집 여행지", "문화 여행지"],
      activeTab: 0,
      travels: [
        {
          name: "서울",
          description: "고대와 현대가 공존하는 도시 도쿄를 경험해보세요.",
          image: "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/03/9b/2f/89/hanoi.jpg?w=500&h=400&s=1",
          category: "뜨는 여행지",
        },
        {
          name: "도쿄",
          description: "고대와 현대가 공존하는 도시 도쿄를 경험해보세요.",
          image: "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/03/9b/2f/89/hanoi.jpg?w=500&h=400&s=1",
          category: "뜨는 여행지",
        },
        {
          name: "파리",
          description: "빛의 도시 파리에서 예술과 로맨스를 느껴보세요.",
          image: "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/03/9b/2f/89/hanoi.jpg?w=500&h=400&s=1",
          category: "인기 여행지",
        },
        {
          name: "알프스",
          description: "대자연의 웅장함을 느낄 수 있는 알프스.",
          image: "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/03/9b/2f/89/hanoi.jpg?w=500&h=400&s=1",
          category: "자연 여행지",
        },
        {
          name: "오사카",
          description: "일본 최고의 음식 여행지, 오사카.",
          image: "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/03/9b/2f/89/hanoi.jpg?w=500&h=400&s=1",
          category: "맛집 여행지",
        },
        {
          name: "로마",
          description: "역사가 살아 숨 쉬는 로마를 방문하세요.",
          image: "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/03/9b/2f/89/hanoi.jpg?w=500&h=400&s=1",
          category: "문화 여행지",
        },
      ],
    };
  },
  computed: {
    filteredTravels() {
      return this.travels.filter(
          (travel) => travel.category === this.tabs[this.activeTab]
      );
    },
  },
};
</script>

<style scoped>
/* 전체 컨테이너 */
.travel-container {
  padding: 20px;
  font-family: "Arial", sans-serif;
}

/* 탭 메뉴 */
.tab-menu {
  display: flex;
  gap: 16px;
  margin-bottom: 20px;
}

.tab {
  padding: 10px 20px;
  border: none;
  background-color: transparent;
  font-size: 16px;
  cursor: pointer;
  border-bottom: 2px solid transparent;
  transition: all 0.3s ease;
}

.tab.active {
  border-bottom: 2px solid #000;
  font-weight: bold;
}

.tab:hover {
  color: #007bff;
}

/* 여행 카드 리스트 */
.travel-card-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(500px, 1fr));
  gap: 20px;
}

/* 여행 카드 */
.travel-card {
  display: flex;
  flex-direction: column;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.card-image {
  width: 100%;
  height: 250px;
  object-fit: cover;
}

.card-content {
  padding: 16px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.card-title {
  font-size: 20px;
  font-weight: bold;
}

.card-description {
  font-size: 14px;
  color: #555;
  line-height: 1.5;
}

/* 옵션 버튼 그룹 */
.travel-options {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.option-btn {
  padding: 8px 12px;
  background-color: #f0f0f0;
  border: 1px solid #ddd;
  border-radius: 20px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.option-btn:hover {
  background-color: #e0e0e0;
}

/* 저장 및 여행 스토리 */
.save-section {
  display: flex;
  align-items: center;
  gap: 12px;
}

.save-button {
  padding: 8px 12px;
  background-color: #ffcc00;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
}

.save-button:hover {
  background-color: #ffa500;
}

.travel-story {
  font-size: 14px;
  color: #007bff;
  text-decoration: none;
}

.travel-story:hover {
  text-decoration: underline;
}

/* 상단 문구 스타일 */
.sectionTitleWrap {
  text-align: center;
  margin-bottom: 20px;
}

.sectionTitle{
  font-family: 'LINESeedKR-Bd', sans-serif;
}

.sectionSubTitle{
  font-family : 'NEXON Lv1 Gothic OTF', sans-serif;
}


.brand-name {
  font-size: 30px;
  transition: color 0.3s ease;
  font-family: 'NEXON Lv1 Gothic OTF', sans-serif;
}

</style>
