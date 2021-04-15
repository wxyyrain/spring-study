package com.example.zmq.core;

public class ZmqProducer {

    private ZmqBroker broker;

    public ZmqProducer(ZmqBroker broker) {
        this.broker = broker;
    }

//    public boolean send(String topic, ZmqMessage message) {
//        Zmq kmq = this.broker.findZmq(topic);
//        if (null == kmq) {
//            throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
//        }
//        return kmq.send(message);
//    }

    public int send(String topic, ZmqMessage message) {
        Zmq kmq = this.broker.findZmq(topic);
        if (null == kmq) {
            throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
        }
        return kmq.send(message);
    }
}
