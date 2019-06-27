<template>
  <div class="app-container">
    <el-form ref="form" :model="databaseDetail" label-width="120px">
      <el-form-item label="数据库ID">
        <el-input v-model="databaseDetail.id" disabled />
      </el-form-item>
      <el-form-item label="数据库名称">
        <el-input v-model="databaseDetail.name" />
      </el-form-item>
      <el-form-item label="建表语句">
        <codemirror v-model="databaseDetail.createTable" :options="cmOptions" @ready="onCmReady" />
      </el-form-item>
      <el-form-item label="测试数据">
        <codemirror v-model="databaseDetail.testData" :options="cmOptions" @ready="onCmReady" />
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
      this.$message('submit!')
    },
    onCancel() {
      this.$router.back(-1)
    },
    getDatabaseDetail(databaseId) {
      const apiUrl = this.Url.databaseDetail
      this.$axios
        .get(apiUrl + databaseId)
        .then(res => {
          if (res.status !== 200) {
            alert('Fetch database detail: Network error')
          } else {
            const resData = res.data
            if (resData.code === 200) {
              this.databaseDetail = resData.data
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

