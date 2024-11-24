<template>
  <div class="map">
    <div id="map"></div>
  </div>
</template>

<script>
import { onMounted, ref, watch } from 'vue';

export default {
  name: 'KakaoMap',
  props: {
    locations: {
      type: Array,
      required: true,
      default: () => []
    }
  },
  setup(props) {
    const isLoading = ref(true);
    let map = null;
    let markers = ref([]);

    const loadKakaoMapScript = () => {
      return new Promise((resolve, reject) => {
        try {
          if (window.kakao && window.kakao.maps) {
            console.log("이미 카카오맵이 로드되어 있습니다.");
            resolve();
            return;
          }

          const script = document.createElement("script");
          script.type = "text/javascript";
          script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=1cbc901d3786d37bae1d7e89a64a9427&libraries=services,clusterer`;
          
          script.onload = () => {
            window.kakao.maps.load(() => {
              console.log("카카오맵 스크립트 로드 성공");
              resolve();
            });
          };

          script.onerror = (error) => {
            console.error("카카오맵 스크립트 로드 실패:", error);
            reject(new Error("카카오맵 스크립트 로드 실패"));
          };

          document.head.appendChild(script);
        } catch (error) {
          console.error("스크립트 로드 중 예외 발생:", error);
          reject(error);
        }
      });
    };

    const deleteMarkers = () => {
      if (markers.value.length > 0) {
        markers.value.forEach((marker) => marker.setMap(null));
        markers.value = [];
      }
    };

    const displayMarkers = () => {
      try {
        console.log("마커 표시 시작");
        deleteMarkers();

        if (!props.locations || props.locations.length === 0) {
          console.log("표시할 위치 없음");
          return;
        }

        const bounds = new kakao.maps.LatLngBounds();

        props.locations.forEach((location, index) => {
          console.log(`마커 생성 ${index + 1}:`, location);
          const position = new kakao.maps.LatLng(
            parseFloat(location.latitude), 
            parseFloat(location.longitude)
          );
          
          const marker = new kakao.maps.Marker({
            map: map,
            position: position,
            title: location.title,
            clickable: true
          });

          const infowindow = new kakao.maps.InfoWindow({
            content: `
              <div style="padding: 10px; width: 200px;">
                <h5 style="margin: 0 0 5px 0;">${location.title}</h5>
                <p style="margin: 0;">${location.addr1 || ''}</p>
              </div>
            `
          });

          kakao.maps.event.addListener(marker, 'click', () => {
            markers.value.forEach(m => {
              if (m.infowindow) m.infowindow.close();
            });
            infowindow.open(map, marker);
          });

          marker.infowindow = infowindow;
          markers.value.push(marker);
          bounds.extend(position);
        });

        if (markers.value.length > 0) {
          console.log("지도 범위 재설정");
          map.setBounds(bounds);
        }
      } catch (error) {
        console.error("마커 표시 중 오류:", error);
      }
    };

    onMounted(async () => {
      try {
        await loadKakaoMapScript();
        const container = document.getElementById("map");
        const options = {
          center: new kakao.maps.LatLng(33.450701, 126.570667),
          level: 3,
        };
        map = new kakao.maps.Map(container, options);
        
        if (props.locations.length > 0) {
          displayMarkers();
        }
        isLoading.value = false;
      } catch (error) {
        console.error("카카오맵 초기화 실패:", error);
        isLoading.value = false;
      }
    });

    watch(() => props.locations, (newLocations) => {
      console.log("새로운 위치 정보 받음:", newLocations);
      if (map && newLocations && newLocations.length > 0) {
        console.log("지도 마커 업데이트 시작");
        displayMarkers();
        
        // 첫 번째 위치로 지도 중심 이동
        const firstLocation = newLocations[0];
        const moveLatLon = new kakao.maps.LatLng(
          parseFloat(firstLocation.latitude),
          parseFloat(firstLocation.longitude)
        );
        map.panTo(moveLatLon);
      }
    }, { deep: true });
    return {
      isLoading
    };
  }
};
</script>

<style scoped>
.map {
  display: flex;
  justify-content: center;
}

#map {
  width: 600px;
  height: 600px;
  border-radius: 10px;
  margin-top: 20px;
  margin-bottom: 40px;
}
</style>