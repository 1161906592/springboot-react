package com.zhn.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserInsertDTO {
    private String id;
    @NotBlank(message = "用户名不能为空")
    private String name;
    @NotNull(message = "角色不能为空")
    private Integer role;
    @NotBlank(message = "电话不能为空")
    private String tel;
}
