const BaseUrl = 'http://127.0.0.1:8080/api/'
const Url = {
  // 登录接口
  login: BaseUrl + 'login',
  // 题目基础接口
  problemBaseUrl: BaseUrl + 'problem/',
  // 题目搜索接口
  problemSearch: BaseUrl + 'problem/search/',
  // 题目数量接口
  problemCount: BaseUrl + 'problem/count/',
  // 排名接口
  rankList: BaseUrl + 'user/ranklist',
  // 提交基础接口
  solutionBaseUrl: BaseUrl + 'solution/',
  // 提交搜索接口
  solutionSearch: BaseUrl + 'solution/search/',
  // 提交数量接口
  solutionCount: BaseUrl + 'solution/count/',
  // 数据库基础接口
  databaseBaseUrl: BaseUrl + 'database/',
  // 数据库搜索接口
  databaseSearch: BaseUrl + 'database/search/',
  // 数据库数量接口
  databaseCount: BaseUrl + 'database/count/',
  // 用户基础接口
  userBaseUrl: BaseUrl + 'user/',
  // 用户搜索接口
  userSearch: BaseUrl + 'user/search/',
  // 用户数量接口
  userCount: BaseUrl + 'user/count/'
}
export default Url
