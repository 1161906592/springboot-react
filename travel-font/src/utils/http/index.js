import axios from "axios";
import NProgress from "nprogress"
import { message } from "antd";
import { createHashHistory } from "history";
NProgress.configure({ showSpinner: false });

const router = createHashHistory();

const axiosInstance = axios.create({
  // baseURL: process.env.BASE_API, // api的base_url
  timeout: 0, // 请求超时时间
  withCredentials: true,
  headers: {
    "Content-Type": "application/json;charset=utf-8",
    "token": localStorage.getItem("token")
  }
});

axiosInstance.interceptors.request.use((config) => {
  NProgress.start();
  return config
});

axiosInstance.interceptors.response.use((response) => {
  NProgress.done();
  if (response.data.status === 4) {
    router.push("/login")
  }
  return response;
}, (error) => {
  NProgress.done();
  message.error("网络错误");
  return Promise.reject(error);
});

export default axiosInstance;
