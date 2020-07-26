<template>
  <div class="app-container">
    <el-form ref="userDetail" :model="userDetail" label-width="120px">
      <el-form-item label="管理员ID">
        <el-input v-model="userDetail.id" disabled />
      </el-form-item>
      <el-form-item label="管理员用户名" prop="username">
        <el-input v-model="userDetail.username" disabled />
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
import { getAdminDetail, updateAdmin } from '@/api/admin'

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
    this.getAdminDetail(userId)
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
          const adminId = this.$route.params.id
          if (this.password) {
            this.handleResponse(updateAdmin(adminId, this.password), '修改管理员密码',
              (res) => {
                this.$message.success('修改管理员密码成功')
                this.$router.back(-1)
              })
          } else {
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
    getAdminDetail(adminId) {
      this.handleResponse(getAdminDetail(adminId), '获取管理员详情',
        (res) => {
          this.userDetail = res.data
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

