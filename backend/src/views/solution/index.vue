<template>
  <div class="app-container">
    <div class="operation-button">
      <el-input style="width: 200px;" class="filter-item" />
      <el-button type="primary" @click="onSubmit">搜索</el-button>
      <el-button type="primary" @click="onSubmit">新建题目</el-button>
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
import Vue from 'vue'
import { getProblemList } from '@/api/problem'
import 'vue-easytable/libs/themes-base/index.css'
import { VTable, VPagination } from 'vue-easytable'
import VueHighlightJS from 'vue-highlight.js'
import 'vue-highlight.js/lib/allLanguages'
import 'highlight.js/styles/atom-one-light.css'

export default {
  components: {
    VueHighlightJS,
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
            title: '提交ID',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'uid',
            title: '用户ID',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'pid',
            title: '题目ID',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'sourceCode',
            title: '代码',
            width: 280,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'result',
            title: '运行结果',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          }
        ]
      }
    }
  },
  created() {},
  mounted: function() {
    this.fetchSolutionList()
  },
  methods: {
    pageChange(pageNum) {
      this.pageNum = pageNum
      this.fetchSolutionList()
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize
      this.fetchSolutionList()
    },
    fetchSolutionList() {
      const apiUrl = this.Url.solutionList
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
