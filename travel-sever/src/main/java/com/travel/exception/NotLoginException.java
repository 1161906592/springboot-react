package com.travel.exception;

public class NotLoginException extends Exception {
    public NotLoginException() {
        super("请先登录系统");
    }
}
