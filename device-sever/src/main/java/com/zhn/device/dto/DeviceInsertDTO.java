package com.zhn.device.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class DeviceInsertDTO {
    // 设备id
    String id;
    // 设备名称
    @NotBlank(message = "设备名称不能为空")
    String name;
    // 类型id
    @NotBlank(message = "类型id不能为空")
    String typeId;
    // 设备规格
    @NotBlank(message = "设备规格不能为空")
    String model;
    // 价格
    @NotNull(message = "价格不能为空")
    Double price;
    // 使用人
    @NotBlank(message = "使用人不能为空")
    String userId;
    // 定位
    @NotBlank(message = "定位不能为空")
    String location;
    // 购买日期
    @NotNull(message = "购买日期不能为空")
    Date date;
    // 发票
    String invoice;
    // 备注
    String remark;
}
