<template>
  <div class="app-container">
    <el-form ref="userDetail" :model="userDetail" label-width="120px">
      <el-form-item label="管理员ID">
        <el-input v-model="userDetail.id" disabled />
      </el-form-item>
      <el-form-item label="管理员用户名" prop="username">
        <el-input v-model="userDetail.username" disabled/>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="password" :type="passwordType" placeholder="留空则不修改密码" />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
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
      passwordType: 'password',
      password: '',
      userDetail: {
        id: '',
        username: '',
        submit: '',
        solved: ''
      }
    }
  },
  mounted: function() {
    const userId = this.$route.params.id
    this.getUserDetail(userId)
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    onSubmit() {
      this.$refs.userDetail.validate(valid => {
        if (valid) {
          const userId = this.$route.params.id
          if (this.password) {
            this.updateUserPassword(userId, 'password=' + this.password.trim(), () => {
              this.$router.back(-1)
            })
          }else{
            this.$message({
                message: '没做任何修改',
                type: 'warning'
            })
            this.$router.back(-1)
          }
        } else {
          this.$message.error('请确认所有项目均填写正确！')
        }
      })
    },
    onCancel() {
      this.$router.back(-1)
    },
    getUserDetail(userId) {
      const apiUrl = this.Url.adminBaseUrl
      this.$axios
        .get(apiUrl + userId)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取管理员信息失败，内部错误！')
          } else {
            const resData = res.data
            if (resData.code === 0) {
              this.userDetail = resData.data
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    updateUserPassword(userId, newPassword, successCallback) {
      const apiUrl = this.Url.adminBaseUrl
      this.$axios
        .put(apiUrl + userId, newPassword)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('更新管理员资料失败，内部错误！')
          } else {
            const resData = res.data
            if (resData.code === 0) {
              this.$message({
                message: resData.message,
                type: 'success'
              })
              successCallback()
            } else if (resData.code === 1) {
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

.show-pwd {
  position: absolute;
  right: 10px;
  top: 1px;
  font-size: 16px;
  color: #888;
  cursor: pointer;
  user-select: none;
}
</style>

