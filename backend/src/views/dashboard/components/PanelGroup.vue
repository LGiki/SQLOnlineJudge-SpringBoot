<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handlePanelClick('user')">
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="people" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">用户数量</div>
          <count-to
            :start-val="0"
            :end-val="systemDetail.userCount"
            :duration="1500"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handlePanelClick('database')">
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="database" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">数据库数量</div>
          <count-to
            :start-val="0"
            :end-val="systemDetail.databaseCount"
            :duration="1500"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handlePanelClick('problem')">
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="documentation" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">题目数量</div>
          <count-to
            :start-val="0"
            :end-val="systemDetail.problemCount"
            :duration="1500"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handlePanelClick('solution')">
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="guide" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">用户提交数量</div>
          <count-to
            :start-val="0"
            :end-val="systemDetail.solutionCount"
            :duration="1500"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handlePanelClick('problemCategory')">
        <div class="card-panel-icon-wrapper icon-shanjihuang">
          <svg-icon icon-class="tree-table" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">题目集数量</div>
          <count-to
            :start-val="0"
            :end-val="systemDetail.problemCategoryCount"
            :duration="1500"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handlePanelClick('userGroup')">
        <div class="card-panel-icon-wrapper icon-xiekehong">
          <svg-icon icon-class="peoples" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">用户组数量</div>
          <count-to
            :start-val="0"
            :end-val="systemDetail.userGroupCount"
            :duration="1500"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'
import { getProblemCount } from '@/api/problem'
import { getUserCount } from '@/api/user'
import { getDatabaseCount } from '@/api/database'
import { getSolutionCount } from '@/api/solution'
import { getUserGroupCount } from '@/api/user-group'
import { getProblemCategoryCount } from '@/api/problem-category'

export default {
  components: {
    CountTo
  },
  data() {
    return {
      systemDetail: {
        databaseCount: 0,
        problemCount: 0,
        userCount: 0,
        solutionCount: 0,
        problemCategoryCount: 0,
        userGroupCount: 0
      }
    }
  },
  mounted: function() {
    this.getUserCount()
    this.getDatabaseCount()
    this.getProblemCount()
    this.getSolutionCount()
    this.getProblemCategoryCount()
    this.getUserGroupCount()
  },
  methods: {
    handlePanelClick(type) {
      switch (type) {
        case 'user':
          this.$router.push({ path: '/user/index/' })
          break
        case 'problem':
          this.$router.push({ path: '/problem/index/' })
          break
        case 'solution':
          this.$router.push({ path: '/solution/index/' })
          break
        case 'database':
          this.$router.push({ path: '/database/index/' })
          break
        case 'userGroup':
          this.$router.push({ path: '/user-group/index/' })
          break
        case 'problemCategory':
          this.$router.push({ path: '/problem-category/index/' })
          break
        default:
          this.$message.error('您好像点击了一个未知的按钮')
      }
    },
    getUserCount() {
      this.handleResponse(getUserCount(), '获取用户数量', res => {
        this.systemDetail.userCount = res.data
      })
    },
    getUserGroupCount() {
      this.handleResponse(getUserGroupCount(), '获取用户组数量', res => {
        this.systemDetail.userGroupCount = res.data
      })
    },
    getDatabaseCount() {
      this.handleResponse(getDatabaseCount(), '获取数据库数量', res => {
        this.systemDetail.databaseCount = res.data
      })
    },
    getProblemCount() {
      this.handleResponse(getProblemCount(), '获取题目数量', res => {
        this.systemDetail.problemCount = res.data
      })
    },
    getProblemCategoryCount() {
      this.handleResponse(getProblemCategoryCount(), '获取题目集数量', res => {
        this.systemDetail.problemCategoryCount = res.data
      })
    },
    getSolutionCount() {
      this.handleResponse(getSolutionCount(), '获取解答数量', res => {
        this.systemDetail.solutionCount = res.data
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
    border-color: rgba(0, 0, 0, 0.05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3;
      }

      .icon-shanjihuang {
        background: #b78b26;
      }

      .icon-xiekehong {
        background: #f27635;
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3;
    }

    .icon-shanjihuang {
      color: #b78b26;
    }

    .icon-xiekehong {
      color: #f27635;
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width: 550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
