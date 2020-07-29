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
      <el-button type="danger" @click="openAddUserGroupDialog">
        <i class="el-icon-plus" />&nbsp;新建用户组
      </el-button>
    </div>
    <template>
      <v-table
        :width="400"
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
import { getUserGroupList, deleteUserGroup, createUserGroup } from '@/api/user-group'

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
            width: 100,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'name',
            title: '用户组名称',
            width: 100,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return `<a href="#/user-group/edit/${rowData.id}" title="${rowData.name}">${rowData.name}</a>`
            }
          },
          {
            field: 'count',
            title: '用户数量',
            width: 100,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'action',
            title: '操作',
            width: 100,
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
    this.getUserGroupList()
  },
  methods: {
    rowClick(rowIndex, rowData, column) {
    },
    addUserGroup(userGroupName) {
      this.handleResponse(createUserGroup(userGroupName), '新建用户组',
        (res) => {
          this.$message.success('新建用户组成功')
          this.$router.push('/user-group/edit/' + res.data.id)
        })
    },
    openAddUserGroupDialog() {
      this.$prompt('请输入新用户组名称', '新建用户组', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        if (value) {
          this.addUserGroup(value.trim())
        } else {
          this.$message.error('您输入的新用户组名称为空，请重新输入')
        }
      })
    },
    onSearch() {
      const keyword = this.searchKeyword.trim()
      this.pageNum = 1
      if (keyword.length === 0) {
        this.$message.error('请输入关键字！')
      } else {
        this.isLoading = true
        this.handleResponse(getUserGroupList(this.pageNum, this.pageSize, this.searchType, keyword), '搜索用户组',
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
    getUserGroupList() {
      this.isLoading = true
      this.handleResponse(getUserGroupList(this.pageNum, this.pageSize), '获取用户组列表',
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
      const userGroupId = this.tableConfig.tableData[index].id
      if (params.type === 'delete') {
        if (confirm('您确定要删除该用户组吗？')) {
          this.handleResponse(deleteUserGroup(userGroupId), '删除用户组',
            (res) => {
              this.$message.success('删除用户组成功')
              this.getUserGroupList()
            })
        }
      } else if (params.type === 'edit') {
        this.$router.push({ path: '/user-group/edit/' + userGroupId })
      }
    },
    onCancelSearch() {
      this.inSearch = false
      this.getUserGroupList()
    },
    pageChange(pageNum) {
      this.pageNum = pageNum
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.getUserGroupList()
      }
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize
      if (this.inSearch) {
        this.onSearch()
      } else {
        this.getUserGroupList()
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
