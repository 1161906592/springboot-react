package com.zhn.device.service;

import com.zhn.common.bean.PageParamsDTO;
import com.zhn.common.bean.PageResultVO;
import com.zhn.device.dto.DeviceInsertDTO;
import com.zhn.device.dto.DeviceQueryDTO;
import com.zhn.device.dto.DeviceUpdateDTO;
import com.zhn.device.vo.DeviceVO;

public interface DeviceService {
    PageResultVO<DeviceVO> getDeviceList(DeviceQueryDTO deviceQueryDTO, PageParamsDTO pageParamsDTO);
    void insertDevice(DeviceInsertDTO insertDTO);
    void updateDevice(DeviceUpdateDTO updateDTO);
    void deleteDevice(String id);
}
