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
          <p>{{ problemDetail.description }}</p>
          <h3>建表语句</h3>
          <highlight-code lang="sql">{{ createTableCode }}</highlight-code>
          <template v-if="problemDetail.inputFormat">
            <h3>输入格式</h3>
            <p>{{ problemDetail.inputFormat }}</p>
          </template>
          <template v-if="problemDetail.outputFormat">
            <h3>输出格式</h3>
            <p>{{ problemDetail.outputFormat }}</p>
          </template>
          <template v-if="problemDetail.sampleInput">
            <h3>样例输入</h3>
            <p>{{ problemDetail.sampleInput }}</p>
          </template>
          <template v-if="problemDetail.sampleOutput">
            <h3>样例输出</h3>
            <p>{{ problemDetail.sampleOutput }}</p>
          </template>
          <template v-if="problemDetail.hint">
            <h3>提示</h3>
            <p>{{ problemDetail.hint }}</p>
          </template>
          <h3>提交代码</h3>
          <codemirror v-model="code" :options="cmOptions" @ready="onCmReady"></codemirror>
          <div style="width:100%;padding-top:20px">
            <div class="md-layout text-center justify-content-center">
              <md-button class="md-info md-lg">运行</md-button>&nbsp;&nbsp;&nbsp;
              <md-button class="md-success md-lg">提交</md-button>
            </div>
          </div>
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

export default {
  components: {
    VueHighlightJS,
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
      createTableCode: ""
    };
  },
  methods: {
    onCmReady(cm) {
      cm.on("keypress", () => {
        cm.showHint();
      });
    },
    getProblemDetail(problemId) {
      let apiUrl = this.Url.problemDetail;
      this.$axios
        .get(apiUrl + problemId)
        .then(res => {
          if (res.status !== 200) {
            alert("Network error");
          } else {
            let resData = res.data;
            if (resData.code === 200) {
              this.problemDetail = resData.data;
            }
            this.getCreateTableCode(this.problemDetail.databaseId);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    getCreateTableCode(databaseId) {
      let apiUrl = this.Url.databaseDetail;
      this.$axios
        .get(apiUrl + databaseId)
        .then(res => {
          if (res.status !== 200) {
            alert("Network error");
          } else {
            let resData = res.data;
            if (resData.code === 200) {
              this.createTableCode = resData.data.createTable;
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
