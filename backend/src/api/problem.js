import httpRequest from '@/utils/request'
import { baseApiUrlConfig } from '@/url-config'

export function getProblemCount() {
  return httpRequest({
    url: baseApiUrlConfig.problemCount,
    method: 'get'
  })
}

export function getProblemList(pageNum, pageSize, searchType, keyword) {
  const params = {
    pageNum: pageNum,
    pageSize: pageSize
  }
  if (searchType && keyword) {
    params[searchType] = keyword
  }
  return httpRequest({
    url: `${baseApiUrlConfig.problemBase}/`,
    method: 'get',
    params: params
  })
}

export function getProblemDetail(problemId) {
  return httpRequest({
    url: `${baseApiUrlConfig.problemBase}/${problemId}`,
    method: 'get'
  })
}

export function createProblem(title, description, sampleOutput, hint, answer, databaseId, difficulty, isUpdate, selectAfterUpdate) {
  return httpRequest({
    url: `${baseApiUrlConfig.problemBase}/`,
    method: 'post',
    data: {
      title: title,
      description: description,
      sampleOutput: sampleOutput,
      hint: hint,
      answer: answer,
      databaseId: databaseId,
      difficulty: difficulty,
      isUpdate: isUpdate,
      selectAfterUpdate: selectAfterUpdate
    }
  })
}

export function updateProblem(problemId, title, description, sampleOutput, hint, answer, databaseId, difficulty, isUpdate, selectAfterUpdate) {
  return httpRequest({
    url: `${baseApiUrlConfig.problemBase}/${problemId}`,
    method: 'put',
    data: {
      title: title,
      description: description,
      sampleOutput: sampleOutput,
      hint: hint,
      answer: answer,
      databaseId: databaseId,
      difficulty: difficulty,
      isUpdate: isUpdate,
      selectAfterUpdate: selectAfterUpdate
    }
  })
}

export function deleteProblem(problemId) {
  return httpRequest({
    url: `${baseApiUrlConfig.problemBase}/${problemId}`,
    method: 'delete'
  })
}
