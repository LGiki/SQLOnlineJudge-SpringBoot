const ApiHost = "http://127.0.0.1:8080";
const BaseUrl = ApiHost + "/api/user";
const Url = {
  // 用户登录接口
  login: ApiHost + "/auth/user/login",
  // 题目基础接口
  problemBaseUrl: ApiHost + "/api/public/problems",
  // 排行榜基础接口
  rankListBaseUrl: ApiHost + "/api/public/rank",
  // 用户提交基础接口
  solutionBaseUrl: ApiHost + "/api/public/solutions",
  // 数据库基础接口
  databaseBaseUrl: BaseUrl + "/public/databases",
  // 用户注册接口
  register: ApiHost + "/auth/user/register",
  // 获取用户详细信息接口
  userDetail: BaseUrl + "/users/",
  // 获取用户提交代码接口
  solutionCode: BaseUrl + "/solutions/",
  // 用户提交解答接口
  solutionSubmit: BaseUrl + "/solutions/",
  // 获取用户通过和尝试过的题目列表
  userDoProblemList: BaseUrl + "/problems"
};
export default Url;
