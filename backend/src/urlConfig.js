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
  solutionBaseUrl: ApiHost + '/api/public/solutions',
  // 解答数量接口
  solutionCount: ApiHost + '/api/public/solutions/count',
  // 查询解答代码接口
  solutionCode: BaseUrl + '/solutions/',
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
  adminCount: BaseUrl + '/admins/count',
  // 更改用户状态接口
  userStatusUrl: BaseUrl + '/users/status/',
  // 更改管理员状态接口
  adminStatusUrl: BaseUrl + '/admins/status/',
  // 上传图片接口
  uploadImageUrl: BaseUrl + '/util/upload',
  // 图片显示接口
  showImageUrl: ApiHost + '/api/public/image/',
  // 用户组基础接口
  userGroupBaseUrl: BaseUrl + '/user-group/',
  // 调试运行接口
  runCode: BaseUrl + '/problems/judgement/',
  problemCategoryBaseUrl: BaseUrl + '/problem-category/',
  problemCollectionBaseUrl: BaseUrl + '/problem-collection/'
}
export default Url
