package com.personal.kafkaService.consumer;

import com.personal.util.ConstPool;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 监听服务器上的kafka是否有相关的消息发过来
 *
 * @author 李箎
 */
//@Component
public class EvalConsumer {
    /**
     * 定义该消费者接收指定topics的消息，与controller中的topic对应上即可
     *
     * @param record 变量代表消息本身，可以通过ConsumerRecord<?,?>类型的record变量来打印接收的消息的各种信息
     */
//    @KafkaListener(topics = ConstPool.KAFKA_TOPIC1)
//    @KafkaListener(topics = {"topic1", "topic2"})
//    public void listen(ConsumerRecord<?, ?> record) {
//        System.out.printf("topic is %s, offset is %d, value is %s \n", record.topic(), record.offset(), record.value());
//    }
}
