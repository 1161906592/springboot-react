package com.zhn.common.bean;

import lombok.Data;

import javax.validation.constraints.Min;

// 分页参数
@Data
public class PageParamsDTO {
    @Min(value = 1, message = "页码不能小于1")
    private int pageNum = 1;
    @Min(value = 1, message = "每一页的数量不能小于1")
    private int pageSize = 5;
}
