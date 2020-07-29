<template>
  <div class="app-container">
    <el-form ref="userDetail" :model="userDetail" :rules="checkRules" label-width="120px">
      <el-form-item label="管理员用户名" prop="username">
        <el-input v-model="userDetail.username" placeholder="请输入用户名" />
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
        <el-button @click="$router.back(-1)">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { createAdmin } from '@/api/admin'

export default {
  data() {
    return {
      passwordType: 'password',
      checkRules: {
        username: [
          {
            required: true,
            message: '管理员用户名不能为空',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: '密码不能为空',
            trigger: 'blur'
          }
        ]
      },
      userDetail: {
        username: '',
        password: ''
      }
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
          this.handleResponse(createAdmin(this.userDetail.username.trim(), this.userDetail.password.trim()), '添加管理员',
            (res) => {
              this.$message.success('添加管理员成功')
              this.$router.back(-1)
            })
        } else {
          this.$message.error('请确认所有项目均填写正确！')
        }
      })
    }
  }
}
</script>

<style scoped>
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

