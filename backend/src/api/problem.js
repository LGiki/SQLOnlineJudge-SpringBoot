import request from '@/utils/request'

export function getProblemList(params) {
  return request({
    url: '/api/',
    method: 'get',
    params
  })
}
