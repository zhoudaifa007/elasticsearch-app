package org.spring.springboot.redis.elaasticsearch;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhoudf2 on 2017-11-8.
 */
public class JsonTest {

    public static void main(String[] args) {
        JSONObject request = new JSONObject();
        request.put("from",0);
        request.put("size",5);
        JSONObject query = new JSONObject();
        query.put("match_all",new JSONObject());
        request.put("query", query);
        JSONArray source = new JSONArray();
        source.add("FD_ID");
        request.put("_source",source);
        System.out.println(request.toJSONString());
    }
}
