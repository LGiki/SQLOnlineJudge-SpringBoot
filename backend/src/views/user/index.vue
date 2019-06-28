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
      <el-button type="danger" @click="onNewUser">
        <i class="el-icon-plus" />&nbsp;新建用户
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
      inSearch: false,
      searchKeyword: '',
      pageNum: 1,
      pageSize: 10,
      totalItems: 0,
      isLoading: true,
      tableConfig: {
        tableData: [],
        columns: [
          {
            field: 'id',
            title: '用户ID',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/user/edit/${rowData.id}">${rowData.id}</a>`
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
              return `<a href="#/user/edit/${rowData.id}">${rowData.username}</a>`
            }
          },
          {
            field: 'email',
            title: '邮箱',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
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
        const apiUrl = this.Url.userSearch
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
              this.$message.error('网络错误！')
            } else {
              const resData = res.data
              if (resData.code === 200) {
                this.tableConfig.tableData = resData.data.list
                this.totalItems = resData.data.total
                this.isLoading = false
                this.inSearch = true
              }
            }
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    onCancelSearch() {
      this.inSearch = false
      this.fetchUserList()
    },
    onNewUser() {
      this.$router.push({ path: '/user/add/' })
    },
    customCompFunc(params) {
      const index = params.index
      const userId = this.tableConfig.tableData[index].id
      if (params.type === 'delete') {
        if (confirm('您确定要删除该用户吗？')) {
          this.deleteUser(userId, () => {
            this.fetchUserList()
          })
        }
      } else if (params.type === 'edit') {
        this.$router.push({ path: '/user/edit/' + userId })
      }
    },
    rowClick(rowIndex, rowData, column) {
      // this.$router.push({ path: "/user/edit/" + rowData.id });
    },
    pageChange(pageNum) {
      this.pageNum = pageNum
      this.fetchUserList()
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize
      this.fetchUserList()
    },
    fetchUserList() {
      const apiUrl = this.Url.userBaseUrl
      this.$axios
        .get(apiUrl, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
        })
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取用户列表失败，网络错误！')
          } else {
            const resData = res.data
            if (resData.code === 200) {
              this.tableConfig.tableData = resData.data.list
              this.totalItems = resData.data.total
              this.isLoading = false
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    deleteUser(userId, callback) {
      const apiUrl = this.Url.userBaseUrl
      this.$axios
        .delete(apiUrl + userId)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('删除用户失败，网络错误！')
          } else {
            const resData = res.data
            if (resData.code === 200) {
              this.$message({
                message: '删除用户成功！',
                type: 'success'
              })
              callback()
            }
          }
        })
        .catch(err => {
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
