/**
 * @Author: zhouhaonan
 * @Date: 2020-4-4 14:10
 */
import React from "react";
import { HashRouter, Switch, Route, Redirect } from "react-router-dom";
import routes from "./routes";

const createRouteTree = (routeMap) => {
  const children = routeMap.map(item => {
    let redirect = null;
    if (item.redirect) {
      const render = () => <Redirect to={{ pathname: item.redirect() }} />;
      redirect = <Route exact path={item.path} key={item.path} render={render} />;
    }
    if (item.component) {
      if (item.children) {
        return (
          <item.component path={item.path} key={item.path}>
            {createRouteTree(item.children)}
          </item.component>
        );
      }
      return <Route exact path={item.path} key={item.path} component={item.component} />;
    }
    return redirect;
  });
  return (
    <Switch>
      {children}
      {/*<Redirect to={{ pathname: "/404" }} />*/}
    </Switch>
  );
};

const RouterView = () => {
  return <HashRouter>{createRouteTree(routes)}</HashRouter>;
};

export default RouterView;
