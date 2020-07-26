<template>
  <div class="app-container">
    <el-form ref="databaseDetail" :model="databaseDetail" :rules="checkRules" label-width="120px">
      <el-form-item v-if="!isAdd" label="数据库ID">
        <el-input v-model="databaseDetail.id" disabled />
      </el-form-item>
      <el-form-item label="数据库名称" prop="name">
        <el-input v-model="databaseDetail.name" />
      </el-form-item>
      <el-form-item label="建表语句" prop="createTable">
        <codemirror v-model="databaseDetail.createTable" :options="cmOptions" @ready="onCmReady" />
        <el-input v-if="false" v-model="databaseDetail.createTable" />
      </el-form-item>
      <el-dialog
        title="从Excel导入测试数据"
        :visible.sync="importFromExcelDialogVisible"
        width="60%"
      >
        <el-form>
          <h3>Excel中的数据需要严格按照下图中的要求：</h3>
          <img id="hint-image" src="@/assets/import_excel_description.png" alt="import_excel_description">
          <form enctype="multipart/form-data">
            <el-form-item label="选择要导入的Excel">
              <input id="excel-file" type="file" name="files[]" class="el-button" @change="onExcelFileChange">
            </el-form-item>
          </form>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="importFromExcelDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="importFromExcel()">确定导入</el-button>
        </span>
      </el-dialog>
      <el-form-item label="测试数据" prop="testData">
        <p><el-button @click="importFromExcelDialogVisible = true">从Excel导入测试数据</el-button></p>
        <codemirror v-model="databaseDetail.testData" :options="cmOptions" @ready="onCmReady" />
        <el-input v-if="false" v-model="databaseDetail.testData" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import 'codemirror/mode/sql/sql.js'
import 'codemirror/theme/solarized.css'
import 'codemirror/addon/hint/show-hint.js'
import 'codemirror/addon/hint/sql-hint.js'
import 'codemirror/addon/edit/matchbrackets.js'
import 'codemirror/addon/hint/show-hint.css'
import { getDatabaseDetail, createDatabase, updateDatabase } from '@/api/database'

export default {
  components: {
    codemirror
  },
  data() {
    return {
      selectedFile: '',
      importFromExcelDialogVisible: false,
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
      checkRules: {
        name: [
          {
            required: true,
            message: '数据库名称不能为空',
            trigger: 'blur'
          }
        ],
        createTable: [
          {
            required: true,
            message: '建表语句不能为空',
            trigger: 'blur'
          }
        ],
        testData: [
          {
            required: true,
            message: '测试数据不能为空',
            trigger: 'blur'
          }
        ]
      },
      databaseDetail: {
        id: '',
        name: '',
        createTable: '',
        testData: ''
      }
    }
  },
  computed: {
    codemirror() {
      return this.$refs.myCm.codemirror
    },
    isAdd() {
      return this.$route.params.id === undefined
    }
  },
  mounted: function() {
    const databaseId = this.$route.params.id
    if (!this.isAdd) {
      this.getDatabaseDetail(databaseId)
    }
  },
  methods: {
    parseExcel(file) {
      const that = this
      var reader = new FileReader()
      reader.onload = function(e) {
        var data = e.target.result
        // eslint-disable-next-line no-undef
        var workbook = XLSX.read(data, {
          type: 'binary'
        })
        that.databaseDetail.testData = ''
        workbook.SheetNames.forEach(function(sheetName) {
          // eslint-disable-next-line no-undef
          var XL_row_object = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName])
          var json_object = JSON.stringify(XL_row_object)
          var jsonResult = JSON.parse(json_object)
          for (const index in jsonResult) {
            const row = jsonResult[index]
            const tableFieldList = []
            const tableValues = []
            for (const keyName in row) {
              tableFieldList.push(keyName)
              tableValues.push(row[keyName])
            }
            const sqlExpression = that.constructSQLExpression(sheetName, tableFieldList, tableValues)
            that.databaseDetail.testData += sqlExpression + '\n'
          }
        })
      }
      reader.onerror = function(ex) {
        console.log(ex)
      }
      reader.readAsBinaryString(file)
    },
    onExcelFileChange(event) {
      this.selectedFile = event.target.files[0]
    },
    importFromExcel() {
      if (this.selectedFile == null || this.selectedFile === '' || (!this.selectedFile.name.endsWith('xls') && !this.selectedFile.name.endsWith('xlsx'))) {
        this.$message.error('请正确选择Excel文件！')
      } else {
        this.parseExcel(this.selectedFile)
      }
      this.importFromExcelDialogVisible = false
    },
    onCmReady(cm) {
      cm.on('keypress', () => {
        cm.showHint()
      })
    },
    onSubmit() {
      this.$refs.databaseDetail.validate(valid => {
        if (valid) {
          const database = {
            name: this.databaseDetail.name.trim(),
            createTable: this.databaseDetail.createTable.trim(),
            testData: this.databaseDetail.testData.trim()
          }
          if (this.isAdd) {
            this.handleResponse(createDatabase(database.name, database.createTable, database.testData), '添加数据库',
              (res) => {
                this.$message.success('添加数据库成功')
                this.$router.back(-1)
              })
          } else {
            this.handleResponse(updateDatabase(this.$route.params.id, database.name, database.createTable, database.testData), '更新数据库',
              (res) => {
                this.$message.success('更新数据库成功')
                this.$router.back(-1)
              }
            )
          }
        } else {
          this.$message.error('请确认所有项目均填写正确！')
        }
      })
    },
    onCancel() {
      this.$router.back(-1)
    },
    getDatabaseDetail(databaseId) {
      this.handleResponse(getDatabaseDetail(databaseId), '获取数据库详情', res => {
        this.databaseDetail = res.data
      })
    }
  }
}
</script>

<style scoped>
#hint-image {
  margin-bottom: 5px;
  width: auto;
  height: auto;
  max-width: 90%;
  max-height: 90%;
}
</style>

