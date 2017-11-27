package org.spring.springboot.redis.elaasticsearch;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.spring.springboot.redis.common.ApplicationContextHelper;
import org.spring.springboot.redis.service.GongWenService;
import org.spring.springboot.redis.service.impl.GongWenServiceImpl;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoudf2 on 2017-11-9.
 */
public class SendDataTask implements Runnable {

    private List<String> ids = new ArrayList<String>();

    public SendDataTask(List<String> ids) {
        this.ids = ids;
    }

    @Override
    public void run() {
        Producer producer = (Producer) ApplicationContextHelper.getBean("producer");
        Consumer consumer = (Consumer) ApplicationContextHelper.getBean("consumer");
        JSONArray jsonArray = producer.getData(ids);

        if (!CollectionUtils.isEmpty(jsonArray)) {
            for (Object object : jsonArray) {
                JSONObject jsonObject = (JSONObject) object;
                String index = jsonObject.getString("_index");
                String type = jsonObject.getString("_type");
                String id = jsonObject.getString("_id");
                JSONObject data = jsonObject.getJSONObject("_source");
                int status = -1;
                try {
                    status = consumer.pushData(index, type, id, data);
                } catch (Throwable throwable) {
                    System.out.println(throwable.fillInStackTrace());
                }
            }
        }
    }

}
