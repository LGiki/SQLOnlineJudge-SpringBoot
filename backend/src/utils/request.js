import axios from 'axios'
import { Message } from 'element-ui'
import { baseUrl } from '../url-config'
import router from '../router'

const httpRequest = axios.create({
  baseURL: baseUrl, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

httpRequest.interceptors.request.use(
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

httpRequest.interceptors.response.use(
  response => {
    if (response.status === 403) {
      Message.error('您无权访问当前页面，请登录后重试')
      this.$router.push({ path: '/login' })
    }
    return response
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          Message.error('登录状态异常，请登录后重试')
          localStorage.removeItem('JWT_TOKEN')
          localStorage.removeItem('role')
          router.replace({
            path: '/login'
          })
          location.reload()
      }
    }
    return Promise.reject(error)
  }
)

export default httpRequest
