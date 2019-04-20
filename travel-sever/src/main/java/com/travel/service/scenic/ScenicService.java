package com.travel.service.scenic;

import com.travel.common.PageParams;
import com.travel.common.PageResultEntity;
import com.travel.model.scenic.ScenicVo;

public interface ScenicService {
    PageResultEntity<ScenicVo> getScenic(String name, PageParams pageParams);
}
