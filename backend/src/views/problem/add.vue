<template>
  <div class="app-container">
    <el-form ref="problemDetail" :model="problemDetail" :rules="checkRules" label-width="120px">
      <el-form-item label="题目标题" prop="title">
        <el-input v-model="problemDetail.title" placeholder="请输入题目标题" />
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
      <el-form-item label="题目介绍" prop="description">
        <el-input v-model="problemDetail.description" placeholder="请输入题目介绍" type="textarea" />
      </el-form-item>
      <el-form-item label="样例输出" prop="sampleOutput">
        <el-input v-model="problemDetail.sampleOutput" placeholder="请输入样例输出" type="textarea" />
      </el-form-item>
      <el-form-item label="提示" prop="hint">
        <el-input v-model="problemDetail.hint" placeholder="请输入提示" type="textarea" />
      </el-form-item>
      <el-form-item label="答案" prop="answer">
        <codemirror v-model="problemDetail.answer" :options="cmOptions" @ready="onCmReady" />
        <el-input v-if="false" v-model="problemDetail.answer" placeholder="请输入提示" />
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
        description: [
          {
            required: true,
            message: '题目介绍不能为空',
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
      problemDetail: {
        title: '',
        description: '',
        sampleOutput: '',
        hint: '',
        answer: '',
        databaseId: null
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
          this.addProblem()
        } else {
          this.$message.error('请确认所有项目均填写正确！')
        }
      })
    },
    onCancel() {
      this.$router.back(-1)
    },
    addProblem() {
      const apiUrl = this.Url.problemBaseUrl
      const postData = this.problemDetail
      postData.title.trim()
      postData.description.trim()
      postData.sampleOutput.trim()
      postData.hint.trim()
      this.$axios
        .post(apiUrl, postData)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('添加题目失败，内部错误！')
          } else {
            const resData = res.data
            if (resData.code === 0) {
              this.$message({
                message: resData.message,
                type: 'success'
              })
              this.$router.back(-1)
            } else {
              this.$message.error(resData.message)
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

