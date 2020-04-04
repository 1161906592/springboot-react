import React from "react";

const Loading = ({ pastDelay, error }) => {
  if (pastDelay) {
    return null;
  } else if (error) {
    return <div>当前应用已发布到最新版，请刷新你的浏览器.</div>;
  } else {
    return null;
  }
};

export default Loading;
