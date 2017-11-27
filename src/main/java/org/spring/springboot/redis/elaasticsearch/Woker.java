package org.spring.springboot.redis.elaasticsearch;

import org.spring.springboot.redis.common.ApplicationContextHelper;
import org.spring.springboot.redis.domain.GongWenDo;
import org.spring.springboot.redis.service.GongWenService;
import org.spring.springboot.redis.service.impl.GongWenServiceImpl;

import java.util.List;

/**
 * Created by zhoudf2 on 2017-11-8.
 */
public class Woker implements Runnable {

    private List<GongWenDo> list;

    public Woker(List<GongWenDo> list) {
        this.list = list;
    }

    @Override
    public void run() {
        System.out.println("Start work!!!");
        GongWenService gongWenService = (GongWenServiceImpl) ApplicationContextHelper.getBean("gongWenServiceImpl");
        try {
            gongWenService.batchInsert(list);
        } catch (Throwable e) {
        }
    }
}
