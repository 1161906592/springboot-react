package com.travel.exception;

public class ForbiddenException extends Exception {
    public ForbiddenException() {
        super("请求无权限");
    }
}
