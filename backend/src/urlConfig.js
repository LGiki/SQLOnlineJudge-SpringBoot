const ApiHost = 'http://127.0.0.1:8080'
const BaseUrl = ApiHost + '/api/admin'
const Url = {
  // 登录接口
  login: ApiHost + '/auth/admin/login',
  // 题目基础接口
  problemBaseUrl: BaseUrl + '/problems/',
  // 题目数量接口
  problemCount: BaseUrl + '/problems/count',
  // 解答基础接口
  solutionBaseUrl: ApiHost + '/api/public/solutions/',
  // 解答数量接口
  solutionCount: ApiHost + '/api/public/solutions/count',
  // 查询解答代码接口
  solutionCode: BaseUrl + '/solutions/code',
  // 数据库基础接口
  databaseBaseUrl: BaseUrl + '/databases/',
  // 数据库数量接口
  databaseCount: BaseUrl + '/databases/count',
  // 用户基础接口
  userBaseUrl: BaseUrl + '/users/',
  // 用户数量接口
  userCount: BaseUrl + '/users/count',
  // 管理员基础接口
  adminBaseUrl: BaseUrl + '/admins/',
  // 管理员数量接口
  adminCount: BaseUrl + '/admins/count'
}
export default Url
