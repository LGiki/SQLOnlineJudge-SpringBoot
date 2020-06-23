import Vue from "vue";
import Router from "vue-router";
import Index from "./views/Index.vue";
import Login from "./views/Login.vue";
import Profile from "./views/Profile.vue";
import MainNavbar from "./layout/MainNavbar.vue";
import MainFooter from "./layout/MainFooter.vue";
import EmptyFooter from "./layout/EmptyFooter.vue"
import SubmitStatus from "./views/SubmitStatus.vue";
import ProblemDetail from "./views/ProblemDetail.vue";
import ProblemCategory from "./views/ProblemCategory.vue";
import ProblemCategoryDetail from "./views/ProblemCategoryDetail.vue";
Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "index",
      components: { default: Index, header: MainNavbar, footer: EmptyFooter },
      props: {
        header: { colorOnScroll: 200 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/problem_category",
      name: "problemCategoryList",
      components: {
        default: ProblemCategory,
        header: MainNavbar,
        footer: MainFooter
      },
      props: {
        header: { colorOnScroll: 80 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/problem_category/:id",
      name: "problemCategory",
      components: {
        default: ProblemCategoryDetail,
        header: MainNavbar,
        footer: MainFooter
      },
      props: {
        header: { colorOnScroll: 80 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/problem/:categoryId/:problemId",
      name: "problemDetail",
      components: {
        default: ProblemDetail,
        header: MainNavbar,
        footer: MainFooter
      },
      props: {
        header: { colorOnScroll: 80 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/submit_status/:categoryId",
      name: "solution",
      components: { default: SubmitStatus, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 80 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/login",
      name: "login",
      components: { default: Login, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 80 }
      }
    },
    {
      path: "/profile/:id",
      name: "profile",
      components: { default: Profile, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 200 },
        footer: { backgroundColor: "black" }
      }
    }
  ],
  scrollBehavior: to => {
    if (to.hash) {
      return { selector: to.hash };
    } else {
      return { x: 0, y: 0 };
    }
  }
});
