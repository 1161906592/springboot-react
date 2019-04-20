import React from 'react'
import { Avatar, Menu, Dropdown, Icon, message as aMessage } from 'antd'
import {RouteComponentProps, withRouter} from 'react-router-dom'
import style from './userBar.module.scss'
import http from "../../../utils/http";

interface IProps extends RouteComponentProps {
}

const UserBar = (props: IProps) => {
  const handleLogout = async () => {
    const {data: {status, data, message}} = await http.post("http://localhost:8080/api/user/logout")
    if (status === 1) {
      localStorage.clear()
      aMessage.success("已退出登录")
      props.history.push('/login')
    } else {
      aMessage.error(message)
    }
  }
  const menu = (
  <Menu>
    {/*<Menu.Item onClick={this.handleLogout}><Icon type="logout" />退出登录</Menu.Item>*/}
    {/*<Menu.Divider />*/}
    <Menu.Item onClick={handleLogout}><Icon type="logout"/>退出登录</Menu.Item>
  </Menu>
  )
  return (
  <Dropdown overlay={menu}>
    <div>
      <span className='mr10'>admin</span>
      <Avatar className={style.avatar} icon="user" src="https://gss1.bdstatic.com/-vo3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=4914cf618c0a19d8cb0383030bc1e5b6/503d269759ee3d6dd050111b4f166d224e4ade48.jpg"/>
      <Icon type="down" className='ml6'/>
    </div>
  </Dropdown>
  )
}

export default withRouter(React.memo(UserBar))
