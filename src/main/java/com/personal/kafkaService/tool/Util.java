package com.personal.kafkaService.tool;

import com.personal.kafkaService.consumer.MessageConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author 李箎
 */
public class Util {

    public static Map<String, Object> getKafkaConfigMap() {
        //all prop
        Map<String, Object> kafkaConfigMap = new HashMap<>();
        //也可以是 cdh01:9092,cdh02:9092,cdh03:9092
        kafkaConfigMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        kafkaConfigMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaConfigMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaConfigMap.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer_group1");
        //自行控制提交offset
        kafkaConfigMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        //提交延迟毫秒数 少一点
        kafkaConfigMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 7000);
        //执行超时时间 多一点 和Kafka服务器的会话
        kafkaConfigMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 60000);
        //消息发送的最长等待时间.需大于session.timeout.ms这个时间
        kafkaConfigMap.put("request.timeout.ms", 70000);
        //开始消费位置 latest
        kafkaConfigMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        //消费者多久“心跳”一次 消息队列发现心跳则poll()轮询 最多5秒一调用poll()
        kafkaConfigMap.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 10000);
        kafkaConfigMap.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);
        //少一点 一次从kafka中poll()拉取出来的数据条数 max.poll.records条数据需要在在session.timeout.ms这个时间内处理完
        kafkaConfigMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 30);
        return kafkaConfigMap;
    }

    public static Properties getKafkaConfigProp() {
        Properties kafkaConfigProp = new Properties();
        kafkaConfigProp.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        kafkaConfigProp.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConfigProp.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConfigProp.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer_group1");
        kafkaConfigProp.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        kafkaConfigProp.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 7000);
        kafkaConfigProp.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 60000);
        kafkaConfigProp.put("request.timeout.ms", 70000);
        kafkaConfigProp.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        kafkaConfigProp.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 10000);
        kafkaConfigProp.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);
        kafkaConfigProp.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 30);
        kafkaConfigProp.put("retries", 0);
        kafkaConfigProp.put("batch.size", 16384);
        kafkaConfigProp.put("linger.ms", 1);
        kafkaConfigProp.put("buffer.memory", 33554432);
        return kafkaConfigProp;
    }
}