package org.spring.springboot.redis.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.springboot.redis.dao.GongWenDao;
import org.spring.springboot.redis.domain.GongWenDo;
import org.spring.springboot.redis.service.GongWenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoudf2 on 2017-11-8.
 */
@Component("gongWenServiceImpl")
public class GongWenServiceImpl implements GongWenService {

    private static final Logger logger = LoggerFactory.getLogger(GongWenServiceImpl.class);

    @Autowired
    private GongWenDao gongWenDao;

    @Override
    public int batchInsert(List<GongWenDo> list) {
        System.out.println("----------------------------" + JSONObject.toJSONString(list));
        gongWenDao.batchInsert(list);
        return 0;
    }

    @Override
    public void save(GongWenDo gongWenDo) {
        gongWenDao.saveOne(gongWenDo);
    }

    @Override
    public List<String> getIds(long offSet, int size) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("offSet",offSet);
        map.put("pageSize",size);
        return  gongWenDao.getGongWen(map);
    }

    public  void updateStstus(String docId){
        gongWenDao.updateStatus(docId);
    }
}
