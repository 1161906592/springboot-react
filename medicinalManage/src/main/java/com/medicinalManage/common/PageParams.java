package com.medicinalManage.common;

import javax.validation.constraints.Min;

public class PageParams {
    @Min(value = 1, message = "页码不能小于1")
    private int pageNum = 1;
    @Min(value = 1, message = "每一页的数量不能小于1")
    private int pageSize = 5;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
