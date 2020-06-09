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
        />
      </el-form-item>
      <el-form-item label="题目集名称" prop="name">
        <el-input
          v-model="problemCategoryDetail.name"
          placeholder="请输入题目集名称"
        />
      </el-form-item>
      <el-form-item label="题目集起止时间" prop="duration">
        <div class="block">
          <el-date-picker
            v-model="problemCategoryDetail.duration"
            type="datetimerange"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          />
        </div>
      </el-form-item>
      <el-form-item label="题目集结束后学生能否查看题目" prop="viewAfterEnd">
        <el-switch
          v-model="problemCategoryDetail.viewAfterEnd"
          active-text="能"
          inactive-text="否"
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
            message: '题目集名称不能为空',
            trigger: 'blur'
          }
        ],
        duration: [
          {
            required: true,
            message: '题目集起止时间不能为空',
            trigger: 'blur'
          }
        ],
        viewAfterEnd: [
          {
            required: true,
            message: '必须确定题目集结束后学生能否查看题目',
            trigger: 'blur'
          }
        ]
      },
      problemCategoryDetail: {
        name: '',
        duration: [],
        viewAfterEnd: true
      },
      pickerOptions: {
        shortcuts: [
          {
            text: '未来一周',
            onClick(picker) {
              const start = new Date()
              const end = new Date()
              end.setTime(start.getTime() + 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '未来一个月',
            onClick(picker) {
              const start = new Date()
              const end = new Date()
              end.setTime(start.getTime() + 3600 * 1000 * 24 * 30)
              picker.$emit('pick', [start, end])
            }
          }
        ]
      }
    }
  },
  methods: {
    onSubmit() {
      this.$refs.problemCategoryDetail.validate(valid => {
        if (valid) {
          this.addProblemCategory()
        } else {
          this.$message.error('请确认所有项目均填写正确！')
        }
      })
    },
    onCancel() {
      this.$router.back(-1)
    },
    // Convert date to: yyyy-MM-dd HH:mm:ss
    convertDateToString(date) {
      return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds()
    },
    addProblemCategory() {
      const apiUrl = this.Url.problemCategoryBaseUrl
      const postData = {
        name: this.problemCategoryDetail.name.trim(),
        startTime: this.convertDateToString(this.problemCategoryDetail.duration[0]),
        endTime: this.convertDateToString(this.problemCategoryDetail.duration[1]),
        viewAfterEnd: this.problemCategoryDetail.viewAfterEnd
      }
      this.$axios
        .post(apiUrl, postData)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('添加题目集失败，内部错误！')
          } else {
            const resData = res.data
            if (resData.code === 0) {
              this.$message({
                message: resData.message,
                type: 'success'
              })
              this.$router.push('/problem-category/edit/' + resData.data.id)
            } else {
              this.$message.error(resData.message)
            }
          }
        })
        .catch(err => {
          this.$message.error('添加题目集失败！')
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
