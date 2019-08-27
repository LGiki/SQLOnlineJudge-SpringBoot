import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '主页', icon: 'dashboard' }
    }]
  },
  {
    path: '/database',
    component: Layout,
    redirect: '/database/index',
    meta: { title: '数据库管理', icon: 'database' },
    children: [
      {
        path: 'index',
        name: 'Database List',
        component: () => import('@/views/database/index'),
        meta: { title: '数据库列表' }
      },
      {
        path: 'edit/:id',
        name: 'Edit Database',
        component: () => import('@/views/database/edit'),
        meta: { title: '编辑数据库' },
        hidden: true
      },
      {
        path: 'add',
        name: 'Add Database',
        component: () => import('@/views/database/add'),
        meta: { title: '添加数据库' },
        hidden: true
      }
    ]
  },
  {
    path: '/problem',
    component: Layout,
    redirect: '/problem/index',
    meta: { title: '题目管理', icon: 'tree-table' },
    children: [
      {
        path: 'index',
        name: 'Problems',
        component: () => import('@/views/problem/index'),
        meta: { title: '题目列表' }
      },
      {
        path: 'edit/:id',
        name: 'Edit problem',
        component: () => import('@/views/problem/edit'),
        meta: { title: '编辑题目' },
        hidden: true
      },
      {
        path: 'add',
        name: 'Add problem',
        component: () => import('@/views/problem/add'),
        meta: { title: '添加题目' },
        hidden: true
      }
    ]
  },

  {
    path: '/user',
    component: Layout,
    redirect: '/user/index',
    meta: { title: '用户管理', icon: 'peoples' },
    children: [
      {
        path: 'index',
        name: 'Users',
        component: () => import('@/views/user/index'),
        meta: { title: '用户列表' }
        // hidden: true
      },
      {
        path: 'edit/:id',
        name: 'Edit Users',
        component: () => import('@/views/user/edit'),
        meta: { title: '编辑用户' },
        hidden: true
      },
      {
        path: 'add',
        name: 'Add Users',
        component: () => import('@/views/user/add'),
        meta: { title: '添加用户' },
        hidden: true
      }
    ]
  },
  {
    path: '/admin',
    component: Layout,
    redirect: '/admin/index',
    meta: { title: '管理员管理', icon: 'peoples' },
    children: [
      {
        path: 'index',
        name: 'Admins',
        component: () => import('@/views/admin/index'),
        meta: { title: '管理员列表' }
        // hidden: true
      },
      {
        path: 'edit/:id',
        name: 'Edit Admin',
        component: () => import('@/views/admin/edit'),
        meta: { title: '编辑管理员' },
        hidden: true
      },
      {
        path: 'add',
        name: 'Add Admin',
        component: () => import('@/views/admin/add'),
        meta: { title: '添加管理员' },
        hidden: true
      }
    ]
  },
  {
    path: '/solution',
    component: Layout,
    redirect: '/solution/index',
    meta: { title: '提交查看', icon: 'chart' },
    children: [
      {
        path: 'index',
        name: 'Solutions',
        component: () => import('@/views/solution/index'),
        meta: { title: '提交列表' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
