package com.example.demo.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleKafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/kafka/normal/{message}")
    public void sendMessage1(@PathVariable("message") String normalMessage) {

        for (int i = 0; i < 9; i++) {
            kafkaTemplate.send("test_topic", i + "");
        }
    }

    /**
     * 带回调的发送方法
     *
     * @param callbackMessage
     */
    @GetMapping("/kafka/callbackOne/{message}")
    public void sendMessage2(@PathVariable("message") String callbackMessage) {
        kafkaTemplate.send("test_topic", callbackMessage).addCallback(success -> {
            // 消息发送到的topic
            String topic = success.getRecordMetadata().topic();
            // 消息发送到的分区
            int partition = success.getRecordMetadata().partition();
            // 消息在分区内的offset
            long offset = success.getRecordMetadata().offset();
            System.out.println("发送消息成功:" + topic + "-" + partition + "-" + offset);
        }, failure -> System.out.println("发送消息失败:" + failure.getMessage()));
    }

//    @GetMapping("/kafka/callbackTwo/{message}")
//    public void sendMessage3(@PathVariable("message") String callbackMessage) {
//        kafkaTemplate.send("test_topic", callbackMessage).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("发送消息失败："+ex.getMessage());
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, Object> result) {
//                System.out.println("发送消息成功：" + result.getRecordMetadata().topic() + "-"
//                        + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());
//            }
//        });
//    }

    /**
     * 带事务的发送
     */
    @GetMapping("/kafka/transaction")
    public void sendMessage7() {
        // 声明事务：后面报错消息不会发出去
        kafkaTemplate.executeInTransaction(operations->{
            operations.send("test_topic", "test executeInTransaction");
            throw new RuntimeException("fail");
        });
        // 不声明事务：后面报错但前面消息已经发送成功了
//        kafkaTemplate.send("test_topic", "test executeInTransaction");
//        throw new RuntimeException("fail");
    }
}
