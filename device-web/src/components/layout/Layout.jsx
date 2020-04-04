/**
 * @Author: liuyang
 * @Date: 2019-10-11 11:47
 */
import React, { useEffect, useMemo } from "react";
import { Link, withRouter } from "react-router-dom";
import { Layout, Menu, message as antMsg } from "antd";
import { LogoutOutlined, HomeOutlined, UserOutlined } from "@ant-design/icons";
import style from "./Layout.module.scss";
import http from "@/http";
import getUserLoginInfo from "@/utils/getUserLoginInfo";

const { Header, Content, Sider } = Layout;

const CommonLayout = withRouter(props => {
  useEffect(() => {
    localStorage.setItem("historyPath", props.location.pathname);
  }, [props.location.pathname]);
  const userInfo = useMemo(getUserLoginInfo, []);
  // 菜单
  const menuList = useMemo(() => {
    const menuList = [
      { id: "1", name: "设备管理", icon: HomeOutlined, path: "/home/device" },
      { id: "2", name: "人员管理", icon: UserOutlined, path: "/home/user" },
    ];
    if (userInfo.isAdmin) {
      return menuList;
    }
    return menuList.filter(d => !d.isAdmin);
  }, [userInfo]);
  // 退出登录
  const doLogout = async () => {
    let {
      data: { status, message }
    } = await http.post(`/api/user/logout`);
    if (status) {
      props.history.push("/login");
    } else {
      antMsg.error(message);
    }
  };
  return (
    <>
      {/* 顶部 */}
      <Header className={style.header}>
        <div className={style.logo}>设备管理系统</div>
        <div className={style.apiDoc}/>
        <div className={style.user}>
          <span className={style.userName}>欢迎您，{userInfo.name}</span>
          <div className={style.logout} onClick={doLogout}>
            <LogoutOutlined />
          </div>
        </div>
      </Header>
      {/* 侧边栏 */}
      <Sider width={200} className={style.side}>
        <Menu mode="inline" selectedKeys={[props.history.location.pathname]} style={{ height: "100%", borderRight: 0 }}>
          {menuList.map(item => {
            return (
              <Menu.Item key={item.path}>
                <Link to={item.path}>
                  <item.icon />
                  {item.name}
                </Link>
              </Menu.Item>
            );
          })}
        </Menu>
      </Sider>
      {/* 内容区 */}
      <Layout style={{ marginLeft: 200, paddingTop: 56 }}>
        <Content style={{ padding: 16, minHeight: "calc(100vh - 56px)" }}>{props.children}</Content>
      </Layout>
    </>
  );
});

export default CommonLayout;
