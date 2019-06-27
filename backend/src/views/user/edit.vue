<template>
  <div class="app-container">
    <el-form ref="form" :model="userDetail" label-width="120px">
      <el-form-item label="用户ID">
        <el-input v-model="userDetail.id" disabled />
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="userDetail.username" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="userDetail.password" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="userDetail.email" />
      </el-form-item>
      <el-form-item label="通过数">
        <el-input v-model="userDetail.solved" disabled />
      </el-form-item>
      <el-form-item label="提交数">
        <el-input v-model="userDetail.submit" disabled />
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
      userDetail: {
        id: '',
        username: '',
        password: '',
        email: '',
        submit: '',
        solved: ''
      },
    }
  },
  mounted: function() {
    const userId = this.$route.params.id
    this.getUserDetail(userId)
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
    getUserDetail(userId) {
      const apiUrl = this.Url.userDetail
      this.$axios
        .get(apiUrl + userId)
        .then(res => {
          if (res.status !== 200) {
            alert('Fetch problem detail: Network error')
          } else {
            const resData = res.data
            if (resData.code === 200) {
              this.userDetail = resData.data
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

