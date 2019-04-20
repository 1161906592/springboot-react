import React from 'react'
import { Layout, Icon } from 'antd'
import UserBar from './UserBar'
import LeftNavTree from './LeftNavTree'
import style from './mainLayout.module.scss'
import { requestFullscreen, exitFullscreen, checkIsFullscreen } from '../../../utils/bom/screen'

interface IState {
  isCollapsed: boolean,
  isFullscreen: boolean
}

class MainLayout extends React.Component<any, IState> {
  constructor (props: any) {
    super(props)
    this.state = {
      isCollapsed: false,
      isFullscreen: checkIsFullscreen()
    }
  }

  // 展开收起左侧导航栏
  handleToggle = () => {
    this.setState((state) => ({ isCollapsed: !state.isCollapsed, }))
  }

  // 全屏
  handleFullScreen = () => {
    requestFullscreen(document.body)
    this.setState({ isFullscreen: true })
    window.addEventListener('resize', this.handleWindowResize)
  }

  handleWindowResize = () => {
    if (!checkIsFullscreen()) {
      this.setState({ isFullscreen: false })
      window.removeEventListener('resize', this.handleWindowResize)
    }
  }

  // 退出全屏
  handleExitFullScreen = () => {
    exitFullscreen()
    this.setState({ isFullscreen: false })
    window.removeEventListener('resize', this.handleWindowResize)
  }

  render () {
    return (
    <Layout>
      {/* 左侧导航栏 */}
      <Layout.Sider collapsible={true} collapsed={this.state.isCollapsed} trigger={null} className={style.sider}>
        <div className={style.logo + ' m16 pl10 pr10 tac fsz16 t-ellipsis'}>药品管理系统</div>
        {/* 导航树 */}
        <LeftNavTree/>
      </Layout.Sider>
      <Layout className={style.layoutRight + ' ' + (this.state.isCollapsed ? style.isCollapsed : style.noCollapsed)}>
        {/* header */}
        <Layout.Header className={style.layoutHeader + ' df jcsb'}>
          <div>
            <Icon className={style.trigger + ' mr14 cup fsz18'} type={this.state.isCollapsed ? 'menu-unfold' : 'menu-fold'} onClick={this.handleToggle}/>
            <Icon className={style.trigger + ' cup fsz18'} type={this.state.isFullscreen ? 'fullscreen-exit' : 'fullscreen'} onClick={this.state.isFullscreen ? this.handleExitFullScreen : this.handleFullScreen}/>
          </div>
          <UserBar/>
        </Layout.Header>
        {/* 主体区域 */}
        <Layout.Content className={style.main + ' m20'}>
          {this.props.children}
        </Layout.Content>
      </Layout>
    </Layout>
    )
  }
}

export default MainLayout
