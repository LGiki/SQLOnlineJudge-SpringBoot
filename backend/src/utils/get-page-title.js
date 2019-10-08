import defaultSettings from '@/settings'

const title = defaultSettings.title || 'SQL在线考试系统 - 后台管理'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
