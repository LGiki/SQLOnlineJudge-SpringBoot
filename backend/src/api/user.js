import httpRequest from '../utils/request'
import { baseApiUrlConfig } from '../url-config'

export function getUserCount() {
  return httpRequest({
    url: baseApiUrlConfig.userCount,
    method: 'get'
  })
}

export function getUserList(pageNum, pageSize, searchType, keyword) {
  const params = {
    pageNum: pageNum,
    pageSize: pageSize
  }
  if (searchType && keyword) {
    params[searchType] = keyword
  }
  return httpRequest({
    url: `${baseApiUrlConfig.userBase}/`,
    method: 'get',
    params: params
  })
}

export function createUser(username, password, email, studentNo) {
  return httpRequest({
    url: `${baseApiUrlConfig.userBase}/`,
    method: 'post',
    data: {
      username: username,
      password: password,
      email: email,
      studentNo: studentNo
    }
  })
}

export function updateUser(userId, username, password, email, studentNo) {
  return httpRequest({
    url: `${baseApiUrlConfig.userBase}/${userId}`,
    method: 'put',
    data: {
      username: username,
      password: password,
      email: email,
      studentNo: studentNo
    }
  })
}

export function getUserDetail(userId) {
  return httpRequest({
    url: `${baseApiUrlConfig.userBase}/${userId}`,
    method: 'get'
  })
}

export function deleteUser(userId) {
  return httpRequest({
    url: `${baseApiUrlConfig.userBase}/${userId}`,
    method: 'delete'
  })
}
