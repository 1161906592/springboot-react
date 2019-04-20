import React, { ChangeEvent } from "react";
import {
  Input,
  Button,
  Table,
  Popconfirm,
  Divider,
  message as aMessage,
  Modal,
  Form, Icon
} from "antd";
import { Link, RouteComponentProps } from "react-router-dom";
import {ColumnProps} from "antd/lib/table";
import http from "../../utils/http";
import { PaginationProps } from "antd/lib/pagination";
import { FormComponentProps } from "antd/lib/form";

interface DataSourceItem {
  companyName: string;
  phone: string;
  address: string;
  companyId: string;
}

interface IProps extends FormComponentProps, RouteComponentProps {
}

interface IState {
  tableData: Array<DataSourceItem>;
  addCompanyModal: boolean;
  modalTitle?: "添加厂商" | "修改厂商"
}

class Manufacturer extends React.Component<IProps, IState> {
  private columns: ColumnProps<any>[];
  private companyName: string = "";
  private pagination: PaginationProps = {
    pageSize: 10,
    current: 1,
    total: 0,
    showSizeChanger: true,
    showTotal: total => '共 ' + total + ' 条'
  };
  private updateCompanyId?: string;
  constructor (props: any) {
    super(props);
    this.columns = [
      {
        title: '序号',
        dataIndex: 'index',
        key: 'index',
        width: 50,
        render: (text, row, index) => {
          let current = this.pagination.current || 1;
          let pageSize = this.pagination.pageSize || 10;
          return <div style={{textAlign: "center"}}>{(current - 1) * pageSize + index + 1}</div>
        }
      },
      {
        title: '厂商名称',
        dataIndex: 'companyName',
        key: 'companyName'
      },
      {
        title: '地址',
        dataIndex: 'address',
        key: 'address'
      },
      {
        title: '联系电话',
        dataIndex: 'phone',
        key: 'phone',
        width: 140,
      },
      {
        title: '创建时间',
        dataIndex: 'createTime',
        key: 'createTime',
        width: 200
      },
      {
        title: '操作',
        key: 'action',
        width: 140,
        render: (text, row) => {
          const handleUpdate = () => {
            this.updateCompanyId = row.companyId;
            this.handleUpdate(row);
          };
          const onConfirm = () => this.handleDelete(row.companyId);
          return (
          <>
            <a href="javascript:void 0" onClick={handleUpdate}>修改</a>
            <Divider type="vertical"/>
            <Popconfirm title="确定删除此厂商?" onConfirm={onConfirm}>
              <a href="javascript:void 0">删除</a>
            </Popconfirm>
          </>
          )
        }
      }
    ];
    this.state = {
      tableData: [],
      addCompanyModal: false
    }
  }

  componentWillMount(): void {
    this.handleSearch()
  }

  getCompanyList = async () => {
    const {data: {status, data, message}} = await http.get("http://localhost:8080/api/company/list", {
      params: {
        companyName: this.companyName,
        pageNum: this.pagination.current,
        pageSize: this.pagination.pageSize
      }
    });
    if (status === 1) {
      this.pagination.total = data.total;
      this.setState({
        tableData: data.result.map((item: any, index: number) => {
          item.key = index;
          return item
        })
      })

    } else {
      aMessage.error(message)
    }
  };

  handleInput = (e: any) => {
    this.companyName = e.target.value
  };

  handleSearch = () => {
    this.pagination.current = 1;
    this.getCompanyList()
  };

  handleTableChange = (pagination: PaginationProps) => {
    this.pagination = pagination;
    this.getCompanyList()
  };

  // 添加厂商
  handleAddCompany = () => {
    this.props.form.resetFields();
    this.updateCompanyId = undefined;
    this.setState({
      addCompanyModal: true,
      modalTitle: "添加厂商"
    });
  };

  handleModalOk = () => {
    this.props.form.validateFields((err: boolean, values: any) => {
      if (!err) {
        if (this.updateCompanyId) {
          this.updateCompany(values)
        } else {
          this.addCompany(values)
        }
      }
    })
  };

  addCompany = async (values: any) => {
    const {data: {status, message}} = await http.post(`http://localhost:8080/api/company`, values)
    if (status === 1) {
      aMessage.success("添加成功");
      this.handleHideModal();
      this.handleSearch();
    } else {
      aMessage.error(message)
    }
  };

  handleHideModal = () => {
    this.setState({
      addCompanyModal: false
    });
  };

  handleUpdate = (row: DataSourceItem) => {
    this.setState({
      addCompanyModal: true,
      modalTitle: "修改厂商"
    }, () => {
      this.props.form.setFieldsValue(row);
    });
  };

  updateCompany = async (values: any) => {
    const {data: {status, message}} = await http.put(`http://localhost:8080/api/company`, values, {
      params: {
        companyId: this.updateCompanyId
      }
    })
    if (status === 1) {
      aMessage.success("修改成功");
      this.handleHideModal();
      this.handleSearch();
    } else {
      aMessage.error(message)
    }
  };

  // 删除
  handleDelete = async (companyId: string) => {
    const {data: {status, message}} = await http.delete(`http://localhost:8080/api/company`, {
      params: {
        companyId
      }
    });
    if (status === 1) {
      aMessage.success("删除成功");
      this.handleSearch();
    } else {
      aMessage.error(message)
    }
  };

  render () {
    const {getFieldDecorator} = this.props.form;
    const formItemLayout = {
      labelCol: {
        xs: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 19, offset: 0 }
      },
    };
    return (
    <div className="p20">
      <div className="df aic jcsb">
        <div className="df aic">
          <Input placeholder="查询厂商名称" onChange={this.handleInput} style={{ width: '240px' }}/>
          <Button htmlType="button" type="primary" icon="search" href="javascript:void(0)" className="ml10" onClick={this.handleSearch}>查询</Button>
        </div>
        <Button htmlType="button" type="primary" icon="plus" href="javascript:void(0)" onClick={this.handleAddCompany}>添加厂商</Button>
        <Modal width={440} title={this.state.modalTitle} visible={this.state.addCompanyModal} onOk={this.handleModalOk} onCancel={this.handleHideModal}>
          <Form {...formItemLayout}>
            <Form.Item hasFeedback={true} label="厂商名称">
              {getFieldDecorator('companyName', {rules: [{required: true, message: '请输入厂商名称'}]})(<Input autoComplete="off" placeholder="请输入厂商名称" maxLength={20}/>)}
            </Form.Item>
            <Form.Item hasFeedback={true} label="地址">
              {getFieldDecorator('address', )(<Input autoComplete="off" placeholder="请输入厂商地址" maxLength={50}/>)}
            </Form.Item>
            <Form.Item hasFeedback={true} label="联系电话" style={{marginBottom: 0}}>
              {getFieldDecorator('phone', )(<Input autoComplete="off" placeholder="请输入联系电话" maxLength={20}/>)}
            </Form.Item>
          </Form>
        </Modal>
      </div>
      <Table dataSource={this.state.tableData} columns={this.columns} pagination={this.pagination} bordered={true} onChange={this.handleTableChange} className="mt20" size="small"/>
    </div>
    )
  }
}

export default Form.create<IProps>()(Manufacturer)
