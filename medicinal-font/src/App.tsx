import * as React from 'react'
import RouterView from './router/RouterView'
import { LocaleProvider } from 'antd'
import zhCN from 'antd/lib/locale-provider/zh_CN'
import 'antd/lib/style/index.css'
import hideGlobalLoading from './utils/base/hideGlobalLoading'

hideGlobalLoading()

const App = () => {
  return (
  <LocaleProvider locale={zhCN}>
    <RouterView/>
  </LocaleProvider>
  )
}

export default App
