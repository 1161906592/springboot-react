import React, { useCallback, useEffect, useState } from "react";
import style from "./DeviceManage.module.scss";
import { Button, Divider, message as antMsg, Popconfirm, Table, Input, Select, Form } from "antd";
import { PlusOutlined } from "@ant-design/icons";
import { withRouter } from "react-router-dom";
import http from "@/http";
import moment from "moment";
import DeviceModal from "./DeviceModal";

const DeviceManage = (props) => {
  const columns = [
    {
      title: "序号",
      dataIndex: "id",
      key: "id",
      align: "center",
      width: 60,
      render: (text, record, index) => {
        return (pageNum - 1) * pageSize + index + 1;
      }
    },
    { title: "名称", dataIndex: "name", key: "name" },
    { title: "类型", dataIndex: "typeId", key: "typeId" },
    { title: "规格", dataIndex: "model", key: "model" },
    { title: "价格", dataIndex: "price", key: "price" },
    { title: "使用者", dataIndex: "userName", key: "userId" },
    { title: "定位", dataIndex: "location", key: "location" },
    {
      title: "购买日期",
      dataIndex: "date",
      key: "date",
      render: (text, record) => {
        return moment(record.lastModified).format("YYYY-MM-DD");
      }
    },
    { title: "发票", dataIndex: "invoice", key: "invoice" },
    { title: "备注", dataIndex: "remark", key: "remark" },
    {
      title: "操作",
      dataIndex: "action",
      key: "action",
      align: "center",
      width: 120,
      render: (text, record) => (
      <div className={style.handle} onClick={e => e.stopPropagation()}>
        <span onClick={() => handleEdit(record)}>修改</span>
        <Divider type="vertical" />
        <Popconfirm title="确定删除?" onConfirm={() => doDelete(record.id)}>
          <span>删除</span>
        </Popconfirm>
      </div>
      )
    }
  ];
  // 分页
  let [pageSize, setPageSize] = useState(10);
  let [pageNum, setPageNum] = useState(1);
  let [total, setTotal] = useState(0);
  // 数据获取
  let [name, setName] = useState();
  let [userId, setUserId] = useState();
  const nameChange = (e) => setName(e.target.value);
  const userIdChange = (value) => {
    console.log(value);
    setUserId(value)
  };
  // 分页参数
  const pagination = {
    showSizeChanger: true,
    showTotal: total => `共 ${total} 条`,
    pageSize,
    total,
    hideOnSinglePage: true,
    onChange: (page, size) => {
      size && setPageSize(size);
      setPageNum(page);
    },
    onShowSizeChange: (current, size) => {
      setPageSize(size);
      setPageNum(current);
    }
  };
  let [curItem, setCurItem] = useState({});
  // 创建空间
  let [createVisible, setCreateVisible] = useState(false);
  const handleAdd = () => {
    setCurItem({});
    setCreateVisible(true)
  };
  const handleCreateOk = () => {
    getTableData();
    setCreateVisible(false);
  };
  const handleEdit = (item) => {
    setCurItem(item);
    setCreateVisible(true)
  };
  const doDelete = async (id) => {
    let {
      data: { status, message }
    } = await http.delete(`/api/device/device`, {
      params: { id }
    });
    if (status) {
      antMsg.success("删除成功");
      getTableData();
    } else {
      antMsg.error(message);
    }
  };
  // 表格数据
  let [tableDta, setTableData] = useState([]);
  // loading
  let [loading, setLoading] = useState(false);
  const getTableData = useCallback(async () => {
    setLoading(true);
    try {
      let {
        data: { status, data, message }
      } = await http.get(`/api/device/pageDevice`, {
        params: {
          userId,
          name,
          pageSize,
          pageNum
        }
      });
      if (status) {
        setTableData(data.result.map((item) => {
          return {
            ...item,
            date: moment(item.date)
          }
        }));
        setTotal(data.total);
      } else {
        antMsg.error(message);
      }
    } finally {
      setLoading(false);
    }
  }, [userId, name, pageNum, pageSize]);
  useEffect(() => {
    getTableData();
  }, [getTableData]);
  let [ userOption, setUserOption ] = useState([]);
  const getUserOption = useCallback(async () => {
    let {
      data: { status, data, message }
    } = await http.get(`/api/user/userOption`);
    if (status) {
      setUserOption(data);
    } else {
      antMsg.error(message);
    }
  }, []);
  useEffect(() => {
    getUserOption();
  }, [getUserOption]);
  return (
    <>
      <div className={style.main}>
        <div className={style.header}>
          <div className={style.left}>
            <div className={style.label}>名称</div>
            <Input placeholder="请输入名称" style={{ width: 240, marginRight: 8 }} onChange={nameChange} allowClear />
            <div className={style.label}>使用人</div>
            <Select
              placeholder="请选择使用人"
              showSearch
              optionFilterProp={"children"}
              style={{ width: 240, marginRight: 8 }}
              onChange={userIdChange}
              allowClear
            >
                {
                  userOption.map((item, index) => {
                    return <Select.Option value={item.id} key={index}>{item.name}</Select.Option>
                  })
                }
            </Select>
            <Button type="primary" onClick={getTableData}>
              查询
            </Button>
          </div>
          <Button type="primary" icon={<PlusOutlined />} onClick={handleAdd}>
            添加设备
          </Button>
        </div>
        <Table
          rowKey={record => record.name}
          style={{ marginTop: 16 }}
          dataSource={tableDta}
          columns={columns}
          pagination={pagination}
          loading={loading}
        />
      </div>
      <DeviceModal visible={createVisible} data={curItem} userOption={userOption} onOk={handleCreateOk} onCancel={() => setCreateVisible(false)}/>
    </>
  );
};

export default withRouter(DeviceManage);
