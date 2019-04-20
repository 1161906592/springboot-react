import React from 'react'
import {Button, Form, Icon, Input, message as aMessage} from 'antd'
import {RouteComponentProps, withRouter} from 'react-router-dom'
import {FormComponentProps} from "antd/lib/form/Form";
import http from "../../../utils/http";

interface IProps extends FormComponentProps, RouteComponentProps {
}

class LoginForm extends React.Component<IProps, any>{
  handleSubmit = (e: React.MouseEvent) => {
    e.preventDefault()
    this.props.form.validateFields(async (err: boolean, values: any) => {
      if (!err) {
        const {data: {status, data, message}} = await http.post("http://localhost:8080/api/user/login", values)
        if (status === 1) {
          localStorage.setItem('token', data)
          aMessage.success('登录成功')
          const {data: {status, data: data1, message}} = await http.get("http://localhost:8080/api/sys/menu")
          if (status === 1) {
            localStorage.setItem('userRoutes', data1.join(','))
            this.props.history.push('/home/batch')
          } else {
            aMessage.error(message)
          }
        } else {
          aMessage.error(message)
        }
      }
    })
  }
  render() {
    const {getFieldDecorator} = this.props.form
    return (
    <Form>
      <Form.Item hasFeedback={true}>
        {getFieldDecorator('userName', {rules: [{required: true, message: '请输入用户名'}]})(<Input prefix={<Icon type="user" style={{color: 'rgba(0,0,0,.25)'}}/>} autoComplete="off" placeholder="用户名"/>)}
      </Form.Item>
      <Form.Item hasFeedback={true}>
        {getFieldDecorator('password', {rules: [{required: true, message: '请输入密码'}],})(<Input prefix={<Icon type="lock" style={{color: 'rgba(0,0,0,.25)'}}/>} type="password" autoComplete="off" placeholder="密码"/>)}
      </Form.Item>
      <Form.Item>
        <Button type="primary" href="javascript:void(0)" htmlType="submit" block={true} onClick={this.handleSubmit}>登录</Button>
      </Form.Item>
    </Form>
    )
  }
}

export default withRouter(Form.create<IProps>()(LoginForm))
