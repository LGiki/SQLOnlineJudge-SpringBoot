<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handlePanelClick('user')">
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="peoples" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">用户数量</div>
          <count-to
            :start-val="0"
            :end-val="systemDetail.userCount"
            :duration="1600"
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
            :duration="2000"
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
            :duration="2200"
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
            :duration="2600"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'

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
        solutionCount: 0
      }
    }
  },
  mounted: function() {
    this.getUserCount()
    this.getDatabaseCount()
    this.getProblemCount()
    this.getSolutionCount()
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
      }
    },
    getUserCount() {
      const apiUrl = this.Url.userCount
      this.$axios
        .get(apiUrl)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取用户数量失败，网络错误！')
          } else {
            const resData = res.data
            if (resData.code === 200) {
              this.systemDetail.userCount = resData.data
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    getDatabaseCount() {
      const apiUrl = this.Url.databaseCount
      this.$axios
        .get(apiUrl)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取数据库数量失败，网络错误！')
          } else {
            const resData = res.data
            if (resData.code === 200) {
              this.systemDetail.databaseCount = resData.data
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    getProblemCount() {
      const apiUrl = this.Url.problemCount
      this.$axios
        .get(apiUrl)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取题目数量失败，网络错误！')
          } else {
            const resData = res.data
            if (resData.code === 200) {
              this.systemDetail.problemCount = resData.data
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    getSolutionCount() {
      const apiUrl = this.Url.solutionCount
      this.$axios
        .get(apiUrl)
        .then(res => {
          if (res.status !== 200) {
            this.$message.error('获取用户提交数量失败，网络错误！')
          } else {
            const resData = res.data
            if (resData.code === 200) {
              this.systemDetail.solutionCount = resData.data
            }
          }
        })
        .catch(err => {
          console.log(err)
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
