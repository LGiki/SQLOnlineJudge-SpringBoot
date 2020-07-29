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
      <el-form-item label="题目集起止时间" prop="duration">
        <div class="block">
          <el-date-picker
            v-model="problemCategoryDetail.duration"
            type="datetimerange"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          />
        </div>
      </el-form-item>
      <el-form-item label="题目集结束后学生能否查看题目" prop="viewAfterEnd">
        <el-switch
          v-model="problemCategoryDetail.viewAfterEnd"
          active-text="能"
          inactive-text="否"
        />
      </el-form-item>
      <el-form-item label="题目列表">
        <div class="operation-button">
          <el-button type="primary" @click="openAddFromProblemListDialog">
            <i class="el-icon-plus"/>&nbsp;添加题目
          </el-button>
          <el-button
            v-if="problemCollectionProblemListSelection && problemCollectionProblemListSelection.length > 0"
            type="danger"
            @click="onDeleteCollectionProblemSelection"
          >
            <i class="el-icon-delete"/>&nbsp;移除所选的{{ problemCollectionProblemListSelection ? problemCollectionProblemListSelection.length + '个' : '' }}题目
          </el-button>
        </div>
        <v-table
          :width="800"
          is-horizontal-resize
          style="width:100%"
          :is-loading="problemCollectionListIsLoading"
          :columns="problemCollectionTableConfig.columns"
          :table-data="problemCollectionTableConfig.tableData"
          row-hover-color="#eee"
          row-click-color="#edf7ff"
          :select-all="onProblemCollectionListSelectAll"
          :select-change="onProblemCollectionListSelectChange"
          :cell-edit-done="problemScoreEditDone"
        />
        <div class="bd">
          <el-pagination
            background
            :current-page="problemCollectionListPageNum"
            :page-sizes="[10, 20, 30]"
            :page-size="problemCollectionListPageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="problemCollectionListTotalItems"
            @size-change="onProblemCollectionListPageSizeChange"
            @current-change="onProblemCollectionListPageChange"
          />
        </div>
      </el-form-item>
      <el-form-item label="允许的用户组">
        <div class="operation-button">
          <el-button type="primary" @click="openAddFromUserGroupListDialog">
            <i class="el-icon-plus"/>&nbsp;添加用户组
          </el-button>
          <el-button
            v-if="problemCategoryAllowUserGroupListSelection && problemCategoryAllowUserGroupListSelection.length > 0"
            type="danger"
            @click="onDeleteProblemCategoryAllowUserGroupListSelection"
          >
            <i class="el-icon-delete"/>&nbsp;移除所选{{ problemCategoryAllowUserGroupListSelection ? problemCategoryAllowUserGroupListSelection.length + '个' : '' }}个用户组的权限
          </el-button>
        </div>
        <v-table
          :width="100"
          is-horizontal-resize
          style="width:100%"
          :is-loading="problemCategoryAllowUserGroupListIsLoading"
          :columns="problemCategoryAllowUserGroupListTableConfig.columns"
          :table-data="problemCategoryAllowUserGroupListTableConfig.tableData"
          row-hover-color="#eee"
          row-click-color="#edf7ff"
          :select-all="onProblemCategoryAllowUserGroupListSelectAll"
          :select-change="onProblemCategoryAllowUserGroupListSelectChange"
        />
        <div class="bd">
          <el-pagination
            background
            :current-page="problemCategoryAllowUserGroupListPageNum"
            :page-sizes="[10, 20, 30]"
            :page-size="problemCategoryAllowUserGroupListPageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="problemCategoryAllowUserGroupListTotalItems"
            @size-change="onProblemCategoryAllowUserGroupListPageSizeChange"
            @current-change="onProblemCategoryAllowUserGroupListPageChange"
          />
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSave">保存</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
      <el-dialog
        title="选择题目添加到题目集"
        :visible.sync="addFromProblemListDialogVisible"
        width="80%"
      >
        <el-table
          ref="problemListTable"
          :data="problemListTableData"
          tooltip-effect="dark"
          style="width: 100%"
          @select="onProblemListSelect"
          @select-all="onProblemListSelectAll"
        >
          <el-table-column
            type="selection"
            align="center"
            width="50"
          />
          <el-table-column
            prop="problemId"
            label="题目ID"
            align="center"
            width="100"
          />
          <el-table-column
            prop="databaseName"
            label="数据库"
            min-width="1"
            align="center"
          />
          <el-table-column
            prop="problemTitle"
            label="题目标题"
            align="center"
            min-width="3"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            prop="problemDifficulty"
            label="题目难度"
            align="center"
            width="100"
            :formatter="(row, column, cellValue, index) => {let difficultyStars = '';for (let i = 0; i < row.problemDifficulty; i++) {difficultyStars += '★'};return difficultyStars}"
          />
        </el-table>
        <div class="bd">
          <el-pagination
            background
            :current-page="problemListPageNum"
            :page-sizes="[10, 20, 30]"
            :page-size="problemListPageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="problemListTotalItems"
            @size-change="onProblemListPageSizeChange"
            @current-change="onProblemListPageChange"
          />
        </div>
        <span slot="footer" class="dialog-footer">
          <template v-if="selectedProblemIds && selectedProblemIds.length !== 0"><p>将新增 <strong>{{ selectedProblemIds.length }}</strong> 道题目到题目集中</p></template>
          <el-button
            v-if="selectedProblemIds && selectedProblemIds.length !== 0"
            type="warning"
            @click="resetSelectedProblemIds"
          >重 置</el-button>
          <el-button
            type="danger"
            @click="addFromProblemListDialogVisible = false"
          >关 闭</el-button>
          <el-button
            type="primary"
            @click="onAddProblem"
          >确 定</el-button>
        </span>
      </el-dialog>
      <el-dialog
        title="选择题目添加到题目集"
        :visible.sync="addFromUserGroupListDialogVisible"
        width="80%"
      >
        <el-table
          ref="userGroupListTable"
          :data="userGroupListTableData"
          tooltip-effect="dark"
          style="width: 100%"
          @select="onUserGroupListSelect"
          @select-all="onUserGroupListSelectAll"
        >
          <el-table-column
            type="selection"
            align="center"
            width="50"
          />
          <el-table-column
            prop="id"
            label="用户组ID"
            align="center"
            min-width="1"
          />
          <el-table-column
            prop="name"
            label="用户组名称"
            min-width="1"
            align="center"
          />
        </el-table>
        <div class="bd">
          <el-pagination
            background
            :current-page="userGroupListPageNum"
            :page-sizes="[10, 20, 30]"
            :page-size="userGroupListPageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="userGroupListTotalItems"
            @size-change="onUserGroupPageSizeChange"
            @current-change="onUserGroupListPageChange"
          />
        </div>
        <span slot="footer" class="dialog-footer">
          <template v-if="selectedUserGroupIds && selectedUserGroupIds.length !== 0"><p>将新增 <strong>{{ selectedUserGroupIds.length }}</strong> 道题目到题目集中</p></template>
          <el-button
            v-if="selectedUserGroupIds && selectedUserGroupIds.length !== 0"
            type="warning"
            @click="resetSelectedUserGroupIds"
          >重 置</el-button>
          <el-button
            type="danger"
            @click="addFromUserGroupListDialogVisible = false"
          >关 闭</el-button>
          <el-button
            type="primary"
            @click="onAddUserGroup"
          >确 定</el-button>
        </span>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
