package com.zhn.user.bean;

import lombok.Data;

@Data
public class User {
    String id;
    String name;
    String password;
    int role;
    String tel;
}
