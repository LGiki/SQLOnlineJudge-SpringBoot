<template>
  <div class="app-container">
    <el-form ref="databaseDetail" :model="databaseDetail" :rules="checkRules" label-width="120px">
      <el-form-item label="数据库ID">
        <el-input v-model="databaseDetail.id" disabled />
      </el-form-item>
      <el-form-item label="数据库名称" prop="name">
        <el-input v-model="databaseDetail.name" />
      </el-form-item>
      <el-form-item label="建表语句" prop="createTable">
        <codemirror v-model="databaseDetail.createTable" :options="cmOptions" @ready="onCmReady" />
        <el-input v-if="false" v-model="databaseDetail.createTable" />
      </el-form-item>
      <el-form-item label="测试数据" prop="testData">
        <codemirror v-model="databaseDetail.testData" :options="cmOptions" @ready="onCmReady" />
        <el-input v-if="false" v-model="databaseDetail.testData" />
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
        name: [
          {
            required: true,
            message: '数据库名称不能为空',
            trigger: 'blur'
          }
        ],
        createTable: [
          {
            required: true,
            message: '建表语句不能为空',
            trigger: 'blur'
          }
        ],
        testData: [
          {
            required: true,
            message: '测试数据不能为空',
            trigger: 'blur'
          }
        ]
      },
      databaseDetail: {
        id: '',
        name: '',
        createTable: '',
        testData: ''
      }
    }
  },
  computed: {
    codemirror() {
      return this.$refs.myCm.codemirror
    }
  },
  mounted: function() {
    const databaseId = this.$route.params.id
    this.getDatabaseDetail(databaseId)
  },
  methods: {
    onCmReady(cm) {
      cm.on('keypress', () => {
        cm.showHint()
      })
    },
    onSubmit() {
      this.$refs.databaseDetail.validate(valid => {
        if (valid) {
          const databaseId = this.$route.params.id
          const database = {
            name: this.databaseDetail.name.trim(),
            createTable: this.databaseDetail.createTable,
            testData: this.databaseDetail.testData
          }
          this.updateDatabase(databaseId, database, () => {
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
    getDatabaseDetail(databaseId) {
      const apiUrl = this.Url.databaseBaseUrl
      this.$axios
        .get(apiUrl + databaseId)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取数据库详情失败，网络错误！')
          } else {
            const resData = res.data
            if (resData.code === 0) {
              this.databaseDetail = resData.data
            } else {
              this.$message.error('获取数据库详情失败！')
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    updateDatabase(databaseId, database, successCallback) {
      const apiUrl = this.Url.databaseBaseUrl
      this.$axios
        .put(apiUrl + databaseId, database)
        .then(res => {
          if (res.status !== 201) {
            this.$message.error('更新数据库失败，网络错误！')
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
    }
  }
}
</script>

<style scoped>
.line {
  text-align: center;
}
</style>

