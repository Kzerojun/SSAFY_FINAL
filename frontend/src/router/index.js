import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Main.vue';
import KakaoMap from '@/components/KakaoMap.vue';
import Board from '@/views/BoardList.vue';
import PostDetail from "@/views/PostDetail.vue";
import Login from "@/views/Login.vue";
import Signup from "@/views/Signup.vue";
import Gatcha from '@/views/Gatcha.vue';
import MainSearch from "@/views/Travel.vue";
import Schedule from '@/views/ScheduleList.vue';
import ScheduleAttraction from '@/views/ScheduleAttraction.vue';
import Notify from "@/views/Notify.vue";
import Mypage from "@/views/Mypage.vue";


const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
  },
  {
    path: '/map',
    name: 'kakao-map',
    component: KakaoMap,
    props: route => ({
      latitude: parseFloat(route.query.lat) || 37.5665, // 기본 위도 (서울)
      longitude: parseFloat(route.query.lng) || 126.9780, // 기본 경도 (서울)
      level: parseInt(route.query.level) || 3, // 기본 확대 레벨
    }),
  },

  { path: '/board', name: 'board', component: Board },
  { path: '/post/:id/:tab', name: 'post-detail', component: PostDetail },
  { path: '/login', name: 'login', component: Login },
  { path: '/signup', name: 'signup', component: Signup },
  { path: '/gatcha', name: 'gatcha', component: Gatcha},
  { path: '/schedule', name: 'schedule', component: Schedule},
  {path :'/notify', name :'notify', component: Notify},
  { path : '/search',name :'search',component: MainSearch},
  { path: '/schedule/:scheduleId/attractions', 
    name: 'schedule-attractions',
    component: ScheduleAttraction,
    props: true,
  },{
    path : '/mypage',name : 'mypage',component: Mypage
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
