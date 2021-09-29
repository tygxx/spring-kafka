package com.yy.ds.kafka.consumer;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "test", groupId = "test")
    public void flowDnsConsumer(List<ConsumerRecord> consumerRecords, Acknowledgment ack) {
        System.out.println("当前线程名称：" + Thread.currentThread().getName());
        Long startTime = System.currentTimeMillis();
        for (ConsumerRecord record : consumerRecords) {
            log.debug("拉取到的消息：", record.value());
        }
        // 手动提交
        // ack.acknowledge();
        log.debug("处理一批次kafka数据时间：{}", System.currentTimeMillis() - startTime);
    }
}