package com.medicinalManage.common;

public class ResponseEntity<T> {
    private int status = 1;
    private String message = "";
    private T data;

    // 成功 无消息 无数据
    public ResponseEntity() { }
    // 成功 无消息 有数据
    public ResponseEntity(T data) {
        this.data = data;
    }

    // 有消息
    public ResponseEntity(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // 完整三项
    public ResponseEntity(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
