import * as React from 'react'
import {Button, Form, Icon, Input, message as aMessage} from 'antd'
import {RouteComponentProps, withRouter} from 'react-router-dom'
import style from './register.module.scss'
import {FormComponentProps} from "antd/lib/form";
import http from '../../../utils/http'

interface IProps extends FormComponentProps, RouteComponentProps {
}

const Register = ({form, history}: IProps) => {
  const validateConfirmPassword = (rule: any, value: any, callback: any) => {
    if (value && value !== form.getFieldValue('password')) {
      callback('两次输入的密码不一致')
    } else {
      callback()
    }
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    form.validateFields(async (err, values) => {
      if (!err) {
        const {data: {status, data, message}} = await http.post("http://localhost:8080/api/user/register", values)
        if (status === 1) {
          localStorage.setItem('token', data)
          aMessage.success('注册成功')
          const {data: {status, data: data1, message}} = await http.get("http://localhost:8080/api/sys/menu")
          if (status === 1) {
            localStorage.setItem('userRoutes', data1.join(','))
            history.push('/home/batch')
          } else {
            aMessage.error(message)
          }
        } else {
          aMessage.error(message)
        }
      }
    })
  }

  const {getFieldDecorator} = form
  return (
  <div className={style.container}>
    <div className={style.register + " p20 mat"}>
      <h1 className="tac mb40">注册</h1>
      <Form>
        <Form.Item hasFeedback={true}>
          {getFieldDecorator('userName', {rules: [{required: true, message: '请输入用户名'}]})(<Input prefix={<Icon type="user" style={{color: 'rgba(0,0,0,.25)'}}/>} autoComplete="off" placeholder="用户名"/>)}
        </Form.Item>
        <Form.Item hasFeedback={true}>
          {getFieldDecorator('password', {rules: [{required: true, message: '请输入密码'}],})(<Input prefix={<Icon type="lock" style={{color: 'rgba(0,0,0,.25)'}}/>} type="password" autoComplete="off" placeholder="密码"/>)}
        </Form.Item>
        <Form.Item hasFeedback={true}>
          {getFieldDecorator('confirm', {rules: [{required: true, message: '请确认密码'}, {validator: validateConfirmPassword,}],})(<Input prefix={<Icon type="lock" style={{color: 'rgba(0,0,0,.25)'}}/>} type="password" autoComplete="off" placeholder="确认密码"/>)}
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit" href="javascript:void(0)" block={true} onClick={handleSubmit}>确认</Button>
        </Form.Item>
      </Form>
    </div>
  </div>
  )
}

export default withRouter(Form.create()(Register))
