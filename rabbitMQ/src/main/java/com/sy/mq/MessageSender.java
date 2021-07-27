package com.sy.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendMessage(String queue, String message) {
        amqpTemplate.convertAndSend(queue, message);
    }
}
