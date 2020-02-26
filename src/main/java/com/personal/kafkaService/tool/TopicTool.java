package com.personal.kafkaService.tool;

import org.apache.kafka.clients.admin.AdminClient;

import java.util.Arrays;

/**
 * @author 李箎
 */
public class TopicTool {
    private static AdminClient client = AdminClient.create(Util.getKafkaConfigProp());

    /**
     * 删除topic
     * @param topicName
     */
    public static void delTopic(String topicName) {
        client.deleteTopics(Arrays.asList(topicName));
    }

    public static void main(String[] args) {
        delTopic("postResume");
    }
}
