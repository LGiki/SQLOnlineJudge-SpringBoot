<template>
  <div class="app-container">
    <el-form ref="userGroupDetail" :model="userGroupDetail" :rules="checkRules" label-width="120px">
      <el-form-item label="用户组名称" prop="name">
        <el-input v-model="userGroupDetail.name" placeholder="请输入用户组名称" />
      </el-form-item>
      <el-form-item label="用户组的用户列表">
        <div class="operation-button">
          <el-button type="primary" @click="openAddFromUserListDialog">
            <i class="el-icon-plus" />&nbsp;添加用户
          </el-button>
          <el-button
            v-if="userCollectionUserListSelection && userCollectionUserListSelection.length > 0"
            type="danger"
            @click="onDeleteUserCollectionSelection"
          >
            <i class="el-icon-delete" />&nbsp;移除所选的{{ userCollectionUserListSelection ? userCollectionUserListSelection.length + '个' : '' }}用户
          </el-button>
        </div>
        <el-table
          ref="userCollectionUserListTable"
          :data="userCollectionUserListTableData"
          tooltip-effect="dark"
          style="width: 100%"
          @select="onUserCollectionUserListSelect"
          @select-all="onUserCollectionUserListSelectAll"
        >
          <el-table-column
            type="selection"
            align="center"
            width="50"
          />
          <el-table-column
            prop="id"
            label="用户ID"
            align="center"
            width="100"
          />
          <el-table-column
            prop="username"
            label="用户名"
            align="center"
            min-width="3"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            prop="studentNo"
            label="学号"
            min-width="3"
            align="center"
          />
        </el-table>
        <div class="bd">
          <el-pagination
            background
            :current-page="userCollectionUserListPageNum"
            :page-sizes="[10, 20, 30]"
            :page-size="userCollectionUserListPageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="userCollectionUserListTotalItems"
            @size-change="onUserCollectionUserListPageSizeChange"
            @current-change="onUserCollectionUserListPageChange"
          />
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
        <el-button @click="$router.back(-1)">取消</el-button>
      </el-form-item>
      <el-dialog
        title="选择用户添加到用户组"
        :visible.sync="addFromUserListDialogVisible"
        width="80%"
      >
        <el-table
          ref="userListTable"
          :data="userListTableData"
          tooltip-effect="dark"
          style="width: 100%"
          @select="onUserListSelect"
          @select-all="onUserListSelectAll"
        >
          <el-table-column
            type="selection"
            align="center"
            width="50"
          />
          <el-table-column
            prop="id"
            label="用户ID"
            align="center"
            width="100"
          />
          <el-table-column
            prop="username"
            label="用户名"
            align="center"
            min-width="3"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            prop="studentNo"
            label="学号"
            min-width="3"
            align="center"
          />
        </el-table>
        <div class="bd">
          <el-pagination
            background
            :current-page="userListPageNum"
            :page-sizes="[10, 20, 30]"
            :page-size="userListPageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="userListTotalItems"
            @size-change="onUserListPageSizeChange"
            @current-change="onUserListPageChange"
          />
        </div>
        <span slot="footer" class="dialog-footer">
          <template v-if="selectedUserIds && selectedUserIds.length !== 0"><p>将新增 <strong>{{ selectedUserIds.length }}</strong> 个用户到用户组中</p></template>
          <el-button
            v-if="selectedUserIds && selectedUserIds.length !== 0"
            type="warning"
            @click="resetSelectedUserIds"
          >重 置</el-button>
          <el-button
            type="danger"
            @click="addFromUserListDialogVisible = false"
          >关 闭</el-button>
          <el-button
            type="primary"
            @click="onAddUser"
          >确 定</el-button>
        </span>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
import { getUserGroupDetail, updateUserGroup } from '@/api/user-group'
import { getUserCollectionUserList, getUserIdsByUserGroupId, insertUserGroupCollectionInBulk, deleteUserGroupCollectionInBulk } from '@/api/user-collection'
import { getUserList } from '@/api/user'

