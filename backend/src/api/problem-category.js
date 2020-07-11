import httpRequest from '@/utils/request'
import { baseApiUrlConfig } from '@/url-config'

export function getProblemCategoryCount() {
  return httpRequest({
    url: baseApiUrlConfig.problemCategoryCount,
    method: 'get'
  })
}

export function getProblemCategoryList(pageNum, pageSize, searchType, keyword) {
  const params = {
    pageNum: pageNum,
    pageSize: pageSize
  }
  if (searchType && keyword) {
    params[searchType] = keyword
  }
  return httpRequest({
    url: `${baseApiUrlConfig.problemCategoryBase}/`,
    method: 'get',
    params: params
  })
}

export function createProblemCategory(name, startTime, endTime, viewAfterEnd) {
  return httpRequest({
    url: `${baseApiUrlConfig.problemCategoryBase}/`,
    method: 'post',
    data: {
      name: name,
      startTime: startTime,
      endTime: endTime,
      viewAfterEnd: viewAfterEnd
    }
  })
}

export function updateProblemCategory(problemCategoryId, name, startTime, endTime, viewAfterEnd) {
  return httpRequest({
    url: `${baseApiUrlConfig.problemCategoryBase}/${problemCategoryId}`,
    method: 'put',
    data: {
      name: name,
      startTime: startTime,
      endTime: endTime,
      viewAfterEnd: viewAfterEnd
    }
  })
}

export function getProblemCategoryDetail(problemCategoryId) {
  return httpRequest({
    url: `${baseApiUrlConfig.problemCategoryBase}/${problemCategoryId}`,
    method: 'get'
  })
}

export function deleteProblemCategory(problemCategoryId) {
  return httpRequest({
    url: `${baseApiUrlConfig.problemCategoryBase}/${problemCategoryId}`,
    method: 'delete'
  })
}
