package com.medicinalManage.dao;

import com.github.pagehelper.Page;
import com.medicinalManage.common.PageParams;

import java.util.List;

public interface Dao {
    public <T> T getOne(String sqlId, Object args);
    public <E> List<E> getList(String sqlId, Object args);
    public <E> Page<E> getPageList(String sqlId, Object args, PageParams pageParams);
    public int update(String sqlId,Object args);
    public int insert(String sqlId, Object args);
    public int delete(String sqlId,Object args);
}
