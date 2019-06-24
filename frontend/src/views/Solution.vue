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
                  <div class="icon icon-info">
                    <md-icon
                      alt="Circle Image"
                      class="md-size-6x img-raised rounded-circle img-fluid img-middle"
                    >info</md-icon>
                  </div>
                </div>
                <div class="name">
                  <h3 class="title">提交状态</h3>
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
            </div>
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
        <p>{{ code }}</p>
      </template>

      <template slot="footer">
        <md-button class="md-danger md-simple" @click="codeModalHide">Close</md-button>
      </template>
    </modal>
  </div>
</template>


<script>
import "vue-easytable/libs/themes-base/index.css";
import { VTable, VPagination } from "vue-easytable";
import { Pagination } from "@/components";
import { Modal } from "@/components";
export default {
  components: {
    Modal,
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
      codeModal: false,
      pageNum: 1,
      pageSize: 10,
      totalItems: 0,
      isLoading: true,
      code: '',
      tableConfig: {
        tableData: [],
        columns: [
          {
            field: "id",
            title: "ID",
            width: 100,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "uid",
            title: "用户名",
            width: 100,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "pid",
            title: "题目ID",
            width: 100,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "submitTime",
            title: "提交时间",
            width: 100,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "result",
            title: "结果",
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
    codeModalHide() {
      this.codeModal = false;
    },
    codeModalShow() {
      this.codeModal = true;
    },
    rowClick(rowIndex, rowData, column) {
      let solution = this.tableConfig.tableData[rowIndex];
      this.code = solution.sourceCode;
      this.codeModal = true;
    },
    pageChange(pageNum) {
      this.pageNum = pageNum;
      this.getSolutionList();
    },
    pageSizeChange(newPageSize) {
      this.pageSize = newPageSize;
      this.getSolutionList();
    },
    getSolutionList() {
      let apiUrl = this.Url.solutionList;
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