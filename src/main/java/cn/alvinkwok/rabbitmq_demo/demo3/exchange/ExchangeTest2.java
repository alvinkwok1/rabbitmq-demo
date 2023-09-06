package cn.alvinkwok.rabbitmq_demo.demo3.exchange;

import cn.alvinkwok.rabbitmq_demo.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 验证交换机的自动删除
 */
public class ExchangeTest2 {
    private static final String EXCHANGE_NAME = "demo3_exchange_test2";

    private static final String QUEUE_NAME = "demo3_queue_test2";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMQConfig.IP_ADDRESS);
        factory.setPort(RabbitMQConfig.PORT);
        factory.setUsername(RabbitMQConfig.USERNAME);
        factory.setPassword(RabbitMQConfig.PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 创建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, true, null);

        // 创建一个队列，并进行绑定
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);

        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "demo3_test");
        // 绑定后观察是否存在
        TimeUnit.SECONDS.sleep(10);
        channel.queueUnbind(QUEUE_NAME, EXCHANGE_NAME, "demo3_test");
        // 10s 后观察是否卸载
        channel.close();
        connection.close();

    }
}
