package com.sy.service.impl;

import com.sy.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private RedisTemplate<String, Object> stringRedis;

    @Resource
    private RedisTemplate<String, Object> fastRedis;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public Boolean setString(String key, String value) {
        try {
            stringRedis.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean setFast(String key, String value) {
        try {
            fastRedis.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean setJDK(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
