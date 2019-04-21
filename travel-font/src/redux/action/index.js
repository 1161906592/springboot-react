/**
 * @Author: liuyang
 * @Date: 2019-04-21 14:40
 */
import http from "@utils/http";

export const SET_USER = "setUser";

const getUserBasicInfo = async () => {
  const { data: { status, data } } = await http.get("http://localhost:8080/api/user/basicInfo");
  if (status === 1) {
    return {
      user: data
    };
  } else {
    return {
      user: {}
    };
  }
};

export const setUser = async () => {
  return {
    type: SET_USER,
    preload: await getUserBasicInfo()
  };
};
