package com.example.zmq.demo;


import com.example.zmq.core.ZmqBroker;
import com.example.zmq.core.ZmqConsumer;
import com.example.zmq.core.ZmqMessage;
import com.example.zmq.core.ZmqProducer;
import lombok.SneakyThrows;

public class ZmqDemo {

    @SneakyThrows
    public static void main(String[] args) {
        String topic = "test_topic";
        ZmqBroker broker = new ZmqBroker();
        broker.createTopic(topic);

        ZmqConsumer consumer = broker.createConsumer();
        consumer.subscribe(topic);

        final boolean[] flag = new boolean[1];
        flag[0] = true;

        new Thread(() -> {
            while (flag[0]) {
                ZmqMessage<Order> message = consumer.poll(100);
                if (null != message) {
                    System.out.println(message.getBody());
                }
            }
            System.out.println("程序退出。");
        }).start();

        ZmqProducer producer = broker.createProducer();
        for (int i = 0; i < 1000; i++) {
            Order order = new Order(1000L + i, System.currentTimeMillis(), "USD2CNY", 6.51d);
            producer.send(topic, new ZmqMessage<>(null, order));
        }

        Thread.sleep(500);
        System.out.println("点击任何键，发送一条消息；点击q或e，退出程序。");
        while (true) {
            char c = (char) System.in.read();
            if(c > 20) {
                System.out.println(c);
                producer.send(topic, new ZmqMessage(null, new Order(100000L + c, System.currentTimeMillis(), "USD2CNY", 6.52d)));
            }

            if( c == 'q' || c == 'e') {
                break;
            }
        }

        flag[0] = false;

    }
}
