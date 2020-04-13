package com.personal.kafkaService.consumer;

import com.personal.chatService.ChatServer;
import com.personal.kafkaService.tool.Util;
import com.personal.util.ConstPool;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.Arrays;

import static com.personal.chatService.ChatServer.wbSockets;

/**
 * 私聊
 * WebSocket和kafka实现数据实时推送到前端！！！
 *
 * @author 李箎
 */
public class ConsumerKafka extends Thread {

    private KafkaConsumer<String, String> consumer;
    private String topic = ConstPool.KAFKA_TOPIC1;

    public ConsumerKafka() {

    }

    @Override
    public void run() {
        //加载kafka消费者参数
        //创建消费者对象
        consumer = new KafkaConsumer<String, String>(Util.getKafkaConfigProp());
        consumer.subscribe(Arrays.asList(this.topic));
        //死循环，持续消费kafka
        while (true) {
            try {
                //消费数据，并设置超时时间
                ConsumerRecords<String, String> records = consumer.poll(100);
                //Consumer message
                for (ConsumerRecord<String, String> record : records) {
                    //Send message to every client
                    for (ChatServer webSocket : wbSockets) {
                        //关键！！！
                        webSocket.send(record.value());
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                continue;
            }
        }
    }

    public void close() {
        try {
            consumer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //供测试用，若通过tomcat启动需通过其他方法启动线程
    public static void main(String[] args) {
        ConsumerKafka consumerKafka = new ConsumerKafka();
        consumerKafka.start();
    }
}
