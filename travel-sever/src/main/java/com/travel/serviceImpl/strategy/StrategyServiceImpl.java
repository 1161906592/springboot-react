package com.travel.serviceImpl.strategy;

import com.travel.dao.Dao;
import com.travel.model.scenic.ScenicDto;
import com.travel.model.strategy.StrategyVo;
import com.travel.service.strategy.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StrategyServiceImpl implements StrategyService {
    @Autowired
    Dao dao;
    @Override
    public List<List<String>> getStrategy(ScenicDto scenicDto) {
        List<StrategyVo> strategyVoList = dao.getList("com.travel.mapper.strategy.StrategyMapper.getStrategy", null);
        List<List<String>> allRouteList = new ArrayList<>();
        for (int i = 0; i < scenicDto.getDuration(); i++) {
            StrategyAlgorithm.getDayRoute(allRouteList, scenicDto, strategyVoList);
        }
        return allRouteList;
    }
}
