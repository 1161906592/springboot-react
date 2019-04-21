import * as React from "react";
import { Button, Form, Icon, Input, message as aMessage } from "antd";
import { withRouter } from "react-router-dom";
import style from "./register.module.scss";
import http from "@utils/http";
import { connect } from "react-redux";
import { setUser } from "@redux/action";

const mapDispatchToProps = (dispatch, ownProps) => ({
  setUser: (user) => {
    dispatch(setUser(user));
  }
});

@withRouter
@Form.create()
@connect(null, mapDispatchToProps)
class Register extends React.Component {
  validateConfirmPassword = (rule, value, callback) => {
    if (value && value !== this.props.form.getFieldValue("password")) {
      callback("两次输入的密码不一致");
    } else {
      callback();
    }
  };
  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields(async (err, values) => {
      if (!err) {
        const { data: { status, data, message } } = await http.post("http://localhost:8080/api/user/register", values);
        if (status === 1) {
          localStorage.setItem("token", data);
          aMessage.success("注册成功");
          this.props.setUser();
          this.props.history.push("/");
        } else {
          aMessage.error(message);
        }
      }
    });
  };

  render () {
    const { getFieldDecorator } = this.props.form;
    return (
    <div className={style.register + " p20 mat"}>
      <h1 className="tac mb40">注册</h1>
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
        <Form.Item hasFeedback={true}>
          {getFieldDecorator("confirm", {
            rules: [
              { required: true, message: "请确认密码" },
              { validator: this.validateConfirmPassword, }
            ],
          })(
          <Input prefix={<Icon type="lock" style={{ color: "rgba(0,0,0,.25)" }} />}
                 type="password"
                 autoComplete="off"
                 placeholder="确认密码" />
          )}
        </Form.Item>
        <Form.Item>
          <Button type="primary" block={true} onClick={this.handleSubmit}>确认</Button>
        </Form.Item>
      </Form>
    </div>
    );
  }
}

export default Register;
