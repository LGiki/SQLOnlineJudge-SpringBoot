<template>
  <div class="app-container">
    <template>
      <v-table
        is-horizontal-resize
        style="width:100%"
        :columns="tableConfig.columns"
        :table-data="tableConfig.tableData"
        row-hover-color="#eee"
        row-click-color="#edf7ff"
        @on-custom-comp="customCompFunc"
      ></v-table>
    </template>
  </div>
</template>

<script>
import Vue from "vue";
import { getList } from "@/api/table";
import "vue-easytable/libs/themes-base/index.css";
import { VTable, VPagination } from "vue-easytable";

export default {
  components: {
    VTable,
    VPagination
  },
  data() {
    return {
      pageIndex: 1,
      pageSize: 10,
      totalItems: 0,
      isLoading: true,
      tableConfig: {
        tableData: [
          {
            id: 1,
            title: "Test",
            solve: 123,
            submit: 666
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
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              return rowData.submit == 0
                ? 0
                : (rowData.solve / rowData.submit).toFixed(2);
            }
          },
          {
            field: "action",
            title: "操作",
            width: 80,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true,
            componentName: "table-operation"
            // formatter: function(rowData, rowIndex, pagingIndex, field) {
            //   return '<svg-icon icon-class="edit" />';
            // }
          }
        ]
      }
    };
  },
  created() {},
  methods: {
    customCompFunc(params) {
      console.log(params);

      if (params.type === "delete") {
        // do delete operation

      } else if (params.type === "edit") {
        // do edit operation

      }
    }
  }
};
Vue.component("table-operation", {
  template: `<span>
        <a href="" @click.stop.prevent="update(rowData,index)"><svg-icon icon-class="edit" /></a>&nbsp;
        <a href="" @click.stop.prevent="deleteRow(rowData,index)"><i class="el-icon-delete" /></a>
        </span>`,
  props: {
    rowData: {
      type: Object
    },
    field: {
      type: String
    },
    index: {
      type: Number
    }
  },
  methods: {
    update() {
      // 参数根据业务场景随意构造
      let params = { type: "edit", index: this.index, rowData: this.rowData };
      this.$emit("on-custom-comp", params);
    },

    deleteRow() {
      // 参数根据业务场景随意构造
      let params = { type: "delete", index: this.index };
      this.$emit("on-custom-comp", params);
    }
  }
});
</script>
