import React from "react";
import RouterView from "./router/RouterView";
import { LocaleProvider } from "antd";
import zhCN from "antd/lib/locale-provider/zh_CN";
import "antd/lib/style/index.css";
import "./app.scss";
import hideGlobalLoading from "./utils/base/hideGlobalLoading";
import { connect, Provider } from "react-redux";
import store from "@redux/store";
import { setUser } from "@redux/action";

hideGlobalLoading();

const mapDispatchToProps = (dispatch, ownProps) => ({
  setUser: (user) => {
    dispatch(setUser(user));
  }
});

@connect(null, mapDispatchToProps)
class ReduxWrapper extends React.Component {
  componentWillMount () {
    this.props.setUser()
  }

  render () {
    return (
    <LocaleProvider locale={zhCN}>
      <RouterView />
    </LocaleProvider>
    );
  }
}

const App = () => (
<Provider store={store}>
  <ReduxWrapper />
</Provider>
);

export default App;
