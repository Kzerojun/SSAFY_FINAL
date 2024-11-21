<template>
  <div class="signup-container">
    <div class="background-overlay"></div>

    <ATypographyTitle :level="2" class="title">회원가입</ATypographyTitle>
    <p class="subtitle">당신만의 특별한 랜덤 여행을 시작하세요!</p>

    <AForm
        :model="formState"
        @finish="onFinish"
        @finishFailed="onFinishFailed"
        :rules="rules"
        layout="vertical"
        class="signup-form"
    >
      <!-- 이메일 폼 아이템 -->
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

      <!-- 비밀번호 폼 아이템 -->
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

      <!-- 비밀번호 확인 폼 아이템 -->
      <AFormItem
          name="confirmPassword"
          label="비밀번호 확인"
          has-feedback
      >
        <AInputPassword
            v-model:value="formState.confirmPassword"
            placeholder="비밀번호를 다시 입력하세요"
        />
      </AFormItem>

      <!-- 이름 폼 아이템 -->
      <AFormItem
          name="name"
          label="회원 성함"
          has-feedback
      >
        <AInput
            v-model:value="formState.name"
            placeholder="이름을 입력하세요"
        />
      </AFormItem>

      <!-- 제출 버튼 -->
      <AFormItem>
        <AButton
            type="primary"
            html-type="submit"
            :loading="loading"
            block
            class="submit-button"
        >
          회원가입
        </AButton>
      </AFormItem>
    </AForm>
  </div>
</template>

<script>
import { defineComponent, reactive, ref } from 'vue';
import { Form as AForm, FormItem as AFormItem } from 'ant-design-vue';
import { Input as AInput, InputPassword as AInputPassword } from 'ant-design-vue';
import { Button as AButton } from 'ant-design-vue';
import { Typography } from 'ant-design-vue';
import { message } from 'ant-design-vue';
import axios from 'axios';

const { Title: ATypographyTitle } = Typography;

export default defineComponent({
  name: 'Signup',
  components: {
    AForm,
    AFormItem,
    AInput,
    AInputPassword,
    AButton,
    ATypographyTitle,
  },
  setup() {
    const loading = ref(false);

    // 폼 상태 관리
    const formState = reactive({
      email: '',
      password: '',
      confirmPassword: '',
      name: '',
    });

    // 폼 검증 규칙
    const rules = {
      email: [
        { required: true, message: '이메일을 입력해주세요' },
        { type: 'email', message: '올바른 이메일 형식이 아닙니다' },
      ],
      password: [
        { required: true, message: '비밀번호를 입력해주세요' },
        { min: 6, message: '비밀번호는 최소 6자 이상이어야 합니다' },
      ],
      confirmPassword: [
        { required: true, message: '비밀번호 확인을 입력해주세요' },
        ({ getFieldValue }) => ({
          validator(_, value) {
            if (!value || getFieldValue('password') === value) {
              return Promise.resolve();
            }
            return Promise.reject('비밀번호가 일치하지 않습니다');
          },
        }),
      ],
      name: [
        { required: true, message: '이름을 입력해주세요' },
        { min: 2, message: '이름은 최소 2자 이상이어야 합니다' },
      ],
    };

    // 폼 제출 성공 핸들러
    const onFinish = async (values) => {
      loading.value = true;
      try {
        const response = await axios.post('http://localhost:80/enjoytrip/auth/signup', {
          email: values.email,
          password: values.password,
          name: values.name,
        });

        if (response.data.code === 'SU') {
          message.success('회원가입이 완료되었습니다!');
          // 로그인 페이지로 리다이렉트
          setTimeout(() => {
            window.location.href = '/login';
          }, 1500);
        } else {
          throw new Error(response.data.message || '회원가입에 실패했습니다');
        }
      } catch (error) {
        message.error(error.message || '회원가입 처리 중 오류가 발생했습니다');
      } finally {
        loading.value = false;
      }
    };

    // 폼 제출 실패 핸들러
    const onFinishFailed = (errorInfo) => {
      message.error('입력 정보를 다시 확인해주세요');
      console.log('Failed:', errorInfo);
    };

    return {
      formState,
      rules,
      loading,
      onFinish,
      onFinishFailed,
    };
  },
});
</script>

<style scoped>
@font-face {
  font-family: 'NanumSquareNeoLight';
  src: url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareNeo/NanumSquareNeoTTF-aLt.eot);
  src: url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareNeo/NanumSquareNeoTTF-aLt.eot?#iefix) format("embedded-opentype"),
  url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareNeo/NanumSquareNeoTTF-aLt.woff) format("woff"),
  url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareNeo/NanumSquareNeoTTF-aLt.ttf) format("truetype");
}

.signup-container {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 60px;
  min-height: 100vh;
  background-color: rgba(255, 255, 255, 0.9);
}

.background-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url("https://source.unsplash.com/1600x900/?travel,adventure");
  background-size: cover;
  background-position: center;
  filter: brightness(0.8);
  z-index: -1;
}

.title {
  font-size: 36px !important;
  font-weight: bold !important;
  color: #333 !important;
  margin-bottom: 16px !important;
  font-family: 'NanumSquareNeoLight', Arial, sans-serif;
}

.subtitle {
  font-size: 18px;
  color: #666;
  margin-bottom: 24px;
  font-family: 'NanumSquareNeoLight', Arial, sans-serif;
  text-align: center;
}

.signup-form {
  width: 100%;
  max-width: 400px;
  padding: 24px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.submit-button {
  font-weight: bold;
  height: 40px;
  font-size: 16px;
  background-color: #ffcc00;
  border-color: #ffcc00;
}

.submit-button:hover {
  background-color: #ffa500;
  border-color: #ffa500;
}

:deep(.ant-form-item-label > label) {
  font-family: 'NanumSquareNeoLight', Arial, sans-serif;
  font-weight: 500;
}

:deep(.ant-input) {
  font-family: 'NanumSquareNeoLight', Arial, sans-serif;
}
</style>