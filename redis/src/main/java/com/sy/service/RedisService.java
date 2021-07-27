package com.sy.service;

public interface RedisService {

    Boolean setString(String key, String value);

    Boolean setFast(String key, String value);

    Boolean setJDK(String key, String value);
}
