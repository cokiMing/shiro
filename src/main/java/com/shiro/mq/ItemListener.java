package com.shiro.mq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * Created by wuyiming on 2017/8/8.
 */
@Component("itemListener")
public class ItemListener implements MessageListener{

    private static Log log = LogFactory.getLog(ItemListener.class);

    @Override
    public void onMessage(Message message) {
        try{
            log.info("监听信息: " + new String(message.getBody()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
