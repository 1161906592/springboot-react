/**
 * @Author: liuyang
 * @Date: 2019-04-21 14:40
 */
import { applyMiddleware, createStore } from "redux";
import promise from 'redux-promise';
import reducer from "@redux/reducer";

const storeStates = {
  a: 100,
  user: {}
};

export default createStore(reducer, storeStates, applyMiddleware(promise));
