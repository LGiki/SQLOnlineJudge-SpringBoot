import Vue from "vue";
import Router from "vue-router";
import Index from "./views/Index.vue";
import Login from "./views/Login.vue";
import Profile from "./views/Profile.vue";
import MainNavbar from "./layout/MainNavbar.vue";
import MainFooter from "./layout/MainFooter.vue";
import Problem from "./views/Problem.vue";
import Ranklist from "./views/Ranklist.vue";
import Solution from "./views/Solution.vue";
import ProblemDetail from "./views/ProblemDetail.vue";
// import Register from "./views/Register.vue";
import ProblemCategory from "./views/ProblemCategory.vue";
import ProblemCategoryDetail from "./views/ProblemCategoryDetail.vue";
Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "index",
      components: { default: Index, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 200 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/problem-category",
      name: "problemCategory",
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
      path: "/problem-category/:id",
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
    // {
    //   path: "/problem",
    //   name: "problem",
    //   components: { default: Problem, header: MainNavbar, footer: MainFooter },
    //   props: {
    //     header: { colorOnScroll: 80 },
    //     footer: { backgroundColor: "black" }
    //   }
    // },
    {
      path: "/problem/:id",
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
      path: "/ranklist",
      name: "ranklist",
      components: { default: Ranklist, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 80 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/solution",
      name: "solution",
      components: { default: Solution, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 80 },
        footer: { backgroundColor: "black" }
      }
    },
    // {
    //   path: "/register",
    //   name: "register",
    //   components: { default: Register, header: MainNavbar, footer: MainFooter },
    //   props: {
    //     header: { colorOnScroll: 80 }
    //   }
    // },
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
