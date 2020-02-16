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
          <div class="md-layout">
            <h4><b>用户ID</b>：{{ userDetail.id }}</h4>
          </div>
          <div class="md-layout">
            <h4><b>学号</b>：{{ userDetail.studentNo }}</h4>
          </div>
          <div class="md-layout">
            <h4><b>用户邮箱</b>：{{ userDetail.email }}</h4>
          </div>
          <div class="md-layout">
            <h4><b>提交数</b>：{{ userDetail.submit }}</h4>
          </div>
          <div class="md-layout">
            <h4><b>通过数</b>：{{ userDetail.solved }}</h4>
          </div>
          <div class="md-layout">
            <h4>
              <b>通过率</b>：{{
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
export default {
  components: {
    Tabs
  },
  bodyClass: "profile-page",
  data() {
    return {
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
      default: require("@/assets/img/city-profile.jpg")
    },
    img: {
      type: String,
      default: require("@/assets/img/faces/christian.jpg")
    }
  },
  methods: {
    fetchUserInformation(userId) {
      this.$axios
        .get(this.Url.userDetail + userId)
        .then(res => {
          if (res.status !== 200) {
            alert("获取用户详细信息失败，内部错误！");
          } else {
            const resData = res.data;
            if (resData.code === 0) {
              this.userDetail = resData.data;
            } else {
              alert(resData.message);
            }
          }
        })
        .catch(err => {
          alert("获取用户详细信息失败，未知错误！");
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
    this.fetchUserInformation(userId);
  }
};
</script>

<style lang="scss" scoped>
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
