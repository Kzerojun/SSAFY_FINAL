import { defineStore } from 'pinia'
import apiClient from "@/api/apiClient.js";

export const useAuthStore = defineStore('auth', {
    state: () => ({
        accessToken: null,
        email: null,
        name : null
    }),

    actions: {
        setAccessToken(token) {
            this.accessToken = token;
        },
        async fetchUser() {
            if (!this.accessToken) return;
            try {
                const response = await apiClient.get('user/info');
                const {email, name} = response.data;
                this.email = email;
                this.name = name;
            } catch (error) {
                console.error('Failed to fetch user:', error);
                this.email = null;
                this.name = null;
            }
        },
        clearAuth() {
            this.accessToken = null;
            this.user = null;
        },
    },
});