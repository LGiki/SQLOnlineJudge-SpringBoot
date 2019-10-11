<template>
  <div class="wrapper">
    <div class="section page-header header-filter" :style="headerStyle"></div>
    <div class="main main-raised">
      <div class="section">
        <div class="container">
          <div class="md-layout">
            <div class="text-center" style="width:100%">
              <h2 class="title">{{ problemDetail.title }}</h2>
            </div>
          </div>
          <h3>题目描述</h3>
          <div v-html="problemDetail.description" />
          <h3>建表语句</h3>
          <highlight-code lang="sql">{{
            problemDetail.createTable
          }}</highlight-code>
          <template v-if="problemDetail.sampleOutput">
            <h3>样例输出</h3>
            <div v-html="problemDetail.sampleOutput" />
          </template>
          <template v-if="problemDetail.hint">
            <h3>提示</h3>
            <div v-html="problemDetail.hint" />
          </template>
          <template v-if="isLogin">
            <h3>提交代码</h3>
            <codemirror
              v-model="code"
              :options="cmOptions"
              @ready="onCmReady"
            ></codemirror>
            <div style="width:100%;padding-top:20px">
              <div class="md-layout text-center justify-content-center">
                <md-button class="md-info md-lg" @click="runCode">调试运行</md-button>&nbsp;&nbsp;&nbsp;
                <md-button class="md-success md-lg" @click="submitSolution"
                  >提交</md-button
                >
              </div>
            </div>
          </template>
          <template v-else>
            <div style="width:100%;padding-top:20px">
              <div class="text-center justify-content-center">
                <p>您还未登录，请登录后再提交代码。</p>
                <br />
                <md-button class="md-success md-lg" href="#/login"
                  >登录</md-button
                >
              </div>
            </div>
          </template>
          <modal v-if="runResultModal" @close="runResultModalHide">
          <template slot="header">
            <h4 class="modal-title">调试运行结果</h4>
            <md-button
              class="md-simple md-just-icon md-round modal-default-button"
              @click="runResultModalHide"
            >
              <md-icon>clear</md-icon>
            </md-button>
          </template>
          <template slot="body">
            <p>
              <highlight-code>{{ runResult }}</highlight-code>
            </p>
          </template>
          <template slot="footer">
            <md-button class="md-danger md-simple" @click="runResultModalHide"
              >关闭</md-button
            >
          </template>
        </modal>
        </div>
      </div>
      <div class="section section-with-padding">
        <div class="container">
          <div class="features text-center">
            <div class="md-layout"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import VueHighlightJS from "vue-highlight.js";
import "vue-highlight.js/lib/allLanguages";
import "highlight.js/styles/atom-one-light.css";
import { codemirror } from "vue-codemirror";
import "codemirror/lib/codemirror.css";
import "codemirror/mode/sql/sql.js";
import "codemirror/theme/solarized.css";
import "codemirror/addon/hint/show-hint.js";
import "codemirror/addon/hint/sql-hint.js";
import "codemirror/addon/edit/matchbrackets.js";
import "codemirror/addon/hint/show-hint.css";
import { Modal } from "@/components";

export default {
  components: {
    VueHighlightJS,
    Modal,
    codemirror
  },
  bodyClass: "profile-page",
  props: {
    header: {
      type: String,
      default: require("@/assets/img/city-profile.jpg")
    }
  },
  data() {
    return {
      runResult: '',
      runResultModal: false,
      code: "",
      cmOptions: {
        tabSize: 4,
        mode: "text/x-mysql",
        theme: "solarized light",
        lineNumbers: true,
        styleActiveLine: true,
        lineWrapping: true,
        indentWithTabs: true,
        hintOptions: {
          completeSingle: false
        },
        line: true
      },
      problemDetail: {},
      createTableCode: "",
      isLogin: localStorage.JWT_TOKEN != null
    };
  },
  methods: {
    runCode() {
      const apiUrl = this.Url.runCode;
      this.$axios
        .post(apiUrl, {
          'pid': this.problemDetail.id,
          'sourceCode': this.code
        })
        .then(res => {
          if (res.status !== 200) {
            alert("调试运行失败，内部错误！");
          } else {
            this.runResult = ''
            let resData = res.data;
            if (resData.code === 0) {
              let runResult = resData.data;
              let reg = /\((.*?)\)/g;
              let res = reg.exec(runResult);
              while (res) {
                this.runResult += res[0] + '\r\n';
                res = reg.exec(runResult);
              }
              this.runResultShow();
            } else {
              alert(resData.message);
            }
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    runResultModalHide() {
      this.runResultModal = false;
    },
    runResultShow() {
      this.runResultModal = true;
    },
    onCmReady(cm) {
      cm.on("keypress", () => {
        cm.showHint();
      });
    },
    getProblemDetail(problemId) {
      let apiUrl = this.Url.problemBaseUrl;
      this.$axios
        .get(apiUrl + "/" + problemId)
        .then(res => {
          if (res.status !== 200) {
            alert("获取题目详情失败，内部错误！");
          } else {
            let resData = res.data;
            if (resData.code === 0) {
              this.problemDetail = resData.data;
            } else {
              alert(resData.message);
            }
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    submitSolution() {
      const apiUrl = this.Url.solutionSubmit;
      const problemId = this.$route.params.id;
      let postData = {
        pid: problemId,
        sourceCode: this.code
      };
      this.$axios
        .post(apiUrl, postData)
        .then(res => {
          if (res.status !== 200) {
            alert("提交解答代码失败，内部错误！");
          } else {
            let resData = res.data;
            if (resData.code === 0) {
              alert("解答提交成功！");
              this.$router.push({ path: "/solution/" });
            } else {
              alert(resData.message);
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
    },
    codemirror() {
      return this.$refs.myCm.codemirror;
    }
  },
  mounted: function() {
    let problemId = this.$route.params.id;
    this.getProblemDetail(problemId);
  }
};
</script>

<style lang="scss" scoped>
.section {
  padding: 0;
}

.section-with-padding {
  padding-bottom: 40px;
}

.profile-page .page-header {
  height: 200px;
  background-position: center center;
}

.justify-content-center {
  justify-content: center !important;
}
</style>
