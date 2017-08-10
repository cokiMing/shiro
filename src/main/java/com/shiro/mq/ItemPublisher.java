package com.shiro.mq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by wuyiming on 2017/8/8.
 */
@Component
public class ItemPublisher  {
    @Resource
    private AmqpTemplate amqpTemplate;

    private static Log log = LogFactory.getLog(ItemPublisher.class);

    public void sendMessage(Object message){
        log.info("发送消息："+message);
        amqpTemplate.convertAndSend("rabbit.ee", message);
        Message receive = amqpTemplate.receive();
        log.info("收到反馈："+ new String(receive.getBody()));
    }
}
