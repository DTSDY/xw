package com.sy.mq;

import com.sy.constant.MessageConstant;
import com.sy.service.ReceiveMessageService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReceiveMQService {

    private static final Logger logger = LoggerFactory.getLogger(ReceiveMQService.class);

    @Autowired
    private ReceiveMessageService receiveMessageService;

    @RabbitListener(queues = MessageConstant.TEST_RECEIVER_SENDER, containerFactory = "containerFactory")
    public void reciever(String msg){
        if (StringUtils.isNotBlank(msg)) {
            logger.error("ssssyy 接收到的消息为: "+ msg);
            receiveMessageService.transToRedis(msg);
        }
    }
}
