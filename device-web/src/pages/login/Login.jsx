/**
 * @Author: zhouhaonan
 * @Date: 2020-4-4 14:17
 */
import React, { useEffect } from "react";
import style from "./Login.module.scss";
import { Form, Input, Button, message as antMsg } from "antd";
import { UserOutlined, LockOutlined } from "@ant-design/icons";
import { withRouter } from "react-router-dom";
import http from "@/http";

const Login = (props) => {
  useEffect(() => {
    localStorage.clear();
  }, []);
  const [form] = Form.useForm();
  const handleLogin = async () => {
    form.validateFields().then(values => {
      doLogin(values);
    });
  };

  const doLogin = async (values) => {
    let {
      data: { status, data, message }
    } = await http.post(`/api/user/login`, values);
    if (status) {
      localStorage.setItem("userInfo", JSON.stringify(data));
      props.history.push("/");
    } else {
      antMsg.error(message);
    }
  };
  return (
    <div className={style.main}>
      <div className={style.logSection}>
        <div className={style.title}>设备管理系统</div>
        <Form style={{ marginTop: 40 }} form={form}>
          <Form.Item name={"tel"} rules={[{ required: true, message: "请输入手机号" }]}>
            <Input size="large" prefix={<UserOutlined style={{ color: "rgba(0,0,0,.25)" }} />} placeholder="手机号" />
          </Form.Item>
          <Form.Item name={"password"} rules={[{ required: true, message: "请输入密码" }]}>
            <Input size="large" prefix={<LockOutlined style={{ color: "rgba(0,0,0,.25)" }} />} type="password" placeholder="密码" />
          </Form.Item>
        </Form>
        <Button type="primary" style={{ width: "100%" }} size="large" onClick={handleLogin}>
          登录
        </Button>
      </div>
    </div>
  );
};

export default withRouter(Login);