export default {
  data() {
    return {
      checkRules: {
        name: [
          {
            required: true,
            message: '用户组名称不能为空',
            trigger: 'blur'
          }
        ]
      },
      userGroupDetail: {
        name: ''
      },
      userListTableData: [],
      userListTotalItems: 0,
      userListPageSize: 10,
      userListPageNum: 1,
      userListIsLoading: false,
      collectionUserIds: [],
      selectedUserIds: [],
      addFromUserListDialogVisible: false,
      userCollectionUserListSelection: [],
      userCollectionUserListPageNum: 1,
      userCollectionUserListPageSize: 20,
      userCollectionUserListTotalItems: 0,
      userCollectionUserListTableData: [],
    }
  },
  computed: {
    userGroupId() {
      return this.$route.params.id
    }
  },
  mounted: function() {
    this.getUserGroupDetail(this.userGroupId)
    this.getUserCollectionUserList(this.userGroupId)
  },
  methods: {
    onUserCollectionUserListSelect(selection, row) {
      const selected = selection.length && selection.indexOf(row) !== -1
      if (selected) {
        selection.forEach((user) => {
          if (this.userCollectionUserListSelection.indexOf(user.id) === -1) {
            this.userCollectionUserListSelection.push(user.id)
          }
        })
      } else {
        const rowInSelectedIndex = this.userCollectionUserListSelection.indexOf(row.id)
        if (rowInSelectedIndex !== -1) {
          this.userCollectionUserListSelection.splice(rowInSelectedIndex, 1)
        }
      }
    },
    onUserCollectionUserListSelectAll(selection) {
      if (selection.length > 0) {
        selection.forEach((user) => {
          if (this.userCollectionUserListSelection.indexOf(user.id) === -1) {
            this.userCollectionUserListSelection.push(user.id)
          }
        })
      } else {
        this.userCollectionUserListTableData.forEach((user) => {
          const tempIndex = this.userCollectionUserListSelection.indexOf(user.id)
          if (tempIndex !== -1) {
            this.selectedUserIds.splice(tempIndex, 1)
          }
        })
      }
    },
    async getUserIdsByUserGroupId(userGroupId) {
      await this.handleResponse(getUserIdsByUserGroupId(userGroupId), '获取用户组包含的全部用户ID',
        (res) => {
          this.collectionUserIds = res.data
        })
    },
    // 用户列表全选事件
    onUserListSelectAll(selection) {
      if (selection.length > 0) {
        // 全选
        selection.forEach((user) => {
          if (this.collectionUserIds.indexOf(user.id) === -1 && this.selectedUserIds.indexOf(user.id) === -1) {
            this.selectedUserIds.push(user.id)
          }
        })
      } else {
        // 取消全选
        this.userListTableData.forEach((user) => {
          if (this.collectionUserIds.indexOf(user.id) === -1) {
            // 只移除不在用户组内的用户的选中状态
            const tempIndex = this.selectedUserIds.indexOf(user.id)
            if (tempIndex !== -1) {
              this.selectedUserIds.splice(tempIndex, 1)
            }
          } else {
            // 确保已经在用户组内的用户依旧是选中状态
            this.$refs.userListTable.toggleRowSelection(user, true)
          }
        })
      }
    },
    // 用户列表选项框状态变更事件（选择了新的用户 or 取消了某个已选择的用户）
    onUserListSelect(selection, row) {
      const selected = selection.length && selection.indexOf(row) !== -1
      if (selected) {
        selection.forEach((user) => {
          if (this.collectionUserIds.indexOf(user.id) === -1 && this.selectedUserIds.indexOf(user.id) === -1) {
            this.selectedUserIds.push(user.id)
          }
        })
      } else {
        const rowInSelectedIndex = this.selectedUserIds.indexOf(row.id)
        if (rowInSelectedIndex !== -1) {
          this.selectedUserIds.splice(rowInSelectedIndex, 1)
        }
      }
    },
    onUserListPageSizeChange(pageSize) {
      this.userListPageSize = pageSize
      this.refreshUserList()
    },
    onUserListPageChange(pageNum) {
      this.userListPageNum = pageNum
      this.refreshUserList()
    },
    resetSelectedUserIds() {
      this.selectedUserIds.length = 0
      this.refreshUserList()
    },
    onAddUser() {
      if (this.selectedUserIds && this.selectedUserIds.length !== 0) {
        this.handleResponse(insertUserGroupCollectionInBulk(this.userGroupId, this.selectedUserIds), '添加用户',
          (res) => {
            if (res.data.success && res.data.success.length === 0) {
              this.$message.error('添加用户失败，请稍后再试')
            } else if ((res.data.fail && res.data.fail.length) > 0 || (res.data.duplicated && res.data.duplicated.length) > 0) {
              let messageString = '成功执行添加操作'
              if (res.data.fail.length > 0) {
                messageString += '，部分用户添加失败：' + res.data.fail
              }
              if (res.data.duplicated.length > 0) {
                messageString += '，部分用户已存在于用户组中：' + res.data.duplicated
              }
              this.$message({
                message: messageString,
                type: 'warning'
              })
            } else {
              this.$message.success('添加用户成功')
            }
            this.addFromUserListDialogVisible = false
            this.selectedUserIds.length = 0
            this.getUserCollectionUserList(this.userGroupId)
          })
      } else {
        this.$message.error('请选择要添加到用户组的用户')
      }
    },
    async getUserList() {
      this.userListIsLoading = true
      await this.handleResponse(getUserList(this.userListPageNum, this.userListPageSize), '获取用户列表',
        (res) => {
          this.userListTableData = res.data.records
          this.userListTotalItems = res.data.total
        },
        null,
        null,
        () => {
          this.userListIsLoading = false
        })
    },
    async refreshUserList() {
      await this.getUserList()
      for (const user of this.userListTableData) {
        if (this.collectionUserIds.indexOf(user.id) !== -1 || this.selectedUserIds.indexOf(user.id) !== -1) {
          this.$refs.userListTable.toggleRowSelection(user, true)
        }
      }
    },
    // 打开从用户列表添加用户的Dialog
    async openAddFromUserListDialog() {
      this.addFromUserListDialogVisible = true
      await this.getUserIdsByUserGroupId(this.userGroupId)
      await this.refreshUserList()
    },
    // 从用户组中移除所选中的用户
    onDeleteUserCollectionSelection() {
      this.$confirm(`是否从用户组中移除所选的${this.userCollectionUserListSelection ? this.userCollectionUserListSelection.length + '个' : ''}用户?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.userCollectionUserListSelection && this.userCollectionUserListSelection.length > 0) {
          this.handleResponse(deleteUserGroupCollectionInBulk(this.userCollectionUserListSelection), '从用户组中移除选中的用户',
            (res) => {
              if (res.data.success && res.data.success.length === 0) {
                this.$message.error('从用户组中移除选中的用户失败，请稍后再试')
              } else if (res.data.fail && res.data.fail.length > 0) {
                this.userCollectionUserListSelection.length = 0
                this.userCollectionUserListPageNum = 1
                this.$message({
                  message: '成功执行移除操作，但部分用户移除失败：' + res.data.fail,
                  type: 'warning'
                })
              } else {
                this.userCollectionUserListSelection.length = 0
                this.userCollectionUserListPageNum = 1
                this.$message.success('成功从用户组中移除选中的用户')
              }
              this.getUserCollectionUserList(this.userGroupId)
            })
        } else {
          this.$message.error('请检查是否选择了要移除的用户')
        }
      })
    },
    onUserCollectionUserListPageChange(pageNum) {
      this.userCollectionUserListPageNum = pageNum
      this.getUserCollectionUserList(this.userGroupId)
    },
    onUserCollectionUserListPageSizeChange(pageSize) {
      this.userCollectionUserListPageSize = pageSize
      this.getUserCollectionUserList(this.userGroupId)
    },
    getUserCollectionUserList(userGroupId) {
      this.handleResponse(getUserCollectionUserList(this.userCollectionUserListPageNum, this.userCollectionUserListPageSize, 'userGroupId', userGroupId), '获取用户组的用户列表',
        (res) => {
          this.userCollectionUserListTableData = res.data.records
          this.userCollectionUserListTotalItems = res.data.total
        },
        null,
        null,
        () => {
          this.userCollectionUserListTableData.forEach((user) => {
            if (this.userCollectionUserListSelection.indexOf(user.id) !== -1) {
              this.$refs.userCollectionUserListTable.toggleRowSelection(user, true)
            }
          })
        })
    },
    onSubmit() {
      this.$refs.userGroupDetail.validate(valid => {
        if (valid) {
          this.handleResponse(updateUserGroup(this.userGroupId, this.userGroupDetail.name.trim()), '更新用户组',
            (res) => {
              this.$message.success('更新用户组成功')
              this.$router.back(-1)
            })
        } else {
          this.$message.error('请确认所有项目均填写正确！')
        }
      })
    },
    getUserGroupDetail(userGroupId) {
      this.handleResponse(getUserGroupDetail(userGroupId), '获取用户组详情',
        (res) => {
          this.userGroupDetail = res.data
        })
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

