import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon

import VueHighlightJS from 'vue-highlight.js'
import { handleResponse } from '@/utils/response-handler'
/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */
// import { mockXHR } from '../mock'
// if (process.env.NODE_ENV === 'production') {
//   mockXHR()
// }

// set ElementUI locale
Vue.use(ElementUI, { locale })
Vue.use(VueHighlightJS)
Vue.prototype.handleResponse = handleResponse
Vue.prototype.constructSQLExpression = function(tableName, tableFieldList, tableValues) {
  let sqlExpression = 'INSERT INTO `' + tableName + '` ('
  for (let i = 0; i < tableFieldList.length; i++) {
    sqlExpression += '`' + tableFieldList[i] + '`'
    if (i !== tableFieldList.length - 1) {
      sqlExpression += ', '
    }
  }
  sqlExpression += ') VALUES ('
  for (let i = 0; i < tableValues.length; i++) {
    sqlExpression += '\'' + tableValues[i] + '\''
    if (i !== tableValues.length - 1) {
      sqlExpression += ', '
    }
  }
  sqlExpression += ');'
  return sqlExpression
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
      const params = { type: 'edit', index: this.index, rowData: this.rowData }
      this.$emit('on-custom-comp', params)
    },

    deleteRow() {
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
      const params = { type: 'edit', index: this.index, rowData: this.rowData }
      this.$emit('on-custom-comp', params)
    },

    deleteRow() {
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
