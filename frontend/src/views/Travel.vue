<template>
  <div class="main-view">
    <div class="map-container">
      <DragDropSchedule @schedule-created="handleScheduleCreated"/>
    </div>
  </div>
</template>

<script>
import DragDropSchedule from "@/components/DragDropSchedule.vue";

export default {
  components: { DragDropSchedule },
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
  justify-content: center;
  min-height: 90vh;
  background-color: white;
  padding: 20px;
}

.content-container {
  display: flex;
  max-width: 1800px;
  width: 100%;
  gap: 20px;
  margin: 0 auto;
}

.sidebar {
  width: 300px;
  min-width: 300px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.map-container {
  flex: 1;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  min-width: 0; /* Prevents flex items from overflowing */
}

/* 반응형 디자인 */
@media (max-width: 1200px) {
  .content-container {
    flex-direction: column;
  }

  .sidebar {
    width: 100%;
    min-width: 0;
  }

  .map-container {
    width: 100%;
  }
}
</style>