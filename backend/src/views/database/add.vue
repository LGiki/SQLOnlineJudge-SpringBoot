<template>
  <div class="app-container">
    <el-form ref="databaseDetail" :model="databaseDetail" :rules="checkRules" label-width="120px">
      <el-form-item label="数据库名称" prop="name">
        <el-input v-model="databaseDetail.name" placeholder="请输入数据库名称" />
      </el-form-item>
      <el-form-item label="建表语句" prop="createTable">
        <codemirror v-model="databaseDetail.createTable" :options="cmOptions" @ready="onCmReady" />
        <el-input v-if="false" v-model="databaseDetail.createTable" />
      </el-form-item>
      <el-dialog
        title="从Excel导入测试数据 (BETA)"
        :visible.sync="importFromExcelDialogVisible"
        width="50%"
      >
        <p><strong><h3>Excel中的数据需要严格按照下图中的要求：</h3></strong></p>
        <img id="hint-image" src="@/assets/import_excel_description.png" alt="import_excel_description">
        <br>
        <form enctype="multipart/form-data">
          <label for="excel-file">选择要导入的Excel: &nbsp;</label>
            <input @change="onExcelFileChange" id="excel-file" type=file name="files[]" />
        </form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="importFromExcelDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="importFromExcel()">确定导入</el-button>
        </span>
      </el-dialog>
      <el-form-item label="测试数据" prop="testData">
        <p><el-button @click="importFromExcelDialogVisible = true">从Excel导入测试数据 (BETA)</el-button></p>
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
        name: '',
        createTable: '',
        testData: ''
      }
    }
  },
  computed: {
    codemirror() {
      return this.$refs.myCm.codemirror
    }
  },
  methods: {
    onCmReady(cm) {
      cm.on('keypress', () => {
        cm.showHint()
      })
    },
    onSubmit() {
      this.$refs.databaseDetail.validate(valid => {
        if (valid) {
          this.addDatabase()
        } else {
          this.$message.error('请确认所有项目均填写正确！')
        }
      })
    },
    onCancel() {
      this.$router.back(-1)
    },
    parseExcel(that, file) {
      var reader = new FileReader();
      reader.onload = function (e) {
          var data = e.target.result;
          var workbook = XLSX.read(data, {
              type: 'binary'
          });
          that.databaseDetail.testData = '';
          workbook.SheetNames.forEach(function (sheetName) {
              var XL_row_object = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
              var json_object = JSON.stringify(XL_row_object);
              var jsonResult = JSON.parse(json_object);
              for (let index in jsonResult) {
                  let row = jsonResult[index];
                  let tableFieldList = [];
                  let tableValues = [];
                  for (let keyName in row) {
                      tableFieldList.push(keyName);
                      tableValues.push(row[keyName]);
                  }
                  let sqlExpression = that.constructSQLExpression(sheetName, tableFieldList, tableValues);
                  that.databaseDetail.testData += sqlExpression + '\n';
              }
          })
      };
      reader.onerror = function (ex) {
          console.log(ex);
      };
      reader.readAsBinaryString(file);
    },
    onExcelFileChange(event) {
      this.selectedFile = event.target.files[0];
    },
    importFromExcel() {
      if (this.selectedFile == null || this.selectedFile === '' || (!this.selectedFile.name.endsWith('xls') && !this.selectedFile.name.endsWith('xlsx'))) {
        this.$message.error('请正确选择Excel文件！')
      }else{
        this.parseExcel(this, this.selectedFile);
      }
      this.importFromExcelDialogVisible = false;
    },
    addDatabase() {
      const apiUrl = this.Url.databaseBaseUrl
      const postData = this.databaseDetail
      postData.name.trim()
      postData.createTable.trim()
      postData.testData.trim()
      this.$axios
        .post(apiUrl, postData)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('添加数据库失败，内部错误！')
          } else {
            const resData = res.data
            if (resData.code === 0) {
              this.$message({
                message: resData.message,
                type: 'success'
              })
              this.$router.back(-1)
            } else {
              this.$message.error(resData.message)
            }
          }
        })
        .catch(err => {
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
#hint-image {
  margin-bottom: 5px;
  width: auto;
  height: auto;
  max-width: 90%;
  max-height: 90%; 
}
</style>

