<template>
  <div class="profile-container">
    <!-- Navigation Tabs -->
    <div class="tabs-container">
      <div class="tabs">
        <button
            v-for="tab in tabs"
            :key="tab.id"
            :class="['tab', { active: activeTab === tab.id }]"
            @click="activeTab = tab.id"
        >
          {{ tab.name }}
        </button>
      </div>
    </div>

    <!-- Main Content -->
    <div class="content" v-if="activeTab === 'profile'">
      <h2 class="title">프로필 수정</h2>
      <p class="subtitle">프로필 정보를 수정하실 수 있습니다.</p>

      <!-- Profile Section -->
      <div class="profile-section">
        <div class="profile-header">프로필 사진</div>
        <div class="profile-content">
          <div class="profile-image-container">
            <div class="profile-image" @click="triggerFileInput">
              <img :src="previewImage || profileImage || defaultProfileImage" alt="프로필 이미지" />
              <div class="image-overlay">
                <span>클릭하여 이미지 변경</span>
              </div>
            </div>

            <!-- 숨겨진 파일 입력 필드 -->
            <input
                type="file"
                ref="fileInput"
                @change="handleImageChange"
                accept="image/*"
                class="hidden-input"
            />

          </div>
          <div class="profile-buttons">
            <button class="btn" @click="triggerFileInput">사진변경</button>
            <button class="btn" @click="removeImage" v-if="previewImage">삭제</button>
          </div>
          <div class="image-info" v-if="imageError">
            {{ imageError }}
          </div>
        </div>
      </div>


      <!-- Action Buttons -->
      <div class="action-buttons">
        <button
            class="btn primary"
            @click="saveProfile"
            :disabled="isLoading"
        >
          {{ isLoading ? '저장 중...' : '적용' }}
        </button>
        <button class="btn" @click="cancelChanges">취소</button>
      </div>
    </div>

    <!-- Favorite Places Content -->
    <div class="content" v-if="activeTab === 'favorites'">
      <h2 class="title">좋아요한 여행지</h2>
      <div class="favorites-grid">
        <div v-for="place in favoritePlaces" :key="place.id" class="place-card">
          <img :src="place.image" :alt="place.name" class="place-image" />
          <div class="place-info">
            <h3>{{ place.name }}</h3>
            <p>{{ place.description }}</p>
            <div class="place-meta">
              <span>👍 {{ place.likes }}</span>
              <span>📍 {{ place.location }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Loading Overlay -->
    <div class="loading-overlay" v-if="isLoading">
      <div class="loading-spinner"></div>
    </div>
  </div>
</template>

<script>
import {message} from "ant-design-vue";
import apiClient from "@/api/apiClient.js";

export default {
  name: 'TravelProfile',
  data() {
    return {
      tabs: [
        { id: 'profile', name: '내프로필' },
        { id: 'favorites', name: '좋아요한 여행지' },
      ],
      activeTab: 'profile',
      profileImage: '',
      defaultProfileImage:
          'data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxMDAiIGhlaWdodD0iMTAwIiB2aWV3Qm94PSIwIDAgMTAwIDEwMCI+PGNpcmNsZSBjeD0iNTAiIGN5PSI1MCIgcj0iNTAiIGZpbGw9IiNFNkU2RTYiLz48cGF0aCBkPSJNNTAgMjVhMjUgMjUgMCAxIDAgMCA1MCAyNSAyNSAwIDAgMCAwLTUweiIgZmlsbD0iI0ZGRiIvPjwvc3ZnPg==',
      previewImage: '',
      nickname: '',
      imageError: '',
      isLoading: false,
      favoritePlaces: [
        {
          id: 1,
          name: '제주도 성산일출봉',
          description: '유네스코 세계자연유산, 아름다운 일출 명소',
          image: 'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/03/9b/2f/89/hanoi.jpg?w=500&h=400&s=1',
          likes: 1234,
          location: '제주특별자치도',
        },
        {
          id: 2,
          name: '경주 불국사',
          description: '신라시대 불교 문화의 정수를 담은 사찰',
          image: '/api/placeholder/300/200',
          likes: 890,
          location: '경상북도',
        },
      ],
    };
  },
  methods: {
    triggerFileInput() {
      this.$refs.fileInput.click();
    },
    async handleImageChange(event) {
      const file = event.target.files[0];
      if (!file) return;

      const formData = new FormData();
      formData.append('file', file);

      try {
        const response = await apiClient.post("/file/upload",formData);

        if (!response.ok) {
          message.error('이미지 업로드 실패');
        }

        const data = await response.json();
        this.previewImage = data.filePath; // 업로드된 이미지 경로
        this.imageError = '';
      } catch (error) {
        console.error(error);
        this.imageError = '이미지를 업로드하는 중 문제가 발생했습니다.';
      }
    },
    removeImage() {
      this.previewImage = '';
      this.$refs.fileInput.value = '';
    },
    async saveProfile() {
      try {
        this.isLoading = true;

        const payload = {
          email: this.userEmail, // 사용자의 이메일
          profileImagePath: this.previewImage, // 업로드된 이미지 경로
        };

        const response = await apiClient('/user/profile', {
          method: 'PATCH',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(payload),
        });

        if (!response.ok) {
          throw new Error('프로필 저장 실패');
        }

        alert('프로필이 성공적으로 저장되었습니다.');
      } catch (error) {
        console.error(error);
        alert('프로필 저장 중 문제가 발생했습니다.');
      } finally {
        this.isLoading = false;
      }
    },
    cancelChanges() {
      this.previewImage = '';
      this.nickname = '';
      this.$refs.fileInput.value = '';
    },
  },
};
</script>

<style scoped>
.profile-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  min-height: 100vh;
  padding: 40px;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
}