import 'vue-easytable/libs/themes-base/index.css'
import { VTable } from 'vue-easytable'
import ConvertUtil from '@/utils/convert-util'
import { getProblemCategoryDetail, updateProblemCategory } from '@/api/problem-category'
import {
  getProblemCollectionList,
  updateProblemScore,
  getProblemIdsByProblemCategoryId,
  insertProblemCollectionInBulk,
  deleteProblemCollectionInBulk
} from '@/api/problem-collection'
import { getProblemList } from '@/api/problem'
import {
  getUserGroupDetailByProblemCategoryId,
  deleteProblemCategoryPermissionInBulk,
  getUserGroupIdsByProblemCategoryId,
  insertProblemCategoryPermissionInBulk
} from '@/api/problem-category-permission'
import { getUserGroupList } from '@/api/user-group'

export default {
  components: {
    VTable
  },
  data() {
    return {
      checkRules: {
        name: [
          {
            required: true,
            message: '题目集名称不能为空',
            trigger: 'blur'
          }
        ],
        duration: [
          {
            required: true,
            message: '题目集起止时间不能为空',
            trigger: 'blur'
          }
        ],
        viewAfterEnd: [
          {
            required: true,
            message: '必须确定题目集结束后学生能否查看题目',
            trigger: 'blur'
          }
        ]
      },
      collectionProblemIds: [],
      problemCategoryDetail: {
        id: '',
        name: '',
        duration: [],
        viewAfterEnd: true
      },
      addFromUserGroupListDialogVisible: false,
      problemCategoryAllowUserGroupListTotalItems: 0,
      problemCategoryAllowUserGroupListPageSize: 10,
      problemCategoryAllowUserGroupListPageNum: 1,
      problemCategoryAllowUserGroupListSelection: [],
      // 题目集允许的用户组列表
      problemCategoryAllowUserGroupListTableConfig: {
        tableData: [],
        columns: [
          {
            width: 50,
            titleAlign: 'center',
            columnAlign: 'center',
            type: 'selection'
          },
          {
            field: 'userGroupId',
            title: '用户组ID',
            width: 50,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'userGroupName',
            title: '用户组名称',
            width: 50,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          }
        ]
      },
      problemCategoryAllowUserGroupListIsLoading: false,
      problemCollectionListPageNum: 1,
      problemCollectionListPageSize: 10,
      problemCollectionListTotalItems: 0,
      problemCollectionListIsLoading: false,
      problemCollectionTableConfig: {
        tableData: [],
        columns: [
          {
            width: 50,
            titleAlign: 'center',
            columnAlign: 'center',
            type: 'selection'
          },
          {
            field: 'problemId',
            title: '题目ID',
            width: 80,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'databaseName',
            title: '数据库名称',
            width: 100,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          },
          {
            field: 'problemTitle',
            title: '题目标题',
            width: 370,
            titleAlign: 'center',
            columnAlign: 'left',
            isResize: true
          },
          {
            field: 'problemDifficulty',
            title: '难度',
            width: 100,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true,
            formatter: function(rowData, rowIndex, pagingIndex, field) {
              let difficultyStars = ''
              for (let i = 0; i < rowData.problemDifficulty; i++) {
                difficultyStars += '★'
              }
              return difficultyStars
            }
          },
          {
            field: 'problemScore',
            title: '题目分值',
            width: 100,
            isEdit: true,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
          }
        ]
      },
      problemCollectionProblemListSelection: [],
      addFromProblemListDialogVisible: false,
      problemListPageNum: 1,
      problemListPageSize: 10,
      problemListTotalItems: 0,
      problemListTableData: [],
      selectedProblemIds: [],
      pickerOptions: {
        shortcuts: [
          {
            text: '未来一周',
            onClick(picker) {
              const start = new Date()
              const end = new Date()
              end.setTime(start.getTime() + 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '未来一个月',
            onClick(picker) {
              const start = new Date()
              const end = new Date()
              end.setTime(start.getTime() + 3600 * 1000 * 24 * 30)
              picker.$emit('pick', [start, end])
            }
          }
        ]
      },
      userGroupListPageNum: 1,
      userGroupListPageSize: 10,
      userGroupListTableData: [],
      userGroupListTotalItems: 0,
      selectedUserGroupIds: [],
      categoryAllowUserGroupIds: []
    }
  },
  computed: {
    problemCategoryId() {
      return this.$route.params.id
    }
  },
  mounted: function() {
    this.getProblemCategoryDetail(this.problemCategoryId)
    this.getProblemCollectionList(this.problemCategoryId)
    this.getProblemCategoryAllowUserGroupList(this.problemCategoryId)
  },
  methods: {
    onAddUserGroup() {
      if (this.selectedUserGroupIds && this.selectedUserGroupIds.length !== 0) {
        this.handleResponse(insertProblemCategoryPermissionInBulk(this.problemCategoryId, this.selectedUserGroupIds), '添加用户组',
          (res) => {
            if (res.data.success && res.data.success.length === 0) {
              this.$message.error('添加用户组失败，请稍后再试')
            } else if ((res.data.fail && res.data.fail.length) > 0 || (res.data.duplicated && res.data.duplicated.length) > 0) {
              let messageString = '成功执行添加操作'
              if (res.data.fail.length > 0) {
                messageString += '，部分用户组添加失败：' + res.data.fail
              }
              if (res.data.duplicated.length > 0) {
                messageString += '，部分用户组已拥有题目集权限：' + res.data.duplicated
              }
              this.$message({
                message: messageString,
                type: 'warning'
              })
            } else {
              this.$message.success('添加用户组成功')
            }
            this.addFromUserGroupListDialogVisible = false
            this.selectedUserGroupIds.length = 0
            this.getProblemCategoryAllowUserGroupList(this.problemCategoryId)
          })
      } else {
        this.$message.error('请选择要赋予题目集权限的用户组')
      }
    },
    onUserGroupListSelect(selection, row) {
      const selected = selection.length && selection.indexOf(row) !== -1
      if (selected) {
        selection.forEach((userGroup) => {
          if (this.categoryAllowUserGroupIds.indexOf(userGroup.id) === -1 && this.selectedUserGroupIds.indexOf(userGroup.id) === -1) {
            this.selectedUserGroupIds.push(userGroup.id)
          }
        })
      } else {
        const rowInSelectedIndex = this.selectedUserGroupIds.indexOf(row.problemId)
        if (rowInSelectedIndex !== -1) {
          this.selectedUserGroupIds.splice(rowInSelectedIndex, 1)
        }
      }
    },
    onUserGroupListSelectAll(selection) {
      if (selection.length > 0) {
        // 全选
        selection.forEach((userGroup) => {
          if (this.categoryAllowUserGroupIds.indexOf(userGroup.id) === -1 && this.selectedUserGroupIds.indexOf(userGroup.id) === -1) {
            this.selectedUserGroupIds.push(userGroup.id)
          }
        })
      } else {
        // 取消全选
        this.problemListTableData.forEach((userGroup) => {
          if (this.categoryAllowUserGroupIds.indexOf(userGroup.id) === -1) {
            // 只移除不在题目集内的题目的选中状态
            const tempIndex = this.selectedUserGroupIds.indexOf(userGroup.id)
            if (tempIndex !== -1) {
              this.selectedUserGroupIds.splice(tempIndex, 1)
            }
          } else {
            // 确保已经在题目集内的题目依旧是选中状态
            this.$refs.userGroupListTable.toggleRowSelection(userGroup, true)
          }
        })
      }
    },
    resetSelectedUserGroupIds() {
      this.selectedUserGroupIds.length = 0
      this.refreshUserGroupList()
    },
    onUserGroupPageSizeChange(pageSize) {
      this.userGroupListPageSize = pageSize
      this.refreshUserGroupList()
    },
    onUserGroupListPageChange(pageNum) {
      this.userGroupListPageNum = pageNum
      this.refreshUserGroupList()
    },
    async getUserGroupList() {
      await this.handleResponse(getUserGroupList(this.userGroupListPageNum, this.userGroupListPageSize), '获取用户组列表',
        (res) => {
          this.userGroupListTableData = res.data.records
          this.userGroupListTotalItems = res.data.total
        })
    },
    async getUserGroupIdsByProblemCategoryId(problemCategoryId) {
      await this.handleResponse(getUserGroupIdsByProblemCategoryId(problemCategoryId), '获取有题目集权限的全部用户组',
        (res) => {
          this.categoryAllowUserGroupIds = res.data
        })
    },
    async refreshUserGroupList() {
      await this.getUserGroupList()
      for (const userGroup of this.userGroupListTableData) {
        if (this.categoryAllowUserGroupIds.indexOf(userGroup.id) !== -1 || this.selectedUserGroupIds.indexOf(userGroup.id) !== -1) {
          this.$refs.userGroupListTable.toggleRowSelection(userGroup, true)
        }
      }
    },
    async openAddFromUserGroupListDialog() {
      this.addFromUserGroupListDialogVisible = true
      await this.getUserGroupIdsByProblemCategoryId(this.problemCategoryId)
      await this.refreshUserGroupList()
    },
    onDeleteProblemCategoryAllowUserGroupListSelection() {
      this.$confirm(`是否移除所选${this.problemCategoryAllowUserGroupListSelection ? this.problemCategoryAllowUserGroupListSelection.length + '个' : ''}用户组的权限?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.problemCategoryAllowUserGroupListSelection && this.problemCategoryAllowUserGroupListSelection.length > 0) {
          this.handleResponse(deleteProblemCategoryPermissionInBulk(this.problemCategoryAllowUserGroupListSelection), '移除用户组权限',
            (res) => {
              if (res.data.success && res.data.success.length === 0) {
                this.$message.error('从题目集中移除所选用户组的权限失败，请稍后再试')
              } else if (res.data.fail && res.data.fail.length > 0) {
                this.problemCategoryAllowUserGroupListSelection.length = 0
                this.problemCategoryAllowUserGroupListPageNum = 1
                this.$message({
                  message: '成功执行移除操作，但部分用户组移除失败：' + res.data.fail,
                  type: 'warning'
                })
              } else {
                this.problemCategoryAllowUserGroupListSelection.length = 0
                this.problemCategoryAllowUserGroupListPageNum = 1
                this.$message.success('成功移除所选用户组的权限')
              }
              this.getProblemCategoryAllowUserGroupList(this.problemCategoryId)
            })
        } else {
          this.$message.error('请检查是否选择了要移除的用户组')
        }
      })
    },
    onProblemCategoryAllowUserGroupListSelectAll(selection) {
      if (selection.length > 0) {
        // 全选
        selection.forEach((userGroup) => {
          if (this.problemCategoryAllowUserGroupListSelection.indexOf(userGroup.id) === -1) {
            this.problemCategoryAllowUserGroupListSelection.push(userGroup.id)
          }
        })
      } else {
        // 取消全选
        this.problemCategoryAllowUserGroupListTableConfig.tableData.forEach((userGroup) => {
          const tempIndex = this.problemCategoryAllowUserGroupListSelection.indexOf(userGroup.id)
          if (tempIndex !== -1) {
            this.problemCategoryAllowUserGroupListSelection.splice(tempIndex, 1)
          }
        })
      }
    },
    onProblemCategoryAllowUserGroupListSelectChange(selection, rowData) {
      const selected = selection.length && selection.indexOf(rowData) !== -1
      if (selected) {
        selection.forEach((userGroup) => {
          if (this.problemCategoryAllowUserGroupListSelection.indexOf(userGroup.id) === -1) {
            this.problemCategoryAllowUserGroupListSelection.push(userGroup.id)
          }
        })
      } else {
        const rowInSelectedIndex = this.problemCategoryAllowUserGroupListSelection.indexOf(rowData.id)
        if (rowInSelectedIndex !== -1) {
          this.problemCategoryAllowUserGroupListSelection.splice(rowInSelectedIndex, 1)
        }
      }
    },
    onProblemCategoryAllowUserGroupListPageChange(pageNum) {
      this.problemCategoryAllowUserGroupListPageNum = pageNum
      this.getProblemCategoryAllowUserGroupList(this.problemCategoryId)
    },
    onProblemCategoryAllowUserGroupListPageSizeChange(pageSize) {
      this.problemCategoryAllowUserGroupListPageSize = pageSize
      this.getProblemCategoryAllowUserGroupList(this.problemCategoryId)
    },
    // 获取允许的用户组列表
    getProblemCategoryAllowUserGroupList(problemCategoryId) {
      this.problemCategoryAllowUserGroupListIsLoading = true
      this.handleResponse(getUserGroupDetailByProblemCategoryId(problemCategoryId, this.problemCategoryAllowUserGroupListPageNum, this.problemCategoryAllowUserGroupListPageSize), '获取允许的用户组列表',
        (res) => {
          this.problemCategoryAllowUserGroupListTableConfig.tableData = res.data.records
          this.problemCategoryAllowUserGroupListTableConfig.tableData.forEach((userGroup) => {
            if (this.problemCategoryAllowUserGroupListSelection.indexOf(userGroup.id) !== -1) {
              userGroup._checked = true
            }
          })
          this.problemCategoryAllowUserGroupListTotalItems = res.data.total
        },
        null,
        null,
        () => {
          this.problemCategoryAllowUserGroupListIsLoading = false
        })
    },
    // 获取题目集详情
    getProblemCategoryDetail(problemCategoryId) {
      this.handleResponse(getProblemCategoryDetail(problemCategoryId), '获取题目集详情',
        (res) => {
          this.problemCategoryDetail.id = res.data.id
          this.problemCategoryDetail.name = res.data.name
          this.problemCategoryDetail.viewAfterEnd = res.data.viewAfterEnd
          this.problemCategoryDetail.duration = [new Date(res.data.startTime), new Date(res.data.endTime)]
        })
    },
    // 获取题目集包含的题目列表
    getProblemCollectionList(problemCategoryId) {
      this.problemCollectionListIsLoading = true
      this.handleResponse(getProblemCollectionList(this.problemCollectionListPageNum, this.problemCollectionListPageSize, 'categoryId', problemCategoryId), '获取题目集的题目列表',
        (res) => {
          this.problemCollectionTableConfig.tableData = res.data.records
          this.problemCollectionTableConfig.tableData.forEach((problem) => {
            if (this.problemCollectionProblemListSelection.indexOf(problem.id) !== -1) {
              problem._checked = true
            }
          })
          this.problemCollectionListTotalItems = res.data.total
        },
        null,
        null,
        () => {
          this.problemCollectionListIsLoading = false
        })
    },
    // 打开添加题目窗口
    async openAddFromProblemListDialog() {
      this.addFromProblemListDialogVisible = true
      await this.getProblemIdsByProblemCategoryId(this.problemCategoryId)
      await this.refreshProblemList()
    },
    // 刷新题目列表
    async refreshProblemList() {
      await this.getProblemList()
      for (const problem of this.problemListTableData) {
        if (this.collectionProblemIds.indexOf(problem.problemId) !== -1 || this.selectedProblemIds.indexOf(problem.problemId) !== -1) {
          this.$refs.problemListTable.toggleRowSelection(problem, true)
        }
      }
    },
    // 获取题目列表
    async getProblemList() {
      await this.handleResponse(getProblemList(this.problemListPageNum, this.problemListPageSize), '获取题目列表',
        (res) => {
          this.problemListTableData = res.data.records
          this.problemListTotalItems = res.data.total
        })
    },
    // 获取当前题目集包含的所有题目ID
    async getProblemIdsByProblemCategoryId(problemCategoryId) {
      await this.handleResponse(getProblemIdsByProblemCategoryId(problemCategoryId), '获取题目集包含的全部题目ID',
        (res) => {
          this.collectionProblemIds = res.data
        })
    },
    // 题目集的题目列表全选事件，selection：已选项
    onProblemCollectionListSelectAll(selection) {
      if (selection.length > 0) {
        // 全选
        selection.forEach((problem) => {
          if (this.problemCollectionProblemListSelection.indexOf(problem.id) === -1) {
            this.problemCollectionProblemListSelection.push(problem.id)
          }
        })
      } else {
        // 取消全选
        this.problemCollectionTableConfig.tableData.forEach((problem) => {
          const tempIndex = this.problemCollectionProblemListSelection.indexOf(problem.id)
          if (tempIndex !== -1) {
            this.problemCollectionProblemListSelection.splice(tempIndex, 1)
          }
        })
      }
    },
    // 题目集的题目列表选中某一项事件，selection：已选项；rowData：刚选择的项
    onProblemCollectionListSelectChange(selection, rowData) {
      const selected = selection.length && selection.indexOf(rowData) !== -1
      if (selected) {
        selection.forEach((problem) => {
          if (this.problemCollectionProblemListSelection.indexOf(problem.id) === -1) {
            this.problemCollectionProblemListSelection.push(problem.id)
          }
        })
      } else {
        const rowInSelectedIndex = this.problemCollectionProblemListSelection.indexOf(rowData.id)
        if (rowInSelectedIndex !== -1) {
          this.problemCollectionProblemListSelection.splice(rowInSelectedIndex, 1)
        }
      }
    },
    // 题目列表全选事件
    onProblemListSelectAll(selection) {
      if (selection.length > 0) {
        // 全选
        selection.forEach((problem) => {
          if (this.collectionProblemIds.indexOf(problem.problemId) === -1 && this.selectedProblemIds.indexOf(problem.problemId) === -1) {
            this.selectedProblemIds.push(problem.problemId)
          }
        })
      } else {
        // 取消全选
        this.problemListTableData.forEach((problem) => {
          if (this.collectionProblemIds.indexOf(problem.problemId) === -1) {
            // 只移除不在题目集内的题目的选中状态
            const tempIndex = this.selectedProblemIds.indexOf(problem.problemId)
            if (tempIndex !== -1) {
              this.selectedProblemIds.splice(tempIndex, 1)
            }
          } else {
            // 确保已经在题目集内的题目依旧是选中状态
            this.$refs.problemListTable.toggleRowSelection(problem, true)
          }
        })
      }
    },
    // 从题目集删除选中的题目事件
    onDeleteCollectionProblemSelection() {
      this.$confirm(`是否从题目集中移除所选的${this.problemCollectionProblemListSelection ? this.problemCollectionProblemListSelection.length + '个' : ''}题目?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.problemCollectionProblemListSelection && this.problemCollectionProblemListSelection.length > 0) {
          this.handleResponse(deleteProblemCollectionInBulk(this.problemCollectionProblemListSelection), '删除题目',
            (res) => {
              if (res.data.success && res.data.success.length === 0) {
                this.$message.error('从题目集中移除选中的题目失败，请稍后再试')
              } else if (res.data.fail && res.data.fail.length > 0) {
                this.problemCollectionProblemListSelection.length = 0
                this.problemCollectionListPageNum = 1
                this.$message({
                  message: '成功执行移除操作，但部分题目移除失败：' + res.data.fail,
                  type: 'warning'
                })
              } else {
                this.problemCollectionProblemListSelection.length = 0
                this.problemCollectionListPageNum = 1
                this.$message.success('成功从题目集中移除选中的题目')
              }
              this.getProblemCollectionList(this.problemCategoryId)
            })
        } else {
          this.$message.error('请检查是否选择了要删除的题目')
        }
      })
    },
    // 选择题目添加到题目集对话框确定事件
    onAddProblem() {
      if (this.selectedProblemIds && this.selectedProblemIds.length !== 0) {
        this.handleResponse(insertProblemCollectionInBulk(this.problemCategoryId, this.selectedProblemIds), '添加题目',
          (res) => {
            if (res.data.success && res.data.success.length === 0) {
              this.$message.error('添加题目失败，请稍后再试')
            } else if ((res.data.fail && res.data.fail.length) > 0 || (res.data.duplicated && res.data.duplicated.length) > 0) {
              let messageString = '成功执行添加操作'
              if (res.data.fail.length > 0) {
                messageString += '，部分题目添加失败：' + res.data.fail
              }
              if (res.data.duplicated.length > 0) {
                messageString += '，部分题目已存在题目集中：' + res.data.duplicated
              }
              this.$message({
                message: messageString,
                type: 'warning'
              })
            } else {
              this.$message.success('添加题目成功')
            }
            this.addFromProblemListDialogVisible = false
            this.selectedProblemIds.length = 0
            this.getProblemCollectionList(this.problemCategoryId)
          })
      } else {
        this.$message.error('请选择要添加到题目集中的题目')
      }
    },
    // 题目列表页面切换事件
    onProblemListPageChange(pageNum) {
      this.problemListPageNum = pageNum
      this.refreshProblemList()
    },
    // 题目列表每页数量切换事件
    onProblemListPageSizeChange(pageSize) {
      this.problemListPageSize = pageSize
      this.refreshProblemList()
    },
    // 题目集的题目列表页面切换事件
    onProblemCollectionListPageChange(pageNum) {
      this.problemCollectionListPageNum = pageNum
      this.getProblemCollectionList(this.problemCategoryId)
    },
    // 题目集的题目列表每页数量切换事件
    onProblemCollectionListPageSizeChange(pageSize) {
      this.problemCollectionListPageSize = pageSize
      this.getProblemCollectionList(this.problemCategoryId)
    },
    // 重置已选择的题目
    resetSelectedProblemIds() {
      this.selectedProblemIds.length = 0
      this.refreshProblemList()
    },
    // 保存按钮触发事件
    onSave() {
      this.$refs.problemCategoryDetail.validate(valid => {
        if (valid) {
          const problemCategoryId = this.$route.params.id
          const problemCategory = {
            id: this.problemCategoryDetail.id,
            name: this.problemCategoryDetail.name.trim(),
            startTime: ConvertUtil.convertDateToString(this.problemCategoryDetail.duration[0]),
            endTime: ConvertUtil.convertDateToString(this.problemCategoryDetail.duration[1]),
            viewAfterEnd: this.problemCategoryDetail.viewAfterEnd
          }
          this.handleResponse(updateProblemCategory(problemCategoryId, problemCategory.name, problemCategory.startTime, problemCategory.endTime, problemCategory.viewAfterEnd), '更新题目集',
            (res) => {
              this.$message.success('更新题目集成功')
              this.$router.back(-1)
            })
        } else {
          this.$message.error('请确认所有项目均填写正确')
        }
      })
    },
    // 取消题目集编辑
    onCancel() {
      this.$router.back(-1)
    },
    isPositiveInteger(value) {
      return /^[0-9]*[1-9][0-9]*$/.test(value)
    },
    // 题目分值编辑完成事件
    problemScoreEditDone(newValue, oldValue, rowIndex, rowData, field) {
      if (newValue !== oldValue) {
        if (newValue !== '') {
          if (this.isPositiveInteger(newValue)) {
            this.problemCollectionTableConfig.tableData[rowIndex][field] = parseInt(newValue)
            this.handleResponse(updateProblemScore(rowData.id, parseInt(newValue)), '修改题目分值',
              (res) => {
                this.$message.success(res.message)
              },
              null,
              () => {
                this.problemCollectionTableConfig.tableData[rowIndex][field] = parseInt(oldValue)
              })
          } else {
            this.$message.error('请输入一个正整数')
          }
        } else {
          this.$message.error('请输入题目分值')
        }
      }
    },
    // 题目列表选项框状态变更事件（选择了新的题目 or 取消了某个已选择的题目）
    onProblemListSelect(selection, row) {
      const selected = selection.length && selection.indexOf(row) !== -1
      if (selected) {
        selection.forEach((problem) => {
          if (this.collectionProblemIds.indexOf(problem.problemId) === -1 && this.selectedProblemIds.indexOf(problem.problemId) === -1) {
            this.selectedProblemIds.push(problem.problemId)
          }
        })
      } else {
        const rowInSelectedIndex = this.selectedProblemIds.indexOf(row.problemId)
        if (rowInSelectedIndex !== -1) {
          this.selectedProblemIds.splice(rowInSelectedIndex, 1)
        }
      }
    }
  }
}
</script>

<style scoped>
.bd {
  padding-top: 15px;
  text-align: center;
}

.operation-button {
  float: right;
  padding-bottom: 10px;
}
</style>
