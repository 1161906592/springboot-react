import React from "react";
import { Button, Form, Icon, Input, message as aMessage } from "antd";
import { withRouter } from "react-router-dom";
import http from "@utils/http";

@withRouter
@Form.create()
class LoginForm extends React.Component {
  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields(async (err, values) => {
      if (!err) {
        const { data: { status, data, message } } = await http.post("http://localhost:8080/api/user/login", values);
        if (status === 1) {
          localStorage.setItem("token", data);
          aMessage.success("登录成功");
        } else {
          aMessage.error(message);
        }
      }
    });
  };

  render () {
    const { getFieldDecorator } = this.props.form;
    return (
    <Form>
      <Form.Item hasFeedback={true}>
        {getFieldDecorator("userName", { rules: [ { required: true, message: "请输入用户名" } ] })(
        <Input prefix={<Icon type="user" style={{ color: "rgba(0,0,0,.25)" }} />}
               autoComplete="off"
               placeholder="用户名" />
        )}
      </Form.Item>
      <Form.Item hasFeedback={true}>
        {getFieldDecorator("password", { rules: [ { required: true, message: "请输入密码" } ], })(
        <Input prefix={<Icon type="lock" style={{ color: "rgba(0,0,0,.25)" }} />}
               type="password"
               autoComplete="off"
               placeholder="密码" />
        )}
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit" block={true} onClick={this.handleSubmit}>登录</Button>
      </Form.Item>
    </Form>
    );
  }
}

export default LoginForm;
