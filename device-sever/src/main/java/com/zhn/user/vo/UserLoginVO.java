package com.zhn.user.vo;

import lombok.Data;

@Data
public class UserLoginVO {
    private String id;
    private String name;
    private String tel;
    private int role;
    private String token;
}
