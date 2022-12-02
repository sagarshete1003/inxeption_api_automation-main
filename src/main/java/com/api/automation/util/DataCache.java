package com.api.automation.util;

import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class DataCache {
    public static ThreadLocal<Map<String, Response>> serviceResponseCache = new ThreadLocal<>();
    public static ThreadLocal<Map<String, String>> variableCache = new ThreadLocal<Map<String, String>>();

    public static void saveVariable(String key, String value) {
        Map<String, String> dataMap = variableCache.get();
        if(dataMap==null)
            dataMap = new HashMap<>();
        dataMap.put(key, value);
        variableCache.set(dataMap);
    }

    public static String readVariable(String key) {
        Map<String, String> dataMap = variableCache.get();
        if(dataMap==null)
            return key;
        return variableCache.get().get(key);
    }

    public static void saveResponse(String key, Response response) {
        Map<String, Response> data = serviceResponseCache.get();
        if(data==null)
            data = new HashMap<>();
        data.put(key, response);
        serviceResponseCache.set(data);
    }

    public static Response readResponse(String key) {
        return serviceResponseCache.get().get(key);
    }



}
