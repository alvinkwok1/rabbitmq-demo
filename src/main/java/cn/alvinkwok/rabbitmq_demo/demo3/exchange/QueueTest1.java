package cn.alvinkwok.rabbitmq_demo.demo3.exchange;

import cn.alvinkwok.rabbitmq_demo.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description
 * 该例子用于演示队列的非持久性
 *
 * @author alvinkwok
 * @since 2023/9/6
 */
public class QueueTest1 {

    private static final String QUEUE_NAME = "demo3_queue_test1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMQConfig.IP_ADDRESS);
        factory.setPort(RabbitMQConfig.PORT);
        factory.setUsername(RabbitMQConfig.USERNAME);
        factory.setPassword(RabbitMQConfig.PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 创建一个队列，并进行绑定
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 10s 后观察是否卸载
        channel.close();
        connection.close();

    }
}
