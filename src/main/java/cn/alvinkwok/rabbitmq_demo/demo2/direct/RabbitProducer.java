package cn.alvinkwok.rabbitmq_demo.demo2.direct;

import cn.alvinkwok.rabbitmq_demo.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitProducer {
    private static final String EXCHANGE_NAME = "excahnge_demo";
    private static final String ROUTING_KEY_1 = "info";

    private static final String ROUTING_KEY_2 = "warning";
    private static final String QUEUE_1 = "demo2_info";

    private static final String QUEUE_2 = "demo2_warning";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMQConfig.IP_ADDRESS);
        factory.setPort(RabbitMQConfig.PORT);
        factory.setUsername(RabbitMQConfig.USERNAME);
        factory.setPassword(RabbitMQConfig.PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 创建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
        // 创建info队列,
        channel.queueDeclare(QUEUE_1, true, false, false, null);
        // 创建warng队列
        channel.queueDeclare(QUEUE_2, true, false, false, null);
        // 绑定队列
        channel.queueBind(QUEUE_1, EXCHANGE_NAME, ROUTING_KEY_1);
        channel.queueBind(QUEUE_2, EXCHANGE_NAME, ROUTING_KEY_2);
        // 发送一条持久化消息
        String message = "info:Hello World";
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_1, MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        // 发送一条warning的消息
        message = "warning: Hello World";
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_2, MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        channel.close();
        connection.close();
    }
}
