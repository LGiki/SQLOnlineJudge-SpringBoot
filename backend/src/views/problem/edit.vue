<template>
  <div class="app-container">
    <el-form ref="problemDetail" :model="problemDetail" :rules="checkRules" label-width="120px">
      <el-form-item label="题目ID">
        <el-input v-model="problemDetail.id" disabled />
      </el-form-item>
      <el-form-item label="题目标题" prop="title">
        <el-input v-model="problemDetail.title" />
      </el-form-item>
      <el-form-item label="数据库" prop="databaseId">
        <el-select v-model="problemDetail.databaseId" placeholder="选择数据库">
          <el-option
            v-for="databaseItem in databaseList"
            :key="databaseItem.id"
            :label="databaseItem.name"
            :value="databaseItem.id"
          />
        </el-select>
        <el-button v-if="problemDetail.databaseId" @click="showCreateTable(problemDetail.databaseId)">查看该数据库的建表语句</el-button>
      </el-form-item>
      <el-dialog
        title="查看建表语句"
        :visible.sync="dialogVisible"
        width="50%"
      >
        <highlight-code lang="sql">{{ createTable }}</highlight-code>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>
      <el-form-item label="题目描述" prop="description">
        <tinymce v-model="problemDetail.description" :height="250" />
      </el-form-item>
      <el-form-item label="样例输出" prop="sampleOutput">
        <tinymce v-model="problemDetail.sampleOutput" :height="250" />
      </el-form-item>
      <el-form-item label="提示" prop="hint">
        <tinymce v-model="problemDetail.hint" :height="250" />
      </el-form-item>
      <el-form-item>
        <el-button @click="updateAndDeleteProblemHintdialogVisible = true">查看答案填写说明</el-button>
      </el-form-item>
      <el-form-item label="答案" prop="answer">
        <codemirror v-model="problemDetail.answer" :options="cmOptions" @ready="onCmReady" />
        <el-input v-if="false" v-model="problemDetail.answer" placeholder="请输入答案" />
      </el-form-item>
      <el-dialog
        title="答案填写说明"
        :visible.sync="updateAndDeleteProblemHintdialogVisible"
        width="50%"
      >
        若有表如下：
        <highlight-code lang="sql">
          CREATE TABLE `employees` (
          `emp_no` int(11) NOT NULL,
          `birth_date` date NOT NULL,
          `first_name` varchar(14) NOT NULL,
          `last_name` varchar(16) NOT NULL,
          `gender` char(1) NOT NULL,
          `hire_date` date NOT NULL,
          PRIMARY KEY (`emp_no`));
        </highlight-code>
        <p /><h3>1. 对于select操作的题目</h3></p>
        <p>例如查找表employees中emp_no为奇数的记录</p>
        答案只需给出任意一种可行的答案即可，例如
        <highlight-code lang="sql">
          select * from employees where emp_no % 2 = 1;
        </highlight-code>

        <p /><h3>2. 对于update、delete等会对表中记录进行修改的题目</h3></p>
        <p>例如删除表employees中emp_no为奇数的记录</p>
        <p>答案应该这样给出：</p>
        <highlight-code lang="sql">
          delete from employees where emp_no % 2 = 1;
          select * from employees;
        </highlight-code>
        <p>其中最后一行需要给出有进行修改操作的表的select *操作，这样可以用来记录修改后表中数据的变化</p>
        <p>此判题系统根据最后一行的select语句进行用户解答的正确性判断，请确保最后一行语句的正确性</p>
        <p><b>每条语句之间请严格使用';'进行分割</b></p>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="updateAndDeleteProblemHintdialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import VueHighlightJS from 'vue-highlight.js'
import 'vue-highlight.js/lib/allLanguages'
import 'highlight.js/styles/atom-one-light.css'
import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import 'codemirror/mode/sql/sql.js'
import 'codemirror/theme/solarized.css'
import 'codemirror/addon/hint/show-hint.js'
import 'codemirror/addon/hint/sql-hint.js'
import 'codemirror/addon/edit/matchbrackets.js'
import 'codemirror/addon/hint/show-hint.css'
import Tinymce from '@/components/Tinymce'

