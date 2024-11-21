import {defineStore} from "pinia";
import apiClient from "@/api/apiClient.js";

export const useAuthStore = defineStore('auth', {
    state: () => ({
        accessToken: null,
        user: null, // 사용자 정보
    }),
    actions: {
        setAccessToken(token) {
            this.accessToken = token;
        },
        async fetchUser() {
            if (!this.accessToken) return;
            try {
                const response = await apiClient.get('user'); // 사용자 정보 API 호출


                console.log(response);
                const {email, name} = response.data; // email과 name 추출
                this.user = {email, name}; // user 속성에 저장
            } catch (error) {
                console.error('Failed to fetch user:', error);
                this.user = null; // 오류 발생 시 사용자 정보 초기화
            }
        },
        clearAuth() {
            this.accessToken = null;
            this.user = null;
        },
    },
});
