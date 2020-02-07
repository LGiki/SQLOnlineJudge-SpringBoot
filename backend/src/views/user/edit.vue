<template>
  <div class="app-container">
    <el-form ref="userDetail" :model="userDetail" :rules="checkRules" label-width="120px">
      <el-form-item label="用户ID">
        <el-input v-model="userDetail.id" disabled />
      </el-form-item>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="userDetail.username" placeholder="请输入用户名" />
      </el-form-item>
      <el-form-item label="学号" prop="studentNo">
        <el-input type="number" v-model="userDetail.studentNo" placeholder="请输入学号" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="userDetail.email" placeholder="请输入邮箱" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="password" :type="passwordType" placeholder="留空则不修改密码" />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
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
      passwordType: 'password',
      userDetail: {},
      password: '',
      checkRules: {
        username: [
          {
            required: true,
            message: '用户名不能为空',
            trigger: 'blur'
          }
        ],
        email: [
          {
            required: true,
            type: 'email',
            message: '邮箱格式错误',
            trigger: 'blur'
          }
        ],
        studentNo: [
          {
            required: true,
            message: '学号不能为空',
            trigger: 'blur'
          }
        ]
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
          const user = {
            id: this.userDetail.id,
            username: this.userDetail.username.trim(),
            email: this.userDetail.email.trim(),
            studentNo: this.userDetail.studentNo.trim()
          }
          if (this.password) {
            user.password = this.password.trim()
          }
          this.updateUser(userId, user, () => {
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
    getUserDetail(userId) {
      const apiUrl = this.Url.userBaseUrl
      this.$axios
        .get(apiUrl + userId)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取用户信息失败，内部错误！')
          } else {
            const resData = res.data
            if (resData.code === 0) {
              this.userDetail = resData.data
            } else {
              this.$message.error(resData.message)
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    updateUser(userId, user, successCallback) {
      const apiUrl = this.Url.userBaseUrl
      this.$axios
        .put(apiUrl + userId, user)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('更新用户资料失败，内部错误！')
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

