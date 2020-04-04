package com.zhn.device.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeviceUpdateDTO extends DeviceInsertDTO {
    @NotBlank(message = "设备id不能为空")
    String id;
}
