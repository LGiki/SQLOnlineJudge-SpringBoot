const BaseUrl = 'http://127.0.0.1:8080/api/'
const Url = {
  // 登录接口
  login: BaseUrl + 'login',
  // 题目基础接口
  problemBaseUrl: BaseUrl + 'problem/',
  // 题目搜索接口
  problemSearch: BaseUrl + 'problem/search/',
  // 排名接口
  rankList: BaseUrl + 'user/ranklist',
  // 提交列表接口
  solutionList: BaseUrl + 'solution/',
  // 数据库列表接口
  databaseList: BaseUrl + 'database/',
  // 数据库详情接口
  databaseDetail: BaseUrl + 'database/',
  // 用户基础接口
  userBaseUrl: BaseUrl + 'user/',
  // 用户搜索接口
  userSearch: BaseUrl + 'user/search/'
}
export default Url
