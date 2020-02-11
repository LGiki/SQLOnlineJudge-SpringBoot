<template>
  <div class="wrapper">
    <div class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <div
            class="md-layout-item md-size-33 md-small-size-66 md-xsmall-size-100 md-medium-size-40 mx-auto"
          >
            <login-card header-color="green">
              <h2 slot="title" class="card-title">用户登录</h2>
              <p slot="description" class="description">&nbsp;</p>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>face</md-icon>
                <label>用户名/学号</label>
                <md-input v-model="username"></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>lock_outline</md-icon>
                <label>密码</label>
                <md-input
                  @keyup.enter.native="login"
                  v-model="password"
                  type="password"
                ></md-input>
              </md-field>
              <md-button
                @click="login"
                slot="footer"
                class="md-simple md-success md-lg"
                >登录</md-button
              >
            </login-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { LoginCard } from "@/components";
import qs from "qs";

export default {
  components: {
    LoginCard
  },
  bodyClass: "login-page",
  data() {
    return {
      username: "",
      password: ""
    };
  },
  props: {
    header: {
      type: String,
      default: require("@/assets/img/profile_city.jpg")
    }
  },
  methods: {
    isEmpty(obj) {
      if (typeof obj == "undefined" || obj == null || obj == "") {
        return true;
      } else {
        return false;
      }
    },
    login() {
      if (this.isEmpty(this.username) || this.isEmpty(this.password)) {
        alert("请检查所有项是否都填写完整！");
        return;
      }
      let postData = qs.stringify({
        username: this.username.trim(),
        password: this.password
      });
      this.$axios
        .post(this.Url.login, postData)
        .then(res => {
          if (res.status === 401) {
            alert(res.data.message);
          } else if (res.status === 200) {
            localStorage.setItem("JWT_TOKEN", res.data.data.token);
            localStorage.setItem("USER_ID", res.data.data.id);
            window.location.href = "/";
          } else {
            alert("登录失败，内部错误!");
          }
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`
      };
    }
  },
  created: function() {}
};
</script>

<style lang="css"></style>
