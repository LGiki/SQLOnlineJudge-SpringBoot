<template>
  <div class="app-container">
    <el-form
      ref="problemCategoryDetail"
      :model="problemCategoryDetail"
      :rules="checkRules"
      label-width="120px"
    >
      <el-form-item>
        <el-alert
          title="保存题目集之后才能编辑题目集中的题目"
          type="warning"
          show-icon
        >
        </el-alert>
      </el-form-item>
      <el-form-item label="题目集名称" prop="name">
        <el-input
          v-model="problemCategoryDetail.name"
          placeholder="请输入题目集名称"
        />
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
            message: "题目集名称不能为空",
            trigger: "blur"
          }
        ]
      },
      problemCategoryDetail: {
        name: ""
      }
    };
  },
  methods: {
    onSubmit() {
      this.$refs.problemCategoryDetail.validate(valid => {
        if (valid) {
          this.addProblemCategory();
        } else {
          this.$message.error("请确认所有项目均填写正确！");
        }
      });
    },
    onCancel() {
      this.$router.back(-1);
    },
    addProblemCategory() {
      const apiUrl = this.Url.problemCategoryBaseUrl;
      const postData = this.problemCategoryDetail;
      postData.name.trim();
      this.$axios
        .post(apiUrl, postData)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error("添加题目集失败，内部错误！");
          } else {
            const resData = res.data;
            if (resData.code === 0) {
              this.$message({
                message: resData.message,
                type: "success"
              });
              this.$router.push("/problem-category/edit/" + resData.data.id);
            } else {
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
