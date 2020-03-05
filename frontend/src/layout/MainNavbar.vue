<template>
  <md-toolbar
    id="toolbar"
    md-elevation="0"
    class="md-transparent md-absolute"
    :class="extraNavClasses"
    :color-on-scroll="colorOnScroll"
  >
    <div class="md-toolbar-row md-collapse-lateral">
      <div class="md-toolbar-section-start">
        <h3 class="md-title">
          SQL自动评测系统
        </h3>
      </div>
      <div class="md-toolbar-section-end">
        <md-button
          class="md-just-icon md-simple md-toolbar-toggle"
          :class="{ toggled: toggledClass }"
          @click="toggleNavbarMobile()"
        >
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </md-button>

        <div class="md-collapse">
          <div class="md-collapse-wrapper">
            <mobile-menu nav-mobile-section-start="false">
              <!-- Here you can add your items from the section-start of your toolbar -->
            </mobile-menu>
            <md-list>
              <md-list-item href="#/">
                <i class="material-icons">home</i>
                <p>
                  <b>首页</b>
                </p>
              </md-list-item>
              <md-list-item href="#/problem">
                <i class="material-icons">content_paste</i>
                <p>
                  <b>题库</b>
                </p>
              </md-list-item>

              <md-list-item href="#/ranklist">
                <i class="material-icons">assessment</i>
                <p>
                  <b>排行榜</b>
                </p>
              </md-list-item>

              <md-list-item href="#/solution">
                <i class="material-icons">info</i>
                <p>
                  <b>提交状态</b>
                </p>
              </md-list-item>
              <template v-if="!userDetail">
                <md-list-item href="#/register">
                  <i class="material-icons">how_to_reg</i>
                  <p>
                    <b>注册</b>
                  </p>
                </md-list-item>
                <md-list-item href="#/login">
                  <i class="material-icons">assignment_ind</i>
                  <p>
                    <b>登录</b>
                  </p>
                </md-list-item>
              </template>
              <template v-else>
                <md-list-item :href="`#/profile/${userDetail.id}`">
                  <i class="material-icons">person</i>
                  <p>
                    <b>{{ userDetail.username }}</b>
                  </p>
                </md-list-item>
                <md-list-item href="#" @click="logout">
                  <i class="material-icons">power_settings_new</i>
                  <p>
                    <b>注销</b>
                  </p>
                </md-list-item>
              </template>
            </md-list>
          </div>
        </div>
      </div>
    </div>
  </md-toolbar>
</template>

<script>
let resizeTimeout;
function resizeThrottler(actualResizeHandler) {
  // ignore resize events as long as an actualResizeHandler execution is in the queue
  if (!resizeTimeout) {
    resizeTimeout = setTimeout(() => {
      resizeTimeout = null;
      actualResizeHandler();

      // The actualResizeHandler will execute at a rate of 15fps
    }, 66);
  }
}

import MobileMenu from "@/layout/MobileMenu";
export default {
  components: {
    MobileMenu
  },
  props: {
    type: {
      type: String,
      default: "white",
      validator(value) {
        return [
          "white",
          "default",
          "primary",
          "danger",
          "success",
          "warning",
          "info"
        ].includes(value);
      }
    },
    colorOnScroll: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      extraNavClasses: "",
      toggledClass: false,
      userDetail: null
    };
  },
  computed: {
    showDownload() {
      const excludedRoutes = ["login", "landing", "profile"];
      return excludedRoutes.every(r => r !== this.$route.name);
    }
  },
  methods: {
    logout() {
      localStorage.removeItem("JWT_TOKEN");
      localStorage.removeItem("USER_ID");
      this.userDetail = null;
    },
    fetchUserInformation(userId) {
      this.$axios
        .get(this.Url.userDetail + userId)
        .then(res => {
          if (res.status !== 200) {
            // alert("获取用户详细信息失败，内部错误！");
            this.logout();
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
    },
    bodyClick() {
      let bodyClick = document.getElementById("bodyClick");

      if (bodyClick === null) {
        let body = document.querySelector("body");
        let elem = document.createElement("div");
        elem.setAttribute("id", "bodyClick");
        body.appendChild(elem);

        let bodyClick = document.getElementById("bodyClick");
        bodyClick.addEventListener("click", this.toggleNavbarMobile);
      } else {
        bodyClick.remove();
      }
    },
    toggleNavbarMobile() {
      this.NavbarStore.showNavbar = !this.NavbarStore.showNavbar;
      this.toggledClass = !this.toggledClass;
      this.bodyClick();
    },
    handleScroll() {
      let scrollValue =
        document.body.scrollTop || document.documentElement.scrollTop;
      let navbarColor = document.getElementById("toolbar");
      this.currentScrollValue = scrollValue;
      if (this.colorOnScroll > 0 && scrollValue > this.colorOnScroll) {
        this.extraNavClasses = `md-${this.type}`;
        navbarColor.classList.remove("md-transparent");
      } else {
        if (this.extraNavClasses) {
          this.extraNavClasses = "";
          navbarColor.classList.add("md-transparent");
        }
      }
    },
    scrollListener() {
      resizeThrottler(this.handleScroll);
    },
    scrollToElement() {
      let element_id = document.getElementById("downloadSection");
      if (element_id) {
        element_id.scrollIntoView({ block: "end", behavior: "smooth" });
      }
    }
  },
  mounted() {
    document.addEventListener("scroll", this.scrollListener);
    if (localStorage.JWT_TOKEN && localStorage.USER_ID) {
      this.fetchUserInformation(localStorage.USER_ID);
    }
  },
  beforeDestroy() {
    document.removeEventListener("scroll", this.scrollListener);
  }
};
</script>
<style lang="scss" scoped></style>
