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
      <el-button type="danger" @click="onNewDatabase">
        <i class="el-icon-plus" />&nbsp;新建数据库
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
import { getDatabaseList, deleteDatabase } from '@/api/database'

export default {
  components: {
    VTable,
    VPagination
  },
  data() {
    return {
      searchTypeList: [
        {
          label: '数据库ID',
          value: 'id'
        },
        {
          label: '数据库名称',
          value: 'name'
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
            field: 'id',
            title: '数据库ID',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/database/edit/${rowData.id}">${rowData.id}</a>`
            }
          },
          {
            field: 'name',
            title: '数据库名称',
            width: 280,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/database/edit/${rowData.id}" title="${rowData.name}">${rowData.name}</a>`
            }
          },
          {
            field: 'action',
            title: '操作',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            componentName: 'common-operation-button'
          }
        ]
      }
    }
  },
  mounted: function() {
    this.getDatabaseList()
  },
  methods: {
    onSearch() {
      const keyword = this.searchKeyword.trim()
      this.pageNum = 1
      if (keyword.length === 0) {
        this.$message.error('请输入关键字后再进行搜索')
      } else {
        this.isLoading = true
        this.handleResponse(getDatabaseList(this.pageNum, this.pageSize, this.searchType, keyword), '搜索数据库', (res) => {
          if (res.data.total === 0) {
            this.$message({
              type: 'warning',
              message: '未找到相关数据库'
            })
          }
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
      this.getDatabaseList()
    },
    onNewDatabase() {
      this.$router.push({ path: '/database/add/' })
    },
    onTableOperation(params) {
      const index = params.index
      const databaseId = this.tableConfig.tableData[index].id
      if (params.type === 'delete') {
        this.$confirm('此操作将永久删除该数据库, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.handleResponse(deleteDatabase(databaseId), '删除数据库', () => {
            this.$message.success('成功删除数据库！')
            this.getDatabaseList()
          })
        })
      } else if (params.type === 'edit') {
        this.$router.push({ path: '/database/edit/' + databaseId })
      }
    },
    pageChange(pageNum) {
      this.pageNum = pageNum
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.getDatabaseList()
      }
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.getDatabaseList()
      }
    },
    getDatabaseList() {
      this.isLoading = true
      this.handleResponse(getDatabaseList(this.pageNum, this.pageSize), '获取数据库列表', res => {
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
