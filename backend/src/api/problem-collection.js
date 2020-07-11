import httpRequest from '@/utils/request'
import { baseApiUrlConfig } from '@/url-config'

export function getProblemCollectionCount() {
  return httpRequest({
    url: baseApiUrlConfig.problemCollectionBase,
    method: 'get'
  })
}

export function getProblemCollectionList(pageNum, pageSize, searchType, keyword) {
  const params = {
    pageNum: pageNum,
    pageSize: pageSize
  }
  if (searchType && keyword) {
    params[searchType] = keyword
  }
  return httpRequest({
    url: `${baseApiUrlConfig.problemCollectionBase}/`,
    method: 'get',
    params: params
  })
}

export function updateProblemScore(problemCollectionId, newScore) {
  return httpRequest({
    url: `${baseApiUrlConfig.problemCollectionUpdateProblemScore}/${problemCollectionId}`,
    method: 'put',
    data: {
      newProblemScore: newScore
    }
  })
}

export function getProblemIdsByProblemCategoryId(problemCategoryId) {
  return httpRequest({
    url: `${baseApiUrlConfig.problemCollectionProblemIds}/${problemCategoryId}`,
    method: 'get'
  })
}

export function deleteProblemInBulk(problemCollectionIds) {
  return httpRequest({
    url: baseApiUrlConfig.problemCollectionBulk,
    method: 'delete',
    data: problemCollectionIds
  })
}

export function insertProblemInBulk(problemCategoryId, problemIds) {
  return httpRequest({
    url: `${baseApiUrlConfig.problemCollectionBulk}/${problemCategoryId}`,
    method: 'post',
    data: problemIds
  })
}
