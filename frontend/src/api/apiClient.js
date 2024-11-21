import axios from 'axios';
import { useAuthStore } from '@/stores/auth';

const apiClient = axios.create({
  baseURL: 'http://localhost:80/enjoytrip/', // 포트 번호 유지
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

export default apiClient;
