package com.yy.ds.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 普通发送消息
     * 
     * @param topic
     * @param msg
     */
    public void sendMessageGeneral(String topic, String msg) {
        kafkaTemplate.send(topic, msg);
        log.debug("给topic：{}，发送消息：{}", topic, msg);
    }

    /**
     * 发消息----回调处理
     * kafkaTemplate提供了一个回调方法addCallback，我们可以在回调方法中监控消息是否发送成功 或 失败时做补偿处理
     * 
     * @param callbackMessage
     */
    public void sendMessageCallback(String topic, String msg) {
        kafkaTemplate.send(topic, msg).addCallback(success -> {
            // 消息发送到的topic
            String topicRes = success.getRecordMetadata().topic();
            // 消息发送到的分区
            int partition = success.getRecordMetadata().partition();
            // 消息在分区内的offset
            long offset = success.getRecordMetadata().offset();
            log.debug("发送消息成功，topic:{}，partition：{}，offset：{}", topicRes, partition, offset);
        }, failure -> {
            log.error("发送消息失败:{}", failure.getMessage());
        });
    }

    /**
     * 发消息---事务处理
     * kafka事务提交，如果在发送消息时需要创建事务，可以使用 KafkaTemplate 的 executeInTransaction 方法来声明事务
     * 
     * @param callbackMessage
     */
    public void sendMessageTransaction(String topic, String msg) {
        // 声明事务：后面报错消息不会发出去
        kafkaTemplate.executeInTransaction(operations -> {
            operations.send(topic, msg);
            throw new RuntimeException("fail");
        });
    }

}