<template>
  <div class="home-container">
    <ImageSlider />
    <!-- 구분선 컴포넌트 -->
    <Divider style="width: 50%;" />
    <TravelHighlight/>
  </div>
</template>

<script>
import { Typography } from 'ant-design-vue';
import ImageSlider from '@/components/ImageSlider.vue';
import TravelHighlight from "@/components/TravelHighlgiht.vue";
import Divider from "@/components/Divider.vue"; // 이미지 슬라이더 컴포넌트 경로를 맞춰주세요

export default {
  name: 'Home',
  components: {
    Divider,
    TravelHighlight,
    TypographyTitle: Typography.Title,
    ImageSlider,
  },
};
</script>
<style scoped>
.home-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 24px;
  background-color: white; /* 배경색을 흰색으로 변경 */
  min-height: 100vh;
}

.Divider{
  width: 50%;
}
</style>
