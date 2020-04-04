import React, { useCallback, useEffect, useState } from "react";
import style from "./UserManage.module.scss";
import { Button, Divider, message as antMsg, Popconfirm, Table, Input } from "antd";
import { PlusOutlined } from "@ant-design/icons";
import { withRouter } from "react-router-dom";
import http from "@/http";
import UserModal from "./UserModal";

const UserManage = (props) => {
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
    { title: "用户名", dataIndex: "name", key: "name" },
    { title: "手机号", dataIndex: "tel", key: "tel" },
    {
      title: "用户类型",
      dataIndex: "role",
      key: "role",
      render: (text, record, index) => {
        return {
          1: "管理员",
          2: "普通用户"
        }[record.role];
      }
    },
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
  let [name, setName] = useState("");
  const userNameChange = (e) => setName(e.target.value);
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
    } = await http.delete(`/api/user/user`, {
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
      } = await http.get(`/api/user/pageUser`, {
        params: {
          name,
          pageSize,
          pageNum
        }
      });
      if (status) {
        setTableData(data.result);
        setTotal(data.total);
      } else {
        antMsg.error(message);
      }
    } finally {
      setLoading(false);
    }
  }, [name, pageNum, pageSize]);
  useEffect(() => {
    getTableData();
  }, [getTableData]);
  return (
  <>
    <div className={style.main}>
      <div className={style.header}>
        <div className={style.left}>
          <div className={style.label}>用户名</div>
          <Input placeholder="请输入用户名" style={{ width: 240, marginRight: 8 }} onChange={userNameChange} allowClear />
          <Button type="primary" onClick={getTableData}>
            查询
          </Button>
        </div>
        <Button type="primary" icon={<PlusOutlined />} onClick={handleAdd}>
          添加人员
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
    <UserModal visible={createVisible} data={curItem} onOk={handleCreateOk} onCancel={() => setCreateVisible(false)}/>
  </>
  );
};

export default withRouter(UserManage);
