import httpRequest from '@/utils/request'
import { baseApiUrlConfig } from '@/url-config'

export function getUserGroupCount() {
  return httpRequest({
    url: baseApiUrlConfig.userGroupCount,
    method: 'get'
  })
}

export function getUserGroupList(pageNum, pageSize, searchType, keyword) {
  const params = {
    pageNum: pageNum,
    pageSize: pageSize
  }
  if (searchType && keyword) {
    params[searchType] = keyword
  }
  return httpRequest({
    url: `${baseApiUrlConfig.userGroupBase}/`,
    method: 'get',
    params: params
  })
}

export function createUserGroup(name, description) {
  return httpRequest({
    url: `${baseApiUrlConfig.userGroupBase}/`,
    method: 'post',
    data: {
      name: name,
      description: description
    }
  })
}

export function updateUserGroup(userGroupId, name, description) {
  return httpRequest({
    url: `${baseApiUrlConfig.userGroupBase}/${userGroupId}`,
    method: 'put',
    data: {
      name: name,
      description: description
    }
  })
}

export function getUserGroupDetail(userGroupId) {
  return httpRequest({
    url: `${baseApiUrlConfig.userGroupBase}/${userGroupId}`,
    method: 'get'
  })
}

export function deleteUserGroup(userGroupId) {
  return httpRequest({
    url: `${baseApiUrlConfig.userGroupBase}/${userGroupId}`,
    method: 'delete'
  })
}
