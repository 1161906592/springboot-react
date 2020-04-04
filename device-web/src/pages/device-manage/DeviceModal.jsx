import React, { useCallback, useEffect, useState } from "react";
import { Form, Input, message as antMsg, Modal, Select, DatePicker } from "antd";
import http from "@/http";

const DeviceModal = (props) => {
  const [form] = Form.useForm();
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
      } = await http[props.data.id ? "put" : "post"](`/api/device/device`, {
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
  <Modal getContainer={false} confirmLoading={loading} title={"设备信息"} visible={props.visible} onOk={handleSave} onCancel={handleCancel} maskClosable={false}>
    <Form {...formLayout} form={form} initialValues={props.data} style={{ marginBottom: -24 }}>
      <Form.Item label={"名称"} name={"name"} rules={[{ required: true, message: "请输入用户名" }]}>
        <Input autoComplete="off" placeholder="请输入用户名" />
      </Form.Item>
      <Form.Item label={"类型"} name={"typeId"} rules={[{ required: true, message: "请输入类型" }]}>
        <Input autoComplete="off" placeholder="请输入类型" />
      </Form.Item>
      <Form.Item label={"规格"} name={"model"} rules={[{ required: true, message: "请输入规格" }]}>
        <Input autoComplete="off" placeholder="请输入规格" />
      </Form.Item>
      <Form.Item label={"价格"} name={"price"} rules={[{ required: true, message: "请输入价格" }]}>
        <Input autoComplete="off" placeholder="请输入价格" />
      </Form.Item>
      <Form.Item label={"使用者"} name={"userId"} rules={[{ required: true, message: "请选择使用者" }]}>
        <Select
          placeholder="请选择使用者"
          showSearch
          optionFilterProp={"children"}
        >
          {
            props.userOption.map((item, index) => {
              return <Select.Option value={item.id} key={index}>{item.name}</Select.Option>
            })
          }
        </Select>
      </Form.Item>
      <Form.Item label={"定位"} name={"location"} rules={[{ required: true, message: "请输入定位" }]}>
        <Input autoComplete="off" placeholder="请输入定位" />
      </Form.Item>
      <Form.Item label={"购买日期"} name={"date"} rules={[{ required: true, message: "请输入定位" }]}>
        <DatePicker format={"YYYY-MM-DD"} style={{ width: "100%" }} />
      </Form.Item>
      <Form.Item label={"发票"} name={"invoice"}>
        <Input autoComplete="off" placeholder="请输入发票" />
      </Form.Item>
      <Form.Item label={"备注"} name={"remark"}>
        <Input.TextArea autoSize={{ minRows: 2, maxRows: 2 }} autoComplete="off" placeholder="请输入备注" />
      </Form.Item>
    </Form>
  </Modal>
  );
};

export default React.memo(DeviceModal);
