package com.travel.serviceImpl.scenic;

import com.github.pagehelper.Page;
import com.travel.common.PageParams;
import com.travel.common.PageResultEntity;
import com.travel.dao.Dao;
import com.travel.model.scenic.ScenicVo;
import com.travel.service.scenic.ScenicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScenicServiceImpl implements ScenicService {
    @Autowired
    Dao dao;
    @Override
    public PageResultEntity<ScenicVo> getScenic(String name, PageParams pageParams) {
        Page<ScenicVo> scenicVoPage = dao.getPageList("com.travel.mapper.scenic.ScenicMapper.getScenic", name, pageParams);
        return new PageResultEntity<>(scenicVoPage);
    }
}
