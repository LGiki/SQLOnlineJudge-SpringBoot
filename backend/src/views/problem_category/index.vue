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
        <i class="el-icon-plus" />&nbsp;新建题目集
      </el-button>
    </div>
    <template>
      <v-table
        :width="1100"
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
import { getProblemCategoryList, deleteProblemCategory } from '@/api/problem-category'

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
            width: 50,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'name',
            title: '题目集名称',
            width: 350,
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
            width: 200,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'endTime',
            title: '结束时间',
            width: 200,
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
  created() {},
  mounted: function() {
    this.getProblemCategoryList()
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
        this.handleResponse(getProblemCategoryList(this.pageNum, this.pageSize, this.searchType, keyword), '搜索题目集',
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
    getProblemCategoryList() {
      this.isLoading = true
      this.handleResponse(getProblemCategoryList(this.pageNum, this.pageSize), '搜索题目集',
        (res) => {
          this.tableConfig.tableData = res.data.records
          this.totalItems = res.data.total
        },
        null,
        null,
        () => {
          this.isLoading = false
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
          this.handleResponse(deleteProblemCategory(problemCategoryId), '删除题目集',
            (res) => {
              this.$message.success('成功删除题目集！')
              this.getProblemCategoryList()
            })
        })
      } else if (params.type === 'edit') {
        this.$router.push({ path: '/problem-category/edit/' + problemCategoryId })
      } else if (params.type === 'showSolution') {
        this.$router.push({ path: '/solution/problem_category/' + problemCategoryId })
      }
    },
    onNewProblemCollection() {
      this.$router.push({ path: '/problem-category/add/' })
    },
    onCancelSearch() {
      this.inSearch = false
      this.getProblemCategoryList()
    },
    pageChange(pageNum) {
      this.pageNum = pageNum
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.getProblemCategoryList()
      }
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.getProblemCategoryList()
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
