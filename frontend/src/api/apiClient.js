import axios from 'axios';
import { useAuthStore } from '@/stores/auth';

const apiClient = axios.create({
  baseURL: 'http://localhost:80/enjoytrip/',
});

// 요청 인터셉터 추가
apiClient.interceptors.request.use(
    (config) => {
      const authStore = useAuthStore();

      // Pinia에서 액세스 토큰 가져오기
      const token = authStore.accessToken;

      if (token) {
        // Authorization 헤더에 토큰 추가
        config.headers.Authorization = `Bearer ${token}`;
      }

      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
);

// 응답 인터셉터 추가
apiClient.interceptors.response.use(
    (response) => {
      return response;
    },
    (error) => {
      if (error.response && error.response.status === 401) {
        // 401 에러: 토큰 만료 시 처리
        const authStore = useAuthStore();
        authStore.clearAuth();
        console.error('토큰이 만료되어 인증 정보를 초기화했습니다.');
      }
      return Promise.reject(error);
    }
);

export default apiClient;
