var ConvertUtil = {
  // 将日期格式化为：yyyy-MM-dd HH:mm:ss
  convertDateToString(date) {
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds()
  }
}

export default ConvertUtil
