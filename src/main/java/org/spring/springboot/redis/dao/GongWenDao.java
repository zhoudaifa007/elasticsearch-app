package org.spring.springboot.redis.dao;

import org.apache.ibatis.annotations.Param;
import org.spring.springboot.redis.domain.GongWenDo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoudf2 on 2017-11-8.
 */
public interface GongWenDao {

    void batchInsert(List<GongWenDo> list);

    void saveOne(GongWenDo gongWenDo);

    List<String> getGongWen(Map<String,Object> map);

   void  updateStatus(String docId);
}
