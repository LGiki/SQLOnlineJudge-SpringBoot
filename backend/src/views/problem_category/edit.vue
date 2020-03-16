<template>
  <div class="app-container">
    <el-form
      ref="problemCategoryDetail"
      :model="problemCategoryDetail"
      :rules="checkRules"
      label-width="120px"
    >
      <el-form-item label="题目集ID" prop="id">
        <el-input
          v-model="problemCategoryDetail.id"
          placeholder="请输入题目集ID"
          type="number"
          disabled
        />
      </el-form-item>
      <el-form-item label="题目集名称" prop="name">
        <el-input
          v-model="problemCategoryDetail.name"
          placeholder="请输入题目集名称"
        />
      </el-form-item>

      <el-dialog
        title="选择题目添加到题目集"
        :visible.sync="addFromProblemListDialogVisible"
        width="80%"
      >
        <template>
          <v-table
            is-horizontal-resize
            style="width:100%"
            :is-loading="problemListIsLoading"
            :columns="problemListTableConfig.columns"
            :table-data="problemListTableConfig.tableData"
            row-hover-color="#eee"
            row-click-color="#edf7ff"
            :row-click="problemListRowClick"
            :select-all="problemListSelectAll"
            :select-change="problemListSelectChange"
            :select-group-change="problemListSelectGroupChange"
          />
        </template>
        <template>
          <div class="bd">
            <v-pagination
              :show-paging-count="3"
              :total="problemListTotalItems"
              :page-size="problemListPageSize"
              :layout="['total', 'sizer', 'prev', 'pager', 'next', 'jumper']"
              @page-change="problemListPageChange"
              @page-size-change="problemListPageSizeChange"
            />
          </div>
        </template>
        <span slot="footer" class="dialog-footer">
          <el-button
            type="danger"
            @click="addFromProblemListDialogVisible = false"
            >关 闭</el-button
          >&nbsp;
          <el-button type="primary" @click="onAddFromProblemListSubmit"
            >确 定</el-button
          >
        </span>
      </el-dialog>
      <el-form-item label="题目集题目列表">
        <div class="operation-button">
          <el-button type="primary" @click="addFromProblemList">
            <i class="el-icon-plus" />&nbsp;添加题目
          </el-button>
          <el-button
            v-if="selectedProblemCollections.length != 0"
            type="danger"
            @click="deleteSelectedProblemFromProblemCollection"
          >
            <i class="el-icon-delete" />&nbsp;删除所选题目
          </el-button>
        </div>
        <template>
          <v-table
            is-horizontal-resize
            style="width:100%"
            :is-loading="problemCollectionListIsLoading"
            :columns="problemCollectionTableConfig.columns"
            :table-data="problemCollectionTableConfig.tableData"
            row-hover-color="#eee"
            row-click-color="#edf7ff"
            :row-click="problemCollectionListRowClick"
            :select-all="problemCollectionListSelectAll"
            :select-change="problemCollectionListSelectChange"
            :select-group-change="problemCollectionListSelectGroupChange"
          />
        </template>
        <template>
          <div class="bd">
            <v-pagination
              :show-paging-count="3"
              :total="problemCollectionListTotalItems"
              :page-size="problemCollectionListPageSize"
              :layout="['total', 'sizer', 'prev', 'pager', 'next', 'jumper']"
              @page-change="problemCollectionListPageChange"
              @page-size-change="problemCollectionListPageSizeChange"
            />
          </div>
        </template>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import "vue-easytable/libs/themes-base/index.css";
import { VTable, VPagination } from "vue-easytable";

