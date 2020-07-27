import httpRequest from '@/utils/request'
import { baseApiUrlConfig } from '@/url-config'

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

export function deleteProblemCollectionInBulk(problemCollectionIds) {
  return httpRequest({
    url: baseApiUrlConfig.problemCollectionBulk,
    method: 'delete',
    data: problemCollectionIds
  })
}

export function insertProblemCollectionInBulk(problemCategoryId, problemIds) {
  return httpRequest({
    url: `${baseApiUrlConfig.problemCollectionBulk}/${problemCategoryId}`,
    method: 'post',
    data: problemIds
  })
}
