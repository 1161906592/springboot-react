package com.travel.utils;

import java.util.UUID;

public class StringUtils {
    public static String getUUid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
