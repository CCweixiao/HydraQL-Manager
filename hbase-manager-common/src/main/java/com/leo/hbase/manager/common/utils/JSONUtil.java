package com.leo.hbase.manager.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leo.hbase.manager.common.exception.BusinessException;

/**
 * @author leojie 2020/11/29 2:49 下午
 */
public class JSONUtil {
    public static void checkHasKey(JSONObject jsonObj, String key) {
        if(jsonObj == null){
            throw new BusinessException("json obj is null.");
        }

        if (!jsonObj.containsKey(key)) {
            throw new BusinessException("key " + key + " is not in json str.");
        }
    }

    public static String getStrV(JSONObject jsonObj, String key){
        checkHasKey(jsonObj, key);
        return jsonObj.getString(key);
    }

    public static JSONArray getJsonArr(JSONObject jsonObj, String key){
        checkHasKey(jsonObj, key);
        return jsonObj.getJSONArray(key);
    }


}
