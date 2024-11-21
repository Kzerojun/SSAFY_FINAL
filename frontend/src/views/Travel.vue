<template>
  <div class="main-view">
    <div class="sidebar">

      <DragDropSchedule
          :schedule="schedule"
          @update:schedule="updateSchedule"
      />
    </div>
    <div class="map-container">
      <KakaoMap />
    </div>
  </div>
</template>

<script>
import DragDropSchedule from "@/components/DragDropSchedule.vue";
import KakaoMap from "@/components/KakaoMap.vue";

export default {
  components: { DragDropSchedule, KakaoMap },
  data() {
    return {
      recommendations: [
        { id: 1, title: "도쿄 타워", description: "랜드마크", image: "..." },
        { id: 2, title: "시부야 스카이", description: "전망대", image: "..." },
      ],
      schedule: [],
    };
  },
  methods: {
    handleSearch(query) {
      console.log("Search:", query);
    },
    updateSchedule(newSchedule) {
      this.schedule = newSchedule;
    },
  },
};
</script>

<style scoped>
.main-view {
  display: flex;
}

.sidebar {
  width: 60%;
  height: 100%;
  padding: 10px;
  overflow-y: auto;
}

.map-container {
  padding : 20px;
  width: 100%;
}
</style>
