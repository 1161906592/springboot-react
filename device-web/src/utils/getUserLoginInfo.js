function getUserLoginInfo() {
  const userInfoStr = localStorage.getItem("userInfo");
  if (userInfoStr) {
    return JSON.parse(userInfoStr);
  }
  return {
    read: false,
    write: false,
    displayName: "",
    isAdmin: 0,
    userId: ""
  };
}

export default getUserLoginInfo;
