import React from 'react'
import { Link } from 'react-router-dom'
import style from './login.module.scss'
import LoginForm from './LoginForm'

class Login extends React.Component {
  render () {
    return (
    <div className={style.container}>
      <div className={style.login + " p20 mat"}>
        <h1 className="tac mb40">药品管理系统</h1>
        <LoginForm/>
        <Link to="/register">立即注册</Link>
      </div>
    </div>
    )
  }
}

export default Login
