<template>
  <div class="wrapper">
    <parallax class="section header-filter" :style="headerStyle"></parallax>
    <div class="main main-raised">
      <div class="name">
        <h2><strong>{{ problemCategoryInfo.name }}</strong></h2>
      </div>
      <div class="section no-padding">
        <div class="container">
          <div class="countdown">
            <h4>离结束还有：{{ countDownStr }}</h4>
          </div>
          <div class="features text-center">
            <div class="md-layout">
              <template>
                <v-table
                  :width="1000"
                  column-width-drag
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
      problemCategoryInfo: {
        id: 0,
        name: "",
        startTime: null,
        endTime: null
      },
      countDownStr: '123',
      countDownStrRefreshIntervalId: -1,
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
            field: "problemId",
            title: "题目ID",
            width: 50,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "databaseName",
            title: "数据库名称",
            width: 100,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "problemTitle",
            title: "题目名称",
            width: 600,
            titleAlign: "center",
            columnAlign: "left",
            isResize: true
          },
          {
            field: "problemDifficulty",
            title: "题目难度",
            width: 100,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              let difficultyStars = '';
              for (let i = 0; i < rowData.problemDifficulty; i++) {
                difficultyStars += '★';
              }
              return difficultyStars;
            }
          },
          {
            field: "problemSolved",
            title: "通过数",
            width: 50,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "problemSubmit",
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
              return rowData.problemSubmit == 0
                ? 0
                : (rowData.problemSolved / rowData.problemSubmit).toFixed(2);
            }
          }
        ]
      }
    };
  },
  methods: {
    setCountDownStrRefreshInterval() {
      let that = this;
      setInterval(function() {
        that.countDownStr = that.getCountDown(that.problemCategoryInfo.endTime);
      }, 1000);
    },
    clearCountDownStrRefreshInterval() {
      if (this.countDownStrRefreshIntervalId != -1) {
        clearInterval(this.countDownStrRefreshIntervalId);
      } 
    },
    getCountDown(endDatetimeStr) {
      let currentDatetime = new Date();
      let endDatetime = new Date(endDatetimeStr);
      let totalLeftSeconds = parseInt((endDatetime.getTime() - currentDatetime.getTime()) / 1000);
      let leftDays = parseInt(totalLeftSeconds / (24 * 60 * 60));
      let leftHours = parseInt(totalLeftSeconds / (60 * 60) % 24);
      let leftMinutes = parseInt(totalLeftSeconds / 60 % 60);
      let leftSeconds = parseInt(totalLeftSeconds % 60);
      let countDownStr = '';
      if (leftDays > 0) {
        countDownStr += `${leftDays} 天`;
      }
      if (leftHours > 0) {
        countDownStr += ` ${leftHours} 小时`;
      }
      if (leftMinutes > 0) {
        countDownStr += ` ${leftMinutes} 分钟`;
      }
      if (leftSeconds > 0) {
        countDownStr += ` ${leftSeconds} 秒`;
      }
      return countDownStr;
    },
    rowClick(rowIndex, rowData, column) {
      let problemCategoryId = this.$route.params.id;
      this.$router.push({ path: "/problem/" + problemCategoryId + '/' + rowData.problemId });
    },
    pageChange(pageNum) {
      this.pageNum = pageNum;
      this.getProblemCategoryDetail();
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize;
      this.getProblemCategoryDetail();
    },
    getProblemCategoryDetail() {
      this.isLoading = true;
      let problemCategoryId = this.$route.params.id;
      let apiUrl = this.Url.problemCollectionBaseUrl;
      this.$axios
        .get(apiUrl + problemCategoryId)
        .then(res => {
          if (res.status !== 200) {
            alert("获取题目集详情失败，内部错误！");
          } else {
            let resData = res.data;
            if (resData.code === 0) {
              this.tableConfig.tableData = resData.data.records;
              this.totalItems = resData.data.total;
            } else {
              alert(resData.message);
            }
          }
          this.isLoading = false;
        })
        .catch(err => {
          this.isLoading = false;
          console.log(err);
        });
    },
    getProblemCategoryInfo() {
      let problemCategoryId = this.$route.params.id;
      let apiUrl = this.Url.problemCategoryBaseUrl;
      this.$axios
        .get(apiUrl + problemCategoryId)
        .then(res => {
          if (res.status !== 200) {
            alert("获取题目集详情失败，内部错误！");
          } else {
            this.problemCategoryInfo = res.data.data;
            this.countDownStr = this.getCountDown(this.problemCategoryInfo.endTime);
            this.setCountDownStrRefreshInterval();
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
    this.getProblemCategoryDetail();
    this.getProblemCategoryInfo();
  },
  destroyed: function() {
    this.clearCountDownStrRefreshInterval();
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

.countdown {
  text-align: right;
}
</style>
