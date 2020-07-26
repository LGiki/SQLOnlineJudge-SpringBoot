<template>
  <div class="app-container">
    <el-form ref="problemDetail" :model="problemDetail" :rules="checkRules" label-width="120px">
      <el-form-item v-if="!isAdd" label="题目ID">
        <el-input v-model="problemDetail.id" disabled />
      </el-form-item>
      <el-form-item label="题目标题" prop="title">
        <el-input v-model="problemDetail.title" />
      </el-form-item>
      <el-form-item label="题目难度" prop="difficulty">
        <el-rate
          v-model="problemDetail.difficulty"
          show-text
          :texts="[1, 2, 3, 4, 5]"
        />
      </el-form-item>
      <el-form-item label="数据库" prop="databaseId">
        <el-select v-model="problemDetail.databaseId" placeholder="选择数据库">
          <el-option
            v-for="databaseItem in databaseList"
            :key="databaseItem.id"
            :label="databaseItem.name"
            :value="databaseItem.id"
          />
        </el-select>
        <el-button v-if="problemDetail.databaseId" @click="getDatabaseDetail(problemDetail.databaseId, 'createTable')">查看该数据库的<strong>建表语句</strong></el-button>
        <el-button v-if="problemDetail.databaseId" @click="getDatabaseDetail(problemDetail.databaseId, 'testData')">查看该数据库的<strong>测试数据</strong></el-button>
      </el-form-item>
      <el-dialog
        :title="viewDatabaseDetailDialogTitle"
        :visible.sync="viewDatabaseDetailDialogVisible"
        width="50%"
      >
        <highlight-code lang="sql">{{ databaseDetailCode }}</highlight-code>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="viewDatabaseDetailDialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>
      <el-form-item label="题目描述" prop="description">
        <tinymce v-model="problemDetail.description" :height="200" />
      </el-form-item>
      <el-form-item label="表的样例" prop="sampleOutput">
        <tinymce v-model="problemDetail.sampleOutput" :height="200" />
      </el-form-item>
      <el-form-item label="提示" prop="hint">
        <tinymce v-model="problemDetail.hint" :height="100" />
      </el-form-item>
      <el-form-item>
        <el-button @click="updateAndDeleteProblemHintDialogVisible = true">查看答案填写说明</el-button>
      </el-form-item>
      <el-form-item label="是否带有Update操作" prop="isUpdate">
        <el-switch
          v-model="problemDetail.isUpdate"
          active-text="带有Update操作的题目"
          inactive-text="常规Select题目"
        />
      </el-form-item>
      <el-form-item label="答案" prop="answer">
        <codemirror v-model="problemDetail.answer" :options="cmOptions" @ready="onCmReady" />
        <el-input v-if="false" v-model="problemDetail.answer" />
      </el-form-item>
      <el-form-item v-if="problemDetail.isUpdate" label="对有进行修改的表的Select语句" prop="selectAfterUpdate">
        <codemirror v-model="problemDetail.selectAfterUpdate" :options="cmOptions" @ready="onCmReady" />
        <el-input v-if="false" v-model="problemDetail.selectAfterUpdate" />
      </el-form-item>
      <el-form-item>
        <el-button type="warning" plain @click="runCode">调试运行答案</el-button>
      </el-form-item>
      <el-dialog
        title="答案填写说明"
        :visible.sync="updateAndDeleteProblemHintDialogVisible"
        width="50%"
      >
        若有表如下：
        <highlight-code lang="sql">
          CREATE TABLE `employees` (
          `emp_no` int(11) NOT NULL,
          `birth_date` date NOT NULL,
          `first_name` varchar(14) NOT NULL,
          `last_name` varchar(16) NOT NULL,
          `gender` char(1) NOT NULL,
          `hire_date` date NOT NULL,
          PRIMARY KEY (`emp_no`));
        </highlight-code>
        <h3>1. 对于select操作的题目</h3>
        <p>例如查找表employees中emp_no为奇数的记录</p>
        答案只需给出任意一种可行的答案即可，例如
        <highlight-code lang="sql">
          select * from employees where emp_no % 2 = 1;
        </highlight-code>
        <h3>2. 对于update、delete等会对表中记录进行修改的题目</h3>
        <p>例如删除表employees中emp_no为奇数的记录</p>
        <p>则需要打开"<strong>是否带有Update操作</strong>"开关</p>
        <p>答案正常给出：</p>
        <highlight-code lang="sql">
          delete from employees where emp_no % 2 = 1;
        </highlight-code>
        <p>之后在"<strong>对有进行修改的表的Select语句</strong>"中填入对有进行修改操作的表的select *语句：</p>
        <highlight-code lang="sql">
          select * from employees;
        </highlight-code>
        <p>本判题系统通过该Select语句来验证修改表格数据类题目中学生的答案是否正确的，所以请确保Select语句的正确性</p>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="updateAndDeleteProblemHintDialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>
      <el-dialog
        title="调试运行结果"
        :visible.sync="runCodeDialogVisible"
        width="50%"
      >
        <p><strong>代码：</strong></p>
        <highlight-code lang="sql">{{ problemDetail.answer }}</highlight-code>
        <p><strong>运行结果：</strong></p>
        <highlight-code>{{ runResult }}</highlight-code>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="runCodeDialogVisible = false">关 闭</el-button>
        </span>
      </el-dialog>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import 'vue-highlight.js/lib/allLanguages'
