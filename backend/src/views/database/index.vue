<template>
  <div class="app-container">
    <div class="operation-button">
      <el-input style="width: 200px;" class="filter-item" />
      <el-button type="primary" @click="onSubmit">搜索</el-button>
      <el-button type="primary" @click="onSubmit">新建数据库</el-button>
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
import { getProblemList } from '@/api/problem'
import 'vue-easytable/libs/themes-base/index.css'
import { VTable, VPagination } from 'vue-easytable'

export default {
  components: {
    VTable,
    VPagination
  },
  data() {
    return {
      pageNum: 1,
      pageSize: 10,
      totalItems: 0,
      isLoading: true,
      tableConfig: {
        tableData: [],
        columns: [
          {
            field: 'id',
            title: '数据库ID',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'name',
            title: '数据库名称',
            width: 280,
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
            componentName: 'table-operation'
          }
        ]
      }
    }
  },
  created() {},
  mounted: function() {
    this.fetchDatabaseList()
  },
  methods: {
    customCompFunc(params) {
      if (params.type === 'delete') {
        // do delete operation
        alert('Delete')
      } else if (params.type === 'edit') {
        // do edit operation
        alert('Edit')
      }
    },
    rowClick(rowIndex, rowData, column) {
      this.$router.push({ path: '/database/edit/' + rowData.id })
    },
    pageChange(pageNum) {
      this.pageNum = pageNum
      this.fetchDatabaseList()
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize
      this.fetchDatabaseList()
    },
    fetchDatabaseList() {
      const apiUrl = this.Url.databaseList
      this.$axios
        .get(apiUrl, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
        })
        .then(res => {
          if (res.status !== 200) {
            alert('Network error')
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