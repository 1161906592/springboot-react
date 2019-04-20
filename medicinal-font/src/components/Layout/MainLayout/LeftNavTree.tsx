import React from 'react'
import {Menu, Icon} from 'antd'
import {RouteComponentProps, withRouter} from 'react-router-dom'

interface NavData {
  text: string
  icon?: string
  path: string
  children?: Array<NavData>
}

interface IProps extends RouteComponentProps {
}

interface IState {
  navData: Array<NavData>
}

const createNavTree = (data: Array<NavData>) => {
  return data.map((item: NavData) => {
    if (item.children) {
      return (
      <Menu.SubMenu key={item.path} title={<span>{item.icon && <Icon type={item.icon}/>}<span>{item.text}</span></span>}>
        {createNavTree(item.children)}
      </Menu.SubMenu>
      )
    } else {
      return (
      <Menu.Item key={item.path}>
        {item.icon && <Icon type={item.icon}/>}
        <span className="nav-text">{item.text}</span>
      </Menu.Item>
      )
    }
  })
}

class LeftNavTreeImp extends React.Component<IProps, IState>{
  constructor(props: IProps) {
    super(props)
    this.state = {
      navData: [
        {
          text: '批次管理',
          icon: 'appstore-o',
          path: '/home/batch'
        },
        {
          text: '药品管理',
          icon: 'appstore-o',
          path: '/home/medicinal'
        },
        {
          text: '厂商管理',
          icon: 'appstore-o',
          path: '/home/manufacturer'
        }
      ]
    }
  }

  // 路由跳转
  private handleSelectNav = ({keyPath}: {keyPath: Array<string>}): void => {
    keyPath.reverse()
    this.props.history.push(keyPath.join(''))
  }

  render() {
    let paths: Array<string> = this.props.location.pathname.split('/').map(d => '/' + d)
    let keys: Array<string> = [paths.slice(1, 3).join('')].concat(paths.slice(3))
    return (
    <Menu theme="dark" mode="inline" defaultOpenKeys={keys} defaultSelectedKeys={keys} onClick={this.handleSelectNav}>
      {createNavTree(this.state.navData)}
    </Menu>
    )
  }
}

export default withRouter(LeftNavTreeImp)
