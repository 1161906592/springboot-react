package com.travel.utils;

import com.travel.common.CacheEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class CacheUtils {
    // 缓存对象
    private static final Map<String, CacheEntity> cacheMap = new HashMap<>();
    // 过期时间 ms
    private static final int delayTime = 1000 * 30 * 60;

    @Scheduled(initialDelay = delayTime, fixedDelay = delayTime)
    private void checkDelay() {
        for (String key: cacheMap.keySet()) {
            long time = cacheMap.get(key).getTime();
            if (new Date().getTime() - time > delayTime) {
                cacheMap.remove(key);
            }
        }
    }

    public static String setCache(Object object) {
        String cacheId = StringUtils.getUUid();
        CacheEntity cacheEntity = new CacheEntity();
        cacheEntity.setTime(new Date().getTime());
        cacheEntity.setObject(object);
        cacheMap.put(cacheId, cacheEntity);
        return cacheId;
    }

    public static Object getCache(String cacheId) {
        CacheEntity cacheEntity = cacheMap.get(cacheId);
        if (cacheEntity == null) {
            return null;
        }
        return cacheEntity.getObject();
    }

    public static void updateCacheTime(String cacheId) {
        CacheEntity cacheEntity = cacheMap.get(cacheId);
        if (cacheEntity != null) {
            cacheEntity.setTime(new Date().getTime());
        }
    }

    public static void removeCache(String cacheId) {
        cacheMap.remove(cacheId);
    }
}
