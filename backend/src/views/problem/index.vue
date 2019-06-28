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
        <svg-icon icon-class="search"/>&nbsp;搜索
      </el-button>
      <el-button v-if="inSearch" type="primary" @click="onCancelSearch">
        <i class="el-icon-close"/>&nbsp;取消搜索
      </el-button>
      <el-button type="danger" @click="onNewProblem">
        <i class="el-icon-plus"/>&nbsp;新建题目
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
import "vue-easytable/libs/themes-base/index.css";
import { VTable, VPagination } from "vue-easytable";

export default {
  components: {
    VTable,
    VPagination
  },
  data() {
    return {
      inSearch: false,
      searchKeyword: "",
      pageNum: 1,
      pageSize: 10,
      totalItems: 0,
      isLoading: true,
      tableConfig: {
        tableData: [],
        columns: [
          {
            field: "id",
            title: "题目ID",
            width: 80,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "title",
            title: "标题",
            width: 280,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "solve",
            title: "通过数",
            width: 80,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "submit",
            title: "提交数",
            width: 80,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "accept_rate",
            title: "通过率",
            width: 80,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return rowData.submit === 0
                ? 0
                : (rowData.solve / rowData.submit).toFixed(2);
            }
          },
          {
            field: "action",
            title: "操作",
            width: 80,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true,
            componentName: "table-operation"
          }
        ]
      }
    };
  },
  created() {},
  mounted: function() {
    this.fetchProblemList();
  },
  methods: {
    onSearch() {
      const keyword = this.searchKeyword.trim();
      if (keyword.length === 0) {
        this.$message.error("请输入关键字！");
      } else {
        const apiUrl = this.Url.problemSearch;
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
              this.$message.error("搜索失败，网络错误！");
            } else {
              const resData = res.data;
              if (resData.code === 200) {
                this.tableConfig.tableData = resData.data.list;
                this.totalItems = resData.data.total;
                this.isLoading = false;
                this.inSearch = true;
              }
            }
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    onCancelSearch() {
      this.inSearch = false;
      this.fetchProblemList();
    },
    onNewProblem() {
      this.$router.push({ path: "/problem/add/" });
    },
    customCompFunc(params) {
      if (params.type === "delete") {
        alert("Delete");
      } else if (params.type === "edit") {
        alert("Edit");
      }
    },
    rowClick(rowIndex, rowData, column) {
      this.$router.push({ path: "/problem/edit/" + rowData.id });
    },
    pageChange(pageNum) {
      this.pageNum = pageNum;
      this.fetchProblemList();
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize;
      this.fetchProblemList();
    },
    fetchProblemList() {
      const apiUrl = this.Url.problemBaseUrl;
      this.$axios
        .get(apiUrl, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
        })
        .then(res => {
          if (res.status !== 200) {
            this.$message.error("获取题目列表失败，网络错误！");
          } else {
            const resData = res.data;
            if (resData.code === 200) {
              this.tableConfig.tableData = resData.data.list;
              this.totalItems = resData.data.total;
              this.isLoading = false;
            }
          }
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
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
