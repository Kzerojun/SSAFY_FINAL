<template>
  <a-layout class="layout">
    <!-- Main Content Section -->
    <a-layout-content class="content">
      <div class="header-row">
        <h2 class="content-title">공지사항</h2>
        <a-button
            type="primary"
            :icon="h(EditOutlined)"
            @click="showWriteModal"
            class="write-button"
        >
          글쓰기
        </a-button>
      </div>

      <!-- 로딩 중 -->
      <div v-if="loading" class="empty-state">
        <p class="empty-text">로딩 중...</p>
      </div>

      <!-- 데이터가 없을 때 -->
      <div v-else-if="posts.length === 0" class="empty-state">
        <p class="empty-text">현재 등록된 공지사항이 없습니다.</p>
      </div>

      <!-- 데이터가 있을 때 -->
      <a-row v-else :gutter="[200, 30]" justify="center">
        <a-col
            v-for="post in posts"
            :key="post.articleNo"
            :xs="24"
            :sm="12"
            :md="8"
            :lg="6"
        >
          <a-card
              :title="post.subject"
              hoverable
              @click="onCardClick(post)"
              class="card"
          >
            <div class="card-content">
              <p>{{ post.content }}</p>
              <p><strong>작성자:</strong> {{ post.userName }}</p>
              <p><strong>조회수:</strong> {{ post.hit }}</p>
              <p><strong>등록일:</strong> {{ post.registerTime }}</p>
            </div>
          </a-card>
        </a-col>
      </a-row>
    </a-layout-content>

    <!-- Modal for 글쓰기 -->
    <a-modal
        :open="isModalVisible"
        title="공지사항 작성"
        :ok-text="'작성'"
        :cancel-text="'취소'"
        @ok="handleOk"
        @cancel="handleCancel"
    >
      <a-form :model="formState">
        <a-form-item
            label="제목"
            name="title"
            :rules="[{ required: true, message: '제목을 입력해주세요!' }]"
        >
          <a-input v-model:value="formState.title" />
        </a-form-item>
        <a-form-item
            label="내용"
            name="content"
            :rules="[{ required: true, message: '내용을 입력해주세요!' }]"
        >
          <a-textarea v-model:value="formState.content" :rows="4" />
        </a-form-item>
      </a-form>
    </a-modal>
  </a-layout>
</template>

<script>
import { defineComponent, ref, reactive } from "vue";
import {
  Button,
  Layout,
  Row,
  Col,
  Card,
  Modal,
  Input,
  Form,
  message,
} from "ant-design-vue";
import { EditOutlined } from "@ant-design/icons-vue";
import { h } from "vue";
import apiClient from "@/api/apiClient"; // Assumes an API client is configured

export default defineComponent({
  components: {
    "a-button": Button,
    "a-layout": Layout,
    "a-layout-content": Layout.Content,
    "a-row": Row,
    "a-col": Col,
    "a-card": Card,
    "a-modal": Modal,
    "a-input": Input,
    "a-textarea": Input.TextArea,
    "a-form": Form,
    "a-form-item": Form.Item,
    EditOutlined,
  },
  setup() {
    const posts = ref([]);
    const loading = ref(true);
    const isModalVisible = ref(false);
    const formState = reactive({
      title: "",
      content: "",
    });

    // Fetch posts from backend
    const fetchPosts = async () => {
      try {
        const response = await apiClient.get("/board/all");
        posts.value = response.data;
      } catch (error) {
        console.error("게시글 불러오기 실패:", error);
        message.error("게시글 불러오는 중 오류가 발생했습니다.");
      } finally {
        loading.value = false;
      }
    };

    const showWriteModal = () => {
      isModalVisible.value = true;
    };

    const handleCancel = () => {
      isModalVisible.value = false;
      formState.title = "";
      formState.content = "";
    };

    const handleOk = async () => {
      if (!formState.title || !formState.content) {
        message.error("제목과 내용을 입력해주세요!");
        return;
      }

      try {
        const requestBody = {
          title: formState.title,
          content: formState.content,
        };

        const response = await apiClient.post("/board/write", requestBody, {
          headers: {
            "Content-Type": "application/json",
          },
        });

        message.success("글이 성공적으로 작성되었습니다!");

        handleCancel();
      } catch (error) {
        console.error("글 작성 실패:", error);
        message.error("글 작성 중 오류가 발생했습니다.");
      }
    };

    const onCardClick = (post) => {
      alert(`"${post.subject}"를 클릭하셨습니다.`);
    };

    // Fetch posts on component mount
    fetchPosts();

    return {
      posts,
      loading,
      isModalVisible,
      formState,
      showWriteModal,
      handleCancel,
      handleOk,
      onCardClick,
      h,
      EditOutlined,
    };
  },
});
</script>

<style scoped>
.layout {
  background-color: white;
}

.empty-state {
  text-align: center;
  margin-top: 100px;
}

.empty-text {
  font-size: 18px;
  color: #888;
  font-weight: 500;
}

.content {
  font-family: "GowunDodum-Regular", sans-serif;
  padding: 20px;
  margin: 0 auto;
  max-width: 1200px;
  background-color: white;
}

.header-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.content-title {
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

.write-button {
  background-color: #007bff;
  color: white;
  border: none;
}

.card {
  border-radius: 10px;
  background-color: white;
  width: 300px;
  height: 300px;
  margin: 0 auto;
}

.card-content {
  text-align: center;
}
</style>
