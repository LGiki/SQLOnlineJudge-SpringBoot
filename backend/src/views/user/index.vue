<template>
  <div class="app-container">
    <div class="operation-button">
      <el-input
        v-model="searchKeyword"
        prefix-icon="el-icon-search"
        placeholder="请输入搜索关键字"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="onSearch"
      />
      <el-select v-model="searchType">
        <el-option
          v-for="item in searchTypeList"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <el-button type="primary" @click="onSearch">
        <svg-icon icon-class="search" />&nbsp;搜索
      </el-button>
      <el-button v-if="inSearch" type="primary" @click="onCancelSearch">
        <i class="el-icon-close" />&nbsp;取消搜索
      </el-button>
      <el-button type="danger" @click="onNewUser">
        <i class="el-icon-plus" />&nbsp;添加用户
      </el-button>
      <el-button type="warning" @click="newUserBatchDialogVisible = true">
        <i class="el-icon-plus" />&nbsp;批量添加用户
      </el-button>
      <el-dialog title="批量添加用户" :visible.sync="newUserBatchDialogVisible" width="60%">
        <el-tabs v-model="activeName" type="card">
          <el-tab-pane label="根据学号批量添加" name="addByStudentNo">
            <el-form
              ref="newUserBatchByStudentNoData"
              :model="newUserBatchByStudentNoData"
              :rules="checkRules.addByStudentNoForm"
            >
              <el-form-item label="起始学号" prop="start">
                <el-input
                  v-model="newUserBatchByStudentNoData.start"
                  type="number"
                  placeholder="请输入起始学号"
                  @keyup.enter.native="onNewUserBatch"
                />
              </el-form-item>
              <el-form-item label="终止学号" prop="end">
                <el-input
                  v-model="newUserBatchByStudentNoData.end"
                  type="number"
                  placeholder="请输入终止学号"
                  @keyup.enter.native="onNewUserBatch"
                />
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="从Excel文件中导入" name="addFromExcel">
            <p>
              <strong>
                <h3>Excel中的数据需要严格按照下图中的要求：</h3>
              </strong>
            </p>
            <img
              id="hint-image"
              src="@/assets/create_users_in_bulk_hint.png"
              alt="import_excel_description"
            >
            <el-form>
              <form enctype="multipart/form-data">
                <el-form-item label="选择要导入的Excel">
                  <input
                    id="excel-file"
                    type="file"
                    name="files[]"
                    class="el-button"
                    @change="onExcelFileChange"
                  >
                </el-form-item>
              </form>
            </el-form>
          </el-tab-pane>
        </el-tabs>
        <br>
        <span slot="footer" class="dialog-footer">
          <strong>默认密码为用户学号</strong>
          <el-button @click="newUserBatchDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="onNewUserBatch">确定</el-button>
        </span>
      </el-dialog>
      <el-dialog title="批量添加用户确认" :visible.sync="newUserBatchConfirmDialogVisible" width="70%">
        <el-form>
          <el-form-item label="确认添加以下用户：">
            <el-input
              v-model="newStudentNoListStr"
              type="textarea"
              :disabled="true"
              :autosize="{ minRows: 8, maxRows: 8}"
            />
          </el-form-item>
          <p>
            将会添加
            <strong>{{ newStudentNoListCount }}</strong> 个用户
          </p>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="newUserBatchConfirmDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="onNewUserBatchConfirmed">确定添加</el-button>
        </span>
      </el-dialog>
      <el-dialog title="正在批量添加用户" :visible.sync="newUserBatchProgressDialogVisible" width="60%">
        <el-row>
          <el-col :span="12">
            <p class="center">
              <strong>待添加用户</strong>
            </p>
            <select class="newStudentList" multiple="multiple" size="8">
              <option v-for="item in newStudentNoList">{{ item.studentNo }}</option>
            </select>
          </el-col>
          <el-col :span="12">
            <p class="center">
              <strong>已添加用户</strong>
            </p>
            <select class="newStudentList" multiple="multiple" size="8">
              <option v-for="item in studentNoAddedList">{{ item }}</option>
            </select>
          </el-col>
        </el-row>
        <p>
          <strong>请等待新用户批量添加完成之后再关闭该窗口！</strong>
        </p>
        <span slot="footer" class="dialog-footer">
          <el-button @click="closeNewUserBatchProgressDialog">关闭</el-button>
        </span>
      </el-dialog>
    </div>
    <template>
      <v-table
        :width="1000"
        is-horizontal-resize
        style="width:100%"
        :is-loading="isLoading"
        :columns="tableConfig.columns"
        :table-data="tableConfig.tableData"
        :multiple-sort="false"
        row-hover-color="#eee"
        row-click-color="#edf7ff"
        @sort-change="sortChange"
        @on-custom-comp="onTableOperation"
      />
    </template>
    <template>
      <div class="bd">
        <v-pagination
          :show-paging-count="3"
          :total="totalItems"
          :page-size="pageSize"
          :layout="['total', 'sizer', 'prev', 'pager', 'next', 'jumper']"
          @page-change="pageChange"
          @page-size-change="pageSizeChange"
        />
      </div>
    </template>
  </div>
