<template>
  <div class="dashboard-container">
    <div class="dashboard-text">{{ welcome }}，欢迎使用SQL考试系统</div>
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
  computed: {
    welcome() {
      const currentHours = new Date().getHours()
      if (currentHours < 6) {
        return '凌晨好'
      } else if (currentHours < 9) {
        return '早上好'
      } else if (currentHours < 12) {
        return '上午好'
      } else if (currentHours < 14) {
        return '中午好'
      } else if (currentHours < 17) {
        return '下午好'
      } else if (currentHours < 18) {
        return '傍晚好'
      } else {
        return '晚上好'
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

.dashboard-text {
  color: #474b4c;
  text-shadow: 3px 3px 6px #ddd;
}
</style>
