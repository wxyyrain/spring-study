package com.example.zmq.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class ZmqMessage<T> {

    private HashMap<String, Object> headers;

    private T body;

    /**
     * 消息的偏移量
     */
    int offset;

    public ZmqMessage(HashMap<String, Object> headers, T body) {
        this.headers = headers;
        this.body = body;
    }
}
