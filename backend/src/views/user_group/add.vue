<template>
  <div class="app-container">
    <el-form ref="userGroupDetail" :model="userGroupDetail" :rules="checkRules" label-width="120px">
      <el-form-item label="用户组名称" prop="name">
        <el-input v-model="userGroupDetail.name" placeholder="请输入用户组名称" />
      </el-form-item>
      <el-form-item label="用户组简介" prop="description">
        <el-input v-model="userGroupDetail.description" placeholder="请输入用户组简介" />
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
      checkRules: {
        name: [
          {
            required: true,
            message: '用户组名称不能为空',
            trigger: 'blur'
          }
        ]
      },
      userGroupDetail: {
        name: '',
        description: ''
      }
    }
  },
  methods: {
    onSubmit() {
      this.$refs.userGroupDetail.validate(valid => {
        if (valid) {
          this.addUserGroup()
        } else {
          this.$message.error('请确认所有项目均填写正确！')
        }
      })
    },
    onCancel() {
      this.$router.back(-1)
    },
    addUserGroup() {
      const apiUrl = this.Url.userGroupBaseUrl
      let postData = this.userGroupDetail
      postData.name.trim()
      postData.description.trim()
      this.$axios
      .post(apiUrl, postData)
      .then(res => {
        if (res.status !== 200) {
          this.$message.error('添加用户组失败，内部错误！')
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
    }
  }
}
</script>

<style scoped>
.line {
  text-align: center;
}
</style>

