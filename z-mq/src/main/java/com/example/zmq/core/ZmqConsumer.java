package com.example.zmq.core;

public class ZmqConsumer<T> {

    private final ZmqBroker broker;

    private Zmq zmq;

    /**
     * 消费的消息偏移量
     */
    int offset;

    public ZmqConsumer(ZmqBroker broker) {
        this.broker = broker;
    }

    /**
     * 订阅topic，获取mq
     *
     * @param topic
     */
    public void subscribe(String topic) {
        this.zmq = this.broker.findZmq(topic);
        if (null == zmq) {
            throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
        }
    }

    public ZmqMessage<T> poll(long timeout) {
        ZmqMessage message = zmq.poll(offset, timeout);
        offset++;
        return message;
    }
}
