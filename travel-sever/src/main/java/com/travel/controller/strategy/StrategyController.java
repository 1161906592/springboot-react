package com.travel.controller.strategy;

import com.travel.common.ResponseEntity;
import com.travel.model.scenic.ScenicDto;
import com.travel.service.strategy.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StrategyController {
    @Autowired
    StrategyService strategyService;
    @GetMapping("/strategy")
    public ResponseEntity<List> getStrategy (ScenicDto scenicDto) {
        List<List<String>> list = strategyService.getStrategy(scenicDto);
        return new ResponseEntity<>(list);
    }
}
