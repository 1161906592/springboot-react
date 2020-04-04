import loadable from "react-loadable";
import Loading from "../components/loading/Loading";

const routes = [
  {
    path: "/",
    redirect: () => localStorage.getItem("historyPath") || "/home/user"
  },
  {
    path: "/login",
    name: "登录",
    component: loadable({
      loader: () => import("@/pages/login/Login"),
      loading: Loading
    })
  },
  {
    path: "/home",
    component: loadable({
      loader: () => import("@/components/layout/Layout"),
      loading: Loading
    }),
    children: [
      {
        path: "/home/user",
        name: "人员管理",
        component: loadable({
          loader: () => import("@/pages/user-manage/UserManage"),
          loading: Loading
        })
      },
      {
        path: "/home/device",
        name: "设备管理",
        component: loadable({
          loader: () => import("@/pages/device-manage/DeviceManage"),
          loading: Loading
        })
      },
    ]
  }
];

export default routes;
