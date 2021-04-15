package com.example.zmq.core;

import lombok.SneakyThrows;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Zmq {

    /**
     * 主题
     */
    private String topic;

    /**
     * 容量
     */
    private int capacity;

    /**
     * 存放消息的队列
     */
//    private LinkedBlockingQueue<ZmqMessage> queue;
    private ZQueue<ZmqMessage> queue;

    public Zmq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new ZQueue<>(capacity);
    }

    /**
     * 发送消息
     *
     * @param message
     * @return
     */
//    public boolean send(ZmqMessage message) {
//        return queue.offer(message);
//    }

    public int send(ZmqMessage message) {
        return queue.offer(message);
    }

    /**
     * 拉取消息
     *
     * @return
     */
//    public ZmqMessage poll() {
//        return queue.poll();
//    }

    /**
     * 有超时时间的拉取
     *
     * @param timeout
     * @return
     */
    @SneakyThrows
//    public ZmqMessage poll(long timeout) {
//        return queue.poll(timeout, TimeUnit.MILLISECONDS);
//    }

    public ZmqMessage poll(int offset, long timeout) {
        return queue.poll(offset, timeout, TimeUnit.MILLISECONDS);
    }
}
