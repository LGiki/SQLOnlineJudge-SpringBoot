<template>
  <div class="wrapper">
    <parallax class="section header-filter" :style="headerStyle"></parallax>
    <div class="main main-raised">
      <div class="name">
        <h2 class="title">排行榜</h2>
      </div>
      <div class="section no-padding">
        <div class="container">
          <div class="features text-center">
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
                :total="totalItems"
                :pageSize="pageSize"
                :layout="['total', 'sizer', 'prev', 'pager', 'next', 'jumper']"
              ></v-pagination>
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
      pageNum: 1,
      pageSize: 20,
      totalItems: 0,
      isLoading: true,
      tableConfig: {
        tableData: [],
        columns: [
          {
            field: "rank",
            title: "排名",
            width: 30,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true,
            formatter: this.rankFormatter
          },
          {
            field: "username",
            title: "用户名",
            width: 100,
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
            field: "solved",
            title: "通过数",
            width: 100,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "submit",
            title: "提交数",
            width: 100,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "accept_rate",
            title: "通过率",
            width: 100,
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
    rankFormatter(rowData, rowIndex, pagingIndex, field) {
      let currentRank = (this.pageNum - 1) * this.pageSize + rowIndex + 1;
      console.log(this.pageSize);
      return currentRank <= 3
        ? '<font color="red"><strong>' + currentRank + "</strong></font>"
        : currentRank;
    },
    rowClick(rowIndex, rowData, column) {
      //TODO: jump to user profile page
    },
    pageChange(pageNum) {
      this.pageNum = pageNum;
      this.getRanklist();
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize;
      this.getRanklist();
    },
    getRanklist() {
      let apiUrl = this.Url.rankListBaseUrl;
      this.$axios
        .get(apiUrl, {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
        })
        .then(res => {
          if (res.status !== 200) {
            alert("获取排名失败，内部错误！");
          } else {
            let resData = res.data;
            if (resData.code === 0) {
              this.tableConfig.tableData = resData.data.records;
              this.totalItems = resData.data.total;
              this.isLoading = false;
            } else {
              alert(resData.message);
            }
          }
        })
        .catch(err => {
          console.log(err);
        });
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
    this.getRanklist();
  }
};
</script>

<style lang="scss" scoped>
// .section {
//   padding: 0;
// }

// .section-with-padding {
//   padding-bottom: 40px;
// }

// .img-middle {
//   transform: translate3d(0, -50%, 0);
//   background: rgba(255, 255, 255, 0.835);
// }

// .md-size-6x {
//   width: 150px;
//   min-width: 150px;
//   height: 150px;
//   font-size: 120px !important;
// }

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
