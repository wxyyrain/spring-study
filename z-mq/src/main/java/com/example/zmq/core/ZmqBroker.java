package com.example.zmq.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ZmqBroker {

    /**
     * 队列默认容量为10000
     */
    public static final int CAPACITY = 10000;

    /**
     * key-主题名；value-队列
     */
    private final Map<String, Zmq> kmqMap = new ConcurrentHashMap<>(64);

    /**
     * 创建topic
     *
     * @param name
     */
    public void createTopic(String name) {
        kmqMap.putIfAbsent(name, new Zmq(name, CAPACITY));
    }

    /**
     * 根据topic获取队列
     *
     * @param topic
     * @return
     */
    public Zmq findZmq(String topic) {
        return this.kmqMap.get(topic);
    }

    public ZmqProducer createProducer() {
        return new ZmqProducer(this);
    }

    public ZmqConsumer createConsumer() {
        return new ZmqConsumer(this);
    }
}
