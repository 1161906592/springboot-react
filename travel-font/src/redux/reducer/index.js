/**
 * @Author: liuyang
 * @Date: 2019-04-21 14:40
 */
import { SET_USER } from "@redux/action";

export default (state, action) => {
  const { preload } = action;
  switch (action.type) {
    case SET_USER:
      return { ...state, user: preload.user };
    default:
      return state;
  }
}
