package com.zhn.device.controller;

import com.zhn.common.bean.PageParamsDTO;
import com.zhn.common.bean.PageResultVO;
import com.zhn.common.bean.ResponseVO;
import com.zhn.common.utils.ExportExcelUtils;
import com.zhn.device.dto.DeviceInsertDTO;
import com.zhn.device.dto.DeviceQueryDTO;
import com.zhn.device.dto.DeviceUpdateDTO;
import com.zhn.device.service.DeviceService;
import com.zhn.device.vo.DeviceExportVO;
import com.zhn.device.vo.DeviceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    DeviceService deviceService;

    @GetMapping("/pageDevice")
    public ResponseVO<PageResultVO<DeviceVO>> getDeviceList(@Validated DeviceQueryDTO deviceQueryDTO, @Validated PageParamsDTO pageParamsDTO, HttpServletRequest request) {
        PageResultVO<DeviceVO> deviceVoPageResultVO = deviceService.getDeviceList(deviceQueryDTO, pageParamsDTO, request);
        return new ResponseVO<>(deviceVoPageResultVO);
    }

    @PostMapping("/device")
    public ResponseVO addDevice(@Validated @RequestBody DeviceInsertDTO insertDTO) throws Exception {
        deviceService.insertDevice(insertDTO);
        return new ResponseVO<>();
    }

    @PutMapping("/device")
    public ResponseVO updateDevice(@Validated @RequestBody DeviceUpdateDTO updateDTO) throws Exception {
        deviceService.updateDevice(updateDTO);
        return new ResponseVO<>();
    }

    @DeleteMapping("/device")
    public ResponseVO deleteDevice(@RequestParam String id) {
        deviceService.deleteDevice(id);
        return new ResponseVO<>();
    }

    @GetMapping("/exportDevice")
    public void exportDevice(@Validated DeviceQueryDTO deviceQueryDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<DeviceExportVO> deviceVOS = deviceService.exportDeviceList(deviceQueryDTO, request);
        ExportExcelUtils.export("设备信息", deviceVOS, DeviceExportVO.class, response);
    }
}
