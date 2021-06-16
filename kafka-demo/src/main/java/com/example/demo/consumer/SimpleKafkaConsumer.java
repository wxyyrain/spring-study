package com.example.demo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimpleKafkaConsumer {

//    @KafkaListener(topics = {"test_topic"})
//    public void onMessage1(ConsumerRecord<?, ?> record) {
//        // 消费的哪个topic、partition的消息,打印出消息内容
//        System.out.println("简单消费：" + record.topic() + "-" + record.partition() + "-" + record.value());
//    }

    /**
     * 同时监听topic1和topic2，监听topic1的0号分区、topic2的 "0号和1号" 分区，指向1号分区的offset初始值为8
     */
//    @KafkaListener(id = "consumer1", groupId = "felix-group", topicPartitions = {
//            @TopicPartition(topic = "topic1", partitions = {"0"}),
//            @TopicPartition(topic = "topic2", partitions = "0", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "8"))
//    })
//    public void onMessage2(ConsumerRecord<?, ?> record) {
////        ① id：消费者ID；
////        ② groupId：消费组ID；
////        ③ topics：监听的topic，可监听多个；
////        ④ topicPartitions：可配置更加详细的监听信息，可指定topic、parition、offset监听。
////        topics和topicPartitions不能同时使用
//        System.out.println("topic:" + record.topic() + "|partition:" + record.partition() + "|offset:" + record.offset() + "|value:" + record.value());
//    }

    // 批量消费
//    @KafkaListener(id = "consumer2", groupId = "felix-group", topics = "test_topic")
//    public void onMessage3(List<ConsumerRecord<?, ?>> records) {
//        System.out.println(">>>批量消费一次，records.size()=" + records.size());
//        for (ConsumerRecord<?, ?> record : records) {
//            System.out.println(record.value());
//        }
//    }

    // 将这个异常处理器的BeanName放到@KafkaListener注解的errorHandler属性里面
//    @KafkaListener(topics = {"test_topic"}, errorHandler = "consumerAwareErrorHandler")
//    public void onMessage4(ConsumerRecord<?, ?> record) throws Exception {
//        throw new Exception("简单消费-模拟异常");
//    }

    // 批量消费也一样，异常处理器的message.getPayload()也可以拿到各条消息的信息
//    @KafkaListener(topics = "test_topic", errorHandler = "consumerAwareErrorHandler")
//    public void onMessage5(List<ConsumerRecord<?, ?>> records) throws Exception {
//        System.out.println("批量消费一次...");
//        throw new Exception("批量消费-模拟异常");
//    }

    // 消息过滤监听
//    @KafkaListener(topics = {"test_topic"}, containerFactory = "filterContainerFactory")
//    public void onMessage6(ConsumerRecord<?, ?> record) {
//        System.out.println(record.value());
//    }

    // 消息转发
//    @KafkaListener(topics = {"test_topic"})
//    @SendTo("test_topic_copy")
//    public String onMessage7(ConsumerRecord<?, ?> record) {
//        return record.value() + "-forward message";
//    }
}
