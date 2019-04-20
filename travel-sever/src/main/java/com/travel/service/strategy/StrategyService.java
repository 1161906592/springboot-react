package com.travel.service.strategy;

import com.travel.model.scenic.ScenicDto;

import java.util.List;

public interface StrategyService {
    List<List<String>> getStrategy(ScenicDto scenicDto);
}
