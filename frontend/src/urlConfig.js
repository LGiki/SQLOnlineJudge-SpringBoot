const BaseUrl = 'http://127.0.0.1:8080/api/'
const Url = {
    //用户登录接口
    login: BaseUrl + 'login',
    //题目基础接口
    problemBaseUrl: BaseUrl + 'problems/',
    //排行榜基础接口
    rankListBaseUrl: BaseUrl + '/public/rank',
    //用户提交基础接口
    solutionBaseUrl: BaseUrl + '/public/solutions',
    //数据库基础接口
    databaseBaseUrl: BaseUrl + 'databases/'
}
export default Url