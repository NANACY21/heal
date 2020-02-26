package com.personal.kafkaService.producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统消息生产者
 *
 * @author 李箎
 */
@SuppressWarnings("ALL")
@Service
@Transactional
//@AllArgsConstructor
public class SystemMessageProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTl;

    /**
     * 使用kafka模板发送信息 发到指定话题上
     *
     * @param topic
     * @param data
     */
    public void send(String topic, Object data) {
        //使用kafka模板发送信息 发到指定话题上
        kafkaTemplate.send(topic, data.toString());
    }
}
