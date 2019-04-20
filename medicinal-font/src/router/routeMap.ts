import Loadable from 'react-loadable'
import Loading from '../components/Loading/Loading'


const routeMap:Array<Project.Route> = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: '登录',
    component: Loadable({
      loader: () => import('../pages/mine/login/Login'),
      loading: Loading
    })
  },
  {
    path: '/register',
    name: '注册',
    component: Loadable({
      loader: () => import('../pages/mine/register/Register'),
      loading: Loading
    })
  },
  {
    path: '/home',
    component: Loadable({
      loader: () => import('../components/Layout/MainLayout/MainLayout'),
      loading: Loading
    }),
    children: [
      {
        path: '/batch',
        name: '批次管理',
        component: Loadable({
          loader: () => import('../pages/batchManage/BatchList'),
          loading: Loading
        })
      },
      {
        path: '/batch/detail',
        name: '批次详情',
        component: Loadable({
          loader: () => import('../pages/userManage/UserManage'),
          loading: Loading
        })
      },
      {
        path: '/medicinal',
        name: '药品管理',
        component: Loadable({
          loader: () => import('../pages/userManage/UserManage'),
          loading: Loading
        })
      },
      {
        path: '/manufacturer',
        name: '厂商管理',
        component: Loadable({
          loader: () => import('../pages/manufacturer/Manufacturer'),
          loading: Loading
        })
      }
    ]
  },
  {
    path: '/404',
    name: '找不到页面呢~',
    component: Loadable({
      loader: () => import('../pages/errorPage/404/NotFound'),
      loading: Loading
    })
  }
]

export default routeMap
