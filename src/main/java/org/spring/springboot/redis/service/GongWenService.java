package org.spring.springboot.redis.service;

import org.spring.springboot.redis.domain.GongWenDo;

import java.util.List;

/**
 * Created by zhoudf2 on 2017-11-8.
 */
public interface GongWenService {

     int batchInsert(List<GongWenDo> list);

     void save(GongWenDo gongWenDo);

      List<String> getIds(long offSet, int size);

      void updateStstus(String id);
}
