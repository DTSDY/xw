package com.sy.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    //    第一种序列化方式
    @Bean(name = "stringRedis")
    public RedisTemplate<String, Object> stringRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> stringRedisTemplate = new RedisTemplate<>();
        stringRedisTemplate.setValueSerializer(new StringRedisSerializer());
        stringRedisTemplate.setHashValueSerializer(new StringRedisSerializer());
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }

    //    第二种序列化方式
    @Bean(name = "fastRedis")
    public RedisTemplate<String, Object> fastRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> fastRedisTemplate = new RedisTemplate<>();
        //使用fastjson序列化
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        fastRedisTemplate.setValueSerializer(new StringRedisSerializer());
        fastRedisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        fastRedisTemplate.setKeySerializer(new StringRedisSerializer());
        fastRedisTemplate.setHashKeySerializer(fastJsonRedisSerializer);
        fastRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return fastRedisTemplate;
    }

    //    第三种序列化方式 如果不格式化会导致乱码
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> jdkRedisTemplate = new RedisTemplate<>();
        jdkRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return jdkRedisTemplate;
    }
}
