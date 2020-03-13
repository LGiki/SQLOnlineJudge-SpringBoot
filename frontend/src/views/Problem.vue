<template>
  <div class="wrapper">
    <parallax class="section header-filter" :style="headerStyle"></parallax>
    <div class="main main-raised">
      <div class="name">
        <h2 class="title">题目列表</h2>
      </div>
      <div class="section no-padding">
        <div class="container">
          <div class="search">
            <md-field class="md-form-group" slot="inputs">
              <md-icon>search</md-icon>
              <label>请输入搜索关键字</label>
              <md-input v-model="searchKeyword"></md-input>
              <li class="md-list-item">
                <a
                  href="javascript:void(0)"
                  class="md-list-item-router md-list-item-container md-button-clean dropdown"
                >
                  <div class="md-list-item-content">
                    <drop-down direction="down">
                      <md-button
                        slot="title"
                        class="md-button md-button-link md-simple dropdown-toggle"
                        data-toggle="dropdown"
                      >
                        <i class="material-icons">apps</i>
                        <p>{{ searchType.label }}</p>
                      </md-button>
                      <ul class="dropdown-menu dropdown-with-icons">
                        <li v-for="item in searchTypeList">
                          <a @click="selectSearchType(item)">
                            <i class="material-icons">layers</i>
                            <p>{{ item.label }}</p>
                          </a>
                        </li>
                      </ul>
                    </drop-down>
                  </div>
                </a>
              </li>
              <md-button class="md-info" @click="onSearch">搜索</md-button
              >&nbsp;
              <md-button class="md-info" v-if="inSearch" @click="cancelSearch"
                >取消搜索</md-button
              >
            </md-field>
          </div>
          <div class="features">
            <div class="md-layout">
              <v-table
                :is-loading="isLoading"
                is-horizontal-resize
                style="width:100%"
                :columns="tableConfig.columns"
                :table-data="tableConfig.tableData"
                row-hover-color="#eee"
                row-click-color="#edf7ff"
                :row-click="rowClick"
              ></v-table>
              <div class="bd text-center">
                <v-pagination
                  @page-change="pageChange"
                  @page-size-change="pageSizeChange"
                  :showPagingCount="3"
                  :pageSize="pageSize"
                  :total="totalItems"
                  :layout="[
                    'total',
                    'sizer',
                    'prev',
                    'pager',
                    'next',
                    'jumper'
                  ]"
                ></v-pagination>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "vue-easytable/libs/themes-base/index.css";
