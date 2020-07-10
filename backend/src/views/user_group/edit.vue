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
import { getUserGroupDetail, updateUserGroup } from '@/api/user-group'

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
  mounted: function() {
    const userGroupId = this.$route.params.id
    this.getUserGroupDetail(userGroupId)
  },
  methods: {
    onSubmit() {
      this.$refs.userGroupDetail.validate(valid => {
        if (valid) {
          const userGroupId = this.$route.params.id
          const userGroup = {
            name: this.userGroupDetail.name.trim(),
            description: this.userGroupDetail.description.trim()
          }
          this.handleResponse(updateUserGroup(userGroupId, userGroup.name, userGroup.description), '更新用户组',
            (res) => {
              this.$message.success('更新用户组成功')
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
    getUserGroupDetail(userGroupId) {
      this.handleResponse(getUserGroupDetail(userGroupId), '获取用户组详情',
        (res) => {
          this.userGroupDetail = res.data
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

