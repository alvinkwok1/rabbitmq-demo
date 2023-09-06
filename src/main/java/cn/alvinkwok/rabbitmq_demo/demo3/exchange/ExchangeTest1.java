package cn.alvinkwok.rabbitmq_demo.demo3.exchange;

import cn.alvinkwok.rabbitmq_demo.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 验证不持久化
 */
public class ExchangeTest1 {
    private static final String EXCHANGE_NAME = "demo3_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMQConfig.IP_ADDRESS);
        factory.setPort(RabbitMQConfig.PORT);
        factory.setUsername(RabbitMQConfig.USERNAME);
        factory.setPassword(RabbitMQConfig.PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 创建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", false, false, null);
        channel.close();
        connection.close();

        // 执行完成后先检查队列是否存在

        // 把rabbitmq重启

        // 再次检查队列是否存在,预期是不见了
    }
}
