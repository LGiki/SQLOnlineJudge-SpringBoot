<template>
  <div class="dashboard-container">
    <div class="dashboard-text">Welcome to SQL Online Judge</div>
    <panel-group />
  </div>
</template>

<script>
import PanelGroup from './components/PanelGroup'

export default {
  name: 'Dashboard',
  components: {
    PanelGroup
  },
  data() {
    return {
      systemDetail: {
        databaseCount: 0,
        problemCount: 0,
        userCount: 0,
        solutionCount: 0
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 45px;
    line-height: 60px;
    text-align: center;
  }
}
</style>
