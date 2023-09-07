package cn.alvinkwok.rabbitmq_demo.demo2.fanout;

import cn.alvinkwok.rabbitmq_demo.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitProducer {
    private static final String EXCHANGE_NAME = "demo2_fanout";
    private static final String QUEUE_1 = "demo2_fanout_queue1";

    private static final String QUEUE_2 = "demo2_fanout_queue2";


    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMQConfig.IP_ADDRESS);
        factory.setPort(RabbitMQConfig.PORT);
        factory.setUsername(RabbitMQConfig.USERNAME);
        factory.setPassword(RabbitMQConfig.PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 创建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout", false, true, null);
        // 创建info队列,
        channel.queueDeclare(QUEUE_1, false, false, true, null);
        // 创建warng队列
        channel.queueDeclare(QUEUE_2, false, false, true, null);
        // 绑定队列
        channel.queueBind(QUEUE_1, EXCHANGE_NAME, "");
        channel.queueBind(QUEUE_2, EXCHANGE_NAME, "");
        // 预期路由到queue1 和queue2
        String message = "fanout";
        channel.basicPublish(EXCHANGE_NAME, "", MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());

        channel.close();
        connection.close();
    }
}
