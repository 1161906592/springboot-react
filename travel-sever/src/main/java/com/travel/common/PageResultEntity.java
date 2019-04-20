package com.travel.common;

import com.github.pagehelper.Page;

import java.util.List;

public class PageResultEntity<T> {
    private List<T> result;
    private long total;

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public PageResultEntity(Page<T> page) {
        this.result = page.getResult();
        this.total = page.getTotal();
    }
}
