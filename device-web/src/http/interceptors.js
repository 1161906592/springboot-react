// 配置请求拦截器
import { createHashHistory } from "history";
import NProgress from "nprogress";

const router = createHashHistory();

export const requestInterceptor = (config) => {
  NProgress.start();
  config.headers.token = localStorage.getItem("token");
  return config;
};

export const requestErrorInterceptor = (error) => {
  // todo
  throw error;
};

export const responseInterceptor = (response) => {
  NProgress.done();
  if (!response.data.status && response.data.message === "请先登录系统") {
    router.push({ pathname: "/login" });
  }
  // todo
  return response;
};

export const responseErrorInterceptor = (error) => {
  if (error.message.endsWith("403")) {
    router.push({ pathname: "/login" });
  }
  NProgress.done();
  // todo
  throw error;
};
