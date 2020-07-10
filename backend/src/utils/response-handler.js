import { Message } from 'element-ui'

export function handleResponse(AxiosPromise, operationString, successCallback, failResponseCallback, failCallback, finallyCallback) {
  return AxiosPromise.then(res => {
    if (res.status !== 200) {
      Message.error(`${operationString}失败：远程服务器错误`)
    } else {
      const resData = res.data
      if (resData.code === 0) {
        if (successCallback) {
          successCallback(resData)
        }
      } else {
        Message.error(`${operationString}失败：${resData.message}`)
      }
    }
  }).catch(err => {
    if (err.response) {
      if (failResponseCallback) {
        failResponseCallback(err.response)
      } else {
        Message.error(`${operationString}失败：${err.response.data.message}`)
      }
    } else if (err.request) {
      Message.error(`${operationString}失败：远程服务器未响应请求`)
    } else {
      Message.error(`${operationString}失败：发送请求失败`)
    }
    if (failCallback) {
      failCallback(err)
    }
  }).finally(finallyCallback)
}
