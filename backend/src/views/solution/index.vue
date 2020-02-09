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
    </div>
    <template>
      <v-table
        is-horizontal-resize
        style="width:100%"
        :is-loading="isLoading"
        :columns="tableConfig.columns"
        :table-data="tableConfig.tableData"
        row-hover-color="#eee"
        row-click-color="#edf7ff"
        :row-click="rowClick"
      />
    </template>
    <el-dialog
      title="查看代码"
      :visible.sync="dialogVisible"
      width="50%"
    >
      <!-- <code>{{ sourceCode }}</code> -->
      <highlight-code lang="sql">{{ sourceCode }}</highlight-code>
      <h3 v-if="runError">运行错误详情：</h3>
      <code v-if="runError">{{ runError }}</code>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
    <template>
      <div class="bd">
        <v-pagination
          :show-paging-count="3"
          :total="totalItems"
          :layout="['total', 'sizer', 'prev', 'pager', 'next', 'jumper']"
          @page-change="pageChange"
          @page-size-change="pageSizeChange"
        />
      </div>
    </template>
  </div>
</template>

<script>
import VueHighlightJS from 'vue-highlight.js'
import 'vue-highlight.js/lib/allLanguages'
import 'highlight.js/styles/atom-one-light.css'
import 'vue-easytable/libs/themes-base/index.css'
import { VTable, VPagination } from 'vue-easytable'
import 'vue-highlight.js/lib/allLanguages'
import 'highlight.js/styles/atom-one-light.css'

export default {
  components: {
    VueHighlightJS,
    VTable,
    VPagination
  },
  data() {
    return {
      searchTypeList: [
        {
          label: '提交ID',
          value: 'id'
        },
        {
          label: '用户ID',
          value: 'uid'
        },
        {
          label: '题目ID',
          value: 'pid'
        }
      ],
      sourceCode: '',
      dialogVisible: false,
      runError: null,
      searchType: 'id',
      inSearch: false,
      searchKeyword: '',
      pageNum: 1,
      pageSize: 10,
      totalItems: 0,
      isLoading: false,
      tableConfig: {
        tableData: [],
        columns: [
          {
            field: 'id',
            title: '提交ID',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'uid',
            title: '用户ID',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/user/edit/${rowData.uid}" title="${rowData.uid}">${rowData.uid}</a>`
            }
          },
          {
            field: 'username',
            title: '用户名',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/user/edit/${rowData.uid}" title="${rowData.username}">${rowData.username}</a>`
            }
          },
          {
            field: 'pid',
            title: '题目ID',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/problem/edit/${rowData.pid}" title="${rowData.pid}">${rowData.pid}</a>`
            }
          },
          {
            field: 'title',
            title: '题目标题',
            width: 280,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/problem/edit/${rowData.pid}" title="${rowData.title}">${rowData.title}</a>`
            }
          },
          {
            field: 'sourceCode',
            title: '代码',
            width: 100,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a>查看代码</a>`
            }
          },
          {
            field: 'result',
            title: '运行结果',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              let fontColor = 'black'
              switch (rowData.result) {
                case 'Accepted':
                  fontColor = 'blue'
                  break
                case 'Wrong Answer':
                  fontColor = 'red'
                  break
                case 'Compile Error':
                  fontColor = 'green'
                  break
                case 'Unknown':
                case 'Judging':
                  fontColor = 'grey'
                  break
              }
              return `<font color="${fontColor}">${rowData.result}</font>`
            }
          }
        ]
      }
    }
  },
  created() {},
  mounted: function() {
    this.fetchSolutionList()
  },
  methods: {
    rowClick(rowIndex, rowData, column) {
      if (column.field == 'sourceCode') {
        this.fetchSolutionCode(rowData.id)
      }
    },
    onSearch() {
      const keyword = this.searchKeyword.trim()
      if (keyword.length === 0) {
        this.$message.error('请输入关键字！')
      } else {
        this.isLoading = true
        const apiUrl = this.Url.solutionBaseUrl
        this.$axios
          .get(apiUrl, {
            params: {
              [this.searchType]: keyword,
              pageNum: this.pageNum,
              pageSize: this.pageSize
            }
          })
          .then(res => {
            if (res.status !== 200) {
              this.$message.error('搜索失败，内部错误！')
            } else {
              const resData = res.data
              if (resData.code === 0) {
                this.tableConfig.tableData = resData.data.records
                this.totalItems = resData.data.total
                this.inSearch = true
              } else {
                this.$message.error(resData.message)
              }
            }
            this.isLoading = false
          })
          .catch(err => {
            this.$message.error('搜索失败！')
            this.isLoading = false
            console.log(err)
          })
      }
    },
    onCancelSearch() {
      this.inSearch = false
      this.fetchSolutionList()
    },
    pageChange(pageNum) {
      this.pageNum = pageNum
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.fetchSolutionList()
      }
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.fetchSolutionList()
      }
    },
    fetchSolutionList() {
      this.isLoading = true
      const apiUrl = this.Url.solutionBaseUrl
      this.$axios
        .get(apiUrl, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
        })
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取用户提交列表失败，内部错误！')
          } else {
            const resData = res.data
            if (resData.code === 0) {
              this.tableConfig.tableData = resData.data.records
              this.totalItems = resData.data.total
            } else {
              this.$message.error(resData.message)
            }
          }
          this.isLoading = false
        })
        .catch(err => {
          this.$message.error('获取用户提交列表失败！')
          this.isLoading = false
          console.log(err)
        })
    },
    fetchSolutionCode(solutionId) {
      const apiUrl = this.Url.solutionCode
      this.$axios
        .get(apiUrl + solutionId)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取解答代码失败！')
          } else {
            const resData = res.data
            if (resData.code === 0) {
              this.sourceCode = resData.data.sourceCode
              this.runError = resData.data.runError
              this.dialogVisible = true
            } else {
              this.$message.error(resData.message)
            }
          }
          this.isLoading = false
        })
        .catch(err => {
          this.$message.error('获取解答代码失败！')
          console.log(err)
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
</style>
