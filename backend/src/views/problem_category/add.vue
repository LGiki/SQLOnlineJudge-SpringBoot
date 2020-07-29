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
        <el-button @click="$router.back(-1)">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import ConvertUtil from '@/utils/convert-util'
import { createProblemCategory } from '@/api/problem-category'

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
          const postData = {
            name: this.problemCategoryDetail.name.trim(),
            startTime: ConvertUtil.convertDateToString(this.problemCategoryDetail.duration[0]),
            endTime: ConvertUtil.convertDateToString(this.problemCategoryDetail.duration[1]),
            viewAfterEnd: this.problemCategoryDetail.viewAfterEnd
          }
          this.handleResponse(createProblemCategory(postData.name, postData.startTime, postData.endTime, postData.viewAfterEnd), '添加题目集',
            (res) => {
              this.$message.success('添加题目集成功')
              this.$router.push('/problem-category/edit/' + res.data.id)
            })
        } else {
          this.$message.error('请确认所有项目均填写正确！')
        }
      })
    }
  }
}
</script>
