package org.spring.springboot.redis.elaasticsearch;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by zhoudf2 on 2017-11-8.
 */
@Component("producer")
public class Producer {

    private final static String ES_URL = "http://10.16.75.91:9200/test_gongwen/_search";

    @Autowired
    private RestTemplate restTemplate;

    public JSONArray getIds(long offset, int size){
        JSONObject request = new JSONObject();
        request.put("from",offset);
        request.put("size",size);
        JSONObject query = new JSONObject();
        query.put("match_all",new JSONObject());
        request.put("query", query);
        JSONArray source = new JSONArray();
        source.add("FD_ID");
        request.put("_source",source);
        System.out.println(request.toJSONString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
        ResponseEntity<String> queryResponse = restTemplate
                . exchange(ES_URL, HttpMethod.POST, entity, String.class);
        if (queryResponse.getStatusCode() == HttpStatus.OK) {
            String resp  = (String)queryResponse.getBody();
            JSONObject userJson = JSONObject.parseObject(resp);
            JSONArray jsonArray =  userJson.getJSONObject("hits").getJSONArray("hits");
            return jsonArray;

        } else if (queryResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return null;
        }

        return  null;
    }


    public JSONArray getData(List<String> ids){
        JSONObject request = new JSONObject();
        JSONObject query = new JSONObject();
        request.put("query",query);
        JSONObject terms = new JSONObject();
        query.put("terms",terms);
        JSONArray _id = new JSONArray();
        for(String id:ids) {
            _id.add(id);
        }
        terms.put("_id",_id);
        System.out.println(request.toJSONString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
        ResponseEntity<String> queryResponse = restTemplate
                . exchange(ES_URL, HttpMethod.POST, entity, String.class);
        if (queryResponse.getStatusCode() == HttpStatus.OK) {
            String resp  = (String)queryResponse.getBody();
            JSONObject userJson = JSONObject.parseObject(resp);
            JSONArray jsonArray =  userJson.getJSONObject("hits").getJSONArray("hits");
            return jsonArray;

        } else if (queryResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return null;
        }

        return  null;
    }

}
