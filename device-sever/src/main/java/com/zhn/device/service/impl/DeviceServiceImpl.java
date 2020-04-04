package com.zhn.device.service.impl;

import com.github.pagehelper.Page;
import com.zhn.common.bean.PageParamsDTO;
import com.zhn.common.bean.PageResultVO;
import com.zhn.common.dao.Dao;
import com.zhn.common.utils.StringUtils;
import com.zhn.device.dto.DeviceInsertDTO;
import com.zhn.device.dto.DeviceQueryDTO;
import com.zhn.device.dto.DeviceUpdateDTO;
import com.zhn.device.service.DeviceService;
import com.zhn.device.vo.DeviceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    Dao dao;

    @Override
    public PageResultVO<DeviceVO> getDeviceList(DeviceQueryDTO deviceQueryDTO, PageParamsDTO pageParamsDTO) {
        Page<DeviceVO> deviceVoPage = dao.getPageList("com.zhn.device.DeviceMapper.getDeviceList", deviceQueryDTO, pageParamsDTO);
        return new PageResultVO<>(deviceVoPage);
    }

    @Override
    public void insertDevice(DeviceInsertDTO insertDTO) {
        String id = StringUtils.getUUid();
        insertDTO.setId(id);
        dao.insert("com.zhn.device.DeviceMapper.insertDevice", insertDTO);
    }

    @Override
    public void updateDevice(DeviceUpdateDTO updateDTO) {
        dao.update("com.zhn.device.DeviceMapper.updateDevice", updateDTO);
    }

    @Override
    public void deleteDevice(String id) {
        dao.delete("com.zhn.device.DeviceMapper.deleteDevice", id);
    }
}
