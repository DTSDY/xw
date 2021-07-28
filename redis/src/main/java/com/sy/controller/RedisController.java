package com.sy.controller;

import com.sy.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/setStr/{key}/{value}")
    public Boolean setString(@PathVariable String key, @PathVariable String value) {
        return redisService.setString(key,value);
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("ssssssssssssssssssssss");
        return ";ssss";
    }

    @GetMapping("/setFast/{key}/{value}")
    public Boolean setFast(@PathVariable String key, @PathVariable String value) {
        return redisService.setString(key,value);
    }
}
