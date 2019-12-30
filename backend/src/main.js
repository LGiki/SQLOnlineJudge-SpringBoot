import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon

import VueHighlightJS from 'vue-highlight.js'

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */
// import { mockXHR } from '../mock'
import Url from './urlConfig'
import Axios from 'axios'
// if (process.env.NODE_ENV === 'production') {
//   mockXHR()
// }

Axios.interceptors.request.use(
  config => {
    if (localStorage.JWT_TOKEN) { // 判断是否存在token，如果存在的话，则每个http header都加上token
      config.headers.Authorization = `Bearer ${localStorage.JWT_TOKEN}`
    }
    return config
  },
  err => {
    return Promise.reject(err)
  }
)

Axios.interceptors.response.use(
  response => {
    if (response.status === 403) {
      this.$router.push({ path: '/login' })
    }
    return response
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          localStorage.removeItem('JWT_TOKEN')
          localStorage.removeItem('role')
          router.replace({
            path: '/login'
          })
          location.reload()
      }
    }
    return Promise.reject(error) // 返回接口返回的错误信息
  }
)

// set ElementUI lang to EN
Vue.use(ElementUI, { locale })
Vue.use(VueHighlightJS)
Vue.prototype.Url = Url
Vue.prototype.$axios = Axios
Vue.prototype.constructSQLExpression = function(tableName, tableFieldList, tableValues) {
  let sqlExpression = 'INSERT INTO `' + tableName + '` (';
        for (let i = 0; i < tableFieldList.length; i++) {
            sqlExpression += '`' + tableFieldList[i] + '`';
            if (i !== tableFieldList.length - 1) {
                sqlExpression += ', ';
            }
        }
        sqlExpression += ') VALUES (';
        for (let i = 0; i < tableValues.length; i++) {
            sqlExpression += '\'' + tableValues[i] + '\'';
            if (i !== tableValues.length - 1) {
                sqlExpression += ', ';
            }
        }
        sqlExpression += ');';
        return sqlExpression;
}
router.beforeEach((to, from, next) => {
  if (!localStorage.JWT_TOKEN && to.path !== '/login') {
    return next('/login')
  }
  next()
})

Vue.component('table-operation', {
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
      const params = { type: 'edit', index: this.index, rowData: this.rowData }
      this.$emit('on-custom-comp', params)
    },

    deleteRow() {
      // 参数根据业务场景随意构造
      const params = { type: 'delete', index: this.index }
      this.$emit('on-custom-comp', params)
    }
  },
  template: `<span>
        <a href="" @click.stop.prevent="update(rowData,index)"><svg-icon icon-class="edit" /></a>&nbsp;
        <a href="" @click.stop.prevent="deleteRow(rowData,index)"><i class="el-icon-delete" /></a>
        </span>`
})

Vue.component('user-operation', {
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
      const params = { type: 'edit', index: this.index, rowData: this.rowData }
      this.$emit('on-custom-comp', params)
    },

    deleteRow() {
      // 参数根据业务场景随意构造
      const params = { type: 'delete', index: this.index }
      this.$emit('on-custom-comp', params)
    }
  },
  template: `<span>
        <a href="" @click.stop.prevent="update(rowData,index)"><svg-icon icon-class="edit" /></a>&nbsp;
        <a href="" @click.stop.prevent="deleteRow(rowData,index)"><svg-icon icon-class="lock" /></a>
        </span>`
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
