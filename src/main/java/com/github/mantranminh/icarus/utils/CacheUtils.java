package com.github.mantranminh.icarus.utils;

import java.util.stream.Collectors;

public class CacheUtils {
    public static String getParticularCacheKey(String prefix, Object[] args, int stringArgLimit) {
        String key = ListUtils.extractStringArguments(args, stringArgLimit).stream()
                .collect(Collectors.joining(":"));
        return prefix + key;
    }
}