export default {
  components: {
    VueHighlightJS,
    codemirror,
    Tinymce
  },
  data() {
    return {
      updateAndDeleteProblemHintdialogVisible: false,
      dialogVisible: false,
      createTable: '',
      checkRules: {
        title: [
          {
            required: true,
            message: '题目标题不能为空',
            trigger: 'blur'
          },
          {
            max: 100,
            message: '题目标题长度最大为100',
            trigger: 'blur'
          }
        ],
        databaseId: [
          {
            required: true,
            message: '数据库不能为空',
            trigger: 'blur'
          }
        ],
        answer: [
          {
            required: true,
            message: '答案不能为空',
            trigger: 'blur'
          }
        ]
      },
      cmOptions: {
        tabSize: 4,
        mode: 'text/x-mysql',
        theme: 'solarized light',
        lineNumbers: true,
        styleActiveLine: true,
        lineWrapping: true,
        indentWithTabs: true,
        hintOptions: {
          completeSingle: false
        },
        line: true
      },
      problemDetail: {
        id: '',
        title: '',
        description: '',
        sampleOutput: '',
        hint: '',
        answer: '',
        solve: 0,
        submit: 0,
        databaseId: 0
      },
      databaseList: []
    }
  },
  computed: {
    codemirror() {
      return this.$refs.myCm.codemirror
    }
  },
  mounted: function() {
    const problemId = this.$route.params.id
    this.getProblemDetail(problemId)
    this.getDatabaseList()
  },
  methods: {
    onCmReady(cm) {
      cm.on('keypress', () => {
        cm.showHint()
      })
    },
    onSubmit() {
      this.$refs.problemDetail.validate(valid => {
        if (valid) {
          const problemId = this.$route.params.id
          const problem = {
            id: problemId,
            title: this.problemDetail.title.trim(),
            description: this.problemDetail.description.trim(),
            sampleOutput: this.problemDetail.sampleOutput.trim(),
            hint: this.problemDetail.hint.trim(),
            answer: this.problemDetail.answer,
            solve: this.problemDetail.solve,
            submit: this.problemDetail.submit,
            databaseId: this.problemDetail.databaseId
          }
          this.updateProblem(problemId, problem, () => {
            this.$router.back(-1)
          })
        } else {
          this.$message.error('请确认所有项目均填写正确！')
        }
      })
    },
    onCancel() {
      this.$router.back(-1)
    },
    getProblemDetail(problemId) {
      const apiUrl = this.Url.problemBaseUrl
      this.$axios
        .get(apiUrl + problemId)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取题目详情失败，内部错误！')
          } else {
            const resData = res.data
            if (resData.code === 0) {
              this.problemDetail = resData.data
              if (!this.problemDetail.description) {
                this.problemDetail.description = ''
              }
              if (!this.problemDetail.sampleOutput) {
                this.problemDetail.sampleOutput = ''
              }
              if (!this.problemDetail.hint) {
                this.problemDetail.hint = ''
              }
            } else {
              this.$message.error('获取题目详情失败！')
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    getDatabaseList() {
      const apiUrl = this.Url.databaseBaseUrl
      this.$axios
        .get(apiUrl)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取数据库列表失败，内部错误！')
          } else {
            const resData = res.data
            if (resData.code === 0) {
              this.databaseList = resData.data.records
            } else {
              this.$message.error('获取数据库列表失败！')
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    updateProblem(problemId, problem, successCallback) {
      const apiUrl = this.Url.problemBaseUrl
      this.$axios
        .put(apiUrl + problemId, problem)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('更新题目失败，内部错误！')
          } else {
            const resData = res.data
            if (resData.code === 0) {
              this.$message({
                message: resData.message,
                type: 'success'
              })
              successCallback()
            } else {
              this.$message.error(resData.message)
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    showCreateTable(databaseId) {
      const apiUrl = this.Url.databaseBaseUrl
      this.$axios
        .get(apiUrl + databaseId)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取数据库建表语句失败，内部错误！')
          } else {
            const resData = res.data
            if (resData.code === 0) {
              this.createTable = resData.data.createTable
              this.dialogVisible = true
            } else {
              this.$message.error(resData.message)
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    }
  }
}
</script>

<style scoped>
.line {
  text-align: center;
}
</style>

