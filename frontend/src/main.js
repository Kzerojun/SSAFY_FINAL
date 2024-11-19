import { createApp } from 'vue';
import App from './App.vue';
import 'ant-design-vue/dist/reset.css';

// Ant Design Vue 아이콘 등록
import { UserOutlined, LaptopOutlined, NotificationOutlined } from '@ant-design/icons-vue';

// Pinia 및 Vue Router 등록
import { createPinia } from 'pinia';
import router from './router';

const pinia = createPinia(); // Pinia 생성
const app = createApp(App);

// Ant Design 아이콘 컴포넌트 등록
app.component('user-outlined', UserOutlined);
app.component('laptop-outlined', LaptopOutlined);
app.component('notification-outlined', NotificationOutlined);

// Pinia 및 Router 사용
app.use(pinia);
app.use(router);


app.mount('#app');