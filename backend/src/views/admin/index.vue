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
        <i class="el-icon-plus" />&nbsp;新建管理员
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
import { getAdminList, deleteAdmin } from '@/api/admin'

export default {
  components: {
    VTable,
    VPagination
  },
  data() {
    return {
      searchTypeList: [
        {
          label: '管理员ID',
          value: 'id'
        },
        {
          label: '管理员用户名',
          value: 'username'
        },
        {
          label: '邮箱',
          value: 'email'
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
            title: '管理员ID',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/admin/edit/${rowData.id}">${rowData.id}</a>`
            }
          },
          {
            field: 'username',
            title: '管理员用户名',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/admin/edit/${rowData.id}" title="${rowData.username}">${rowData.username}</a>`
            }
          },
          {
            field: 'status',
            title: '管理员状态',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'action',
            title: '操作',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            componentName: 'user-operation-button'
          }
        ]
      }
    }
  },
  created() {},
  mounted: function() {
    this.getAdminList()
  },
  methods: {
    onSearch() {
      const keyword = this.searchKeyword.trim()
      this.pageNum = 1
      if (keyword.length === 0) {
        this.$message.error('请输入关键字！')
      } else {
        this.isLoading = true
        this.handleResponse(getAdminList(this.pageNum, this.pageSize, this.searchType, keyword), '搜索管理员',
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
      this.getAdminList()
    },
    onNewUser() {
      this.$router.push({ path: '/admin/add/' })
    },
    onTableOperation(params) {
      const index = params.index
      const adminId = this.tableConfig.tableData[index].id
      if (params.type === 'delete') {
        let confirmMessage = '您确定要锁定该管理员吗？'
        let operationTypeString = '锁定'
        if (this.tableConfig.tableData[index].status !== '正常') {
          confirmMessage = '您确定要解锁该管理员吗？'
          operationTypeString = '解锁'
        }
        if (confirm(confirmMessage)) {
          this.handleResponse(deleteAdmin(adminId), '删除管理员',
            (res) => {
              this.$message.success(`${operationTypeString}管理员成功`)
              this.getAdminList()
            })
        }
      } else if (params.type === 'edit') {
        this.$router.push({ path: '/admin/edit/' + adminId })
      }
    },
    pageChange(pageNum) {
      this.pageNum = pageNum
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.getAdminList()
      }
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.getAdminList()
      }
    },
    getAdminList() {
      this.isLoading = true
      this.handleResponse(getAdminList(this.pageNum, this.pageSize), '获取管理员列表',
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
