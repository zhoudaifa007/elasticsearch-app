package org.spring.springboot.redis.elaasticsearch;


import com.alibaba.fastjson.JSONObject;
import org.spring.springboot.redis.common.ApplicationContextHelper;
import org.spring.springboot.redis.service.GongWenService;
import org.spring.springboot.redis.service.impl.GongWenServiceImpl;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by zhoudf2 on 2017-11-9.
 */
public class CollectTask implements Runnable {

    private ExecutorService executor;

    public CollectTask() {
        executor = Executors.newFixedThreadPool(16);
    }

    @Override
    public void run() {

            RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextHelper.getBean("redisTemplate");
            BoundSetOperations<String, String> operation = redisTemplate.boundSetOps("gongwen:id");
            Long size = operation.size();
            int count = 0;
            List<String> ids = new ArrayList<String>();
            for (int i = 0; i < size; i++) {
                String id = operation.pop();
                if(i < 200020) {
                    continue;
                }
                count = count + 1;
                ids.add(id);
                if (count == 10) {
                    SendDataTask sendDataTask = new SendDataTask(ids);
                    executor.submit(sendDataTask);
                    ids = new ArrayList<String>();
                    count = 0;
                }
            }
        }
    }

