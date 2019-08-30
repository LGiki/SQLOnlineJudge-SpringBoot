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
                <label>用户名</label>
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
    login() {
      let postData = qs.stringify({
        username: this.username,
        password: this.password
      });
      this.$axios
        .post(this.Url.login, postData)
        .then(res => {
          if (res.status !== 200) {
            alert("登录失败，网路错误!");
          } else {
            localStorage.setItem("JWT_TOKEN", res.data.data.token);
            localStorage.setItem("USER_ID", res.data.data.id);
            window.location.href = "/";
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
