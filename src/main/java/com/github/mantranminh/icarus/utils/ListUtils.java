package com.github.mantranminh.icarus.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static List<String> extractStringArguments(Object[] args, int limit) {
        List<String> strArgs = new ArrayList<>();
        for (Object obj : args) {
            if (obj instanceof String) {
                strArgs.add(String.valueOf(obj));
                if (limit > 0 && strArgs.size() == limit) {
                    break;
                }
            }
        }
        return strArgs;
    }
}
