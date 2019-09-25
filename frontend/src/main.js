import Vue from "vue";
import App from "./App.vue";
import router from "./router";

import MaterialKit from "./plugins/material-kit";
import VueHighlightJS from "vue-highlight.js";
import Axios from "axios";
import Url from "./urlConfig";
import { exists } from "fs";
import Notifications from "./components/NotificationPlugin";

Vue.config.productionTip = false;

Vue.use(MaterialKit);
Vue.use(VueHighlightJS);
Vue.use(Notifications);

Axios.interceptors.request.use(
  config => {
    if (localStorage.JWT_TOKEN) {
      // 判断是否存在token，如果存在的话，则每个http header都加上token
      config.headers.Authorization = `Bearer ${localStorage.JWT_TOKEN}`;
    }
    return config;
  },
  err => {
    return Promise.reject(err);
  }
);

Axios.interceptors.response.use(
  response => {
    if (response.status === 403) {
      this.$router.push({ path: "/login" });
    }
    return response;
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          if (router.history.current.name !== "login") {
            router.replace({
              path: "/login"
            });
            location.reload();
          } else {
            return Promise.resolve(error.response)
          }
      }
    }
    return Promise.reject(error);
  }
);

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
