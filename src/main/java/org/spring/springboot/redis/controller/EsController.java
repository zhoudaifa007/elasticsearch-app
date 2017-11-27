package org.spring.springboot.redis.controller;

import com.alibaba.fastjson.JSONArray;
import org.spring.springboot.redis.domain.GongWenDo;
import org.spring.springboot.redis.elaasticsearch.CollectTask;
import org.spring.springboot.redis.elaasticsearch.DispatchTask;
import org.spring.springboot.redis.elaasticsearch.QueryTask;
import org.spring.springboot.redis.elaasticsearch.Producer;
import org.spring.springboot.redis.model.ServerResponse;
import org.spring.springboot.redis.service.GongWenService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zhoudf2 on 2017-11-8.
 */
@RestController
public class EsController {


    @Resource
    private Producer producer;

    @Resource
    private GongWenService  gongWenService;

    @RequestMapping(value = "/api/es")
    public ServerResponse login() {
        JSONArray resp = producer.getIds(0L,5);
        LinkedBlockingQueue<List<GongWenDo>> queue  = new LinkedBlockingQueue<List<GongWenDo>>();
        QueryTask task = new QueryTask(queue);
        Thread thread1 = new Thread(task, "Thread 1");
        thread1.start();
        DispatchTask task1 = new DispatchTask(queue);
        Thread thread2 = new Thread(task1, "Thread 2");
        thread2.start();
        return ServerResponse.successWithData(resp);
    }

    @RequestMapping(value = "/api/data")
    public ServerResponse getData() {
        CollectTask task = new CollectTask();
        Thread thread1 = new Thread(task, "Thread CollectTask");
        thread1.start();
        return ServerResponse.successWithData(null);
    }

    @RequestMapping(value = "/api/test")
    public ServerResponse test(@RequestParam("id") String id) {
        gongWenService.updateStstus(id);
        return ServerResponse.successWithData(null);
    }




}
