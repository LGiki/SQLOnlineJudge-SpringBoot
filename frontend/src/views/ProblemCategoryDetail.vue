<template>
  <div class="wrapper">
    <parallax class="section header-filter" :style="headerStyle"></parallax>
    <div class="main main-raised">
      <div class="name">
        <h2>
          <strong>{{ problemCategoryInfo.name }}</strong>
        </h2>
      </div>
      <div class="section no-padding">
        <div class="container">
          <div class="countdown">
            <h4>{{ countDownStr }}</h4>
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
      countDownStr: "",
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
            field: "progress",
            title: "做题进度",
            width: 40,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              if (!localStorage.JWT_TOKEN) {
                return '<i class="material-icons status-icon">help_center</i>';
              }
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
                default:
                  // 未知
                  return '<i class="material-icons status-icon">help_center</i>';
              }
            }
          },
          {
            field: "problemId",
            title: "题目ID",
            width: 40,
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
              let difficultyStars = "";
              for (let i = 0; i < rowData.problemDifficulty; i++) {
                difficultyStars += "★";
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
      },
      userCategoryProgress: null
    };
  },
  methods: {
    setCountDownStrRefreshInterval() {
      let that = this;
      this.countDownStrRefreshIntervalId = setInterval(function() {
        that.refreshCountDown(that.problemCategoryInfo.endTime);
      }, 1000);
    },
    clearCountDownStrRefreshInterval() {
      if (this.countDownStrRefreshIntervalId != -1) {
        clearInterval(this.countDownStrRefreshIntervalId);
      }
    },
    refreshCountDown(endDatetimeStr) {
      let currentTimestamp = new Date().getTime();
      let endTimestamp = new Date(endDatetimeStr).getTime();
      if (currentTimestamp <= endTimestamp) {
        let totalLeftSeconds = parseInt(
          (endTimestamp - currentTimestamp) / 1000
        );
        let leftDays = parseInt(totalLeftSeconds / (24 * 60 * 60));
        let leftHours = parseInt((totalLeftSeconds / (60 * 60)) % 24);
        let leftMinutes = parseInt((totalLeftSeconds / 60) % 60);
        let leftSeconds = parseInt(totalLeftSeconds % 60);
        let countDownStr = "";
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
        this.countDownStr = "离结束还有：" + countDownStr;
      } else {
        this.clearCountDownStrRefreshInterval();
        //TODO: 题目集已结束，清除页面
      }
    },
    rowClick(rowIndex, rowData, column) {
      this.$router.push({
        path: "/problem/" + this.problemCategoryId + "/" + rowData.problemId
      });
    },
    pageChange(pageNum) {
      this.pageNum = pageNum;
      this.getProblemCategoryDetail(this.problemCategoryId);
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize;
      this.getProblemCategoryDetail(this.problemCategoryId);
    },
    calcProblemProgress() {
      for (let i = 0; i < this.tableConfig.tableData.length; i++) {
        let problemItem = this.tableConfig.tableData[i];
        let problemId = problemItem.problemId;
        if (this.userCategoryProgress.accept.indexOf(problemId) != -1) {
          problemItem.progress = 1;
        } else if (
          this.userCategoryProgress.try &&
          this.userCategoryProgress.try.indexOf(problemId) != -1
        ) {
          problemItem.progress = 2;
        } else {
          problemItem.progress = 0;
        }
      }
    },
    async getUserCategoryProgress(problemCategoryId) {
      let apiUrl = this.Url.userCategoryProgress;
      await this.$axios
        .get(apiUrl + problemCategoryId)
        .then(res => {
          if (res.status !== 200) {
            this.$notify({
              group: "notify",
              text: "获取用户已做题目列表失败：远程服务器错误",
              type: "error"
            });
          } else {
            if (res.data.code === 0) {
              this.userCategoryProgress = res.data.data;
            } else {
              this.$notify({
                group: "notify",
                text: res.data.message,
                type: "error"
              });
            }
          }
        })
        .catch(err => {
          this.$notify({
            group: "notify",
            text: "获取用户已做题目列表失败：发送请求失败",
            type: "error"
          });
          console.log(err);
        });
    },
    async getProblemCategoryDetail(problemCategoryId) {
      this.isLoading = true;
      let apiUrl = this.Url.problemCollectionBaseUrl;
      await this.$axios
        .get(apiUrl + problemCategoryId)
        .then(res => {
          if (res.status !== 200) {
            this.$notify({
              group: "notify",
              text: "获取题目集的题目列表失败：远程服务器错误",
              type: "error"
            });
          } else {
            let resData = res.data;
            if (resData.code === 0) {
              resData.data.records.forEach(element => {
                element.progress = 3;
              });
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
            text: "获取题目集的题目列表失败：发送请求失败",
            type: "error"
          });
        });
    },
    async getProblemCategoryInfo(problemCategoryId) {
      let apiUrl = this.Url.problemCategoryBaseUrl;
      await this.$axios
        .get(apiUrl + problemCategoryId)
        .then(res => {
          if (res.status !== 200) {
            this.$notify({
              group: "notify",
              text: "获取题目集详情失败：远程服务器错误",
              type: "error"
            });
          } else {
            this.problemCategoryInfo = res.data.data;
            this.refreshCountDown(this.problemCategoryInfo.endTime);
            this.setCountDownStrRefreshInterval();
          }
        })
        .catch(err => {
          console.log(err);
          this.$notify({
            group: "notify",
            text: "获取题目集详情失败：发送请求失败",
            type: "error"
          });
        });
    }
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`
      };
    },
    problemCategoryId() {
      return this.$route.params.id;
    }
  },
  created: async function() {
    this.getProblemCategoryInfo(this.problemCategoryId);
    await this.getProblemCategoryDetail(this.problemCategoryId);
    if (localStorage.JWT_TOKEN) {
      await this.getUserCategoryProgress(this.problemCategoryId);
      this.calcProblemProgress();
    }
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
