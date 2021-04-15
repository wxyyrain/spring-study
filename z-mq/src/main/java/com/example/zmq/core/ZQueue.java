package com.example.zmq.core;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ZQueue<T> {

    private int capacity;

    private int offset;

    private Object[] queue;

    private ReentrantLock lock = new ReentrantLock();

    private Condition notEmpty = lock.newCondition();

    public ZQueue(int capacity) {
        this.offset = 0;
        this.capacity = capacity;
        queue = new Object[capacity];
    }

    /**
     * 将消息放到队列尾部，并返回消息的偏移量
     *
     * @param message
     * @return
     */
    public int offer(ZmqMessage<T> message) {
        lock.lock();
        try {
            if (offset == capacity) {
                throw new RuntimeException("ZQueue full");
            }
            queue[offset] = message;
            offset++;
            message.setOffset(offset);
            notEmpty.signalAll();
            return message.getOffset();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            lock.unlock();
        }
    }


    public ZmqMessage<T> poll(int offset, long timeout, TimeUnit timeUnit) {
        lock.lock();
        try {
            // 如果offset为0，则等待生产者
            if (offset == 0) {
                notEmpty.await(timeout, timeUnit);
            }
//            int index = offset - 1;
            return (ZmqMessage<T>) queue[offset];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }
}
