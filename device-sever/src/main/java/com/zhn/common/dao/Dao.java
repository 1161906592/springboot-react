package com.zhn.common.dao;

import com.github.pagehelper.Page;
import com.zhn.common.bean.PageParamsDTO;

import java.util.List;

public interface Dao {
    public <T> T getOne(String sqlId, Object args);
    public <E> List<E> getList(String sqlId);
    public <E> List<E> getList(String sqlId, Object args);
    public <E> Page<E> getPageList(String sqlId, PageParamsDTO pageParams);
    public <E> Page<E> getPageList(String sqlId, Object args, PageParamsDTO pageParams);
    public int update(String sqlId, Object args);
    public int insert(String sqlId, Object args);
    public int delete(String sqlId, Object args);
}
