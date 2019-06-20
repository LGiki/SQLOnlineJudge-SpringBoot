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
                  :columns="columns"
                  :table-data="tableData"
                  row-hover-color="#eee"
                  row-click-color="#edf7ff"
                  :row-click="rowClick"
                ></v-table>
              </template>
              <div class="bd">
                <template>
                  <pagination
                    class="justify-content-center"
                    type="info"
                    v-model="infoPagination"
                    :page-count="3"
                  ></pagination>
                </template>
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
        <p>SELECT * FROM `TEST`</p>
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
      tableData: [
        {
          solutionId: "1",
          username: "Admin",
          problemId: "52",
          submitTime: "2019-06-20 08:00:00",
          result: "Accept"
        },
        {
          solutionId: "1",
          username: "Admin",
          problemId: "52",
          submitTime: "2019-06-20 08:00:00",
          result: "Accept"
        },
        {
          solutionId: "1",
          username: "Admin",
          problemId: "52",
          submitTime: "2019-06-20 08:00:00",
          result: "Accept"
        },
        {
          solutionId: "1",
          username: "Admin",
          problemId: "52",
          submitTime: "2019-06-20 08:00:00",
          result: "Accept"
        },
        {
          solutionId: "1",
          username: "Admin",
          problemId: "52",
          submitTime: "2019-06-20 08:00:00",
          result: "Accept"
        },
        {
          solutionId: "1",
          username: "Admin",
          problemId: "52",
          submitTime: "2019-06-20 08:00:00",
          result: "Accept"
        },
        {
          solutionId: "1",
          username: "Admin",
          problemId: "52",
          submitTime: "2019-06-20 08:00:00",
          result: "Accept"
        },
        {
          solutionId: "1",
          username: "Admin",
          problemId: "52",
          submitTime: "2019-06-20 08:00:00",
          result: "Accept"
        },
        {
          solutionId: "1",
          username: "Admin",
          problemId: "52",
          submitTime: "2019-06-20 08:00:00",
          result: "Accept"
        },
        {
          solutionId: "1",
          username: "Admin",
          problemId: "52",
          submitTime: "2019-06-20 08:00:00",
          result: "Accept"
        },
        {
          solutionId: "1",
          username: "Admin",
          problemId: "52",
          submitTime: "2019-06-20 08:00:00",
          result: "Accept"
        }
      ],
      columns: [
        {
          field: "solutionId",
          title: "ID",
          width: 100,
          titleAlign: "center",
          columnAlign: "center",
          isResize: true
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
          field: "problemId",
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
      this.codeModal = true;
    }
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`
      };
    }
  }
};
</script>
<style lang="scss" scoped>
.section {
  padding: 0;
}

.section-with-padding {
  //   padding: 40px 0;
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

.justify-content-center {
  justify-content: center !important;
}
</style>