import Vue from "vue";
import App from "./App.vue";
import router from "./router";

import MaterialKit from "./plugins/material-kit";
import VueHighlightJS from 'vue-highlight.js';
import Axios from 'axios'
import Url from './urlConfig'

Vue.config.productionTip = false;

Vue.use(MaterialKit);
Vue.use(VueHighlightJS);

Axios.interceptors.request.use(
    config => {
      // if (localStorage.JWT_TOKEN) {  // 判断是否存在token，如果存在的话，则每个http header都加上token
      //   config.headers.Authorization = `token ${localStorage.JWT_TOKEN}`;
      // }
      config.headers.Authorization = 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImxvZ2luVHlwZSI6IkFkbWluIiwiZXhwIjoxNTY2OTQyNjMwLCJpYXQiOjE1NjY4NzA2MzB9.0CONq-yrmnfN1MENNPvismxO8X2Ms1mUwbW8FJ4Yke49u8A_4SpxGKL5xnvwWT_4C80YSTwrz80vKGJ1SRks8Q'
      return config
    },
    err => {
      return Promise.reject(err)
    }
)

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
