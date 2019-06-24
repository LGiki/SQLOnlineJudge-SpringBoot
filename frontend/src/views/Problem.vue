<template>
  <div class="wrapper">
    <parallax class="section page-header header-filter" :style="headerStyle"></parallax>
    <div class="main main-raised">
      <div class="section profile-content">
        <div class="container">
          <div class="md-layout">
            <div class="md-layout-item md-size-50 mx-auto">
              <div class="profile">
                <div class="avatar">
                  <div class="icon icon-success">
                    <md-icon
                      alt="Circle Image"
                      class="md-size-6x img-raised rounded-circle img-fluid img-middle"
                    >assignment</md-icon>
                  </div>
                </div>
                <div class="name">
                  <h3 class="title">题目列表</h3>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="section section-with-padding">
        <div class="container">
          <div class="features text-center">
            <div class="md-layout">
              <template>
                <v-table
                  is-horizontal-resize
                  style="width:100%"
                  :columns="tableConfig.columns"
                  :table-data="tableConfig.tableData"
                  row-hover-color="#eee"
                  row-click-color="#edf7ff"
                  :row-click="rowClick"
                ></v-table>
              </template>
              <template>
                <div class="bd">
                  <v-pagination
                    @page-change="pageChange"
                    @page-size-change="pageSizeChange"
                    :is-loading="isLoading"
                    :showPagingCount="3"
                    :total="totalItems"
                    :layout="['total', 'sizer', 'prev', 'pager', 'next', 'jumper']"
                  ></v-pagination>
                </div>
              </template>
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
      default: require("@/assets/img/city-profile.jpg")
    }
  },
  data() {
    return {
      pageIndex: 1,
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
              return rowData.submit == 0 ? 0 : rowData.solve / rowData.submit;
            }
          }
        ]
      }
    };
  },
  methods: {
    rowClick(rowIndex, rowData, column) {
      this.$router.push({ path: "/problem/" + rowData.id });
    },
    getProblemList() {
      let apiUrl = this.Url.problemList;
      this.$axios
        .get(apiUrl, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
        })
        .then(res => {
          if (res.status !== 200) {
            alert("Network error");
          } else {
            let resData = res.data;
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
    },
    pageChange(pageNum) {
      this.pageNum = pageNum;
      this.getProblemList();
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize;
      this.getProblemList();
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
    this.getProblemList();
  }
};
</script>

<style lang="scss" scoped>
.section {
  padding: 0;
}

.section-with-padding {
  padding-bottom: 40px;
}

.img-middle {
  transform: translate3d(0, -50%, 0);
  background: rgba(255, 255, 255, 0.835);
}

.md-size-6x {
  width: 150px;
  min-width: 150px;
  height: 150px;
  font-size: 120px !important;
}

.title {
  margin-top: 20px;
  margin-bottom: 20px;
  min-height: 20px;
}

.bd {
  padding-top: 10px;
  width: 100%;
}

.bd /deep/ a {
  color: #333 !important;
}

.justify-content-center {
  justify-content: center !important;
}
</style>
