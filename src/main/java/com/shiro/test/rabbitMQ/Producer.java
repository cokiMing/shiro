package com.shiro.test.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.concurrent.TimeoutException;

/**
 * Created by wuyiming on 2017/8/8.
 */
public class Producer {
    private final static String QUEUE_NAME = "hello2";// 队列名不能重复 之前已有就会失败

    public static void main(String[] argv) throws java.io.IOException,TimeoutException {

        /* 使用工厂类建立Connection和Channel，并且设置参数 */
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.0.0.98");// MQ的IP
        factory.setPort(5672);// MQ端口
        factory.setUsername("coki");// MQ用户名
        factory.setPassword("wym271847279");// MQ密码
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /* 创建消息队列，并且发送消息 */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "消息2";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("生产了个'" + message + "'");

        /* 关闭连接 */
        channel.close();
        connection.close();
    }
}