</template>

<script>
import 'vue-easytable/libs/themes-base/index.css'
import { VTable, VPagination } from 'vue-easytable'
import { getUserList, deleteUser } from '@/api/user'

export default {
  components: {
    VTable,
    VPagination
  },
  data() {
    return {
      orderByStudentNo: '', // 是否按照学号排序，可选值：'' => 按照用户ID降序, 'asc' => 按照学号升序， 'desc' => 按照学号降序
      newStudentNoListCount: 0, // 要批量添加的新用户的数量
      newStudentNoListStr: '', // 要批量添加的新用户学号字符串，一行一个学号
      newStudentNoList: [], // 要批量添加的新用户学号列表
      studentNoAddedList: [], // 已经添加成功的新用户学号列表
      currentAddProgress: 0,
      checkRules: {
        addByStudentNoForm: {
          start: [
            {
              required: true,
              message: '起始学号不能为空',
              trigger: 'blur'
            }
          ],
          end: [
            {
              required: true,
              message: '终止学号不能为空',
              trigger: 'blur'
            }
          ]
        }
      },
      searchTypeList: [
        {
          label: '用户ID',
          value: 'id'
        },
        {
          label: '用户名',
          value: 'username'
        },
        {
          label: '邮箱',
          value: 'email'
        },
        {
          label: '学号',
          value: 'studentNo'
        }
      ],
      selectedFile: '',
      newUserBatchByStudentNoData: {
        start: '',
        end: ''
      },
      activeName: 'addByStudentNo',
      newUserBatchDialogVisible: false,
      newUserBatchConfirmDialogVisible: false,
      newUserBatchProgressDialogVisible: false,
      searchType: 'id',
      inSearch: false,
      searchKeyword: '',
      pageNum: 1,
      pageSize: 20,
      totalItems: 0,
      isLoading: false,
      tableConfig: {
        tableData: [],
        columns: [
          {
            field: 'id',
            title: '用户ID',
            width: 100,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/user/edit/${rowData.id}">${rowData.id}</a>`
            }
          },
          {
            field: 'username',
            title: '用户名',
            width: 200,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/user/edit/${rowData.id}" title="${rowData.username}">${rowData.username}</a>`
            }
          },
          {
            field: 'studentNo',
            title: '学号',
            width: 200,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            orderBy: ''
          },
          {
            field: 'email',
            title: '邮箱',
            width: 100,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'status',
            title: '用户状态',
            width: 100,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'action',
            title: '操作',
            width: 300,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            componentName: 'user-operation-button-with-solution'
          }
        ]
      }
    }
  },
  created() {},
  mounted: function() {
    this.getUserList()
  },
  methods: {
    onChangeTableStatus() {
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.getUserList()
      }
    },
    sortChange(params) {
      this.orderByStudentNo = params.studentNo
      this.onChangeTableStatus()
    },
    parseExcel(file) {
      const that = this
      let isFileFormatCorrect = true
      var reader = new FileReader()
      reader.onload = function(e) {
        var data = e.target.result
        // eslint-disable-next-line no-undef
        var workbook = XLSX.read(data, {
          type: 'binary'
        })
        workbook.SheetNames.forEach(function(sheetName) {
          // eslint-disable-next-line no-undef
          var XL_row_object = XLSX.utils.sheet_to_row_object_array(
            workbook.Sheets[sheetName]
          )
          var json_object = JSON.stringify(XL_row_object)
          var jsonResult = JSON.parse(json_object)
          for (const row of jsonResult) {
            Object.keys(row).map(key => row[key.trim()] = typeof row[key] === 'string' ? row[key].trim() : row[key])
            if (isFileFormatCorrect && row.hasOwnProperty('学号')) {
              const newStudent = {}
              if (row.hasOwnProperty('姓名')) {
                newStudent.username = row['姓名']
                newStudent.studentNo = row['学号']
              } else {
                newStudent.username = row['学号']
                newStudent.studentNo = row['学号']
              }
              // console.log(sheetName);
              that.newStudentNoList.push(newStudent)
            } else {
              isFileFormatCorrect = false
              break
            }
          }
        })
        if (isFileFormatCorrect) {
          that.refreshStudentNoListStr()
          that.newUserBatchConfirmDialogVisible = true
          that.newUserBatchDialogVisible = false
        } else {
          that.$message.error('请检查 Excel 文件格式是否正确！')
        }
      }
      reader.onerror = function(ex) {
        console.log(ex)
      }
      reader.readAsBinaryString(file)
    },
    closeNewUserBatchProgressDialog() {
      this.newUserBatchProgressDialogVisible = false
      this.getUserList()
    },
    isEmpty(obj) {
      return typeof obj === 'undefined' || obj == null || obj === ''
    },
    onExcelFileChange(event) {
      this.selectedFile = event.target.files[0]
    },
    onSearch() {
      const keyword = this.searchKeyword.trim()
      this.pageNum = 1
      if (keyword.length === 0) {
        this.$message.error('请输入关键字！')
      } else {
        this.isLoading = true
        this.handleResponse(getUserList(this.pageNum, this.pageSize, this.orderByStudentNo, this.searchType, keyword), '搜索用户',
          (res) => {
            this.tableConfig.tableData = res.data.records
            this.totalItems = res.data.total
            this.inSearch = true
          },
          null,
          null,
          () => {
            this.isLoading = false
          })
      }
    },
    onNewUserBatchConfirmed() {
      this.newUserBatchConfirmDialogVisible = false
      this.newUserBatchProgressDialogVisible = true
      for (const studentNo of this.newStudentNoList) {
        this.addUserInBulk(studentNo)
      }
    },
    onCancelSearch() {
      this.inSearch = false
      this.getUserList()
    },
    refreshStudentNoListStr() {
      if (this.newStudentNoList) {
        this.newStudentNoListCount = this.newStudentNoList.length
        this.newStudentNoListStr = '' // Clear
        for (const newStudent of this.newStudentNoList) {
          if (newStudent.studentNo === newStudent.username) {
            this.newStudentNoListStr += newStudent.studentNo + '\n'
          } else {
            this.newStudentNoListStr +=
              newStudent.studentNo + ' - ' + newStudent.username + '\n'
          }
        }
      }
    },
    addUserInBulk(student) {
      const apiUrl = this.Url.userBaseUrl
      const postData = {
        username: student.username,
        studentNo: student.studentNo,
        email: student.studentNo + '@jmu.edu.cn',
        password: student.studentNo
      }
      this.$axios
        .post(apiUrl, postData)
        .then(res => {
          if (res.status !== 200) {
            // this.$message.error("添加用户" + student.studentNo + "失败，内部错误！");
          } else {
            const resData = res.data
            if (resData.code === 0) {
              const studentNoIndexOf = this.newStudentNoList.indexOf(student)
              if (studentNoIndexOf != -1) {
                this.newStudentNoList.splice(studentNoIndexOf, 1)
                this.studentNoAddedList.push(student.studentNo)
              }
            }
          }
          this.currentAddProgress += 1
          if (this.currentAddProgress == this.newStudentNoListCount) {
            if (this.newStudentNoList.length === 0) {
              this.$message({
                message: '批量添加用户操作完成，全部用户均添加成功！',
                type: 'success'
              })
            } else {
              this.$message.error('批量添加用户操作完成，部分用户添加失败！')
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    onNewUserBatch() {
      this.newStudentNoList.length = 0
      this.studentNoAddedList.length = 0
      this.currentAddProgress = 0
      switch (this.activeName) {
        case 'addByStudentNo':
          if (
            this.isEmpty(this.newUserBatchByStudentNoData.start) ||
            this.isEmpty(this.newUserBatchByStudentNoData.end)
          ) {
            this.$message.error('请检查起始学号与终止学号是否输入完整！')
          } else {
            const startNo = Number(this.newUserBatchByStudentNoData.start)
            const endNo = Number(this.newUserBatchByStudentNoData.end)
            if (startNo > endNo) {
              this.$message.error('起始学号不能大于终止学号！')
            } else {
              for (let i = startNo; i <= endNo; i++) {
                this.newStudentNoList.push({
                  username: i,
                  studentNo: i
                })
              }
              this.refreshStudentNoListStr()
              this.newUserBatchConfirmDialogVisible = true
              this.newUserBatchDialogVisible = false
            }
          }
          break
        case 'addFromExcel':
          if (
            this.selectedFile == null ||
            this.selectedFile === '' ||
            (!this.selectedFile.name.endsWith('xls') &&
              !this.selectedFile.name.endsWith('xlsx'))
          ) {
            this.$message.error('请正确选择Excel文件！')
          } else {
            this.parseExcel(this.selectedFile)
          }
          break
        default:
          break
      }
    },
    onNewUser() {
      this.$router.push({ path: '/user/add/' })
    },
    onTableOperation(params) {
      const index = params.index
      const userId = this.tableConfig.tableData[index].id
      if (params.type === 'delete') {
        let confirmMessage = '您确定要锁定该用户吗？'
        let operationTypeString = '锁定'
        if (this.tableConfig.tableData[index].status !== '正常') {
          confirmMessage = '您确定要解锁该用户吗？'
          operationTypeString = '解锁'
        }
        this.$confirm(confirmMessage, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.handleResponse(deleteUser(userId),
            (res) => {
              this.$message.success(`${operationTypeString}用户成功`)
              this.getUserList()
            })
        })
      } else if (params.type === 'edit') {
        this.$router.push({ path: '/user/edit/' + userId })
      }
    },
    pageChange(pageNum) {
      this.pageNum = pageNum
      this.onChangeTableStatus()
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize
      this.onChangeTableStatus()
    },
    getUserList() {
      this.isLoading = true
      this.handleResponse(getUserList(this.pageNum, this.pageSize, this.orderByStudentNo), '获取用户列表',
        (res) => {
          this.tableConfig.tableData = res.data.records
          this.totalItems = res.data.total
        },
        null,
        null,
        () => {
          this.isLoading = false
        })
    }
  }
}
</script>
<style lang="scss" scoped>
.bd {
  padding-top: 15px;
  text-align: center;
}

.operation-button {
  float: right;
  padding-bottom: 10px;
}

.newStudentList {
  text-align: center;
  width: 100%;
}

.center {
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
