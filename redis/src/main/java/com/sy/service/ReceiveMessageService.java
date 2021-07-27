package com.sy.service;

public interface ReceiveMessageService {
    void transToRedis(String msg);
}