export default {
  components: {
    VTable,
    VPagination
  },
  data() {
    return {
      problemInCollection: [], //用于确定已经在题目集中的题目
      selectedProblems: [],
      selectedProblemCollections: [],
      alreadyPerformAddedProblemCount: 0,
      successAddedProblemCount: 0,
      alreadyPerformDeletedProblemCollectionCount: 0,
      successDeletedProblemCollectionCount: 0,
      checkRules: {
        name: [
          {
            required: true,
            message: "题目集名称不能为空",
            trigger: "blur"
          }
        ]
      },
      problemCategoryDetail: {
        id: "",
        name: ""
      },
      addFromProblemListDialogVisible: false,
      searchType: "id",
      inSearch: false,
      searchKeyword: "",
      problemCollectionListPageNum: 1,
      problemCollectionListPageSize: 20,
      problemCollectionListTotalItems: 0,
      problemCollectionListIsLoading: false,
      problemListIsLoading: false,
      problemCollectionTableConfig: {
        tableData: [],
        columns: [
          {
            width: 60,
            titleAlign: "center",
            columnAlign: "center",
            type: "selection"
          },
          {
            field: "problemId",
            title: "题目ID",
            width: 30,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "problemTitle",
            title: "题目标题",
            width: 60,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "databaseName",
            title: "数据库名称",
            width: 30,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          }
        ]
      },
      problemListPageNum: 1,
      problemListPageSize: 10,
      problemListTotalItems: 0,
      problemListIsLoading: false,
      problemListTableConfig: {
        tableData: [],
        columns: [
          {
            width: 60,
            titleAlign: "center",
            columnAlign: "center",
            type: "selection"
          },
          {
            field: "id",
            title: "题目ID",
            width: 30,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          },
          {
            field: "title",
            title: "题目标题",
            width: 60,
            titleAlign: "center",
            columnAlign: "center",
            isResize: true
          }
        ]
      }
    };
  },
  mounted: function() {
    this.initPage();
  },
  methods: {
    initPage() {
      this.problemInCollection.length = 0;
      this.selectedProblems.length = 0;
      this.selectedProblemCollections.length = 0;
      this.alreadyPerformAddedProblemCount= 0;
      this.successAddedProblemCount= 0;
      this.alreadyPerformDeletedProblemCollectionCount= 0;
      this.successDeletedProblemCollectionCount = 0;
      let problemCategoryId = this.$route.params.id;
      this.getProblemCategoryDetail(problemCategoryId);
      this.fetchProblemCollectionList(problemCategoryId);
    },
    problemCollectionListSelectAll(selection) {
      this.selectedProblemCollections = selection;
    },
    problemCollectionListSelectChange(selection, rowData) {
      // console.log("select-change", selection, rowData);
    },
    problemCollectionListSelectGroupChange(selection) {
      this.selectedProblemCollections = selection;
    },
    problemListSelectAll(selection) {
      this.selectedProblems = selection;
    },
    problemListSelectChange(selection, rowData) {
      // console.log("select-change", selection, rowData);
    },
    problemListSelectGroupChange(selection) {
      this.selectedProblems = selection;
    },
    onSubmit() {
      this.$refs.problemCategoryDetail.validate(valid => {
        if (valid) {
          let problemCategoryId = this.$route.params.id;
          let problemCategory = {
            id: this.problemCategoryDetail.id,
            name: this.problemCategoryDetail.name.trim()
          };
          this.updateProblemCategory(problemCategoryId, problemCategory, () => {
            this.$router.back(-1);
          });
        } else {
          this.$message.error("请确认所有项目均填写正确！");
        }
      });
    },
    onCancel() {
      this.$router.back(-1);
    },
    getProblemCategoryDetail(problemCategoryId) {
      const apiUrl = this.Url.problemCategoryBaseUrl;
      this.$axios
        .get(apiUrl + problemCategoryId)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error("获取题目集信息失败，内部错误！");
          } else {
            const resData = res.data;
            if (resData.code === 0) {
              this.problemCategoryDetail = resData.data;
            } else {
              this.$message.error(resData.message);
            }
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    updateProblemCategory(problemCategoryId, problemCategory, successCallback) {
      const apiUrl = this.Url.problemCategoryBaseUrl;
      this.$axios
        .put(apiUrl + problemCategoryId, problemCategory)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error("更新题目集信息失败，内部错误！");
          } else {
            const resData = res.data;
            if (resData.code === 0) {
              this.$message({
                message: resData.message,
                type: "success"
              });
              successCallback();
            } else {
              this.$message.error(resData.message);
            }
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    problemCollectionListPageChange(pageNum) {
      this.problemCollectionListPageNum = pageNum;
      if (this.inSearch) {
        this.onSearch();
      } else {
        this.fetchProblemCategoryList();
      }
    },
    problemCollectionListPageSizeChange(newPageSize) {
      this.problemCollectionListPageSize = newPageSize;
      if (this.inSearch) {
        this.onSearch();
      } else {
        this.fetchProblemCategoryList();
      }
    },
    problemListPageChange(pageNum) {
      this.problemListPageNum = pageNum;
      if (this.inSearch) {
        this.onSearch();
      } else {
        this.fetchProblemList();
      }
    },
    problemListPageSizeChange(newPageSize) {
      this.problemListPageSize = newPageSize;
      if (this.inSearch) {
        this.onSearch();
      } else {
        this.fetchProblemList();
      }
    },
    problemCollectionListRowClick(rowIndex, rowData, column) {},
    problemListRowClick(rowIndex, rowData, column) {},
    fetchProblemCollectionList(problemCategoryId) {
      this.problemCollectionListIsLoading = true;
      let apiUrl = this.Url.problemCollectionBaseUrl;
      this.$axios
        .get(apiUrl, {
          params: {
            categoryId: problemCategoryId,
            pageNum: this.problemCollectionListPageNum,
            pageSize: this.problemCollectionListPageSize
          }
        })
        .then(res => {
          if (res.status !== 200) {
            this.$message.error("获取题目集详情失败，内部错误！");
          } else {
            const resData = res.data;
            if (resData.code === 0) {
              this.problemCollectionTableConfig.tableData =
                resData.data.records;
              this.problemInCollection.length = 0;
              for (let item of resData.data.records) {
                this.problemInCollection.push(item.problemId);
                this.selectedProblems.push(item);
              }
              this.problemCollectionListTotalItems = resData.data.total;
            } else {
              this.$message.error(resData.message);
            }
          }
          this.problemCollectionListIsLoading = false;
        })
        .catch(err => {
          this.$message.error("获取题目集详情失败！");
          this.problemCollectionListIsLoading = false;
          console.log(err);
        });
    },
    fetchProblemList() {
      this.problemListIsLoading = true;
      const apiUrl = this.Url.problemBaseUrl;
      this.$axios
        .get(apiUrl, {
          params: {
            pageNum: this.problemListPageNum,
            pageSize: this.problemListPageSize
          }
        })
        .then(res => {
          if (res.status !== 200) {
            this.$message.error("获取题目列表失败，内部错误！");
          } else {
            const resData = res.data;
            this.problemListTableConfig.tableData.length = 0;
            if (resData.code === 0) {
              for (let item of resData.data.records) {
                if (this.problemInCollection.indexOf(item.id) != -1) {
                  item._checked = true;
                }
                this.problemListTableConfig.tableData.push(item);
              }
              this.problemListTotalItems = resData.data.total;
            } else {
              this.$message.error(resData.message);
            }
          }
          this.problemListIsLoading = false;
        })
        .catch(err => {
          this.$message.error("获取题目列表失败！");
          this.problemListIsLoading = false;
          console.log(err);
        });
    },
    deleteProblemCollection(problemCollectionId, totalNum) {
      let apiUrl = this.Url.problemCollectionBaseUrl;
      this.$axios
        .delete(apiUrl + problemCollectionId)
        .then(res => {
          this.alreadyPerformDeletedProblemCollectionCount += 1;
          if (res.status !== 200) {
            this.$message.error("删除题目失败，内部错误！");
          } else {
            const resData = res.data;
            if (resData.code === 0) {
              this.successDeletedProblemCollectionCount += 1;
              if (this.alreadyPerformDeletedProblemCollectionCount == totalNum) {
                if (this.successDeletedProblemCollectionCount == totalNum) {
                  this.$message({
                    message: "删除题目成功！",
                    type: "success"
                  });
                } else {
                  this.$message.error("题目删除完毕，部分题目删除失败！");
                }
                this.initPage();
              }
            } else {
              this.$message.error(resData.message);
            }
          }
        })
        .catch(err => {
          this.alreadyPerformDeletedProblemCollectionCount += 1;
          this.$message.error("删除题目失败！");
          console.log(err);
        });
    },
    deleteSelectedProblemFromProblemCollection() {
      this.alreadyPerformDeletedProblemCollectionCount = 0;
      this.successDeletedProblemCollectionCount = 0;
      for (let item of this.selectedProblemCollections) {
        this.deleteProblemCollection(
          item.id,
          this.selectedProblemCollections.length
        );
      }
    },
    addFromProblemList() {
      this.addFromProblemListDialogVisible = true;
      this.fetchProblemList();
    },
    addProblemCollection(categoryId, problemId, totalNum) {
      let apiUrl = this.Url.problemCollectionBaseUrl;
      this.$axios
        .post(apiUrl, {
          categoryId: categoryId,
          problemId: problemId
        })
        .then(res => {
          this.alreadyPerformAddedProblemCount += 1;
          if (res.status !== 200) {
            this.$message.error("添加题目失败，内部错误！");
          } else {
            const resData = res.data;
            if (resData.code === 0) {
              this.successAddedProblemCount += 1;
              if (this.alreadyPerformAddedProblemCount == totalNum) {
                if (this.successAddedProblemCount == totalNum) {
                  this.$message({
                    message: "添加题目成功！",
                    type: "success"
                  });
                } else {
                  this.$message.error("题目添加完毕，部分题目添加失败！");
                }
                this.addFromProblemListDialogVisible = false;
                this.initPage();
              }
            } else {
              this.$message.error(resData.message);
            }
          }
        })
        .catch(err => {
          this.alreadyPerformAddedProblemCount += 1;
          this.$message.error("添加题目失败！");
          console.log(err);
        });
    },
    onAddFromProblemListSubmit() {
      let categoryId = this.$route.params.id;
      this.alreadyPerformAddedProblemCount = 0;
      this.successAddedProblemCount = 0;
      let problemListToBeAdd = [];
      for (let item of this.selectedProblems) {
        if (this.problemInCollection.indexOf(item.id) == -1) {
          problemListToBeAdd.push(item.id);
        }
      }
      for (let problemId of problemListToBeAdd) {
        this.addProblemCollection(
          categoryId,
          problemId,
          problemListToBeAdd.length
        );
      }
    }
  }
};
</script>

<style scoped>
.bd {
  padding-top: 15px;
  text-align: center;
}
.line {
  text-align: center;
}
.operation-button {
  float: right;
  padding-bottom: 10px;
}
</style>
