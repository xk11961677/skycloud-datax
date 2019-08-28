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
    // redirect: '/datax',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Dashboard', icon: 'dashboard' }
    }]
  },
  {
    path: '/out',
    component: Layout,
    // redirect: '/datax/job',
    name: 'out',
    meta: { title: '系统页面', icon: 'example' },
    children: [{
      path: 'discovery',
      name: 'discovery',
      component: () => import('@/views/open/discovery/index'),
      meta: { title: '注册中心', icon: 'dashboard' }
    },
    {
      path: 'config',
      name: 'config',
      component: () => import('@/views/open/config/index'),
      meta: { title: '配置中心', icon: 'dashboard' }
    },
    {
      path: 'log',
      name: 'log',
      component: () => import('@/views/open/log/index'),
      meta: { title: '日志查看', icon: 'dashboard' }
    },
    {
      path: 'skywalking',
      name: 'skywalking',
      component: () => import('@/views/open/skywalking/index'),
      meta: { title: '调用链查看', icon: 'dashboard' }
    },
    {
      path: 'monitor',
      name: 'monitor',
      component: () => import('@/views/open/monitor/index'),
      meta: { title: '监控SBA', icon: 'dashboard' }
    },
    {
      path: 'grafana',
      name: 'grafana',
      component: () => import('@/views/open/grafana/index'),
      meta: { title: '监控查看', icon: 'dashboard' }
    },
    {
      path: 'rocketmq',
      name: 'rocketmq',
      component: () => import('@/views/open/rocketmq/index'),
      meta: { title: 'MQ监控', icon: 'dashboard' }
    },
    {
      path: 'hystrix',
      name: 'hystrix',
      component: () => import('@/views/open/hystrix/index'),
      meta: { title: '断路器查看', icon: 'dashboard' }
    }
    ]
  },
  {
    path: '/job',
    component: Layout,
    redirect: '/job/job',
    name: 'Example',
    meta: { title: '定时任务', icon: 'example' },
    children: [
      {
        path: 'job',
        name: '定时任务',
        component: () => import('@/views/open/job/index'),
        meta: { title: '定时任务', icon: 'table' }
      }
    ]
  },
  {
    path: '/datax',
    component: Layout,
    redirect: '/datax/job',
    name: 'Example',
    meta: { title: 'ELT工具', icon: 'example' },
    children: [
      {
        path: 'job',
        name: '同步任务',
        component: () => import('@/views/datax/job/index'),
        meta: { title: '同步任务', icon: 'table' }
      },
      {
        path: 'plugin',
        name: 'DataxPlugin',
        component: () => import('@/views/datax/plugin/index'),
        meta: { title: '插件查看', icon: 'table' }
      },
      {
        path: 'jobConfig',
        name: 'jobConfig',
        component: () => import('@/views/datax/jobConfig/index'),
        meta: { title: '作业配置', icon: 'table' }
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
