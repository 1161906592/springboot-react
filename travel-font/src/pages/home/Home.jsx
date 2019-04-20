import React from "react";
import { Button, Input, message as aMessage, Pagination } from "antd";
import style from "./home.module.scss";
import http from "@utils/http";

class Home extends React.Component {
  constructor (props) {
    super(props);
    this.state = {
      scenicData: [],
      total: 0
    };
  }

  pageParams = {
    pageNum: 1,
    pageSize: 5
  };
  name = "";
  getScenic = async () => {
    const { data: { status, data, message } } = await http.get("http://localhost:8080/api/scenic", {
      params: Object.assign({}, this.pageParams, { name: this.name })
    });
    if (status === 1) {
      this.setState({
        scenicData: data.result,
        total: data.total
      });
    } else {
      aMessage.error(message);
    }
  };
  handleInput = (e) => {
    this.name = e.target.value;
  };
  handlePageChange = (pageNum, pageSize) => {
    this.pageParams = { pageNum, pageSize };
    this.getScenic();
    document.documentElement.scrollTop = document.body.scrollTop = 0
  };

  componentWillMount () {
    this.getScenic();
  }

  render () {
    const { scenicData, total } = this.state;
    return (
    <div className={style.main}>
      <div className={"df p20 " + style.bb}>
        <Input placeholder="搜索旅游景点" onInput={this.handleInput} />
        <Button type="primary" className="ml10" onClick={this.getScenic}>搜索</Button>
      </div>
      <div>
        {scenicData.map((item, index) => {
          return (
          <div key={index} className={"df p20 " + style.bb}>
            <div className={"flxn " + style.img}>
              <img src={item.img} alt="" />
            </div>
            <div className={`ml20 ${style.right}`}>
              <div className={`fsz18 fwb ${style.name} ${style.ellipsis}`}>{item.name}</div>
              <div className={`mt10 fsz12 ${style.address} ${style.ellipsis}`}>{item.address}</div>
              <div className={style.introduce}>{item.introduce}</div>
            </div>
          </div>
          );
        })}
      </div>
      <div className="pt20 pb20 df jcc">
        <Pagination defaultCurrent={1} defaultPageSize={5} onChange={this.handlePageChange} total={total} />
      </div>
    </div>
    );
  }
}

export default Home;
