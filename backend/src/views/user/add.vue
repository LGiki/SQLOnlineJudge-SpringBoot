<template>
  <div class="app-container">
    <el-form ref="userDetail" :model="userDetail" :rules="checkRules" label-width="120px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="userDetail.username" placeholder="请输入用户名" />
      </el-form-item>
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="userDetail.studentNo" type="number" placeholder="请输入学号" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="userDetail.email" placeholder="请输入邮箱" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input
          ref="password"
          v-model="userDetail.password"
          name="password"
          placeholder="请输入密码"
          :type="passwordType"
        />
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
        password: [
          {
            required: true,
            message: '密码不能为空',
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
      },
      userDetail: {}
    }
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
          this.addUser()
        } else {
          this.$message.error('请确认所有项目均填写正确！')
        }
      })
    },
    onCancel() {
      this.$router.back(-1)
    },
    addUser() {
      const apiUrl = this.Url.userBaseUrl
      const postData = this.userDetail
      postData.username.trim()
      postData.password.trim()
      postData.email.trim()
      postData.studentNo.trim()
      this.$axios
        .post(apiUrl, postData)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('添加用户失败，内部错误！')
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

