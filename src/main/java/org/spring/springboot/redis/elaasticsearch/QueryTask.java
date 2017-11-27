package org.spring.springboot.redis.elaasticsearch;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.spring.springboot.redis.common.ApplicationContextHelper;
import org.spring.springboot.redis.domain.GongWenDo;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zhoudf2 on 2017-11-8.
 */
public class QueryTask implements Runnable {

    private long num = 0;

    private long startTime = 0L;

    private LinkedBlockingQueue<List<GongWenDo>> queue;

    public QueryTask(LinkedBlockingQueue<List<GongWenDo>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        recvcGet();
        System.out.println("***************************");
    }


    private void recvcGet() {
        Producer producer = (Producer) ApplicationContextHelper.getBean("producer");
        long i = 0L;
        for (i = 0L; i < 962000L; i = i + 100L) {
            JSONArray jsonArray = producer.getIds(i, 100);
            if (!CollectionUtils.isEmpty(jsonArray)) {
                List<GongWenDo> gongWenDoList = new ArrayList();
                for (Object object : jsonArray) {
                    JSONObject jsonObject = (JSONObject) object;
                    GongWenDo gongWenDo = new GongWenDo();
                    gongWenDo.setIndex(jsonObject.getString("_index"));
                    gongWenDo.setType(jsonObject.getString("_type"));
                    gongWenDo.setDocId(jsonObject.getString("_id"));
                    String fd_id = jsonObject.getJSONObject("_source").getString("FD_ID");

                    if (StringUtils.isEmpty(fd_id)) {
                        fd_id = "0";
                    }
                    if (!isNumeric(fd_id)) {
                        fd_id = "0";
                    }
                    gongWenDo.setFdId(Long.valueOf(fd_id));
                    gongWenDo.setStatus(0);
                    gongWenDo.setCreatTime(new Date());
                    gongWenDo.setUpdateTime(new Date());
                    gongWenDoList.add(gongWenDo);
                }
                queue.add(gongWenDoList);
            }
        }
    }

    private boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
