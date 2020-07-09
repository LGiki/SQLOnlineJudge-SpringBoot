import httpRequest from '../utils/request'
import { baseApiUrlConfig, specialApiUrlConfig } from '../url-config'

export function getSolutionCount() {
  return httpRequest({
    url: baseApiUrlConfig.solutionCount,
    method: 'get'
  })
}
