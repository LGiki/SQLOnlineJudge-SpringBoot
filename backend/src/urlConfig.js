const BaseUrl = 'http://127.0.0.1:8080/api/'
const Url = {
  // 登录接口
  login: BaseUrl + 'login',
  // 题目基础接口
  problemBaseUrl: BaseUrl + 'problems/',
  // 题目数量接口
  problemCount: BaseUrl + 'problems/count',
  // 排名接口
  rankList: BaseUrl + 'user/ranklist',
  // 提交基础接口
  solutionBaseUrl: BaseUrl + 'solutions/',
  // 提交数量接口
  solutionCount: BaseUrl + 'solutions/count',
  // 数据库基础接口
  databaseBaseUrl: BaseUrl + 'databases/',
  // 数据库数量接口
  databaseCount: BaseUrl + 'databases/count',
  // 用户基础接口
  userBaseUrl: BaseUrl + 'users/',
  // 用户数量接口
  userCount: BaseUrl + 'users/count'
}
export default Url
