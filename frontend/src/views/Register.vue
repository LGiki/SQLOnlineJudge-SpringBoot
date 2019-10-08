<template>
  <div class="wrapper">
    <div class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <div
            class="md-layout-item md-size-33 md-small-size-66 md-xsmall-size-100 md-medium-size-40 mx-auto"
          >
            <login-card header-color="green">
              <h2 slot="title" class="card-title">用户注册</h2>
              <p slot="description" class="description">&nbsp;</p>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>face</md-icon>
                <label>用户名</label>
                <md-input v-model="username"></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>email</md-icon>
                <label>邮箱</label>
                <md-input v-model="email"></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>lock_outline</md-icon>
                <label>密码</label>
                <md-input v-model="password" type="password"></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>lock_outline</md-icon>
                <label>请重复密码</label>
                <md-input
                  @keyup.enter.native="register"
                  v-model="repassword"
                  type="password"
                ></md-input>
              </md-field>
              <md-button
                @click="register"
                slot="footer"
                class="md-simple md-success md-lg"
                >注册</md-button
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
      password: "",
      repassword: "",
      email: ""
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
    validateEmail(email) {
      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(String(email).toLowerCase());
    },
    register() {
      if (
        this.isEmpty(this.username) ||
        this.isEmpty(this.password) ||
        this.isEmpty(this.repassword) ||
        this.isEmpty(this.email)
      ) {
        alert("请确认表单输入完整！");
        return;
      }
      if (this.password !== this.repassword) {
        alert("两次密码输入不一致！");
        return;
      }
      if (!this.validateEmail(this.email)) {
        alert("邮箱地址格式错误！");
        return;
      }
      let postData = qs.stringify({
        email: this.email,
        username: this.username,
        password: this.password
      });
      this.$axios
        .post(this.Url.register, postData)
        .then(res => {
          if (res.status !== 200) {
            alert("注册失败，网路错误!");
          } else {
            let resData = res.data;
            if (resData.code === 0) {
              alert("注册成功，请登录！");
              this.$router.push({ path: "/login" });
            } else {
              alert("注册失败！");
            }
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
  }
};
</script>

<style lang="css"></style>
