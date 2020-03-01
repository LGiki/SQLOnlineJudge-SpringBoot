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
      <el-button type="danger" @click="onNewProblem">
        <i class="el-icon-plus" />&nbsp;新建题目
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
        @on-custom-comp="customCompFunc"
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

export default {
  components: {
    VTable,
    VPagination
  },
  data() {
    return {
      searchTypeList: [
        {
          label: '题目ID',
          value: 'id'
        },
        {
          label: '题目标题',
          value: 'title'
        }
      ],
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
            title: '题目ID',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/problem/edit/${rowData.id}">${rowData.id}</a>`
            }
          },
          {
            field: 'title',
            title: '题目标题',
            width: 280,
            titleAlign: 'center',
            // columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/problem/edit/${rowData.id}" title="${rowData.title}">${rowData.title}</a>`
            }
          },
          {
            field: 'solved',
            title: '通过数',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'submit',
            title: '提交数',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'accept_rate',
            title: '通过率',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return rowData.submit === 0
                ? 0
                : (rowData.solved / rowData.submit).toFixed(2)
            }
          },
          {
            field: 'action',
            title: '操作',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            componentName: 'table-operation'
          }
        ]
      }
    }
  },
  mounted: function() {
    this.fetchProblemList()
  },
  methods: {
    onSearch() {
      const keyword = this.searchKeyword.trim()
      if (keyword.length === 0) {
        this.$message.error('请输入关键字！')
      } else {
        this.isLoading = true
        const apiUrl = this.Url.problemBaseUrl
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
            this.isLoading = false
            this.$message.error('搜索失败！')
            console.log(err)
          })
      }
    },
    onCancelSearch() {
      this.inSearch = false
      this.fetchProblemList()
    },
    onNewProblem() {
      this.$router.push({ path: '/problem/add/' })
    },
    customCompFunc(params) {
      const index = params.index
      const problemId = this.tableConfig.tableData[index].id
      if (params.type === 'delete') {
        if (confirm('您确定要删除该题目吗？')) {
          this.deleteProblem(problemId, () => {
            this.$message.success('删除题目成功！')
            this.fetchProblemList()
          })
        }
      } else if (params.type === 'edit') {
        this.$router.push({ path: '/problem/edit/' + problemId })
      }
    },
    pageChange(pageNum) {
      this.pageNum = pageNum
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.fetchProblemList()
      }
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.fetchProblemList()
      }
    },
    fetchProblemList() {
      this.isLoading = true
      const apiUrl = this.Url.problemBaseUrl
      this.$axios
        .get(apiUrl, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
        })
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取题目列表失败，内部错误！')
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
          this.$message.error('获取题目列表失败！')
          this.isLoading = false
          console.log(err)
        })
    },
    deleteProblem(problemId, successCallback) {
      const apiUrl = this.Url.problemBaseUrl
      this.$axios
        .delete(apiUrl + problemId)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('删除题目失败，内部错误！')
          } else {
            successCallback()
          }
        })
        .catch(err => {
          this.$message.error('删除题目失败！')
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
