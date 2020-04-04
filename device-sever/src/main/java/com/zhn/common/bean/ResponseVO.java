package com.zhn.common.bean;

public class ResponseVO<T> {
    private boolean status = true;
    private String message = "";
    private T data;

    // 成功 无消息 无数据
    public ResponseVO() { }
    // 成功 无消息 有数据
    public ResponseVO(T data) {
        this.data = data;
    }

    // 有消息
    public ResponseVO(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    // 完整三项
    public ResponseVO(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
