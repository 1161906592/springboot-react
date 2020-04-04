import React, { useEffect, useState } from "react";
import { Form, Input, message as antMsg, Modal, Select } from "antd";
import http from "@/http";

const UserModal = (props) => {
  const [form] = Form.useForm();
  // console.log(form);
  useEffect(() => {
    form.resetFields()
  }, [form, props.visible]);
  const formLayout = {
    labelCol: { span: 4 },
    wrapperCol: { span: 20 }
  };
  const handleSave = () => {
    form.validateFields().then(values => {
      doSave(values);
    });
  };
  let [loading, setLoading] = useState(false);
  const doSave = async (values) => {
    setLoading(true);
    try {
      let {
        data: { status, message }
      } = await http[props.data.id ? "put" : "post"](`/api/user/user`, {
        ...values,
        id: props.data.id
      });
      if (status) {
        antMsg.success(props.data.id ? "修改成功" : "创建成功");
        props.onOk();
      } else {
        antMsg.error(message);
      }
    } finally {
      setLoading(false);
    }
  };
  const handleCancel = () => {
    props.onCancel();
  };
  return (
  <Modal getContainer={false} confirmLoading={loading} title={"人员信息"} visible={props.visible} onOk={handleSave} onCancel={handleCancel} maskClosable={false}>
    <Form {...formLayout} form={form} initialValues={props.data}>
      <Form.Item label={"用户名"} name={"name"} rules={[{ required: true, message: "请输入用户名" }]}>
        <Input autoComplete="off" placeholder="请输入用户名" />
      </Form.Item>
      <Form.Item label={"手机号"} name={"tel"} rules={[{ required: true, message: "请输入手机号" }]}>
        <Input autoComplete="off" placeholder="请输入手机号" />
      </Form.Item>
      <Form.Item label={"用户类型"} name={"role"} rules={[{ required: true, message: "请选择用户类型" }]}>
        <Select placeholder="请选择用户类型">
          <Select.Option value={1}>管理员</Select.Option>
          <Select.Option value={2}>普通用户</Select.Option>
        </Select>
      </Form.Item>
    </Form>
  </Modal>
  );
};

export default React.memo(UserModal);
