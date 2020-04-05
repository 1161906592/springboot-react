package com.zhn.device.vo;

import com.zhn.common.annotation.ExportEntityField;
import lombok.Data;

import java.util.Date;

@Data
public class DeviceVO {
    // 设备id
    String id;
    // 设备名称
    String name;
    // 类型id
    String typeId;
    // 类型名称
    String typeName;
    // 设备规格
    String model;
    // 价格
    Double price;
    // 使用人id
    String userId;
    // 使用人名字
    String userName;
    // 定位
    String location;
    // 购买日期
    Date date;
    // 发票
    String invoice;
    // 备注
    String remark;
}
