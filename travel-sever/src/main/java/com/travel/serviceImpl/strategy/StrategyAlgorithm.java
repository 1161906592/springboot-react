package com.travel.serviceImpl.strategy;

import com.travel.model.scenic.ScenicDto;
import com.travel.model.strategy.MaxWeight;
import com.travel.model.strategy.StrategyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class StrategyAlgorithm {

    @Autowired
    RestTemplate restTemplate;
    private static RestTemplate client;

    @PostConstruct // 使静态方法中能够调用注入
    public void init() {
        client = restTemplate;
    }

    public static void getDayRoute(List<List<String>> allRouteList, ScenicDto scenicDto, List<StrategyVo> strategyVoList) {
        List <String> currentDayRouteList = new ArrayList<>();
        String currentAddress = scenicDto.getAddress();
        if (allRouteList.size() > 0) {
            List<String> lastDayRoute = allRouteList.get(allRouteList.size() - 1);
            if (lastDayRoute.size() > 0) {
                currentAddress = lastDayRoute.get(lastDayRoute.size() - 1);
            }
        }
        currentDayRouteList.add(currentAddress);
        int currentTime = 0;
        while (currentTime < 11) {
            MaxWeight maxWeight = new MaxWeight();
            maxWeight.setName(null);
            maxWeight.setTime(0);
            maxWeight.setWeight(0);
            for (StrategyVo strategyVo: strategyVoList) {
                if (!isExist(allRouteList, currentDayRouteList, strategyVo.getName())) {
                    double weight = scenicWeight(strategyVo, scenicDto, currentTime);
                    if (weight > maxWeight.getWeight()) {
                        maxWeight.setName(strategyVo.getName());
                        maxWeight.setWeight(weight);
                        maxWeight.setTime(strategyVo.getTime());
                    }
                }
            }
            currentDayRouteList.add(maxWeight.getName());
            currentTime += maxWeight.getTime();
        }
        currentAddress = currentDayRouteList.get(currentDayRouteList.size() - 1);
        // 请求住宿地作为最后一个点 如果不存在 就以当前的最后一个点为最后一个点
        String placeTextUrl = "https://restapi.amap.com/v3/place/text?key=ffa727d4fd33c5ea05b9d1ed6af6d900&keywords={keywords}&types=110000&city=重庆&children=1&offset=1&page=1&extensions=all";
        Map placeTexMap = client.getForObject(placeTextUrl, Map.class, currentAddress);
        List<Map<String, Object>> placeTextPoisList = new ArrayList<>();
        if (placeTexMap != null && placeTexMap.get("pois") != null) {
            placeTextPoisList = (List<Map<String, Object>>) placeTexMap.get("pois");
        }
        String entryLocation = "";
        if (placeTextPoisList.size() > 0) {
            entryLocation = placeTextPoisList.get(0).get("entr_location").toString();
        }
        if (!entryLocation.equals("")) {
            String placeAroundUrl = "https://restapi.amap.com/v3/place/around?key=ffa727d4fd33c5ea05b9d1ed6af6d900&location={entryLocation}&keywords=&types=100000&radius=1000&offset=1&page=1&extensions=all";
            Map placeAroundMap = client.getForObject(placeAroundUrl, Map.class, entryLocation);
            List<Map<String, Object>> placeAroundPoisList = new ArrayList<>();
            if (placeAroundMap != null && placeAroundMap.get("pois") != null) {
                placeAroundPoisList = (List<Map<String, Object>>) placeAroundMap.get("pois");
            }
            String currentPlace = "";
            if (placeAroundPoisList.size() > 0) {
                currentPlace = placeAroundPoisList.get(0).get("name").toString();
            }
            if (!currentPlace.equals("")) {
                currentAddress = currentPlace;
            }
        }
        currentDayRouteList.add(currentAddress);
        allRouteList.add(currentDayRouteList);
    }
    private static boolean isExist (List<List<String>> allRouteList, List<String> currentDayRouteList, String currentScenicName) {
        for (List<String> routeList: allRouteList) {
            for (String scenicName: routeList) {
                if (scenicName.equals(currentScenicName)) {
                    return true;
                }
            }
        }
        for (String scenicName: currentDayRouteList) {
            if (scenicName.equals(currentScenicName)) {
                return true;
            }
        }
        return false;
    }
    private static double scenicWeight (StrategyVo scenicObj, ScenicDto scenicDto, int currentTime) {
        //暂时假定到达景点的时间为1小时
        int roadTime = 2;
        //计算权重,
        double weight = 0;
        //景点类型权重 权重20
        if (scenicObj.getType() == scenicDto.getType()) {
            weight = weight + 20;
        }
        //时间段权重，权重10
        if (currentTime > 18 && scenicObj.getPlayTime() == 2) {
            weight += 10;
        }
        //如果不是最佳游玩时间段，减去20权重
        if (currentTime < 18 && scenicObj.getPlayTime() == 2) {
            weight -= 20;
        }
        //星级权重，权重20
        weight += scenicObj.getScore() * 4;
        //游玩时间占比权重（路上的时间与游玩时间的占比）权重10
        weight += ((double) scenicObj.getTime() / (scenicObj.getTime() + roadTime)) * 10;
        //如果剩余时间不够游玩该景点，减去20权重
        if (currentTime + scenicObj.getTime() > 22) {
            weight -= 20;
        }
        return weight;
    }
}
