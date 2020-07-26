const apiHost = 'http://127.0.0.1:8080'
const baseUrl = `${apiHost}/api/admin`
const baseApiUrlConfig = {
  // 管理员登录
  adminLogin: '/auth',
  // 题目基础操作
  problemBase: '/problems',
  // 题目数量
  problemCount: '/problems/count',
  // 解答数量
  solutionCount: '/solutions/count',
  // 查询解答代码接口
  solutionBase: '/solutions',
  // 数据库基础接口
  databaseBase: '/databases',
  // 查询全部数据库的ID和名称
  getAllDatabase: '/databases/all',
  // 数据库数量接口
  databaseCount: '/databases/count',
  // 用户基础接口
  userBase: '/users',
  // 用户数量接口
  userCount: '/users/count',
  // 管理员基础接口
  adminBase: '/admins',
  // 管理员数量接口
  adminCount: '/admins/count',
  // 更改用户状态接口
  userStatus: '/users/status',
  // 更改管理员状态接口
  adminStatus: '/admins/status',
  // 上传图片接口
  uploadImage: '/util/upload',
  // 用户组基础接口
  userGroupBase: '/user_group',
  // 用户组数量接口
  userGroupCount: '/user_group/count',
  // 调试运行接口
  runCode: '/problems/judgement',
  // 题目集基础接口
  problemCategoryBase: '/problem_category',
  // 用户组数量接口
  problemCategoryCount: '/problem_category/count',
  // 题目集题目列表接口
  problemCollectionBase: '/problem_collection',
  // 批量向题目集中添加题目接口
  createProblemCollectionInBulk: '/problem_collection/bulk',
  // 批量添加、删除题目集题目接口
  problemCollectionBulk: '/problem_collection/bulk',
  // 查询题目集包含的题目ID接口
  problemCollectionProblemIds: '/problem_collection/problem_ids',
  // 更新题目集的题目分值接口
  problemCollectionUpdateProblemScore: '/problem_collection/update_score'
}
const specialApiUrlConfig = {
  // 图片显示接口
  getImage: apiHost + '/api/public/image'
}
export { baseUrl, baseApiUrlConfig, specialApiUrlConfig }
