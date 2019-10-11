import defaultSettings from '@/settings'

const title = defaultSettings.title || 'SQL自动评测系统 - 后台管理'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
