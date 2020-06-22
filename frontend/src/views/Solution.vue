<template>
  <div class="wrapper">
    <parallax class="section header-filter" :style="headerStyle"></parallax>
    <div class="main main-raised">
      <div class="name">
        <h2 class="title">提交状态</h2>
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
          <div class="features text-center">
            <div class="md-layout">
              <template>
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
              </template>
              <div class="bd">
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
        <modal v-if="codeModal" @close="codeModalHide">
          <template slot="header">
            <h4 class="modal-title">代码</h4>
            <md-button
              class="md-simple md-just-icon md-round modal-default-button"
              @click="codeModalHide"
            >
              <md-icon>clear</md-icon>
            </md-button>
          </template>
          <template slot="body">
            <p>
              <highlight-code lang="sql">{{ code }}</highlight-code>
            </p>
            <template v-if="runError">
              <h5>错误详情</h5>
              <p>
                <highlight-code>{{ runError }}</highlight-code>
              </p>
            </template>
          </template>
          <template slot="footer">
            <md-button class="md-danger md-simple" @click="codeModalHide"
              >关闭</md-button
            >
          </template>
        </modal>
      </div>
    </div>
  </div>
</template>

<script>
import "vue-easytable/libs/themes-base/index.css";
import { VTable, VPagination } from "vue-easytable";
import { Pagination } from "@/components";
import { Modal } from "@/components";
import VueHighlightJS from "vue-highlight.js";
import "vue-highlight.js/lib/allLanguages";
import "highlight.js/styles/atom-one-light.css";
export default {
  components: {
    VueHighlightJS,
    Modal,
    VTable,
    VPagination,
    Pagination
  },
  bodyClass: "profile-page",
  props: {
    header: {
      type: String,
      default: require("@/assets/img/bg7.jpg")
    }
  },
  data() {
    return {
      intervalId: -1,
      searchTypeList: [
        {
          label: "提交ID",
          value: "id"
        },
        {
          label: "用户ID",
          value: "uid"
        },
        {
          label: "题目ID",
          value: "pid"
        }
      ],
      searchType: {
        label: "提交ID",
        value: "id"
      },
      searchKeyword: "",
      inSearch: false,
      codeModal: false,
      pageNum: 1,
      pageSize: 20,
      totalItems: 0,
      isLoading: false,
      code: "",
      tableConfig: {
        tableData: [],
        columns: [
          {
            field: "id",
            title: "ID",
            width: 50,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "uid",
            title: "用户ID",
            width: 50,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "username",
            title: "用户名",
            width: 80,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "studentNo",
            title: "学号",
            width: 120,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "pid",
            title: "题目ID",
            width: 50,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "title",
            title: "题目标题",
            width: 300,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "submitTime",
            title: "提交时间",
            width: 180,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "result",
            title: "结果",
            width: 120,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              let fontColor = "black";
              switch (rowData.result) {
                case "Accepted":
                  fontColor = "blue";
                  break;
                case "Wrong Answer":
                  fontColor = "red";
                  break;
                case "Compile Error":
                  fontColor = "green";
                  break;
                case "Unknown":
                case "Judging":
                  fontColor = "grey";
                  break;
              }
              return `<font color="${fontColor}">${rowData.result}</font>`;
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
    codeModalHide() {
      this.codeModal = false;
    },
    codeModalShow() {
      this.codeModal = true;
    },
    rowClick(rowIndex, rowData, column) {
      switch (column.field) {
        case "result":
        case "submitTime":
          if (
            this.tableConfig.tableData[rowIndex].uid != localStorage.USER_ID
          ) {
            this.$notify({
              group: "notify",
              text: "只允许查看自己提交的代码",
              type: "error"
            });
          } else {
            let solutionId = this.tableConfig.tableData[rowIndex].id;
            this.fetchSolutionCode(solutionId);
          }
          break;
        case "uid":
        case "username":
          this.$router.push({
            path: "/profile/" + this.tableConfig.tableData[rowIndex].uid
          });
          break;
        case "pid":
        case "title":
          this.$router.push({
            path: "/problem/" + this.tableConfig.tableData[rowIndex].pid
          });
          break;
      }
    },
    fetchSolutionCode(solutionId) {
      let apiUrl = this.Url.solutionCode;
      this.$axios
        .get(apiUrl + solutionId)
        .then(res => {
          if (res.status !== 200) {
            this.$notify({
              group: "notify",
              text: "获取用户解答代码失败：远程服务器错误",
              type: "error"
            });
          } else {
            let resData = res.data;
            if (resData.code === 0) {
              this.code = resData.data.sourceCode;
              this.runError = resData.data.runError;
              this.codeModal = true;
            } else {
              this.$notify({
                group: "notify",
                text: resData.message,
                type: "error"
              });
            }
          }
        })
        .catch(err => {
          console.log(err);
          this.$notify({
            group: "notify",
            text: "获取用户解答代码失败：无法发送请求",
            type: "error"
          });
        });
    },
    pageChange(pageNum) {
      this.pageNum = pageNum;
      if (this.inSearch) {
        this.onSearch();
      } else {
        this.getSolutionList();
      }
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize;
      if (this.inSearch) {
        this.onSearch();
      } else {
        this.getSolutionList();
      }
    },
    getSolutionList() {
      this.isLoading = true;
      let apiUrl = this.Url.solutionBaseUrl;
      this.$axios
        .get(apiUrl, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
        })
        .then(res => {
          if (res.status !== 200) {
            this.$notify({
              group: "notify",
              text: "获取用户提交列表失败：远程服务器错误",
              type: "error"
            });
          } else {
            let resData = res.data;
            if (resData.code === 0) {
              this.tableConfig.tableData = resData.data.records;
              this.totalItems = resData.data.total;
            } else {
              this.$notify({
                group: "notify",
                text: resData.message,
                type: "error"
              });
            }
          }
          this.isLoading = false;
        })
        .catch(err => {
          this.isLoading = false;
          console.log(err);
          this.$notify({
            group: "notify",
            text: "获取用户提交列表失败：无法发送请求",
            type: "error"
          });
        });
    },
    onSearch() {
      const keyword = this.searchKeyword.trim();
      this.pageNum = 1;
      if (keyword.length === 0) {
        this.$notify({
          group: "notify",
          text: "请输入搜索关键字",
          type: "error"
        });
      } else {
        this.isLoading = true;
        const apiUrl = this.Url.solutionBaseUrl;
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
              this.$notify({
                group: "notify",
                text: "搜索用户提交失败：远程服务器错误",
                type: "error"
              });
            } else {
              const resData = res.data;
              if (resData.code === 0) {
                this.tableConfig.tableData = resData.data.records;
                this.totalItems = resData.data.total;
                this.inSearch = true;
              } else {
                this.$notify({
                  group: "notify",
                  text: resData.message,
                  type: "error"
                });
              }
            }
            this.isLoading = false;
          })
          .catch(err => {
            this.isLoading = false;
            console.log(err);
            this.$notify({
              group: "notify",
              text: "搜索用户提交失败：无法发送请求",
              type: "error"
            });
          });
      }
    },
    cancelSearch() {
      this.getSolutionList();
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
    this.getSolutionList();
    this.intervalId = setInterval(this.getSolutionList, 5000);
  },
  destroyed: function() {
    if (this.intervalId != -1) {
      clearInterval(this.intervalId);
    }
  }
};
</script>
<style lang="scss" scoped>
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
</style>
