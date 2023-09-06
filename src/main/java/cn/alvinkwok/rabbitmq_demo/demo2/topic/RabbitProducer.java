package cn.alvinkwok.rabbitmq_demo.demo2.topic;

import cn.alvinkwok.rabbitmq_demo.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitProducer {
    private static final String EXCHANGE_NAME = "demo2_topic";
    private static final String QUEUE_1 = "demo2_topic_queue1";

    private static final String QUEUE_2 = "demo2_topic_queue2";


    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMQConfig.IP_ADDRESS);
        factory.setPort(RabbitMQConfig.PORT);
        factory.setUsername(RabbitMQConfig.USERNAME);
        factory.setPassword(RabbitMQConfig.PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 创建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic", true, false, null);
        // 创建info队列,
        channel.queueDeclare(QUEUE_1, true, false, false, null);
        // 创建warng队列
        channel.queueDeclare(QUEUE_2, true, false, false, null);
        // 绑定队列
        channel.queueBind(QUEUE_1, EXCHANGE_NAME, "*.rabbitmq.*");
        channel.queueBind(QUEUE_2, EXCHANGE_NAME, "*.*.client");
        channel.queueBind(QUEUE_2, EXCHANGE_NAME, "com.#");
        // 预期路由到queue1 和queue2
        String message = "com.rabbitmq.client";
        channel.basicPublish(EXCHANGE_NAME, "com.rabbitmq.client", MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        // 预期路由到queue2
        message = "com.hidden.client";
        channel.basicPublish(EXCHANGE_NAME, "com.hidden.client", MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        // 预期路由到queue2
        message = "com.hidden.demo";
        channel.basicPublish(EXCHANGE_NAME, "com.hidden.demo", MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        // 预期路由到queue2
        message = "java.util.concurrent";
        channel.basicPublish(EXCHANGE_NAME, "java.util.concurrent", MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        channel.close();
        connection.close();
    }
}
