import React from "react";
import { Link, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import style from "./layout.module.scss";
import { Dropdown, Icon, Menu, message as aMessage } from "antd";
import http from "@utils/http";

function mapStateToProps (state) {
  return {
    user: state.user
  };
}

@withRouter
@connect(mapStateToProps)
class MainLayout extends React.Component {
  handleLogout = async () => {
    const { data: { status, message } } = await http.post("http://localhost:8080/api/user/logout");
    if (status === 1) {
      localStorage.clear();
      aMessage.success("已退出登录");
      this.props.history.push("/login");
    } else {
      aMessage.error(message);
    }
  };

  render () {
    const menu = (
    <Menu>
      <Menu.Item onClick={this.handleLogout}><Icon type="logout" />退出登录</Menu.Item>
    </Menu>
    );
    const { user, children } = this.props;
    return (
    <>
      <header className={style.header}>
        <div className={"df aic jcsb " + style.inner}>
          <div className="fsz24 fwb">自驾游小助手</div>
          <div className="fsz16">
            <Link className="mr20" to={{ pathname: "/" }}>首页</Link>
            <Link className="mr20" to={{ pathname: "/home/strategy" }}>攻略</Link>
            {
              user.userName ? (
              <Dropdown overlay={menu} trigger={[ "click" ]}>
              <span className="cup">
                <span className="mr4">{user.userName}</span>
                <Icon type="down" />
              </span>
              </Dropdown>
              ) :
              <Link to={{ pathname: "/login" }}>登录</Link>
            }
          </div>
        </div>
      </header>
      <main className={style.main + " " + style.inner}>{children}</main>
      <footer className={"tac " + style.footer}>&copy;2019 copyright - 向娇专卖</footer>
    </>
    );
  }

}

export default MainLayout;
