<template>
  <div class="login-container">
    <!-- 배경 이미지 -->
    <div class="background-overlay"></div>

    <!-- 카드 -->
    <Card class="card">
      <TypographyTitle level="2" class="title">강원랜트</TypographyTitle>
      <p class="subtitle">당신만의 특별한 랜덤 여행을 시작하세요!</p>
      <Form @submit.prevent="handleSubmit" layout="vertical" class="login-form">
        <FormItem
            label="이메일"
            name="email"
            :rules="[ { required: true, message: '이메일을 입력하세요!' }, { type: 'email', message: '올바른 이메일 형식이 아닙니다!' } ]"
        >
          <Input v-model="email" type="email" placeholder="이메일을 입력하세요" />
        </FormItem>
        <FormItem
            label="비밀번호"
            name="password"
            :rules="[{ required: true, message: '비밀번호를 입력하세요!' }]"
        >
          <InputPassword v-model="password" placeholder="비밀번호를 입력하세요" />
        </FormItem>
        <FormItem>
          <Button type="primary" html-type="submit" block class="submit-button">
            로그인
          </Button>
        </FormItem>
        <div class="footer">
          <router-link to="/signup">회원가입</router-link> | <router-link to="/forgot-password">비밀번호 찾기</router-link>
        </div>

        <!-- Oauth 로그인 버튼 -->
        <div class="oauth-buttons">
          <button class="oauth-button naver">
            <img src="@/assets/naver_circle.png" alt="네이버 로그인" />
          </button>
          <button class="oauth-button kakao">
            <img src="@/assets/kakaoTalk_logo.svg" alt="카카오 로그인" />
          </button>
        </div>
      </Form>
    </Card>
  </div>
</template>

<script>
import { Card, Typography, Form, Input, Button } from "ant-design-vue";
import axios from "axios";

export default {
  name: "Login",
  components: {
    Card,
    TypographyTitle: Typography.Title,
    Form,
    FormItem: Form.Item,
    Input,
    InputPassword: Input.Password,
    Button,
  },
  data() {
    return {
      email: "",
      password: "",
    };
  },
  methods: {
    async handleSubmit() {
      try {
        const response = await axios.post("http://localhost:80/enjoytrip/user/login", {
          userEmail: this.email,
          userPwd: this.password,
        });

        if (response.status === 200) {
          const data = response.data;
          alert(`안녕하세요, ${data.userName}님!`);
        }
      } catch (error) {
        console.error("로그인 실패:", error.response?.data || error.message);
        alert("로그인 실패! 다시 시도해주세요.");
      }
    },
  },
};
</script>

<style scoped>
/* 로그인 전체 컨테이너 */
.login-container {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #ffffff;
  overflow: hidden;
}

/* 배경 이미지와 오버레이 */
.background-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url("https://source.unsplash.com/1600x900/?travel,adventure");
  background-size: cover;
  background-position: center;
  filter: brightness(0.7);
  z-index: 1;
}

/* 카드 */
.card {
  position: relative;
  z-index: 2;
  width: 500px;
  padding: 24px;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
  background-color: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  text-align: center;
}

.title {
  text-align: center;
  margin-bottom: 12px;
  color: #ffcc00;
  font-family: Arial, sans-serif;
}

.subtitle {
  font-size: 14px;
  color: #666;
  margin-bottom: 20px;
}

/* 로그인 폼 */
.login-form {
  margin-top: 16px;
}

/* 로그인 버튼 */
.submit-button {
  font-weight: bold;
  background-color: #ffcc00; /* 노란색 버튼 */
  border: none;
  transition: background-color 0.3s ease;
}

.submit-button:hover {
  background-color: #ffb700; /* 호버 시 조금 더 진한 노란색 */
  color: #333;
}

/* 링크 섹션 */
.footer {
  margin-top: 16px;
  font-size: 12px;
  color: #555;
}

.footer a {
  color: #007bff;
  text-decoration: none;
}

.footer a:hover {
  text-decoration: underline;
}

/* Oauth 버튼 스타일 */
.oauth-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 20px;
}

.oauth-button {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 50px;
  height: 50px;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  background-color: white;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.oauth-button img {
  width: 60%;
  height: 60%;
}

.oauth-button:hover {
  transform: scale(1.1);
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.2);
}

.oauth-button.naver {
  background-color: #03c75a;
}

.oauth-button.kakao {
  background-color: #fee500;
}
</style>
