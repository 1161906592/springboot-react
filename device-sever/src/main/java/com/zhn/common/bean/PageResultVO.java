package com.zhn.common.bean;

import com.github.pagehelper.Page;
import lombok.Data;

import java.util.List;

// 分页返回公共实体
@Data
public class PageResultVO<T> {
    private List<T> result;
    private long total;

    public PageResultVO(Page<T> page) {
        this.result = page.getResult();
        this.total = page.getTotal();
    }
}
