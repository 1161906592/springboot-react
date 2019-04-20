package com.medicinalManage.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.medicinalManage.common.PageParams;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DaoImpl implements Dao {
    @Autowired
    SqlSession sqlSession;

    @Override
    public <T> T getOne(String sqlId, Object args) {
        return sqlSession.selectOne(sqlId, args);
    }

    @Override
    public <E> List<E> getList(String sqlId, Object args) {
        return sqlSession.selectList(sqlId, args);
    }

    @Override
    public <E> Page<E> getPageList(String sqlId, Object args, PageParams pageParams) {
        PageHelper.startPage(pageParams.getPageNum(), pageParams.getPageSize());
        return (Page) sqlSession.selectList(sqlId, args);
    }

    @Override
    public int update(String sqlId, Object args) {
        return sqlSession.update(sqlId, args);
    }

    @Override
    public int insert(String sqlId, Object args) {
        return sqlSession.insert(sqlId, args);
    }

    @Override
    public int delete(String sqlId, Object args) {
        return sqlSession.delete(sqlId, args);
    }
}
