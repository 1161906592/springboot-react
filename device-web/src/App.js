import React from 'react';
import { ConfigProvider } from "antd";
import zhCN from "antd/es/locale/zh_CN";
import RouterView from "@/router/RouterView";
import moment from "moment";
import "moment/locale/zh-cn";
moment.locale("zh-cn");

function App() {
  return (
    <ConfigProvider locale={zhCN}>
      <RouterView />
    </ConfigProvider>
  );
}

export default App;
