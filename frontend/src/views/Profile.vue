<template>
  <div class="wrapper">
    <parallax
      class="section page-header header-filter"
      :style="headerStyle"
    ></parallax>
    <div class="main main-raised">
      <div class="section profile-content">
        <div class="container">
          <div class="md-layout">
            <div class="md-layout-item md-size-50 mx-auto">
              <div class="profile">
                <div class="avatar">
                  <img
                    :src="img"
                    alt="Circle Image"
                    class="img-raised rounded-circle img-fluid"
                  />
                </div>
                <div class="name">
                  <h3 class="title">{{ userDetail.username }}</h3>
                </div>
              </div>
            </div>
          </div>
          <div class="md-layout text-center">
            <md-button
              @click="modifyProfileModal = true"
              slot="footer"
              class="md-success md-lg"
              >修改密码</md-button
            >
          </div>
          <modal v-if="modifyProfileModal" @close="modifyProfileModalHide">
            <template slot="header">
              <h4 class="modal-title">修改密码</h4>
              <md-button
                class="md-simple md-just-icon md-round modal-default-button"
                @click="modifyProfileModalHide"
              >
                <md-icon>clear</md-icon>
              </md-button>
            </template>
            <template slot="body">
              <md-field>
                <md-icon>lock_outline</md-icon>
                <label>请输入新密码</label>
                <md-input
                  autofocus
                  v-model="newPassword"
                  type="password"
                ></md-input>
              </md-field>
              <md-field>
                <md-icon>lock_outline</md-icon>
                <label>请重复输入新密码</label>
                <md-input
                  @keyup.enter.native="submitModifyProfile"
                  v-model="newPasswordRepeat"
                  type="password"
                ></md-input>
              </md-field>
            </template>
            <template slot="footer">
              <md-button class="md-danger" @click="modifyProfileModalHide"
                >关闭</md-button
              >
              &nbsp;&nbsp;&nbsp;
              <md-button class="md-success" @click="submitModifyProfile"
                >确定</md-button
              >
            </template>
          </modal>
          <div class="md-layout">
            <h4>
              <b><md-icon>face</md-icon>&nbsp;用户ID</b>：{{ userDetail.id }}
            </h4>
          </div>
          <div class="md-layout">
            <h4>
              <b><md-icon>dialpad</md-icon>&nbsp;学号</b>：{{
                userDetail.studentNo
              }}
            </h4>
          </div>
          <div class="md-layout">
            <h4>
              <b><md-icon>email</md-icon>&nbsp;用户邮箱</b>：{{
                userDetail.email
              }}
            </h4>
          </div>
          <div class="md-layout">
            <h4>
              <b><md-icon>send</md-icon>&nbsp;提交数</b>：{{
                userDetail.submit
              }}
            </h4>
          </div>
          <div class="md-layout">
            <h4>
              <b><md-icon>check</md-icon>&nbsp;通过数</b>：{{
                userDetail.solved
              }}
            </h4>
          </div>
          <div class="md-layout">
            <h4>
              <b><md-icon>timeline</md-icon>&nbsp;通过率</b>：{{
                userDetail.submit == 0
                  ? 0
                  : (userDetail.solved / userDetail.submit).toFixed(2)
              }}
            </h4>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Tabs } from "@/components";
import { Modal } from "@/components";
export default {
  components: {
    Tabs,
    Modal
  },
  bodyClass: "profile-page",
  data() {
    return {
      modifyProfileModal: false,
      newPassword: "",
      newPasswordRepeat: "",
      userDetail: {
        username: null,
        id: 0,
        solved: 0,
        submit: 0,
        email: null
      }
    };
  },
  props: {
    header: {
      type: String,
      default: require("@/assets/img/nature-3.jpg")
    },
    img: {
      type: String,
      default: require("@/assets/img/faces/user.png")
    }
  },
  methods: {
    logout() {
      localStorage.removeItem("JWT_TOKEN");
      localStorage.removeItem("USER_ID");
      history.go(0);
    },
    isEmpty(obj) {
      if (typeof obj == "undefined" || obj == null || obj == "") {
        return true;
      } else {
        return false;
      }
    },
    submitModifyProfile() {
      if (
        this.isEmpty(this.newPassword) ||
        this.isEmpty(this.newPasswordRepeat)
      ) {
        this.$notify({
          group: "notify",
          text: "请检查输入是否完整",
          type: "error"
        });
        return;
      }
      if (this.newPassword !== this.newPasswordRepeat) {
        this.$notify({
          group: "notify",
          text: "两次输入的密码不一致，请检查后重新输入",
          type: "error"
        });
        return;
      }
      let userId = this.$route.params.id;
      this.$axios
        .put(this.Url.userDetail, {
          id: userId,
          password: this.newPassword
        })
        .then(res => {
          if (res.status !== 200) {
            this.$notify({
              group: "notify",
              text: "修改用户密码失败：远程服务器错误",
              type: "error"
            });
          } else {
            this.$notify({
              group: "notify",
              text: res.data.message,
              type: "error"
            });
            this.logout();
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    modifyProfileModalHide() {
      this.modifyProfileModal = false;
    },
    fetchUserInformation(userId) {
      this.$axios
        .get(this.Url.userDetail + userId)
        .then(res => {
          if (res.status !== 200) {
            this.$notify({
              group: "notify",
              text: "获取用户详细信息失败：远程服务器错误",
              type: "error"
            });
          } else {
            const resData = res.data;
            if (resData.code === 0) {
              this.userDetail = resData.data;
            } else {
              this.$notify({
                group: "notify",
                text: resData.message,
                type: "error"
              });
            }
          }
        })
        .catch(err => {
          this.$notify({
            group: "notify",
            text: "获取用户详细信息失败：发送请求失败",
            type: "error"
          });
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
  mounted: function() {
    let userId = this.$route.params.id;
    if (userId) {
      this.fetchUserInformation(userId);
    } else {
      this.$router.push({ path: "/login" });
    }
  }
};
</script>

<style lang="scss" scoped>
.text-center {
  margin: 0 auto;
}

.section {
  padding: 0;
}

.profile-tabs /deep/ {
  .md-card-tabs .md-list {
    justify-content: center;
  }

  [class*="tab-pane-"] {
    margin-top: 3.213rem;
    padding-bottom: 50px;

    img {
      margin-bottom: 2.142rem;
    }
  }
}
</style>
