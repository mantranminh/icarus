package com.github.mantranminh.icarus.utils;

public class CacheUtils {
    public static String getParticularCacheKey(String prefix, Object[] args, int stringArgLimit) {
        String key = String.join(":", ListUtils.extractStringArguments(args, stringArgLimit));
        return getParticularCacheKey(prefix, key);
    }

    public static String getParticularCacheKey(String prefix, String key) {
        return prefix + ":" + key;
    }
}
