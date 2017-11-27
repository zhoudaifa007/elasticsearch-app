package org.spring.springboot.redis.elaasticsearch;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * Created by zhoudf2 on 2017-11-9.
 */
@Component("consumer")
public class Consumer {

    private final static String[] ES_URL = { "http://172.20.30.191:9200","http://172.20.30.193:9200","http://172.20.30.194:9200"};

    @Autowired
    private RestTemplate restTemplate;

    /**
     * {
     "_index": "test_gongwen",
     "_type": "gongwen",
     "_id": "786680519",
     "_version": 1,
     "result": "created",
     "_shards": {
     "total": 2,
     "successful": 2,
     "failed": 0
     },
     "created": true
     }
     * @param index
     * @param type
     * @param data
     * @return
     */
    public int pushData(String index,String type,String id, JSONObject data) {
        Random random = new Random();
        int num = (random.nextInt(100) + 1) % 3;

        String put_url = ES_URL[num] + "/" + index + "/" + type + "/" + id;
        System.out.println("++++++++++++++++++++" + put_url);
        System.out.println("data=================" + data.toJSONString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(data.toString(), headers);
        ResponseEntity<String> queryResponse = restTemplate
                . exchange(put_url, HttpMethod.POST, entity, String.class);
        if (queryResponse.getStatusCode() == HttpStatus.OK) {
            String resp  = (String)queryResponse.getBody();
            JSONObject userJson = JSONObject.parseObject(resp);
           int failed =   userJson.getJSONObject("_shards").getIntValue("failed");
            return failed;

        } else if (queryResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return -1;
        }
        return  -1;
    }
}
