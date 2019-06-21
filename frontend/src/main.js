import Vue from "vue";
import App from "./App.vue";
import router from "./router";

import MaterialKit from "./plugins/material-kit";
import VueHighlightJS from 'vue-highlight.js';
import Axios from 'axios'
import Url from './config'

Vue.config.productionTip = false;

Vue.use(MaterialKit);
Vue.use(VueHighlightJS);

Vue.prototype.$axios = Axios;
Vue.prototype.Url = Url;

const NavbarStore = {
  showNavbar: false
};

Vue.mixin({
  data() {
    return {
      NavbarStore
    };
  }
});

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");
