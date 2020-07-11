import httpRequest from '@/utils/request'
import { baseApiUrlConfig } from '@/url-config'

export function runCode(databaseId, code) {
  return httpRequest({
    url: `${baseApiUrlConfig.runCode}/${databaseId}`,
    method: 'post',
    data: {
      sourceCode: code
    }
  })
}
