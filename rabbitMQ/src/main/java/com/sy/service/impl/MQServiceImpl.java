package com.sy.service.impl;

import com.sy.constant.MessageConstant;
import com.sy.mq.MessageSender;
import com.sy.service.MQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQServiceImpl implements MQService {
    @Autowired
    private MessageSender messageSender;

    @Override
    public void send(String msg) {
        messageSender.sendMessage(MessageConstant.TEST_RECEIVER_SENDER, msg);
    }
}
