import httpRequest from '@/utils/request'
import { baseApiUrlConfig } from '@/url-config'

export function getDatabaseCount() {
  return httpRequest({
    url: baseApiUrlConfig.databaseCount,
    method: 'get'
  })
}

export function getDatabaseList(pageNum, pageSize, searchType, keyword) {
  const params = {
    pageNum: pageNum,
    pageSize: pageSize
  }
  if (searchType && keyword) {
    params[searchType] = keyword
  }
  return httpRequest({
    url: `${baseApiUrlConfig.databaseBase}/`,
    method: 'get',
    params: params
  })
}

export function getAllDatabase() {
  return httpRequest({
    url: baseApiUrlConfig.getAllDatabase,
    method: 'get'
  })
}

export function createDatabase(name, createTable, testData) {
  return httpRequest({
    url: `${baseApiUrlConfig.databaseBase}/`,
    method: 'post',
    data: {
      name: name,
      createTable: createTable,
      testData: testData
    }
  })
}

export function updateDatabase(databaseId, name, createTable, testData) {
  return httpRequest({
    url: `${baseApiUrlConfig.databaseBase}/${databaseId}`,
    method: 'put',
    data: {
      name: name,
      createTable: createTable,
      testData: testData
    }
  })
}

export function getDatabaseDetail(databaseId) {
  return httpRequest({
    url: `${baseApiUrlConfig.databaseBase}/${databaseId}`,
    method: 'get'
  })
}

export function deleteDatabase(databaseId) {
  return httpRequest({
    url: `${baseApiUrlConfig.databaseBase}/${databaseId}`,
    method: 'delete'
  })
}
