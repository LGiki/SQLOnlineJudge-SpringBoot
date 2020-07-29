import httpRequest from '@/utils/request'
import { baseApiUrlConfig } from '@/url-config'

export function getUserGroupIdsByProblemCategoryId(problemCategoryId) {
  return httpRequest({
    url: `${baseApiUrlConfig.problemCategoryPermissionGetUserGroupIds}/${problemCategoryId}`,
    method: 'get'
  })
}

export function getUserGroupDetailByProblemCategoryId(problemCategoryId, pageNum, pageSize) {
  const params = {
    pageNum: pageNum,
    pageSize: pageSize
  }
  return httpRequest({
    url: `${baseApiUrlConfig.problemCategoryPermissionGetUserGroupDetail}/${problemCategoryId}`,
    method: 'get',
    params: params
  })
}

export function deleteProblemCategoryPermissionInBulk(problemCategoryPermissionIds) {
  return httpRequest({
    url: baseApiUrlConfig.problemCategoryPermissionBulk,
    method: 'delete',
    data: problemCategoryPermissionIds
  })
}

export function insertProblemCategoryPermissionInBulk(problemCategoryId, userGroupIds) {
  return httpRequest({
    url: `${baseApiUrlConfig.problemCategoryPermissionBulk}/${problemCategoryId}`,
    method: 'post',
    data: userGroupIds
  })
}