import { VTable, VPagination } from "vue-easytable";
import { Pagination } from "@/components";
export default {
  components: {
    VTable,
    VPagination,
    Pagination
  },
  bodyClass: "landing-page",
  props: {
    header: {
      type: String,
      default: require("@/assets/img/bg7.jpg")
    }
  },
  data() {
    return {
      searchTypeList: [
        {
          label: "题目ID",
          value: "id"
        },
        {
          label: "题目标题",
          value: "title"
        }
      ],
      searchType: {
        label: "题目ID",
        value: "id"
      },
      userDoProblem: {},
      searchKeyword: "",
      inSearch: false,
      pageNum: 1,
      pageSize: 20,
      totalItems: 0,
      isLoading: false,
      tableConfig: {
        tableData: [],
        columns: [
          {
            field: "progress",
            title: "做题进度",
            width: 40,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              switch (rowData.progress) {
                case 1:
                  // 已经通过
                  return '<i class="material-icons status-icon">check_box</i>';
                case 2:
                  // 尝试过
                  return '<i class="material-icons status-icon">indeterminate_check_box</i>';
                case 0:
                  // 未尝试
                  return '<i class="material-icons status-icon">check_box_outline_blank</i>';
              }
            }
          },
          {
            field: "id",
            title: "题目ID",
            width: 50,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "title",
            title: "标题",
            width: 400,
            titleAlign: "center",
            // columnAlign: "center",
            isResize: true
          },
          {
            field: "solved",
            title: "通过数",
            width: 50,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "submit",
            title: "提交数",
            width: 50,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "accept_rate",
            title: "通过率",
            width: 50,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return rowData.submit == 0
                ? 0
                : (rowData.solved / rowData.submit).toFixed(2);
            }
          }
        ]
      }
    };
  },
  methods: {
    selectSearchType(searchTypeItem) {
      this.searchType = searchTypeItem;
    },
    rowClick(rowIndex, rowData, column) {
      this.$router.push({ path: "/problem/" + rowData.id });
    },
    getProblemList() {
      this.isLoading = true;
      let apiUrl = this.Url.problemBaseUrl;
      this.$axios
        .get(apiUrl, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
        })
        .then(res => {
          if (res.status !== 200) {
            alert("获取题目列表失败，内部错误！");
          } else {
            let resData = res.data;
            if (resData.code === 0) {
              this.tableConfig.tableData = resData.data.records;
              this.totalItems = resData.data.total;
              this.calcProblemProgress();
            } else {
              alert(resData.message);
            }
          }
          this.isLoading = false;
        })
        .catch(err => {
          this.isLoading = false;
          alert("获取题目列表失败！");
          console.log(err);
        });
    },
    calcProblemProgress() {
      for (let i = 0; i < this.tableConfig.tableData.length; i++) {
        let problemItem = this.tableConfig.tableData[i];
        let problemId = problemItem.id;
        if (
          this.userDoProblem.accept &&
          this.userDoProblem.accept.indexOf(problemId) != -1
        ) {
          problemItem.progress = 1;
        } else if (
          this.userDoProblem.try &&
          this.userDoProblem.try.indexOf(problemId) != -1
        ) {
          problemItem.progress = 2;
        } else {
          problemItem.progress = 0;
        }
      }
    },
    fetchUserDoProblemList(callback) {
      let apiUrl = this.Url.userDoProblemList;
      this.$axios
        .get(apiUrl)
        .then(res => {
          if (res.status !== 200) {
            alert("获取用户已做题目列表失败，内部错误！");
          } else {
            let resData = res.data;
            if (resData.code === 0) {
              this.userDoProblem = resData.data;
            } else {
              alert(resData.message);
            }
          }
          callback();
        })
        .catch(err => {
          alert("获取用户已做题目列表失败！");
          console.log(err);
          callback();
        });
    },
    pageChange(pageNum) {
      this.pageNum = pageNum;
      if (this.inSearch) {
        this.onSearch();
      } else {
        this.getProblemList();
      }
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize;
      if (this.inSearch) {
        this.onSearch();
      } else {
        this.getProblemList();
      }
    },
    onSearch() {
      const keyword = this.searchKeyword.trim();
      this.pageNum = 1;
      if (keyword.length === 0) {
        alert("请输入关键字！");
      } else {
        this.isLoading = true;
        const apiUrl = this.Url.problemBaseUrl;
        this.$axios
          .get(apiUrl, {
            params: {
              [this.searchType.value]: keyword,
              pageNum: this.pageNum,
              pageSize: this.pageSize
            }
          })
          .then(res => {
            if (res.status !== 200) {
              alert("题目搜索失败，内部错误！");
            } else {
              const resData = res.data;
              if (resData.code === 0) {
                this.tableConfig.tableData = resData.data.records;
                this.totalItems = resData.data.total;
                this.inSearch = true;
                if (localStorage.JWT_TOKEN) {
                  this.calcProblemProgress();
                }
              } else {
                alert(resData.message);
              }
            }
            this.isLoading = false;
          })
          .catch(err => {
            this.isLoading = false;
            alert("题目搜索失败！");
            console.log(err);
          });
      }
    },
    cancelSearch() {
      this.getProblemList();
      this.inSearch = false;
    }
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`
      };
    }
  },
  mounted: function() {
    if (localStorage.JWT_TOKEN) {
      this.fetchUserDoProblemList(this.getProblemList);
    } else {
      this.getProblemList();
    }
  }
};
</script>

<style lang="scss">
.bd {
  padding-top: 25px;
  width: 100%;
  padding-bottom: 25px;
}

.bd /deep/ a {
  color: #333 !important;
}

.name {
  padding-top: 10px;
  text-align: center;
  width: 100%;
}

.justify-content-center {
  justify-content: center !important;
}

.no-padding {
  padding: 0 !important;
}

.status-icon {
  width: 24px;
  height: 24px;
  margin: 6px !important;
}
</style>
