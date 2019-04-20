import axios from 'axios'
import {message} from 'antd'
import { createHashHistory }  from "history";

const router = createHashHistory()

const axiosInstance = axios.create({
  // baseURL: process.env.BASE_API, // api的base_url
  timeout: 0, // 请求超时时间
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json;charset=utf-8',
    'token': localStorage.getItem('token')
  }
})

axiosInstance.interceptors.response.use((response) => {
  return response
}, (error) => {
  let errorString = error.toString()
  if (error) {
    if (errorString.endsWith('401')) {
      router.push('/login')
    } else if (errorString.endsWith('403')) {
      message.error('请求无权限')
    } else if (errorString === 'Cancel') {
      // 手动取消本次请求
    } else {
      message.error('请求超时')
    }
  }
  return Promise.reject(error)
})

export default axiosInstance