import 'highlight.js/styles/atom-one-light.css'
import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import 'codemirror/mode/sql/sql.js'
import 'codemirror/theme/solarized.css'
import 'codemirror/addon/hint/show-hint.js'
import 'codemirror/addon/hint/sql-hint.js'
import 'codemirror/addon/edit/matchbrackets.js'
import 'codemirror/addon/hint/show-hint.css'
import Tinymce from '@/components/Tinymce'
import { updateProblem, createProblem, getProblemDetail } from '@/api/problem'
import { getAllDatabase, getDatabaseDetail } from '@/api/database'
import { runCode } from '@/api/judge'

export default {
  components: {
    codemirror,
    Tinymce
  },
  data() {
    return {
      updateAndDeleteProblemHintDialogVisible: false,
      runCodeDialogVisible: false,
      viewDatabaseDetailDialogVisible: false,
      viewDatabaseDetailDialogTitle: '',
      databaseDetailCode: '',
      checkRules: {
        title: [
          {
            required: true,
            message: '题目标题不能为空',
            trigger: 'blur'
          },
          {
            max: 100,
            message: '题目标题长度最大为100',
            trigger: 'blur'
          }
        ],
        difficulty: [
          {
            required: true,
            message: '题目难度不能为空',
            trigger: 'blur'
          }
        ],
        description: [
          {
            required: true,
            message: '题目描述不能为空',
            trigger: 'blur'
          }
        ],
        databaseId: [
          {
            required: true,
            message: '数据库不能为空',
            trigger: 'blur'
          }
        ],
        answer: [
          {
            required: true,
            message: '答案不能为空',
            trigger: 'blur'
          }
        ],
        isUpdate: [
          {
            required: true,
            message: '必须确定题目是否带有Update操作',
            trigger: 'blur'
          }
        ]
      },
      cmOptions: {
        tabSize: 4,
        mode: 'text/x-mysql',
        theme: 'solarized light',
        lineNumbers: true,
        styleActiveLine: true,
        lineWrapping: true,
        indentWithTabs: true,
        hintOptions: {
          completeSingle: false
        },
        line: true
      },
      problemDetail: {
        id: '',
        title: '',
        description: '',
        sampleOutput: '',
        hint: '',
        answer: '',
        databaseId: '',
        difficulty: 1,
        isUpdate: false,
        selectAfterUpdate: ''
      },
      runResult: '',
      databaseList: []
    }
  },
  computed: {
    codemirror() {
      return this.$refs.myCm.codemirror
    },
    isAdd() {
      return this.$route.params.id === undefined
    },
    problemId() {
      return this.$route.params.id
    }
  },
  mounted: function() {
    if (!this.isAdd) {
      this.handleResponse(getProblemDetail(this.problemId), '获取题目详情',
        (res) => {
          this.problemDetail = res.data
          if (!this.problemDetail.description) {
            this.problemDetail.description = ''
          }
          if (!this.problemDetail.sampleOutput) {
            this.problemDetail.sampleOutput = ''
          }
          if (!this.problemDetail.hint) {
            this.problemDetail.hint = ''
          }
        })
    }
    this.getDatabaseList()
  },
  methods: {
    isEmpty(obj) {
      return typeof obj === 'undefined' || obj === null || obj === ''
    },
    onCmReady(cm) {
      cm.on('keypress', () => {
        cm.showHint()
      })
    },
    runCode() {
      if (this.isEmpty(this.problemDetail.databaseId)) {
        this.$message.error('请检查是否已选择数据库！')
        return
      }
      if (this.isEmpty(this.problemDetail.answer)) {
        this.$message.error('请检查SQL代码是否已输入！')
        return
      }
      this.runResult = ''
      this.handleResponse(runCode(this.problemDetail.databaseId, this.problemDetail.answer), '调试运行',
        (res) => {
          const runResult = res.data
          const reg = /\((.*?)\)/g
          let regResult = reg.exec(runResult)
          while (regResult) {
            this.runResult += regResult[0] + '\r\n'
            regResult = reg.exec(runResult)
          }
          this.runCodeDialogVisible = true
        })
    },
    onSubmit() {
      this.$refs.problemDetail.validate(valid => {
        if (valid) {
          const problem = {
            title: this.problemDetail.title.trim(),
            description: this.problemDetail.description.trim(),
            sampleOutput: this.problemDetail.sampleOutput.trim(),
            hint: this.problemDetail.hint.trim(),
            answer: this.problemDetail.answer,
            databaseId: this.problemDetail.databaseId,
            difficulty: this.problemDetail.difficulty,
            isUpdate: this.problemDetail.isUpdate,
            selectAfterUpdate: this.problemDetail.selectAfterUpdate
          }
          if (this.isAdd) {
            this.handleResponse(createProblem(problem.title, problem.description, problem.sampleOutput, problem.hint, problem.answer, problem.databaseId, problem.difficulty, problem.isUpdate, problem.selectAfterUpdate), '添加题目',
              (res) => {
                this.$message.success('添加题目成功')
                this.$router.back(-1)
              })
          } else {
            this.handleResponse(updateProblem(this.problemId, problem.title, problem.description, problem.sampleOutput, problem.hint, problem.answer, problem.databaseId, problem.difficulty, problem.isUpdate, problem.selectAfterUpdate), '添加题目',
              (res) => {
                this.$message.success('更新题目成功')
                this.$router.back(-1)
              })
          }
        } else {
          this.$message.error('请确认所有项目均填写正确！')
        }
      })
    },
    onCancel() {
      this.$router.back(-1)
    },
    getDatabaseList() {
      this.handleResponse(getAllDatabase(), '获取数据库列表',
        (res) => {
          this.databaseList = res.data
        })
    },
    getDatabaseDetail(databaseId, viewType) {
      if (viewType === 'createTable') {
        this.viewDatabaseDetailDialogTitle = '查看建表语句'
      } else if (viewType === 'testData') {
        this.viewDatabaseDetailDialogTitle = '查看测试数据'
      }
      this.databaseDetailCode = ''
      this.handleResponse(getDatabaseDetail(databaseId), '获取数据库详情',
        (res) => {
          if (res.code === 0) {
            this.databaseDetailCode = res.data[viewType]
            this.viewDatabaseDetailDialogVisible = true
          } else {
            this.$message.error(res.message)
          }
        })
    }
  }
}
</script>

<style>
.CodeMirror {
  height: 200px !important;
}
</style>
