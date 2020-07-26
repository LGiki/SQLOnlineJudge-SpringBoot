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
        :width="1000"
        is-horizontal-resize
        style="width:100%"
        :is-loading="isLoading"
        :columns="tableConfig.columns"
        :table-data="tableConfig.tableData"
        row-hover-color="#eee"
        row-click-color="#edf7ff"
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
import { getProblemList, deleteProblem } from '@/api/problem'

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
        },
        {
          label: '题目难度',
          value: 'difficulty'
        }
      ],
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
            field: 'problemId',
            title: '题目ID',
            width: 100,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/problem/edit/${rowData.problemId}">${rowData.problemId}</a>`
            }
          },
          {
            field: 'databaseName',
            title: '数据库',
            width: 200,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/problem/database/${rowData.databaseId}" title="${rowData.databaseName}">${rowData.databaseName}</a>`
            }
          },
          {
            field: 'problemTitle',
            title: '题目标题',
            width: 500,
            titleAlign: 'center',
            columnAlign: 'left',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/problem/edit/${rowData.problemId}" title="${rowData.problemTitle}">${rowData.problemTitle}</a>`
            }
          },
          {
            field: 'problemDifficulty',
            title: '难度',
            width: 100,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              let difficultyStars = ''
              for (let i = 0; i < rowData.problemDifficulty; i++) {
                difficultyStars += '★'
              }
              return difficultyStars
            }
          },
          {
            field: 'action',
            title: '操作',
            width: 300,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            componentName: 'common-operation-button-with-solution'
          }
        ]
      }
    }
  },
  mounted: function() {
    this.getProblemList()
  },
  methods: {
    onSearch() {
      const keyword = this.searchKeyword.trim()
      this.pageNum = 1
      if (keyword.length === 0) {
        this.$message.error('请输入关键字！')
      } else {
        this.isLoading = true
        this.handleResponse(getProblemList(this.pageNum, this.pageSize, this.searchType, keyword), '搜索题目',
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
    onCancelSearch() {
      this.inSearch = false
      this.getProblemList()
    },
    onNewProblem() {
      this.$router.push({ path: '/problem/add/' })
    },
    onTableOperation(params) {
      const index = params.index
      const problemId = this.tableConfig.tableData[index].problemId
      if (params.type === 'delete') {
        this.$confirm('此操作将永久删除该题目, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.handleResponse(deleteProblem(problemId), '删除题目',
            (res) => {
              this.$message.success('成功删除题目！')
              this.getProblemList()
            })
        })
      } else if (params.type === 'edit') {
        this.$router.push({ path: '/problem/edit/' + problemId })
      } else if (params.type === 'showSolution') {
        this.$router.push({ path: '/solution/problem/' + problemId })
      }
    },
    pageChange(pageNum) {
      this.pageNum = pageNum
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.getProblemList()
      }
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.getProblemList()
      }
    },
    getProblemList() {
      this.isLoading = true
      this.handleResponse(getProblemList(this.pageNum, this.pageSize), '获取题目列表',
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
</style>
