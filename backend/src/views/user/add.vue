<template>
  <div class="app-container">
    <el-form ref="form" :model="userDetail" label-width="120px">
      <el-form-item label="用户名">
        <el-input v-model="userDetail.username" placeholder="请输入用户名"/>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="userDetail.email" placeholder="请输入邮箱"/>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="userDetail.password" placeholder="请输入密码"/>
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
        username: "",
        password: "",
        email: ""
      }
    };
  },
  methods: {
    onSubmit() {
      this.addUser();
    },
    onCancel() {
      this.$router.back(-1);
    },
    addUser() {
      const apiUrl = this.Url.userAdd;
      let postData = this.userDetail;
      postData.username.trim();
      postData.password.trim();
      postData.email.trim();
      this.$axios
        .post(apiUrl, postData)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error("网络错误！");
          } else {
            const resData = res.data;
            if (resData.code === 200) {
              this.$message({
                message: resData.message,
                type: "success"
              });
              this.$router.back(-1);
            } else if (resData.code === 400) {
              this.$message.error(resData.message);
            } else if (resData.code === 503) {
              this.$message.error(resData.message);
            }
          }
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
</script>

<style scoped>
.line {
  text-align: center;
}
</style>

