package com.sy.service.impl;

import com.sy.service.ReceiveMessageService;
import com.sy.service.RedisService;
import com.sy.utils.SnowFlake;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiveMessageServiceImpl implements ReceiveMessageService {
    @Autowired
    private RedisService redisService;

    @Override
    public void transToRedis(String msg) {

        SnowFlake s = new SnowFlake(0,1);

//        redisService.setString(String.valueOf(s.nextId()), msg);
        redisService.setFast(String.valueOf(s.nextId()), msg);
//        redisService.setJDK(String.valueOf(s.nextId()),msg);
    }
}
