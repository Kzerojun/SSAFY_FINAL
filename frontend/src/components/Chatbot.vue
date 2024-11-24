<template>
  <div class="chat-widget">
    <button v-if="!isOpen" @click="toggleChat" class="chat-button">
      <div class="bot-icon">
        <img src="@/assets/logo.png" alt="Chatbot Icon" />
      </div>
    </button>
    <div v-show="isOpen" class="chat-container">
      <div class="chat-header">
        <div class="bot-icon-header">
          <img src="@/assets/logo.png" alt="Chatbot Icon" />
        </div>
        <button @click="toggleChat" class="close-button">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" class="w-6 h-6">
            <path d="M18 6L6 18M6 6l12 12" stroke-width="2" stroke-linecap="round" />
          </svg>
        </button>
      </div>
      <div class="chat-messages" ref="messageContainer">
        <div
            v-for="(message, index) in messages"
            :key="index"
            :class="['message', message.type === 'user' ? 'user-message' : 'bot-message']"
        >
          {{ message.text }}
        </div>
        <div v-if="isLoading" class="typing-indicator">
          답변을 준비중입니다...
        </div>
      </div>
      <div class="chat-input">
        <input
            v-model="newMessage"
            @keyup.enter="sendMessage"
            :placeholder="authStore.accessToken ? 'Send message...' : '로그인이 필요한 서비스입니다'"
            type="text"
            :disabled="!authStore.accessToken"
            :class="{ 'input-disabled': !authStore.accessToken }"
        />
        <button
            @click="sendMessage"
            class="send-button"
            :disabled="!authStore.accessToken"
            :class="{ 'button-disabled': !authStore.accessToken }"
        >
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" class="w-5 h-5">
            <path
                d="M22 2L11 13M22 2l-7 20-4-9-9-4 20-7z"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
            />
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { useAuthStore } from '@/stores/auth';

export default {
  name: 'Chatbot',
  data() {
    return {
      isOpen: false,
      messages: [], // 채팅 메시지 리스트
      newMessage: '',
      stompClient: null,
      userEmail: '',
      baseUrl: 'http://localhost:80/enjoytrip',
      isLoading: false, // 로딩 상태 추가
      hasInitialMessage: false, // 초기 메시지 추가 여부
    };
  },
  computed: {
    authStore() {
      return useAuthStore();
    },
  },
  methods: {
    toggleChat() {
      this.isOpen = !this.isOpen;

      // 처음 열릴 때만 기본 메시지 추가
      if (this.isOpen && !this.hasInitialMessage) {
        this.messages.push({
          type: 'bot',
          text: '환영합니다. 강원랜트입니다. \n 궁금한 관광지 정보가 있다면 물어봐주세요.',
        });
        this.hasInitialMessage = true; // 메시지 추가 후 상태 업데이트
      }

      if (this.isOpen) {
        this.$nextTick(() => this.scrollToBottom());
      }
    },
    connectWebSocket() {
      console.log("연결 시도중");
      if (!this.authStore.accessToken) {
        console.log("토큰 없음 - 연결 실패");
        return;
      }

      const socket = new SockJS(`${this.baseUrl}/ssafy`);
      this.stompClient = Stomp.over(socket);

      const headers = {
        Authorization: `Bearer ${this.authStore.accessToken}`,
      };

      this.stompClient.connect(
          headers,
          () => {
            console.log("WebSocket 연결 성공");
            this.stompClient.subscribe(`/chat/${this.userEmail}`, (response) => {
              let responseBody = response.body;

              // 끝에 붙은 null 문자 제거
              if (responseBody.endsWith('\u0000')) {
                responseBody = responseBody.slice(0, -1);
              }

              // 로딩 애니메이션 제거
              this.isLoading = false;

              // 봇 응답 메시지 추가
              this.messages.push({ type: 'bot', text: responseBody });

              this.$nextTick(() => this.scrollToBottom());
            });
          },
          (error) => {
            console.error("WebSocket 연결 실패:", error);
          }
      );
    },
    sendMessage() {
      if (!this.authStore.accessToken || !this.newMessage.trim()) return;

      // 사용자 메시지를 로컬에 추가
      this.messages.push({ type: 'user', text: this.newMessage });

      // 로딩 애니메이션 시작
      this.isLoading = true;

      // WebSocket 연결 여부 확인 및 메시지 전송
      if (this.stompClient && this.stompClient.connected) {
        const payload = {
          userMsg: this.newMessage, // 사용자 메시지
          userEmail: this.authStore.email, // 사용자 이메일 추가
        };

        this.stompClient.send(
            '/travel/chatbot',
            {}, // 빈 헤더
            JSON.stringify(payload) // 메시지 본문
        );
      } else {
        console.log("WebSocket 연결이 없습니다.");
        this.connectWebSocket(); // WebSocket 재연결 시도
      }

      // 입력창 초기화
      this.newMessage = '';
      this.$nextTick(() => this.scrollToBottom());
    },
    scrollToBottom() {
      const container = this.$refs.messageContainer;
      container.scrollTop = container.scrollHeight;
    },
    reconnectWebSocket() {
      console.log("WebSocket 재연결 시도");
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connectWebSocket();
    },
  },
  mounted() {
    if (this.authStore.accessToken) {
      this.authStore.fetchUser().then(() => {
        this.userEmail = this.authStore.email;
        this.connectWebSocket();
      });
    }
  },
  beforeUnmount() {
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
  },
  watch: {
    'authStore.accessToken': {
      handler(newToken) {
        if (newToken) {
          this.authStore.fetchUser().then(() => {
            this.userEmail = this.authStore.email;
            this.connectWebSocket();
          });
        } else {
          if (this.stompClient) {
            this.stompClient.disconnect();
          }
        }
      },
      immediate: true,
    },
  },
};
</script>

