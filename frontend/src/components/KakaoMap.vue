<script setup>
import { ref, watch, onMounted } from "vue";
import { useRouter } from 'vue-router';

const router = useRouter();

const props = defineProps({
  places: Array
})

var map;
const positions = ref([]);
const markers = ref([]);

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
      }&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

watch(
  () => props.spots,
  () => {
    console.log(props.spots[0].latitude, props.spots[0].longitude)
    console.log("나여" + props.spots);
    positions.value = [];
    // 이동할 위도 경도 위치를 생성합니다
    var moveLatLon = new kakao.maps.LatLng(props.spots[0].latitude, props.spots[0].longitude);

    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);
    props.spots.forEach((spot) => {
      let obj = {};
      // console.log(spot.latitude, spot.longitude)
      obj.latlng = new kakao.maps.LatLng(spot.latitude, spot.longitude);
      obj.title = spot.title;
      obj.contentId = spot.contentId;
      obj.contentTypeId = spot.contentTypeId;
      positions.value.push(obj);
    });
    loadMarkers();
  }
);

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);
  // loadMarkers();
};

const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  markers.value = [];
  positions.value.forEach((position, index) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정
    });

    // 인포윈도우 내용에 버튼 추가
    const infowindow = new kakao.maps.InfoWindow({
      content: `
      <div style="width:150px; padding:10px; font-size:14px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">
  <p style="margin:0; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">
    ${position.title}
  </p>
  <button id="btn-${index}" style="margin-top:5px; padding:5px 10px; background:#4CAF50; color:white; border:none; border-radius:3px; cursor:pointer;">
    기록하기
  </button>
</div>
      `, // 표시할 내용
    });

    // 마커에 클릭 이벤트 추가
    kakao.maps.event.addListener(marker, "click", () => {
      // 다른 인포윈도우를 닫음
      markers.value.forEach((m) => {
        if (m.infowindow) m.infowindow.close();
      });

      // 클릭한 마커에 해당하는 인포윈도우를 열기
      infowindow.open(map, marker);
    });

    // 마커와 인포윈도우를 연결하여 관리
    marker.infowindow = infowindow;
    markers.value.push(marker);
  });

  // 지도 범위를 마커 위치에 맞게 설정
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );
  map.setBounds(bounds);
};


const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};
</script>

<template>
  <div class="map">
    <div id="map"></div>
  </div>
</template>

<style>
.map {
  display: flex;
  justify-content: center;
}

#map {
  width: 1200px;
  height: 600px;

  border-radius: 10px;

  margin-top: 20px;
  margin-bottom: 40px;
}
</style>
