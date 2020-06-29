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
      <el-button type="danger" @click="onNewProblemCollection">
        <i class="el-icon-plus" />&nbsp;添加题目集
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

export default {
  components: {
    VTable,
    VPagination
  },
  data() {
    return {
      searchTypeList: [
        {
          label: '题目集ID',
          value: 'id'
        },
        {
          label: '题目集名称',
          value: 'name'
        }
      ],
      sourceCode: '',
      dialogVisible: false,
      runError: null,
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
            title: 'ID',
            width: 5,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'name',
            title: '题目集名称',
            width: 50,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/problem-category/edit/${rowData.id}" title="${rowData.name}">${rowData.name}</a>`
            }
          },
          {
            field: 'startTime',
            title: '开始时间',
            width: 50,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'endTime',
            title: '结束时间',
            width: 50,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'viewAfterEnd',
            title: '结束后能否查看题目',
            width: 50,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return rowData.viewAfterEnd ? '能' : '否'
            }
          },
          {
            field: 'action',
            title: '操作',
            width: 20,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            componentName: 'table-operation'
          }
        ]
      }
    }
  },
  created() {},
  mounted: function() {
    this.fetchProblemCategoryList()
  },
  methods: {
    rowClick(rowIndex, rowData, column) {
    },
    onSearch() {
      const keyword = this.searchKeyword.trim()
      this.pageNum = 1
      if (keyword.length === 0) {
        this.$message.error('请输入关键字！')
      } else {
        this.isLoading = true
        const apiUrl = this.Url.problemCategoryBaseUrl
        this.$axios
          .get(apiUrl, {
            params: {
              [this.searchType]: keyword,
              pageNum: this.pageNum
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
    fetchProblemCategoryList() {
      this.isLoading = true
      const apiUrl = this.Url.problemCategoryBaseUrl
      this.$axios
        .get(apiUrl, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
        })
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取题目集列表失败，内部错误！')
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
          this.$message.error('获取题目集列表失败！')
          this.isLoading = false
          console.log(err)
        })
    },
    deleteProblemCategory(problemCategoryId, successCallback) {
      const apiUrl = this.Url.problemCategoryBaseUrl
      this.$axios
        .delete(apiUrl + problemCategoryId)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('删除题目集失败，内部错误！')
          } else {
            successCallback()
          }
        })
        .catch(err => {
          this.$message.error('删除题目集失败！')
          console.log(err)
        })
    },
    onTableOperation(params) {
      const index = params.index
      const problemCategoryId = this.tableConfig.tableData[index].id
      if (params.type === 'delete') {
        this.$confirm('此操作将永久删除该题目集, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteProblemCategory(problemCategoryId, () => {
            this.$message.success('成功删除题目集！')
            this.fetchProblemCategoryList()
          })
        });
      } else if (params.type === 'edit') {
        this.$router.push({ path: '/problem-category/edit/' + problemCategoryId })
      }
    },
    onNewProblemCollection() {
      this.$router.push({ path: '/problem-category/add/' })
    },
    onCancelSearch() {
      this.inSearch = false
      this.fetchProblemCategoryList()
    },
    pageChange(pageNum) {
      this.pageNum = pageNum
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.fetchProblemCategoryList()
      }
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.fetchProblemCategoryList()
      }
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
