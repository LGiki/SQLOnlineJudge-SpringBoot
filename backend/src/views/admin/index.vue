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
      pageSize: 10,
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
            componentName: 'user-operation'
          }
        ]
      }
    }
  },
  created() {},
  mounted: function() {
    this.fetchUserList()
  },
  methods: {
    onSearch() {
      const keyword = this.searchKeyword.trim()
      if (keyword.length === 0) {
        this.$message.error('请输入关键字！')
      } else {
        this.isLoading = true
        const apiUrl = this.Url.adminBaseUrl
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
      this.fetchUserList()
    },
    onNewUser() {
      this.$router.push({ path: '/admin/add/' })
    },
    customCompFunc(params) {
      const index = params.index
      const userId = this.tableConfig.tableData[index].id
      if (params.type === 'delete') {
        let confirmMessage = '您确定要锁定该管理员吗？'
        let operationTypeString = '锁定'
        if (this.tableConfig.tableData[index].status !== '正常') {
          confirmMessage = '您确定要解锁该管理员吗？'
          operationTypeString = '解锁'
        }
        if (confirm(confirmMessage)) {
          this.deleteUser(userId, operationTypeString, () => {
            this.fetchUserList()
          })
        }
      } else if (params.type === 'edit') {
        this.$router.push({ path: '/admin/edit/' + userId })
      }
    },
    pageChange(pageNum) {
      this.pageNum = pageNum
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.fetchUserList()
      }
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.fetchUserList()
      }
    },
    fetchUserList() {
      this.isLoading = true
      const apiUrl = this.Url.adminBaseUrl
      this.$axios
        .get(apiUrl, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
        })
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取管理员列表失败，内部错误！')
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
          this.$message.error('获取管理员列表失败！')
          this.isLoading = false
          console.log(err)
        })
    },
    deleteUser(userId, operationTypeString, successCallback) {
      const apiUrl = this.Url.adminStatusUrl
      this.$axios
        .put(apiUrl + userId)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error(operationTypeString + '管理员失败，内部错误！')
          } else {
            successCallback()
          }
        })
        .catch(err => {
          this.$message.error(operationTypeString + '管理员失败！')
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
