<template>
  <div class="main-view">
    <div class="sidebar">
      <DragDropSchedule @schedule-created="handleScheduleCreated"/>
    </div>
    <!--
    <div class="map-container">
      <KakaoMap :locations="[]" />
    </div>
    -->
  </div>
</template>

<script>
import DragDropSchedule from "@/components/DragDropSchedule.vue";
import KakaoMap from "@/components/KakaoMap.vue";

export default {
  components: { DragDropSchedule, KakaoMap },
  methods: {
    handleScheduleCreated(scheduleData) {
      console.log('Schedule created:', scheduleData);
    }
  }
};
</script>

<style scoped>
.main-view {
  display: flex;
}

.sidebar {
  width: 100%;
  height: 100%;
  padding: 10px;
  overflow-y: auto;
}

.map-container {
  padding: 20px;
  width: 100%;
}
</style>