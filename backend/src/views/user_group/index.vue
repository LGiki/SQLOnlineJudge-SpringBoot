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
      <el-button type="danger" @click="onNewUserGroup">
        <i class="el-icon-plus" />&nbsp;添加用户组
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
          label: '用户组ID',
          value: 'id'
        },
        {
          label: '用户组名称',
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
            title: '用户组ID',
            width: 30,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'name',
            title: '用户组名称',
            width: 60,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/user-group/edit/${rowData.id}" title="${rowData.name}">${rowData.name}</a>`
            }
          },
          {
            field: 'description',
            title: '用户组简介',
            width: 200,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/user-group/edit/${rowData.id}" title="${rowData.description}">${rowData.description}</a>`
            }
          },
          {
            field: 'action',
            title: '操作',
            width: 40,
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
    this.fetchUserGroupList()
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
        const apiUrl = this.Url.userGroupBaseUrl
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
    fetchUserGroupList() {
      this.isLoading = true
      const apiUrl = this.Url.userGroupBaseUrl
      this.$axios
        .get(apiUrl, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
        })
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取用户组列表失败，内部错误！')
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
          this.$message.error('获取用户组列表失败！')
          this.isLoading = false
          console.log(err)
        })
    },
    deleteUserGroup(userGroupId, successCallback) {
      const apiUrl = this.Url.userGroupBaseUrl
      this.$axios
        .delete(apiUrl + userGroupId)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('删除用户组失败，内部错误！')
          } else {
            successCallback()
          }
        })
        .catch(err => {
          this.$message.error('删除用户组失败！')
          console.log(err)
        })
    },
    customCompFunc(params) {
      const index = params.index
      const userGroupId = this.tableConfig.tableData[index].id
      if (params.type === 'delete') {
        if (confirm('您确定要删除该用户组吗？')) {
          this.deleteUserGroup(userGroupId, () => {
            this.$message.success('删除用户组成功！')
            this.fetchUserGroupList()
          })
        }
      } else if (params.type === 'edit') {
        this.$router.push({ path: '/user-group/edit/' + userGroupId })
      }
    },
    onNewUserGroup() {
      this.$router.push({ path: '/user-group/add/' })
    },
    onCancelSearch() {
      this.inSearch = false
      this.fetchUserGroupList()
    },
    pageChange(pageNum) {
      this.pageNum = pageNum
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.fetchUserGroupList()
      }
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.fetchUserGroupList()
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
