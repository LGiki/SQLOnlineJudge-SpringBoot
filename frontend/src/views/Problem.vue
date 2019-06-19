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
                  :columns="columns"
                  :table-data="tableData"
                  row-hover-color="#eee"
                  row-click-color="#edf7ff"
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
    },
  },
  data() {
    return {
      name: null,
      email: null,
      message: null,
      tableData: [
        {
          id: "6",
          title: "查找最晚入职员工的所有信息",
          solve: "52",
          submit: "66",
          accept_rate: 0.6
        },
        {
          id: "6",
          title: "查找最晚入职员工的所有信息",
          solve: "52",
          submit: "66",
          accept_rate: 0.6
        },
        {
          id: "6",
          title: "查找最晚入职员工的所有信息",
          solve: "52",
          submit: "66",
          accept_rate: 0.6
        },
        {
          id: "6",
          title: "查找最晚入职员工的所有信息",
          solve: "52",
          submit: "66",
          accept_rate: 0.6
        },
        {
          id: "6",
          title: "查找最晚入职员工的所有信息",
          solve: "52",
          submit: "66",
          accept_rate: 0.6
        },
        {
          id: "6",
          title: "查找最晚入职员工的所有信息",
          solve: "52",
          submit: "66",
          accept_rate: 0.6
        },
        {
          id: "6",
          title: "查找最晚入职员工的所有信息",
          solve: "52",
          submit: "66",
          accept_rate: 0.6
        },
        {
          id: "6",
          title: "查找最晚入职员工的所有信息",
          solve: "52",
          submit: "66",
          accept_rate: 0.6
        },
        {
          id: "6",
          title: "查找最晚入职员工的所有信息",
          solve: "52",
          submit: "66",
          accept_rate: 0.6
        },
        {
          id: "6",
          title: "查找最晚入职员工的所有信息",
          solve: "52",
          submit: "66",
          accept_rate: 0.6
        },
        {
          id: "6",
          title: "查找最晚入职员工的所有信息",
          solve: "52",
          submit: "66",
          accept_rate: 0.6
        },
        {
          id: "6",
          title: "查找最晚入职员工的所有信息",
          solve: "52",
          submit: "66",
          accept_rate: 0.6
        },
        {
          id: "6",
          title: "查找最晚入职员工的所有信息",
          solve: "52",
          submit: "66",
          accept_rate: 0.6
        },
        {
          id: "6",
          title: "查找最晚入职员工的所有信息",
          solve: "52",
          submit: "66",
          accept_rate: 0.6
        }
      ],
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
          isResize: true
        }
      ]
    };
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
