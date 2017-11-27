package org.spring.springboot.redis.elaasticsearch;

import org.spring.springboot.redis.domain.GongWenDo;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhoudf2 on 2017-11-8.
 */
public class DispatchTask implements Runnable {


    private LinkedBlockingQueue<List<GongWenDo>> queue;

    private ThreadPoolExecutor executor;

    public DispatchTask(LinkedBlockingQueue<List<GongWenDo>> queue ){
        this.queue = queue;
        executor = new ThreadPoolExecutor(30, 100, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(100));
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("get length:" + queue.size());
            try {
                List<GongWenDo> gongWenDo = queue.poll(2000,TimeUnit.MILLISECONDS);
                if(!CollectionUtils.isEmpty(gongWenDo)) {
                    Woker woker = new Woker(gongWenDo);
                    executor.submit(woker);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
