<template>
  <div class="signup-container">
    <!-- 배경 이미지 -->
    <div class="background-overlay"></div>

    <!-- 회원가입 제목 -->
    <TypographyTitle level="2" class="title">회원가입</TypographyTitle>
    <p class="subtitle">당신만의 특별한 랜덤 여행을 시작하세요!</p>

    <!-- 회원가입 폼 -->
    <Form @submit.prevent="handleSignup" layout="vertical" class="signup-form">
      <!-- 이메일 -->
      <FormItem
          label="이메일"
          name="userEmail"
          :rules="[
          { required: true, message: '이메일을 입력하세요!' },
          { type: 'email', message: '올바른 이메일 형식이 아닙니다!' }
        ]"
      >
        <Input v-model="userEmail" type="email" placeholder="이메일을 입력하세요" />
      </FormItem>

      <!-- 비밀번호 -->
      <FormItem
          label="비밀번호"
          name="userPwd"
          :rules="[ { required: true, message: '비밀번호를 입력하세요!' } ]"
      >
        <InputPassword v-model="userPwd" placeholder="비밀번호를 입력하세요" />
      </FormItem>

      <!-- 비밀번호 확인 -->
      <FormItem
          label="비밀번호 확인"
          name="userPwdConfirm"
          :rules="[
          { required: true, message: '비밀번호를 다시 입력하세요!' },
          { validator: validatePassword, trigger: 'blur' }
        ]"
      >
        <InputPassword v-model="userPwdConfirm" placeholder="비밀번호를 다시 입력하세요" />
      </FormItem>

      <!-- 회원 성함 -->
      <FormItem
          label="회원 성함"
          name="userName"
          :rules="[ { required: true, message: '이름을 입력하세요!' } ]"
      >
        <Input v-model="userName" placeholder="이름을 입력하세요" />
      </FormItem>

      <!-- 회원가입 버튼 -->
      <FormItem>
        <Button type="primary" html-type="submit" block class="submit-button">
          회원가입
        </Button>
      </FormItem>
    </Form>
  </div>
</template>

<script>
import { Typography, Form, Input, Button } from "ant-design-vue";

export default {
  name: "Signup",
  components: {
    TypographyTitle: Typography.Title,
    Form,
    FormItem: Form.Item,
    Input,
    InputPassword: Input.Password,
    Button,
  },
  data() {
    return {
      userEmail: "",
      userPwd: "",
      userPwdConfirm: "",
      userName: "",
    };
  },
  methods: {
    handleSignup() {
      if (!this.userEmail || !this.userPwd || !this.userPwdConfirm || !this.userName) {
        alert("모든 필드를 입력해주세요!");
        return;
      }

      if (this.userPwd !== this.userPwdConfirm) {
        alert("비밀번호가 일치하지 않습니다!");
        return;
      }

      alert("회원가입 성공!");
    },
    validatePassword(_, value) {
      if (value !== this.userPwd) {
        return Promise.reject("비밀번호가 일치하지 않습니다!");
      }
      return Promise.resolve();
    },
  },
};
</script>

<style scoped>
/* NanumSquareNeoLight 폰트 */
@font-face {
  font-family: 'NanumSquareNeoLight';
  src: url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareNeo/NanumSquareNeoTTF-aLt.eot);
  src: url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareNeo/NanumSquareNeoTTF-aLt.eot?#iefix) format("embedded-opentype"), url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareNeo/NanumSquareNeoTTF-aLt.woff) format("woff"), url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareNeo/NanumSquareNeoTTF-aLt.ttf) format("truetype");
}

/* 전체 컨테이너 */
.signup-container {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 100px;
  height: 100vh;
  background-color: #ffffff;
}

/* 배경 이미지 */
.background-overlay {
  position: absolute;
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

/* 제목 */
.title {
  font-size: 36px; /* 글씨 크기 키움 */
  font-weight: bold;
  color: #333;
  margin-bottom: 16px;
  font-family: 'NanumSquareNeoLight', Arial, sans-serif;
}

/* 부제목 */
.subtitle {
  font-size: 18px; /* 글씨 크기 키움 */
  color: #666;
  margin-bottom: 24px;
  font-family: 'NanumSquareNeoLight', Arial, sans-serif;
}

/* 회원가입 폼 */
.signup-form {
  width: 100%;
  max-width: 400px;
  padding: 0 16px;
}

/* 회원가입 버튼 */
.submit-button {
  font-weight: bold;
  background-color: #ffcc00;
}

.submit-button:hover {
  background-color: #ffa500;
  color: #333;
}
</style>
