<template>
  <div class="map-container">
    <div id="map" class="map"></div>
  </div>
</template>

<script>
export default {
  name: "KakaoMap",
  props: {
    latitude: {
      type: Number,
      default: 33.450701, // 제주도 위도
    },
    longitude: {
      type: Number,
      default: 126.570667, // 제주도 경도
    },
    level: {
      type: Number,
      default: 3, // 확대 레벨
    },
  },
  mounted() {
    this.loadKakaoMap();
  },
  methods: {
    loadKakaoMap() {
      if (window.kakao && window.kakao.maps) {
        this.initMap(); // 이미 로드된 경우 바로 초기화
      } else {
        const script = document.createElement("script");
        script.src =
            "//dapi.kakao.com/v2/maps/sdk.js?appkey=YOUR_APP_KEY&autoload=false";
        script.onload = () => {
          kakao.maps.load(() => this.initMap()); // 지도 초기화
        };
        script.onerror = () => {
          console.error("카카오 지도 API 로드 실패");
        };
        document.head.appendChild(script);
      }
    },
    initMap() {
      const container = document.getElementById("map"); // 지도를 표시할 HTML 요소
      const options = {
        center: new kakao.maps.LatLng(this.latitude, this.longitude), // 중심 좌표
        level: this.level, // 확대 레벨
      };

      // 지도 생성
      const map = new kakao.maps.Map(container, options);

      // 마커 생성
      const markerPosition = new kakao.maps.LatLng(this.latitude, this.longitude);
      const marker = new kakao.maps.Marker({
        position: markerPosition,
      });
      marker.setMap(map); // 지도에 마커 추가
    },
  },
};
</script>

<style scoped>
.map-container {
  width: 100%;
  height: 90vh;
}

.map {
  width: 100%;
  height: 100%;
  border: 1px solid #ccc;
  border-radius: 8px;
}
</style>
