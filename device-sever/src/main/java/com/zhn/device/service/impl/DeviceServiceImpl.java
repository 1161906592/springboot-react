package com.zhn.device.service.impl;

import com.github.pagehelper.Page;
import com.zhn.common.bean.PageParamsDTO;
import com.zhn.common.bean.PageResultVO;
import com.zhn.common.bean.SessionEntity;
import com.zhn.common.dao.Dao;
import com.zhn.common.utils.StringUtils;
import com.zhn.device.dto.DeviceInsertDTO;
import com.zhn.device.dto.DeviceQueryDTO;
import com.zhn.device.dto.DeviceUpdateDTO;
import com.zhn.device.service.DeviceService;
import com.zhn.device.vo.DeviceExportVO;
import com.zhn.device.vo.DeviceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    Dao dao;

    @Override
    public PageResultVO<DeviceVO> getDeviceList(DeviceQueryDTO deviceQueryDTO, PageParamsDTO pageParamsDTO, HttpServletRequest request) {
        Page<DeviceVO> deviceVoPage = dao.getPageList("com.zhn.device.DeviceMapper.getDeviceList", deviceQueryDTO, pageParamsDTO);
        SessionEntity sessionEntity = (SessionEntity) request.getSession().getAttribute("sessionEntity");
        // 非系统管理员
        if (sessionEntity.getRole() != 1) {
            deviceVoPage.getResult().forEach(deviceVo -> {
                deviceVo.setInvoice(null);
                deviceVo.setPrice(null);
            });
        }
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

    @Override
    public List<DeviceExportVO> exportDeviceList(DeviceQueryDTO deviceQueryDTO, HttpServletRequest request) {
        List<DeviceVO> deviceVOList = dao.getList("com.zhn.device.DeviceMapper.getDeviceList", deviceQueryDTO);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<DeviceExportVO> deviceExportVOList = new ArrayList<>();
        deviceVOList.forEach(deviceVO -> {
            DeviceExportVO deviceExportVO = new DeviceExportVO();
            deviceExportVO.setName(deviceVO.getName());
            deviceExportVO.setTypeId(deviceVO.getTypeId());
            deviceExportVO.setModel(deviceVO.getModel());
            deviceExportVO.setUserName(deviceVO.getUserName());
            deviceExportVO.setLocation(deviceVO.getLocation());
            deviceExportVO.setDate(df.format(deviceVO.getDate()));
            deviceExportVO.setRemark(deviceVO.getRemark());
            deviceExportVO.setInvoice(deviceVO.getInvoice());
            deviceExportVO.setPrice(deviceVO.getPrice());
            deviceExportVOList.add(deviceExportVO);
        });
        return deviceExportVOList;
    }
}
