<template>
  <div class="app-container">
    <template>
      <v-table
        :width="1350"
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
          :page-size="pageSize"
          @page-change="pageChange"
          @page-size-change="pageSizeChange"
        />
      </div>
    </template>
  </div>
</template>

<script>
import 'vue-highlight.js/lib/allLanguages'
import 'highlight.js/styles/atom-one-light.css'
import 'vue-easytable/libs/themes-base/index.css'
import { VTable, VPagination } from 'vue-easytable'
import { getSolutionList, getSolutionDetail } from '@/api/solution'

export default {
  components: {
    VTable,
    VPagination
  },
  data() {
    return {
      sourceCode: '',
      dialogVisible: false,
      runError: null,
      pageNum: 1,
      pageSize: 20,
      totalItems: 0,
      isLoading: false,
      tableConfig: {
        tableData: [],
        columns: [
          {
            field: 'id',
            title: '提交ID',
            width: 50,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          // {
          //   field: 'uid',
          //   title: '用户ID',
          //   width: 50,
          //   titleAlign: 'center',
          //   columnAlign: 'center',
          //   isResize: true,
          //   formatter: function(rowData, rowIndex, pagingIndex, field) {
          //     return `<a href="#/user/edit/${rowData.uid}" title="${rowData.uid}">${rowData.uid}</a>`
          //   }
          // },
          {
            field: 'username',
            title: '用户名',
            width: 100,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/user/edit/${rowData.uid}" title="${rowData.username}">${rowData.username}</a>`
            }
          },
          {
            field: 'studentNo',
            title: '学号',
            width: 150,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/user/edit/${rowData.uid}" title="${rowData.studentNo}">${rowData.studentNo}</a>`
            }
          },
          // {
          //   field: 'problemCategoryId',
          //   title: '题目集ID',
          //   width: 50,
          //   titleAlign: 'center',
          //   columnAlign: 'center',
          //   isResize: true,
          //   formatter: function(rowData, rowIndex, pagingIndex, field) {
          //     return `<a href="#/problem-category/edit/${rowData.problemCategoryId}" title="${rowData.problemCategoryId}">${rowData.problemCategoryId}</a>`
          //   }
          // },
          {
            field: 'problemCategoryTitle',
            title: '题目集标题',
            width: 200,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/problem-category/edit/${rowData.problemCategoryId}" title="${rowData.problemCategoryTitle}">${rowData.problemCategoryTitle}</a>`
            }
          },
          // {
          //   field: 'pid',
          //   title: '题目ID',
          //   width: 50,
          //   titleAlign: 'center',
          //   columnAlign: 'center',
          //   isResize: true,
          //   formatter: function(rowData, rowIndex, pagingIndex, field) {
          //     return `<a href="#/problem/edit/${rowData.pid}" title="${rowData.pid}">${rowData.pid}</a>`
          //   }
          // },
          {
            field: 'title',
            title: '题目标题',
            width: 400,
            titleAlign: 'center',
            // columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/problem/edit/${rowData.pid}" title="${rowData.title}">${rowData.title}</a>`
            }
          },
          {
            field: 'submitTime',
            title: '提交时间',
            width: 200,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
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
            width: 150,
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
  computed: {
    searchType() {
      switch (this.$route.name) {
        case 'Problem Solution':
          return 'pid'
        case 'Problem Category Solution':
          return 'problemCategoryId'
        case 'User Solution':
          return 'uid'
        default:
          return null
      }
    },
    searchId() {
      if (this.$route.params.problemId) {
        return this.$route.params.problemId
      } else if (this.$route.params.userId) {
        return this.$route.params.userId
      } else if (this.$route.params.problemCategoryId) {
        return this.$route.params.problemCategoryId
      }
      return null
    }
  },
  mounted: function() {
    if (!this.searchType || !this.searchId) {
      this.$message({
        message: '未指定过滤条件，将显示全部提交',
        type: 'warning'
      })
    }
    this.fetchSolutionList()
  },
  methods: {
    rowClick(rowIndex, rowData, column) {
      if (column.field === 'sourceCode') {
        this.fetchSolutionCode(rowData.id)
      }
    },
    pageChange(pageNum) {
      this.pageNum = pageNum
      this.fetchSolutionList()
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize
      this.fetchSolutionList()
    },
    fetchSolutionList() {
      this.isLoading = true
      this.handleResponse(getSolutionList(this.pageNum, this.pageSize, this.searchType, this.searchId), '获取解答列表',
        (resData) => {
          this.tableConfig.tableData = resData.data.records
          this.totalItems = resData.data.total
        },
        null,
        null,
        () => {
          this.isLoading = false
        })
    },
    fetchSolutionCode(solutionId) {
      this.handleResponse(getSolutionDetail(solutionId), '获取解答代码',
        (resData) => {
          this.sourceCode = resData.data.sourceCode
          this.runError = resData.data.runError
          this.dialogVisible = true
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
</style>
