package com.zhn.device.vo;

import com.zhn.common.annotation.ExportEntityField;
import lombok.Data;

@Data
public class DeviceExportVO {
    // 设备名称
    @ExportEntityField(CnName = "名称", EnName = "name")
    String name;
    // 类型名称
    @ExportEntityField(CnName = "类型", EnName = "typeId")
    String typeId;
    // 设备规格
    @ExportEntityField(CnName = "规格", EnName = "model")
    String model;
    // 价格
    @ExportEntityField(CnName = "规格", EnName = "price")
    Double price;
    // 使用人名字
    @ExportEntityField(CnName = "使用人", EnName = "userName")
    String userName;
    // 定位
    @ExportEntityField(CnName = "定位", EnName = "location")
    String location;
    // 购买日期
    @ExportEntityField(CnName = "购买日期", EnName = "date")
    String date;
    // 发票
    @ExportEntityField(CnName = "发票", EnName = "invoice")
    String invoice;
    // 备注
    @ExportEntityField(CnName = "备注", EnName = "remark")
    String remark;
}
