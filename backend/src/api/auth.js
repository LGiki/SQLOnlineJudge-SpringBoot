import httpRequest from '@/utils/request'
import { baseApiUrlConfig } from '@/url-config'
import qs from 'qs'

export function login(username, password) {
  return httpRequest({
    url: baseApiUrlConfig.adminLogin,
    method: 'post',
    data: qs.stringify({
      username: username,
      password: password
    })
  })
}
