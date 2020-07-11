import httpRequest from '@/utils/request'
import { baseUrl, baseApiUrlConfig } from '@/url-config'

export function uploadImage(formData) {
  return httpRequest({
    url: `${baseUrl}${baseApiUrlConfig.uploadImage}`,
    method: 'post',
    data: formData
  })
}
