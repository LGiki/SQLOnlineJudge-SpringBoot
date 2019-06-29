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
      </el-form-item>
      <el-form-item label="题目描述" prop="description">
        <el-input v-model="problemDetail.description" type="textarea" />
      </el-form-item>
      <el-form-item label="输入格式" prop="inputFormat">
        <el-input v-model="problemDetail.inputFormat" type="textarea" />
      </el-form-item>
      <el-form-item label="输出格式" prop="outputFormat">
        <el-input v-model="problemDetail.outputFormat" type="textarea" />
      </el-form-item>
      <el-form-item label="样例输入" prop="sampleInput">
        <el-input v-model="problemDetail.sampleInput" type="textarea" />
      </el-form-item>
      <el-form-item label="样例输出" prop="sampleOutput">
        <el-input v-model="problemDetail.sampleOutput" type="textarea" />
      </el-form-item>
      <el-form-item label="提示" prop="hint">
        <el-input v-model="problemDetail.hint" type="textarea" />
      </el-form-item>
      <el-form-item label="答案" prop="answer">
        <codemirror v-model="problemDetail.answer" :options="cmOptions" @ready="onCmReady" />
        <el-input v-if="false" v-model="problemDetail.answer" placeholder="请输入提示" />
      </el-form-item>
      <el-form-item label="结果是否必须有序" prop="needOrder">
        <el-switch v-model="problemDetail.needOrder" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import 'codemirror/mode/sql/sql.js'
import 'codemirror/theme/solarized.css'
import 'codemirror/addon/hint/show-hint.js'
import 'codemirror/addon/hint/sql-hint.js'
import 'codemirror/addon/edit/matchbrackets.js'
import 'codemirror/addon/hint/show-hint.css'

export default {
  components: {
    codemirror
  },
  data() {
    return {
      checkRules: {
        title: [
          {
            required: true,
            message: '题目标题不能为空',
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
        ],
        needOrder: [
          {
            required: true,
            message: '必须确定结果是否必须有序',
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
        inputFormat: '',
        outputFormat: '',
        sampleInput: '',
        sampleOutput: '',
        hint: '',
        answer: '',
        solve: 0,
        submit: 0,
        needOrder: false,
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
            title: this.problemDetail.title.trim(),
            description: this.problemDetail.description.trim(),
            inputFormat: this.problemDetail.inputFormat.trim(),
            outputFormat: this.problemDetail.outputFormat.trim(),
            sampleInput: this.problemDetail.sampleInput.trim(),
            sampleOutput: this.problemDetail.sampleOutput.trim(),
            hint: this.problemDetail.hint.trim(),
            answer: this.problemDetail.answer,
            solve: this.problemDetail.solve,
            submit: this.problemDetail.submit,
            needOrder: this.problemDetail.needOrder,
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
            this.$message.error('获取题目详情失败，网络错误！')
          } else {
            const resData = res.data
            if (resData.code === 200) {
              this.problemDetail = resData.data
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
            this.$message.error('获取数据库列表失败，网络错误！')
          } else {
            const resData = res.data
            if (resData.code === 200) {
              this.databaseList = resData.data.list
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
            this.$message.error('更新题目失败，网络错误！')
          } else {
            const resData = res.data
            if (resData.code === 200) {
              this.$message({
                message: resData.message,
                type: 'success'
              })
              successCallback()
            } else if (resData.code === 400) {
              this.$message.error(resData.message)
            } else if (resData.code === 503) {
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

