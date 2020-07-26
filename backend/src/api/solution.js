import httpRequest from '@/utils/request'
import { baseApiUrlConfig } from '@/url-config'

export function getSolutionCount() {
  return httpRequest({
    url: baseApiUrlConfig.solutionCount,
    method: 'get'
  })
}

export function getSolutionList(pageNum, pageSize, searchType, keyword) {
  const params = {
    pageNum: pageNum,
    pageSize: pageSize
  }
  if (searchType && keyword) {
    params[searchType] = keyword
  }
  return httpRequest({
    url: `${baseApiUrlConfig.solutionBase}/`,
    method: 'get',
    params: params
  })
}

export function getSolutionDetail(solutionId) {
  return httpRequest({
    url: `${baseApiUrlConfig.solutionBase}/${solutionId}`,
    method: 'get'
  })
}
