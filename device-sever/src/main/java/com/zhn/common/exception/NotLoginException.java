package com.zhn.common.exception;

public class NotLoginException extends Exception {
    public NotLoginException() {
        super("请先登录系统");
    }
}
