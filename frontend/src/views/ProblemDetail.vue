<template>
  <div class="wrapper">
    <parallax class="section header-filter" :style="headerStyle"></parallax>
    <div class="main main-raised">
      <div class="section no-padding">
        <div class="md-layout problem-detail-main">
          <div class="md-layout-item" id="problem-detail-area">
            <div class="md-layout">
              <div class="text-center" style="width:100%">
                <h4>
                  <strong>{{ problemDetail.title }}</strong>
                </h4>
              </div>
            </div>
            <md-content class="md-scrollbar">
              <h4><strong>题目描述</strong></h4>
              <div v-html="problemDetail.description" />
              <h4><strong>建表语句</strong></h4>
              <highlight-code lang="sql">{{
                problemDetail.createTable
              }}</highlight-code>
              <template v-if="problemDetail.sampleOutput">
                <h4><strong>表的样例</strong></h4>
                <div v-html="problemDetail.sampleOutput" />
              </template>
              <template v-if="problemDetail.hint">
                <h4><strong>提示</strong></h4>
                <div v-html="problemDetail.hint" />
              </template>
            </md-content>
          </div>
          <div class="md-layout-item" id="submit-area">
            <template v-if="isLogin">
              <div class="text-center" style="width:100%">
                <h4><strong>提交代码</strong></h4>
              </div>
              <codemirror
                id="codemirror"
                v-model="code"
                :options="cmOptions"
                @ready="onCmReady"
              ></codemirror>
              <div style="width:100%;padding-top:20px">
                <div class="md-layout text-center justify-content-center">
                  <md-button class="md-info md-lg" @click="runCode"
                    >调试运行</md-button
                  >&nbsp;&nbsp;&nbsp;
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
                <div class="text-left">
                  <p><strong>代码：</strong></p>
                  <highlight-code lang="sql">{{ code }}</highlight-code>
                  <p><strong>运行结果：</strong></p>
                  <highlight-code>{{ runResult }}</highlight-code>
                </div>
              </template>
              <template slot="footer">
                <md-button class="md-danger" @click="runResultModalHide"
                  >关闭</md-button
                >
                &nbsp;&nbsp;&nbsp;
                <md-button class="md-success" @click="submitSolution"
                  >提交代码</md-button
                >
              </template>
            </modal>
            <modal v-if="judgeResultModal" @close="judgeResultModalHide">
              <template slot="header">
                <h4 class="modal-title">判题结果</h4>
                <md-button
                  class="md-simple md-just-icon md-round modal-default-button"
                  @click="judgeResultModalHide"
                >
                  <md-icon>clear</md-icon>
                </md-button>
              </template>
              <template slot="body">
                <div class="text-left">
                  <p><strong>代码：</strong></p>
                  <highlight-code lang="sql">
                    {{ code }}
                  </highlight-code>
                  <p><strong>判题状态：</strong></p>
                  <p v-if="judgeResult == 'Judging'">
                    <i class="material-icons rotate img-middle">sync</i
                    ><strong>正在判题…</strong>
                  </p>
                  <p v-else-if="judgeResult == 'Unknown'">
                    <i class="material-icons img-middle">error_outline</i
                    ><strong>判题失败，判题状态未知</strong>
                  </p>
                  <p v-else-if="judgeResult == 'Accepted'">
                    <i class="material-icons img-middle">check</i
                    ><font color="blue"><strong>Accepted</strong></font>
                  </p>
                  <p v-else-if="judgeResult == 'Compile Error'">
                    <i class="material-icons img-middle">warning</i
                    ><font color="green"><strong>Compile Error</strong></font>
                  </p>
                  <p v-else-if="judgeResult == 'Wrong Answer'">
                    <i class="material-icons img-middle">close</i
                    ><font color="red"><strong>Wrong Answer</strong></font>
                  </p>
                  <p v-else-if="judgeResult == 'System Error'">
                    <i class="material-icons img-middle">error_outline</i
                    ><strong>判题失败，系统内部错误</strong>
                  </p>
                  <p v-else-if="judgeResult == 'Failed'">
                    <i class="material-icons img-middle">error_outline</i
                    ><strong>判题失败</strong>
                  </p>
                  <div v-if="judgeResult == 'Compile Error'">
                    <p><strong>错误详情：</strong></p>
                    <highlight-code lang="sql">
                      {{ runError }}
                    </highlight-code>
                  </div>
                </div>
              </template>
              <template slot="footer">
                <md-button class="md-danger" @click="judgeResultModalHide"
                  >关闭</md-button
                >
              </template>
            </modal>
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
      default: require("@/assets/img/bg7.jpg")
    }
  },
  data() {
    return {
      runError: "",
      runResult: "",
      judgeResult: "",
      runResultModal: false,
      judgeResultModal: false,
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
        line: true,
        matchBrackets: true
      },
      intervalId: "",
      problemDetail: {},
      createTableCode: "",
      isLogin: localStorage.JWT_TOKEN != null
    };
  },
  methods: {
    isEmpty(obj) {
      if (typeof obj == "undefined" || obj == null || obj == "") {
        return true;
      } else {
        return false;
      }
    },
    getLatestSolution() {
      if (localStorage.JWT_TOKEN && localStorage.USER_ID) {
        let userId = localStorage.USER_ID;
        const apiUrl = this.Url.latestSolution;
        this.$axios
          .get(apiUrl + this.problemCategoryId + "/" + this.problemId)
          .then(res => {
            if (res.status !== 200) {
              console.log(res);
            } else {
              if (res.data.code === 0) {
                let latestCode = res.data.data.sourceCode;
                if (!this.isEmpty(latestCode)) {
                  this.code = latestCode;
                }
              } else {
                console.log(res.data.message);
              }
            }
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    cancelInterval() {
      if (!this.isEmpty(this.intervalId)) {
        clearInterval(intervalId);
      }
    },
    fetchSolutionCode(solutionId) {
      let apiUrl = this.Url.solutionCode;
      this.$axios
        .get(apiUrl + solutionId)
        .then(res => {
          this.cancelInterval();
          if (res.status !== 200) {
            this.judgeResult = "Failed";
            console.log(res);
          } else {
            let resData = res.data;
            if (resData.code === 0) {
              this.judgeResult = resData.data.result;
              this.runError = resData.data.runError;
            } else {
              this.judgeResult = "Failed";
              console.log(resData.message);
            }
          }
        })
        .catch(err => {
          this.cancelInterval();
          this.judgeResult = "Failed";
          console.log(err);
        });
    },
    runCode() {
      if (this.isEmpty(this.code)) {
        this.$notify({
          group: "notify",
          text: "请检查SQL代码是否已输入",
          type: "error"
        });
        return;
      }
      const apiUrl = this.Url.runCode;
      this.$axios
        .post(apiUrl, {
          pid: this.problemDetail.id,
          sourceCode: this.code
        })
        .then(res => {
          if (res.status !== 200) {
            this.$notify({
              group: "notify",
              text: "调试运行失败：远程服务器错误",
              type: "error"
            });
          } else {
            this.runResult = "";
            let resData = res.data;
            if (resData.code === 0) {
              let runResult = resData.data;
              let reg = /\((.*?)\)/g;
              let res = reg.exec(runResult);
              while (res) {
                this.runResult += res[0] + "\r\n";
                res = reg.exec(runResult);
              }
              this.runResultShow();
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
            text: "调试运行失败：发送请求失败",
            type: "error"
          });
          console.log(err);
        });
    },
    runResultModalHide() {
      this.runResultModal = false;
    },
    judgeResultModalHide() {
      this.judgeResultModal = false;
    },
    runResultShow() {
      this.runResultModal = true;
    },
    onCmReady(cm) {
      cm.on("keypress", () => {
        cm.showHint();
      });
    },
    getProblemDetail(categoryId, problemId) {
      let apiUrl = this.Url.problemBaseUrl;
      this.$axios
        .get(apiUrl + "/" + categoryId + "/" + problemId)
        .then(res => {
          if (res.status !== 200) {
            this.$notify({
              group: "notify",
              text: "获取题目详情失败：远程服务器错误",
              type: "error"
            });
          } else {
            let resData = res.data;
            if (resData.code === 0) {
              this.problemDetail = resData.data;
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
          console.log(err);
          this.$notify({
            group: "notify",
            text: "获取题目详情失败：发送请求失败",
            type: "error"
          });
        });
    },
    submitSolution() {
      if (this.isEmpty(this.code)) {
        this.$notify({
          group: "notify",
          text: "请检查SQL代码是否已输入",
          type: "error"
        });
        return;
      }
      this.judgeResult = "Judging";
      this.runError = "";
      const apiUrl = this.Url.solutionSubmit;
      const problemId = this.$route.params.problemId;
      let postData = {
        pid: problemId,
        sourceCode: this.code
      };
      this.$axios
        .post(apiUrl, postData)
        .then(res => {
          if (res.status !== 200) {
            this.$notify({
              group: "notify",
              text: "提交解答代码失败：远程服务器错误",
              type: "error"
            });
          } else {
            let resData = res.data;
            if (resData.code === 0) {
              let solutionId = resData.data.solutionId;
              setInterval(this.fetchSolutionCode(solutionId), 5000);
              this.judgeResultModal = true;
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
          console.log(err);
          this.$notify({
            group: "notify",
            text: "提交解答代码失败：发送请求失败",
            type: "error"
          });
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
    },
    problemCategoryId() {
      return this.$route.params.categoryId;
    },
    problemId() {
      return this.$route.params.problemId;
    }
  },
  mounted: function() {
    this.getProblemDetail(this.problemCategoryId, this.problemId);
    this.getLatestSolution();
  }
};
</script>

<style lang="scss" scoped>
.img-middle {
  vertical-align: middle;
}

.rotate {
  -webkit-transition-property: -webkit-transform;
  -webkit-transition-duration: 1s;
  -moz-transition-property: -moz-transform;
  -moz-transition-duration: 1s;
  -webkit-animation: rotate 3s linear infinite;
  -moz-animation: rotate 3s linear infinite;
  -o-animation: rotate 3s linear infinite;
  animation: rotate 3s linear infinite;
}

@-webkit-keyframes rotate {
  from {
    -webkit-transform: rotate(0deg);
  }
  to {
    -webkit-transform: rotate(-360deg);
  }
}

@-moz-keyframes rotate {
  from {
    -moz-transform: rotate(0deg);
  }
  to {
    -moz-transform: rotate(-359deg);
  }
}

@-o-keyframes rotate {
  from {
    -o-transform: rotate(0deg);
  }
  to {
    -o-transform: rotate(-359deg);
  }
}

@keyframes rotate {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(-359deg);
  }
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

.md-content {
  max-height: 550px;
  overflow: auto;
}

.no-padding {
  padding: 0 !important;
}

.problem-detail-main {
  padding: 5px 0;
  margin: 0px 5px;
}
</style>
<style lang="scss">
.CodeMirror {
  height: 500px !important;
}
</style>
