<template>
  <div class="board-container">
    <a-tabs v-model:activeKey="activeTab" @change="handleTabChange">
      <a-tab-pane key="notice" tab="공지사항">
        <a-table
            :columns="noticeColumns"
            :dataSource="noticeData"
            :pagination="{ pageSize: 5 }"
            rowKey="id"
            class="board-table"
            @row-click="onRowClick('notice')"
        />
      </a-tab-pane>
      <a-tab-pane key="free" tab="자유게시판">
        <a-table
            :columns="freeColumns"
            :dataSource="freeData"
            :pagination="{ pageSize: 5 }"
            rowKey="id"
            class="board-table"
            @row-click="onRowClick('free')"
        />
      </a-tab-pane>
    </a-tabs>
    <a-button type="primary" @click="createNewPost" class="create-button">새 글 작성</a-button>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

// Ant Design 컴포넌트 명시적 import
import { Tabs, Table, Button } from 'ant-design-vue';
const ATabs = Tabs;
const ATabPane = Tabs.TabPane;
const ATable = Table;
const AButton = Button;

// 라우터 훅
const router = useRouter();

// 상태 관리
const activeTab = ref('notice');

const noticeData = ref([
  { id: 1, title: 'ㅠㅠ', author: '아일릿', date: '2024-11-13', views: 320 },
  { id: 2, title: 'Cherish', author: '아일릿', date: '2024-11-11', views: 210 },
]);

const freeData = ref([
  { id: 1, title: '안녕하세요! 자유게시판 첫 글입니다.', author: 'User1', date: '2024-11-19', views: 150 },
  { id: 2, title: '재밌는 이야기 공유합니다!', author: 'User2', date: '2024-11-18', views: 98 },
]);

const noticeColumns = ref([
  { title: '제목', dataIndex: 'title', key: 'title' },
  { title: '작성자', dataIndex: 'author', key: 'author' },
  { title: '작성일', dataIndex: 'date', key: 'date' },
  { title: '조회수', dataIndex: 'views', key: 'views' },
]);

const freeColumns = ref([
  { title: '제목', dataIndex: 'title', key: 'title' },
  { title: '작성자', dataIndex: 'author', key: 'author' },
  { title: '작성일', dataIndex: 'date', key: 'date' },
  { title: '조회수', dataIndex: 'views', key: 'views' },
]);

// 행 클릭 이벤트
const onRowClick = (tab: string) => (record: any) => {
  console.log(`Row clicked:`, record);
  router.push({ name: 'post-detail', params: { id: record.id, tab } }); // 게시글 ID와 탭 정보를 전달
};

// 탭 변경 이벤트
const handleTabChange = (key: string) => {
  console.log(`Tab changed to: ${key}`);
};

// 새 글 작성으로 이동
const createNewPost = () => {
  console.log('Navigating to the write post page...');
  router.push({ name: 'write' }); // 'write' 라우터로 이동
};
</script>

<style scoped>
.board-container {
  padding: 16px;
}

.board-table {
  margin-top: 16px;
}

.create-button {
  margin-top: 16px;
  float: right;
}

/* 행 호버 효과 */
.clickable-row {
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.clickable-row:hover {
  background-color: #f5f5f5;
}
</style>
