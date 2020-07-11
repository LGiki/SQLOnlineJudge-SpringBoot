import httpRequest from '@/utils/request'
import { baseApiUrlConfig } from '@/url-config'

export function getAdminList(pageNum, pageSize, searchType, keyword) {
  const params = {
    pageNum: pageNum,
    pageSize: pageSize
  }
  if (searchType && keyword) {
    params[searchType] = keyword
  }
  return httpRequest({
    url: `${baseApiUrlConfig.adminBase}/`,
    method: 'get',
    params: params
  })
}

export function createAdmin(username, password) {
  return httpRequest({
    url: `${baseApiUrlConfig.adminBase}/`,
    method: 'post',
    data: {
      username: username,
      password: password
    }
  })
}

export function updateAdmin(adminId, password) {
  return httpRequest({
    url: `${baseApiUrlConfig.adminBase}/${adminId}`,
    method: 'put',
    data: `password=${password}`
  })
}

export function getAdminDetail(adminId) {
  return httpRequest({
    url: `${baseApiUrlConfig.adminBase}/${adminId}`,
    method: 'get'
  })
}

export function deleteAdmin(adminId) {
  return httpRequest({
    url: `${baseApiUrlConfig.adminBase}/${adminId}`,
    method: 'delete'
  })
}
