package com.ydd.pockettoycatcherrwz.util;


import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by zhushouwen on 2017/8/14.
 */

public class JSONUtil {
    /**
     * 将json转化为实体POJO
     * @param jsonStr
     * @param obj
     * @return
     */
    public static <T> Object JSONToObj(String jsonStr, Class<T> obj) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonStr, obj);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 将实体POJO转化为JSON
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static String createJsonObject(Object obj) {
        Gson gson = new Gson();
        String str = gson.toJson(obj);
        return str;
    }
}
