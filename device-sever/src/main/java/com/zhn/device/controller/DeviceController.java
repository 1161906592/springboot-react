package com.zhn.device.controller;

import com.zhn.common.bean.PageParamsDTO;
import com.zhn.common.bean.PageResultVO;
import com.zhn.common.bean.ResponseVO;
import com.zhn.device.dto.DeviceInsertDTO;
import com.zhn.device.dto.DeviceQueryDTO;
import com.zhn.device.dto.DeviceUpdateDTO;
import com.zhn.device.service.DeviceService;
import com.zhn.device.vo.DeviceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    DeviceService deviceService;

    @GetMapping("/pageDevice")
    ResponseVO<PageResultVO<DeviceVO>> getDeviceList(@Validated DeviceQueryDTO deviceQueryDTO, @Validated PageParamsDTO pageParamsDTO) {
        PageResultVO<DeviceVO> deviceVoPageResultVO = deviceService.getDeviceList(deviceQueryDTO, pageParamsDTO);
        return new ResponseVO<>(deviceVoPageResultVO);
    }

    @PostMapping("/device")
    ResponseVO addDevice(@Validated @RequestBody DeviceInsertDTO insertDTO) throws Exception {
        deviceService.insertDevice(insertDTO);
        return new ResponseVO<>();
    }

    @PutMapping("/device")
    ResponseVO updateDevice(@Validated @RequestBody DeviceUpdateDTO updateDTO) throws Exception {
        deviceService.updateDevice(updateDTO);
        return new ResponseVO<>();
    }

    @DeleteMapping("/device")
    ResponseVO deleteDevice(@RequestParam String id) {
        deviceService.deleteDevice(id);
        return new ResponseVO<>();
    }
}
