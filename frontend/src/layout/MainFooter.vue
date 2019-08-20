<template>
  <footer
    class="footer"
    :class="{ [`footer-${type}`]: type }"
    :data-background-color="backgroundColor"
  >
    <div class="copyright justify-content-center ft" align="center">
      &copy; {{ year }}, made with
      <md-icon>favorite</md-icon>by
      <a href="https://www.creative-tim.com/" target="_blank">Creative Tim</a>
      for a better web.
    </div>
  </footer>
</template>
<script>
export default {
  props: {
    backgroundColor: String,
    type: String
  },
  data() {
    return {
      year: new Date().getFullYear()
    };
  }
};
</script>
<style lang="scss" scoped>
.justify-content-center {
  justify-content: center !important;
}
.ft {
  width: 100%;
}
</style>

