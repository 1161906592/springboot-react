package com.travel.common;

public enum ResponseStatus {
    SUCCESS(1, "SUCCESS"),
    FALSE(2, "FALSE"),
    ERROR(3, "ERROR"),
    NOT_LOGIN(4, "NOT_LOGIN"),
    FORBIDDEN(5, "FORBIDDEN");

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    private final int status;
    private final String desc;

    ResponseStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}