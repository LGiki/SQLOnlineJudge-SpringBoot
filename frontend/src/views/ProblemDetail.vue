<template>
  <div class="wrapper">
    <div class="section page-header header-filter" :style="headerStyle"></div>
    <div class="main main-raised">
      <div class="section">
        <div class="container">
          <div class="md-layout">
            <div class="text-center" style="width:100%">
              <h2 class="title">题目详情</h2>
            </div>
          </div>
          <h3>题目描述</h3>
          <p>查找最晚入职员工的所有信息</p>
          <h3>建表语句</h3>
          <highlight-code lang="sql">
            CREATE TABLE `employee` {
            `emp_no` int(11) NOT NULL,
            }
          </highlight-code>
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
      code: "select * from `test`",
      cmOptions: {
        tabSize: 4,
        mode: "text/x-mysql",
        theme: "solarized light",
        lineNumbers: true,
        styleActiveLine: true,
        lineWrapping: true,
        // autofocus: true,
        indentWithTabs: true,
        hintOptions: {
          completeSingle: false
        },
        line: true
      }
    };
  },
  methods: {
    onInputRead(instance) {
      if (instance.state.completionActive) return;
      var cur = instance.getCursor();
      var str = instance.getTokenAt(cur).string;
      if (str.length > 0 && str.match(/^[.`\w@]\w*$/)) {
        this.codemirror.commands.autocomplete(instance);
      }
    },
    onCmReady(cm) {
      cm.on("keypress", () => {
        cm.showHint();
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
