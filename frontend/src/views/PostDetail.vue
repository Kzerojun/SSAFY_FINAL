<template>
  <div class="post-detail-container">
    <a-card :title="post.title" bordered>
      <a-descriptions column="1" bordered>
        <a-descriptions-item label="작성자">{{ post.author }}</a-descriptions-item>
        <a-descriptions-item label="작성일">{{ post.date }}</a-descriptions-item>
        <a-descriptions-item label="조회수">{{ post.views }}</a-descriptions-item>
        <a-descriptions-item label="내용">
          <p>{{ post.content }}</p>
        </a-descriptions-item>
      </a-descriptions>
      <a-button type="primary" class="back-button" @click="goBack">뒤로가기</a-button>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';

// Ant Design 컴포넌트 명시적 import
import { Card, Descriptions, Button } from 'ant-design-vue';
const ACard = Card;
const ADescriptions = Descriptions;
const ADescriptionsItem = Descriptions.Item;
const AButton = Button;

const router = useRouter();
const route = useRoute();

// 게시글 데이터베이스
const allPosts = ref([
  {
    id: 1,
    tab: 'notice',
    title: 'ㅠㅠ',
    author: '아일릿',
    date: '2024-11-13',
    views: 320,
    content: 'Cherish your memories... They will keep you strong.',
  },
  {
    id: 2,
    tab: 'notice',
    title: 'Cherish',
    author: '아일릿',
    date: '2024-11-11',
    views: 210,
    content: 'Every moment is a gift worth cherishing.',
  },
  {
    id: 1,
    tab: 'free',
    title: '안녕하세요! 자유게시판 첫 글입니다.',
    author: 'User1',
    date: '2024-11-19',
    views: 150,
    content: '이 게시글은 자유게시판 첫 글입니다. 모두 반가워요!',
  },
  {
    id: 2,
    tab: 'free',
    title: '재밌는 이야기 공유합니다!',
    author: 'User2',
    date: '2024-11-18',
    views: 98,
    content: '여러분과 나누고 싶은 재밌는 이야기를 여기 적어볼게요.',
  },
]);

// 현재 게시글
const post = ref({
  id: null,
  title: '',
  author: '',
  date: '',
  views: 0,
  content: '',
});

onMounted(() => {
  const { id, tab } = route.params;
  const foundPost = allPosts.value.find(
      (p) => p.id === Number(id) && p.tab === tab
  );
  if (foundPost) {
    post.value = foundPost;
  } else {
    alert('게시글을 찾을 수 없습니다.');
    router.push({ name: 'home' }); // 홈으로 이동
  }
});

const goBack = () => {
  router.push({ name: 'home' });
};
</script>

<style scoped>
.post-detail-container {
  max-width: 800px;
  margin: 50px auto;
  padding: 20px;
}

.back-button {
  margin-top: 16px;
}
</style>
