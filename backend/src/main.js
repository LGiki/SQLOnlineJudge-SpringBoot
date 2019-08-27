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
import '@/permission' // permission control

import VueHighlightJS from 'vue-highlight.js'

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */
import { mockXHR } from '../mock'
import Url from './urlConfig'
import Axios from 'axios'

if (process.env.NODE_ENV === 'production') {
  mockXHR()
}

Axios.interceptors.request.use(
  config => {
    // if (localStorage.JWT_TOKEN) {  // 判断是否存在token，如果存在的话，则每个http header都加上token
    //   config.headers.Authorization = `token ${localStorage.JWT_TOKEN}`;
    // }
    config.headers.Authorization = 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImxvZ2luVHlwZSI6IkFkbWluIiwiZXhwIjoxNTY2OTQyNjMwLCJpYXQiOjE1NjY4NzA2MzB9.0CONq-yrmnfN1MENNPvismxO8X2Ms1mUwbW8FJ4Yke49u8A_4SpxGKL5xnvwWT_4C80YSTwrz80vKGJ1SRks8Q'
    return config
  },
  err => {
    return Promise.reject(err)
  }
)

// set ElementUI lang to EN
Vue.use(ElementUI, { locale })
Vue.use(VueHighlightJS)
Vue.prototype.Url = Url
Vue.prototype.$axios = Axios

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

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