<style scoped>
.chat-widget {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 1000;
}

.chat-button {
  width: 100px;
  height: 100px;
  border-radius: 12px;
  background: white;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.chat-container {
  position: absolute;
  bottom: 80px;
  right: 0;
  width: 320px;
  height: 480px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.chat-header {
  background: #ffcc00;
  padding: 0.75rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.bot-icon-header {
  width: 32px;
  height: 32px;
  background: white;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #ffcc00;
}

.close-button {
  width: 32px;
  height: 32px;
  border: none;
  background: none;
  color: white;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 1rem;
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  background: #f8f9fa;
}

.message {
  max-width: 80%;
  padding: 0.75rem 1rem;
  border-radius: 1rem;
  word-break: break-word;
}

.user-message {
  background: #ffcc00;
  color: white;
  align-self: flex-end;
  border-bottom-right-radius: 4px;
}

.bot-message {
  background: white;
  color: #495057;
  align-self: flex-start;
  border-bottom-left-radius: 4px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.typing-indicator {
  align-self: flex-start;
  background: white;
  padding: 0.75rem 1rem;
  border-radius: 1rem;
  border-bottom-left-radius: 4px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
  color: #495057;
  font-style: italic;
}

.chat-input {
  padding: 0.75rem;
  display: flex;
  gap: 0.5rem;
  background: white;
  border-top: 1px solid #e9ecef;
}

input {
  flex: 1;
  padding: 0.75rem;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  outline: none;
  font-size: 0.875rem;
}

input:focus {
  border-color: #ffcc00;
}

.send-button {
  width: 36px;
  height: 36px;
  background: #ffcc00;
  border: none;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  cursor: pointer;
}

.send-button:hover {
  background: #e6b800;
}

.bot-icon img, .bot-icon-header img {
  width: 100%;
  height: 100%;
  display: block;
  margin: auto;
}

.message {
  white-space: pre-line;
}

.input-disabled {
  background-color: #f5f5f5;
  cursor: not-allowed;
  color: #999;
}

.button-disabled {
  background-color: #cccccc !important;
  cursor: not-allowed;
  opacity: 0.7;
}

.button-disabled:hover {
  background-color: #cccccc !important;
}



</style>
