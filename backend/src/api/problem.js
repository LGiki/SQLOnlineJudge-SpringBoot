import request from '@/utils/request'
import Url from '../urlConfig'

export function getProblemList(params) {
  return request({
    url: Url.problemList,
    method: 'get',
    params
  })
}
