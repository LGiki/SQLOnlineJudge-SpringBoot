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

import CommonOperationButton from '@/components/OperationButton/CommonOperationButton'
import CommonOperationButtonWithSolution from '@/components/OperationButton/CommonOperationButtonWithSolution'
import UserOperationButton from '@/components/OperationButton/UserOperationButton'
import UserOperationButtonWithSolution from '@/components/OperationButton/UserOperationButtonWithSolution'
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

Vue.component('common-operation-button', CommonOperationButton)
Vue.component('common-operation-button-with-solution', CommonOperationButtonWithSolution)
Vue.component('user-operation-button', UserOperationButton)
Vue.component('user-operation-button-with-solution', UserOperationButtonWithSolution)

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
