<template>
  <div class="app-container">
    <div class="operation-button">
      <el-input
        v-model="searchKeyword"
        placeholder="请输入搜索关键字"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="onSearch"
      />
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
      />
    </template>
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
import 'vue-easytable/libs/themes-base/index.css'
import { VTable, VPagination } from 'vue-easytable'
import 'vue-highlight.js/lib/allLanguages'
import 'highlight.js/styles/atom-one-light.css'

export default {
  components: {
    VTable,
    VPagination
  },
  data() {
    return {
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
            field: 'problemTitle',
            title: '题目标题',
            width: 280,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/problem/edit/${rowData.pid}" title="${rowData.problemTitle}">${rowData.problemTitle}</a>`
            }
          },
          {
            field: 'sourceCode',
            title: '代码',
            width: 280,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'result',
            title: '运行结果',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
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
    onSearch() {
      const keyword = this.searchKeyword.trim()
      if (keyword.length === 0) {
        this.$message.error('请输入关键字！')
      } else {
        this.isLoading = true
        const apiUrl = this.Url.solutionSearch
        this.$axios
          .get(apiUrl, {
            params: {
              keyword: keyword,
              pageNum: this.pageNum,
              pageSize: this.pageSize
            }
          })
          .then(res => {
            if (res.status !== 200) {
              this.$message.error('搜索失败，网络错误！')
            } else {
              const resData = res.data
              if (resData.code === 200) {
                this.tableConfig.tableData = resData.data.list
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
      this.fetchSolutionList()
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize
      this.fetchSolutionList()
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
            this.$message.error('获取用户提交列表失败，网络错误！')
          } else {
            const resData = res.data
            if (resData.code === 200) {
              this.tableConfig.tableData = resData.data.list
              this.totalItems = resData.data.total
            }
          }
          this.isLoading = false
        })
        .catch(err => {
          this.$message.error('获取用户提交列表失败！')
          this.isLoading = false
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
