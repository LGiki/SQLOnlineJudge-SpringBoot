<template>
  <div class="app-container">
    <el-form ref="form" :model="problemDetail" label-width="120px">
      <el-form-item label="题目ID">
        <el-input v-model="problemDetail.id" disabled />
      </el-form-item>
      <el-form-item label="数据库">
        <el-select v-model="problemDetail.databaseId" placeholder="选择数据库">
          <el-option v-for="databaseItem in databaseList" :key="databaseItem.id" :label="databaseItem.name" :value="databaseItem.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="题目标题">
        <el-input v-model="problemDetail.title" />
      </el-form-item>
      <el-form-item label="题目描述">
        <el-input v-model="problemDetail.description" type="textarea" />
      </el-form-item>
      <el-form-item label="输入格式">
        <el-input v-model="problemDetail.inputFormat" type="textarea" />
      </el-form-item>
      <el-form-item label="输出格式">
        <el-input v-model="problemDetail.outputFormat" type="textarea" />
      </el-form-item>
      <el-form-item label="样例输入">
        <el-input v-model="problemDetail.sampleInput" type="textarea" />
      </el-form-item>
      <el-form-item label="样例输出">
        <el-input v-model="problemDetail.sampleOutput" type="textarea" />
      </el-form-item>
      <el-form-item label="提示">
        <el-input v-model="problemDetail.hint" type="textarea" />
      </el-form-item>
      <el-form-item label="答案">
        <el-input v-model="problemDetail.answer" type="textarea" />
      </el-form-item>
      <el-form-item label="结果是否必须有序">
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
export default {
  data() {
    return {
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
  mounted: function() {
    const problemId = this.$route.params.id
    this.getProblemDetail(problemId)
    this.getDatabaseList()
  },
  methods: {
    onSubmit() {
      this.$message('submit!')
    },
    onCancel() {
      // this.$message({
      //   message: "cancel!",
      //   type: "warning"
      // });
      this.$router.back(-1)
    },
    getProblemDetail(problemId) {
      const apiUrl = this.Url.problemDetail
      this.$axios
        .get(apiUrl + problemId)
        .then(res => {
          if (res.status !== 200) {
            alert('Fetch problem detail: Network error')
          } else {
            const resData = res.data
            if (resData.code === 200) {
              this.problemDetail = resData.data
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    getDatabaseList() {
      const apiUrl = this.Url.databaseList
      this.$axios
        .get(apiUrl)
        .then(res => {
          if (res.status !== 200) {
            alert('Get database list: Network error')
          } else {
            const resData = res.data
            if (resData.code === 200) {
              this.databaseList = resData.data.list
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

