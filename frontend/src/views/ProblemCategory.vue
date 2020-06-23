<template>
  <div class="wrapper">
    <parallax class="section header-filter" :style="headerStyle"></parallax>
    <div class="main main-raised">
      <div class="name">
        <h2><strong>题目集列表</strong></h2>
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
                  :width="1000"
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
  bodyClass: "profile-page",
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
          label: "题目集ID",
          value: "id"
        },
        {
          label: "题目集名称",
          value: "name"
        }
      ],
      searchType: {
        label: "题目集ID",
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
            title: "题目集ID",
            width: 50,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "name",
            title: "题目集名称",
            width: 400,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "startTime",
            title: "开始时间",
            width: 200,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "endTime",
            title: "结束时间",
            width: 200,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "status",
            title: "状态",
            width: 100,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
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
      this.$router.push({ path: "/problem_category/" + rowData.id });
    },
    pageChange(pageNum) {
      this.pageNum = pageNum;
      if (this.inSearch) {
        this.onSearch();
      } else {
        this.getProblemCategoryList();
      }
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize;
      if (this.inSearch) {
        this.onSearch();
      } else {
        this.getProblemCategoryList();
      }
    },
    getProblemCategoryList() {
      this.isLoading = true;
      const apiUrl = this.Url.problemCategoryListUrl;
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
              text: "获取题目集列表失败：远程服务器错误",
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
            text: "获取题目集列表失败：发送请求失败",
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
          text: "请输入关键字之后再进行搜索",
          type: "error"
        });
      } else {
        this.isLoading = true;
        const apiUrl = this.Url.problemCategoryListUrl;
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
                text: "搜索题目集失败：远程服务器错误",
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
              text: "搜索题目集失败：发送请求失败",
              type: "error"
            });
          });
      }
    },
    cancelSearch() {
      this.getProblemCategoryList();
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
    this.getProblemCategoryList();
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
