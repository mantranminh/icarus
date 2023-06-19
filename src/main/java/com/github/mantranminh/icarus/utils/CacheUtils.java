package com.github.mantranminh.icarus.utils;

public class CacheUtils {
    public static String getParticularCacheKey(String prefix, Object[] args, int stringArgLimit) {
        String key = String.join(":", ListUtils.extractStringArguments(args, stringArgLimit));
        return prefix + ":" + key;
    }
}
