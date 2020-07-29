import httpRequest from '@/utils/request'
import { baseApiUrlConfig } from '@/url-config'

export function getUserCollectionUserList(pageNum, pageSize, searchType, keyword) {
  const params = {
    pageNum: pageNum,
    pageSize: pageSize
  }
  if (searchType && keyword) {
    params[searchType] = keyword
  }
  return httpRequest({
    url: `${baseApiUrlConfig.userCollectionBase}/`,
    method: 'get',
    params: params
  })
}

export function getUserIdsByUserGroupId(userGroupId) {
  return httpRequest({
    url: `${baseApiUrlConfig.userCollectionUserIds}/${userGroupId}`,
    method: 'get'
  })
}

export function insertUserGroupCollectionInBulk(userGroupId, userIds) {
  return httpRequest({
    url: `${baseApiUrlConfig.userCollectionBulk}/${userGroupId}`,
    method: 'post',
    data: userIds
  })
}

export function deleteUserGroupCollectionInBulk(userGroupCollectionIds) {
  return httpRequest({
    url: baseApiUrlConfig.userCollectionBulk,
    method: 'delete',
    data: userGroupCollectionIds
  })
}
