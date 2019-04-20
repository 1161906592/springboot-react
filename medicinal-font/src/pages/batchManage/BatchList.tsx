import React from 'react'
import {
  Input,
  Button,
  Table,
  Popconfirm,
  Divider,
  message as aMessage,
  Tabs,
  Form, Modal, Select, Spin
} from "antd";
import { Link, RouteComponentProps } from "react-router-dom";
import {ColumnProps} from "antd/lib/table";
import http from "../../utils/http";
import { PaginationProps } from "antd/lib/pagination";
import { FormComponentProps } from "antd/lib/form";

interface DataSourceItem {
  batchId: string,
  batchName: string,
  batchFlag: number,
  remarks: string
}

interface tabOption {
  key: number | '',
  text: string;
}

interface IProps extends FormComponentProps, RouteComponentProps {
}

interface IState {
  tableData: Array<DataSourceItem>;
  batchModal: boolean;
  tableLoading: boolean;
  confirmLoading: boolean;
}

class BatchList extends React.Component<IProps, IState> {
  private columns: ColumnProps<any>[];
  private batchName: string = "";
  private pagination: PaginationProps = {
    pageSize: 10,
    current: 1,
    total: 0,
    showSizeChanger: true,
    showTotal: total => '共 ' + total + ' 条'
  };
  private tabOptions: Array<tabOption> = [
    { key: '', text: '全部批次' },
    { key: 1, text: '入库批次' },
    { key: 2, text: '出库批次' },
  ];
  private tabIndex: number = 0;
  private modalTitle?: "添加批次" | "修改批次";
  private updateBatchId?: string;
  constructor (props: any) {
    super(props)
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
        title: '批次名称',
        dataIndex: 'batchName',
        key: 'batchName',
        render: (text, row) => <Link to={{ pathname: '/home/batch/detail', search: 'id=' + row.id }}>{text}</Link>
      },
      {
        title: '批次类型',
        dataIndex: 'batchFlag',
        key: 'batchFlag',
        render: (text, row) => ["入库", "出库"][row.batchFlag - 1]
      },
      {
        title: '备注',
        dataIndex: 'remark',
        key: 'remark'
      },
      {
        title: '创建时间',
        dataIndex: 'createTime',
        key: 'createTime',
        width: 200,
      },
      {
        title: '操作',
        key: 'action',
        width: 140,
        render: (text, row) => {
          const handleUpdate = () => {
            this.updateBatchId = row.batchId;
            this.handleUpdate(row);
          };
          const onConfirm = () => this.handleDelete(row.batchId);
          return (
          <>
            <a href="javascript:void 0" onClick={handleUpdate}>修改</a>
            <Divider type="vertical"/>
            <Popconfirm title="确认删除本批次?" onConfirm={onConfirm}>
              <a href="javascript:void 0">删除</a>
            </Popconfirm>
          </>
          )
        }
      }
    ]
    this.state = {
      tableData: [],
      batchModal: false,
      tableLoading: false,
      confirmLoading: false
    }
  }

  componentWillMount(): void {
    this.handleSearch()
  }

  handleSearch = () => {
    this.pagination.current = 1;
    this.getBatchList()
  };

  getBatchList = async () => {
    this.setState({
      tableLoading: true
    });
    try {
      const {data: {status, data, message}} = await http.get("http://localhost:8080/api/batch/list", {
        params: {
          batchName: this.batchName,
          batchFlag: this.tabOptions[this.tabIndex].key,
          pageSize: this.pagination.pageSize,
          pageNum: this.pagination.current
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
    } finally {
      this.setState({
        tableLoading: false
      });
    }
  };

  handleInput = (e: any) => {
    this.batchName = e.target.value
  };

  handleTabChange = (key: any) => {
    this.tabIndex = key;
    this.handleSearch();
  };

  handleTableChange = (pagination: PaginationProps) => {
    this.pagination = pagination;
    this.getBatchList()
  };

  handleAddBatch = () => {
    this.props.form.resetFields();
    this.updateBatchId = undefined;
    this.modalTitle = '添加批次';
    this.setState({
      batchModal: true
    });
  };

  handleModalOk = () => {
    this.props.form.validateFields(async (err: boolean, values: any) => {
      if (!err) {
        this.setState({
          confirmLoading: true
        });
        try {
          if (this.updateBatchId) {
            await this.updateBatch(values)
          } else {
            await this.addBatch(values)
          }
        } finally {
          this.setState({
            confirmLoading: false
          });
        }
      }
    })
  };

  updateBatch = async (values: any) => {
    const {data: {status, message}} = await http.put(`http://localhost:8080/api/batch`, values, {
      params: {
        batchId: this.updateBatchId
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

  addBatch = async (values: any) => {
    const {data: {status, message}} = await http.post(`http://localhost:8080/api/batch`, values);
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
      batchModal: false
    });
  };

  handleUpdate = (row: DataSourceItem) => {
    this.modalTitle = '修改批次';
    this.setState({
      batchModal: true
    }, () => {
      this.props.form.setFieldsValue(row);
    });
  };

  // 删除
  handleDelete = async (batchId: string) => {
    const {data: {status, message}} = await http.delete(`http://localhost:8080/api/batch`, {
      params: {
        batchId
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
      <div className="df aic jcsb mb20">
        <div className="df aic">
          <Input placeholder="查询入库批次名称" style={{ width: '240px' }} onInput={this.handleInput}/>
          <Button htmlType="button" type="primary" icon="search" href="javascript:void(0)" className="ml10" onClick={this.handleSearch}>查询</Button>
        </div>
        <Button htmlType="button" type="primary" icon="plus" href="javascript:void(0)" onClick={this.handleAddBatch}>新建批次</Button>
        <Modal width={440} title={this.modalTitle} visible={this.state.batchModal} confirmLoading={this.state.confirmLoading} onOk={this.handleModalOk} onCancel={this.handleHideModal}>
          <Form {...formItemLayout}>
            <Form.Item hasFeedback={true} label="批次名称">
              {getFieldDecorator('batchName', {rules: [{required: true, message: '请输入批次名称'}]})(<Input autoComplete="off" placeholder="请输入批次名称" maxLength={50}/>)}
            </Form.Item>
            <Form.Item hasFeedback={true} label="批次类型">
              {getFieldDecorator('batchFlag', {rules: [{required: true, message: '请选择批次类型'}]})(<Select placeholder="请选择批次类型"><Select.Option value={1}>入库</Select.Option><Select.Option value={2}>出库</Select.Option></Select>)}
            </Form.Item>
            <Form.Item hasFeedback={true} label="备注" style={{marginBottom: 0}}>
              {getFieldDecorator('remarks', )(<Input.TextArea autoComplete="off" placeholder="请输入厂商备注" rows={3} style={{resize: "none"}} maxLength={255}/>)}
            </Form.Item>
          </Form>
        </Modal>
      </div>
      <Spin spinning={this.state.tableLoading}>
        <Tabs defaultActiveKey="0" onChange={this.handleTabChange} type="card">
          {this.tabOptions.map((item, index) => (<Tabs.TabPane tab={item.text} key={index + ""}><Table dataSource={this.state.tableData} columns={this.columns} pagination={this.pagination} bordered={true} size="small" onChange={this.handleTableChange}/></Tabs.TabPane>))}
        </Tabs>
      </Spin>
    </div>
    )
  }
}

export default Form.create<IProps>()(BatchList)
