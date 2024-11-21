<template>
  <div class="login-container">
    <div class="background-overlay"></div>

    <ACard class="card">
      <ATypographyTitle :level="2" class="title">강원랜트</ATypographyTitle>
      <p class="subtitle">당신만의 특별한 랜덤 여행을 시작하세요!</p>

      <AForm
          :model="formState"
          @finish="onFinish"
          layout="vertical"
          class="login-form"
          :rules="rules"
      >
        <AFormItem
            name="email"
            label="이메일"
            has-feedback
        >
          <AInput
              v-model:value="formState.email"
              placeholder="이메일을 입력하세요"
          />
        </AFormItem>

        <AFormItem
            name="password"
            label="비밀번호"
            has-feedback
        >
          <AInputPassword
              v-model:value="formState.password"
              placeholder="비밀번호를 입력하세요"
          />
        </AFormItem>

        <AFormItem>
          <AButton
              type="primary"
              html-type="submit"
              :loading="loading"
              block
              class="submit-button"
          >
            로그인
          </AButton>
        </AFormItem>

        <div class="footer">
          <RouterLink to="/signup">회원가입</RouterLink> |
          <RouterLink to="/forgot-password">비밀번호 찾기</RouterLink>
        </div>

        <div class="oauth-buttons">
          <button class="oauth-button naver" @click="handleNaverLogin">
            <img src="@/assets/naver_circle.png" alt="네이버 로그인" />
          </button>
          <button class="oauth-button kakao" @click="handleKakaoLogin">
            <img src="@/assets/kakaoTalk_logo.svg" alt="카카오 로그인" />
          </button>
        </div>
      </AForm>
    </ACard>
  </div>
</template>

<script>
import { defineComponent, reactive, ref } from 'vue';
import { Card as ACard } from 'ant-design-vue';
import { Form as AForm, FormItem as AFormItem } from 'ant-design-vue';
import { Input as AInput, InputPassword as AInputPassword } from 'ant-design-vue';
import { Button as AButton } from 'ant-design-vue';
import { Typography } from 'ant-design-vue';
import { message } from 'ant-design-vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import {useAuthStore} from "@/stores/auth.js";

const { Title: ATypographyTitle } = Typography;

export default defineComponent({
  name: 'Login',
  components: {
    ACard,
    AForm,
    AFormItem,
    AInput,
    AInputPassword,
    AButton,
    ATypographyTitle,
  },
  setup() {
    const router = useRouter();
    const loading = ref(false);

    const formState = reactive({
      email: '',
      password: '',
    });

    const rules = {
      email: [
        { required: true, message: '이메일을 입력하세요!' },
        { type: 'email', message: '올바른 이메일 형식이 아닙니다!' }
      ],
      password: [
        { required: true, message: '비밀번호를 입력하세요!' },
        { min: 6, message: '비밀번호는 최소 6자 이상이어야 합니다' }
      ]
    };


    const authStore = useAuthStore();
    const onFinish = async (values) => {
      loading.value = true;
      try {
        const response = await axios.post('http://localhost:80/enjoytrip/auth/login', {
          accessToken : values.accessToken
        });

        if (response.status === 200) {
          message.success(`강원랜트에 오신거 환영합니다.`);
          authStore.setAccessToken(response.data.accessToken);

          // 로그인 성공 후 메인 페이지로 리다이렉트
          setTimeout(() => {
            router.push('/');
          }, 1500);
        }
      } catch (error) {
        console.error('로그인 실패:', error.response?.data || error.message);
        message.error('이메일 또는 비밀번호가 올바르지 않습니다.');
      } finally {
        loading.value = false;
      }
    };

    const handleNaverLogin = () => {
      // 네이버 로그인 구현
      console.log('네이버 로그인');
    };

    const handleKakaoLogin = () => {
      // 카카오 로그인 구현
      console.log('카카오 로그인');
    };

    return {
      formState,
      loading,
      rules,
      onFinish,
      handleNaverLogin,
      handleKakaoLogin,
    };
  },
});
</script>

<style scoped>
.login-container {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #ffffff;
  overflow: hidden;
}

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

.card {
  position: relative;
  z-index: 2;
  width: 500px;
  padding: 24px;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
  background-color: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
}

.title {
  text-align: center !important;
  margin-bottom: 12px !important;
  color: #ffcc00 !important;
  font-family: Arial, sans-serif;
}

.subtitle {
  font-size: 14px;
  color: #666;
  margin-bottom: 20px;
  text-align: center;
}

.login-form {
  margin-top: 16px;
}

.submit-button {
  font-weight: bold;
  height: 40px;
  font-size: 16px;
  background-color: #ffcc00;
  border-color: #ffcc00;
}

.submit-button:hover {
  background-color: #ffb700;
  border-color: #ffb700;
  color: #333;
}

.footer {
  margin-top: 16px;
  font-size: 12px;
  color: #555;
  text-align: center;
}

.footer a {
  color: #007bff;
  text-decoration: none;
}

.footer a:hover {
  text-decoration: underline;
}

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

:deep(.ant-form-item-label > label) {
  font-family: Arial, sans-serif;
  font-weight: 500;
}

:deep(.ant-input) {
  font-family: Arial, sans-serif;
}
</style>