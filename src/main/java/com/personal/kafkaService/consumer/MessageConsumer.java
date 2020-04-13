package com.personal.kafkaService.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.personal.pojo.msg.Message;
import com.personal.util.ConstPool;
import com.personal.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.TopicPartitionInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;
import java.util.logging.Logger;

/**自动私聊、系统消息
 * 消费者 读取用户消息
 * Kafka 消费 topic 最近 n 条消息
 * Kafka 获取 topic 最近 n 条消息
 * 读取当前topic每个分区内最新的30条消息(如果topic额分区内有没有30条，就获取实际消息)
 * 从消息队列里拿一些消息
 * kafka监控如何获取指定topic的消息总量？？？？
 * topic的增删改查？？？？
 * 资料
 * https://blog.csdn.net/shmily_lsl/article/details/81877447
 * https://www.cnblogs.com/xuwujing/p/8432984.html
 * https://blog.csdn.net/shibuwodai_/article/details/80678717
 * https://www.jianshu.com/p/abbc09ed6703
 * https://blog.csdn.net/Black1499/article/details/90474929
 * https://blog.csdn.net/u013256816/article/details/79996056
 * https://blog.csdn.net/darkWatch/article/details/87449306
 * 消费者配置：https://blog.csdn.net/weixin_30699235/article/details/97102327?utm_source=distribute.pc_relevant.none-task
 * 发送/接收对象 https://blog.csdn.net/hezhihuahzh/article/details/82378444
 * @author 李箎
 */
@SuppressWarnings("ALL")
@Service
@Transactional
@Slf4j
public class MessageConsumer {

    private static final Logger logger = Logger.getLogger(MessageConsumer.class.getName());

    /**
     * 该方法功能：后端控制台里输出某一topic中前多少条消息
     *
     * @param kafkaParams Kafka消费者配置 properties或Map
     * @param topic       topic名称 查询的主题名称
     * @param count       partition中条数 前多少条
     * @param username    该用户的消息
     */
    public Map<String, List<Message>> receiveLatestMessage(Properties kafkaParams, String topic, Integer count, String username) {
        //该用户的全部消息列表
        List<Message> messageList = new ArrayList<>();
        logger.info("create KafkaConsumer");
        //创建消费者
        final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaParams);
        AdminClient client = AdminClient.create(kafkaParams);
        //KafkaAdminClient kafkaClient = (KafkaAdminClient) KafkaAdminClient.create(Util.getKafkaConfigProp());

        try {
            //查询Topic mq的消息是可以持久化的
            DescribeTopicsResult topicResult = client.describeTopics(Arrays.asList(topic));
            Map<String, KafkaFuture<TopicDescription>> descMap = topicResult.values();
            Iterator<Map.Entry<String, KafkaFuture<TopicDescription>>> itr = descMap.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<String, KafkaFuture<TopicDescription>> entry = itr.next();
                //主题名称
                logger.info("key: " + entry.getKey());
                List<TopicPartitionInfo> topicPartitionInfoList = entry.getValue().get().partitions();
                for (TopicPartitionInfo tpi : topicPartitionInfoList) {
                    consumerAction(tpi, consumer, topic, count);
                }
            }
            // 订阅postResume topic
            consumer.subscribe(Arrays.asList(topic));
            //以下代码放到死循环里
            for (int i = 0; i < 2; i++) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                //以下为消息处理 如果消息处理时间大于poll()轮询时间间隔则报错
                for (ConsumerRecord<String, String> record : records) {
                    //主题内的数据
                    //System.out.printf("read offset =%d, key=%s , value= %s, partition=%s\n", record.offset(), record.key(), record.value(), record.partition());
                    Message message = (Message) Util.jsonToObject(new Message(), record.value().toString());
                    //关键！！！
                    if (username.equals(message.getTo()) || username.equals(message.getFrom())) {
                        //关键！！！
                        message.setTimestamp(record.timestamp());
                        messageList.add(message);
                    }
                }
                consumer.commitSync();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("when calling kafka output error." + ex.getMessage());
            System.out.println(ConstPool.KAFKA_CONNECT_ERROR);
        } finally {
            client.close();
            consumer.close();
        }
        System.out.println("##########" + username + "的消息列表");
        for (int i = 0; i < messageList.size(); i++) {
            System.out.println(messageList.get(i).toString());
        }
        //关键！！！
        return Util.msgSortOut(messageList, username);
    }

    /**
     * 上个函数调用这个函数，整个问题报错点在最后一行
     * @param tpi
     * @param consumer
     * @param topic
     * @param count
     */
    public void consumerAction(TopicPartitionInfo tpi, KafkaConsumer<String, String> consumer, String topic, Integer count) {
        int partitionId = tpi.partition();
        Node node = tpi.leader();
        TopicPartition topicPartition = new TopicPartition(topic, partitionId);
        Map<TopicPartition, Long> mapBeginning = consumer.beginningOffsets(Arrays.asList(topicPartition));
        Iterator<Map.Entry<TopicPartition, Long>> itr2 = mapBeginning.entrySet().iterator();
        long beginOffset = 0;
        //mapBeginning只有一个元素，因为Arrays.asList(topicPartition)只有一个topicPartition
        while (itr2.hasNext()) {
            Map.Entry<TopicPartition, Long> tmpEntry = itr2.next();
            beginOffset = tmpEntry.getValue();
        }
        Map<TopicPartition, Long> mapEnd = consumer.endOffsets(Arrays.asList(topicPartition));
        //endIterator
        Iterator<Map.Entry<TopicPartition, Long>> itr3 = mapEnd.entrySet().iterator();
        long lastOffset = 0;
        while (itr3.hasNext()) {
            Map.Entry<TopicPartition, Long> tmpEntry2 = itr3.next();
            lastOffset = tmpEntry2.getValue();
        }

        long expectedOffSet = lastOffset - count;
        expectedOffSet = expectedOffSet > 0 ? expectedOffSet : 1;
        logger.info("Leader of partitionId: " + partitionId + "  is " + node + ".  expectedOffSet:" + expectedOffSet + "，  beginOffset:" + beginOffset + ", lastOffset:" + lastOffset);
        consumer.commitSync(Collections.singletonMap(topicPartition, new OffsetAndMetadata(expectedOffSet - 1)));
    }
}
