package com.travel.controller.scenic;

import com.travel.common.PageParams;
import com.travel.common.PageResultEntity;
import com.travel.common.ResponseEntity;
import com.travel.model.scenic.ScenicVo;
import com.travel.service.scenic.ScenicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScenicController {
    @Autowired
    ScenicService scenicService;

    @GetMapping("/scenic")
    public ResponseEntity<PageResultEntity<ScenicVo>> getScenic(String name, @Validated PageParams pageParams) {
        return new ResponseEntity<>(scenicService.getScenic(name, pageParams));
    }
}
