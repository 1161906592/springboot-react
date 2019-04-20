import React from "react";
import { Link } from "react-router-dom";
import style from "./layout.module.scss"

const MainLayout = (props) =>
<div>
  <header className={style.header}>
    <div className={"df aic jcsb " + style.inner}>
      <div className="fsz24 fwb">自驾游小助手</div>
      <div className="fsz16">
        <Link className="mr20" to={{pathname: "/"}}>首页</Link>
        <Link className="mr20" to={{pathname: "/home/strategy"}}>攻略</Link>
        <Link to={{pathname: "/login"}}>登录</Link>
      </div>
    </div>
  </header>
  <main className={style.main + " " + style.inner}>{props.children}</main>
  <footer className={"tac " + style.footer}>&copy;2019 copyright - 向娇专卖</footer>
</div>;

export default MainLayout;