.tabs-container {
  border-bottom: 1px solid #e5e5e5;
  margin-bottom: 40px;
  width: 100%;
}

.tabs {
  display: flex;
  gap: 40px;
  max-width: 1200px;
  margin: 0 auto;
}

.tab {
  padding: 15px 30px;
  border: none;
  background: none;
  cursor: pointer;
  color: #666;
  font-size: 16px;
  position: relative;
  transition: color 0.3s;
}

.tab.active {
  color: #ff6b6b;
  font-weight: bold;
}

.tab.active::after {
  content: '';
  position: absolute;
  bottom: -2px;
  left: 0;
  width: 100%;
  height: 2px;
  background-color: #ff6b6b;
}

.content {
  max-width: 1000px;
  margin: 0 auto;
}

.title {
  font-size: 28px;
  margin-bottom: 20px;
  color: #333;
}

.subtitle {
  color: #666;
  margin-bottom: 40px;
  font-size: 16px;
}

.profile-section, .nickname-section {
  display: grid;
  grid-template-columns: 200px 1fr;
  margin-bottom: 40px;
  gap: 20px;
}

.profile-header, .nickname-header {
  font-weight: bold;
  font-size: 18px;
  padding: 10px 0;
}

.profile-image {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  overflow: hidden;
  background: #e6e6e6;
  margin-bottom: 20px;
}

.profile-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.profile-buttons {
  display: flex;
  gap: 15px;
}

.btn {
  padding: 10px 20px;
  border: 1px solid #ddd;
  border-radius: 6px;
  background: white;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s;
}

.btn:hover {
  background: #f5f5f5;
}

.btn.primary {
  background: #ff6b6b;
  color: white;
  border: none;
}

.hidden-input {
  display: none; /* 파일 입력 필드를 완전히 숨김 */
}

.btn.primary:hover {
  background: #ff5252;
}

.nickname-input {
  width: 100%;
  max-width: 400px;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 16px;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 60px;
}

/* 좋아요한 여행지 스타일 */
.favorites-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 30px;
  margin-top: 30px;
}

.place-card {
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
}

.place-card:hover {
  transform: translateY(-5px);
}

.place-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.place-info {
  padding: 20px;
}

.place-info h3 {
  margin: 0 0 10px 0;
  font-size: 18px;
  color: #333;
}

/* 버튼 비활성화 스타일 */
.btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.place-info p {
  color: #666;
  margin: 0 0 15px 0;
  font-size: 14px;
}

.place-meta {
  display: flex;
  justify-content: space-between;
  color: #888;
  font-size: 14px;
}

@media (max-width: 768px) {
  .profile-container {
    padding: 20px;
  }

  .profile-section, .nickname-section {
    grid-template-columns: 1fr;
  }

  .tabs {
    gap: 20px;
  }

  .tab {
    padding: 10px 15px;
    font-size: 14px;
  }

  .favorites-grid {
    grid-template-columns: 1fr;
  }

  .profile-image-container {
    position: relative;
  }

  .profile-image {
    cursor: pointer;
    position: relative;
  }

  .image-overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    opacity: 0;
    transition: opacity 0.3s;
    border-radius: 50%;
  }

  .profile-image:hover .image-overlay {
    opacity: 1;
  }

  .hidden-input {
    display: none;
  }

  .image-info {
    color: #ff4444;
    margin-top: 10px;
    font-size: 14px;
  }

  .loading-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(255, 255, 255, 0.8);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
  }

  .loading-spinner {
    width: 40px;
    height: 40px;
    border: 4px solid #f3f3f3;
    border-top: 4px solid #ff6b6b;
    border-radius: 50%;
    animation: spin 1s linear infinite;
  }

  @keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
  }




}
</style>