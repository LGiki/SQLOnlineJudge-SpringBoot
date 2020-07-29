<template>
  <div class="app-container">
    <el-form ref="userGroupDetail" :model="userGroupDetail" :rules="checkRules" label-width="120px">
      <el-form-item>
        <el-alert
          title="保存用户组之后才能向用户组内添加用户"
          type="warning"
          show-icon
        />
      </el-form-item>
      <el-form-item label="用户组名称" prop="name">
        <el-input v-model="userGroupDetail.name" placeholder="请输入用户组名称" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { createUserGroup } from '@/api/user-group'
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
        name: ''
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
      this.handleResponse(createUserGroup(this.userGroupDetail.name.trim(), this.userGroupDetail.description.trim()), '添加用户组',
        (res) => {
          this.$message.success('添加用户组成功')
          this.$router.back(-1)
        })
    }
  }
}
</script>
