package org.spring.springboot.redis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.springboot.redis.model.LoginParams;
import org.spring.springboot.redis.model.LogoutParams;
import org.spring.springboot.redis.model.ServerResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhoudf2 on 2017-9-21.
 */
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ServerResponse login(@RequestBody LoginParams loginParams) {
        String token = UUID.randomUUID().toString();
        Map<String,String> map = new HashMap<String,String>();
        map.put("token", token);
        ServerResponse serverResponse = ServerResponse.successWithData(map);
        return serverResponse;
    }

    @RequestMapping(value = "/api/logout", method = RequestMethod.POST)
    public ServerResponse logout(@RequestBody LogoutParams loginParams) {
        return new ServerResponse(null);
    }

}
