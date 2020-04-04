package com.zhn.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserUpdateDTO extends UserInsertDTO {
    @NotBlank(message = "用户id不能为空")
    String id;
    String password;
}
