package com.yy.ds.kafka.consumer;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/*
 *@Description: 被动监听kafka的topic获取数据，相关配置在application.yml文件中
 *@ClassAuthor: tengYong
 *@Date: 2021-10-09 13:52:33
*/
@Slf4j
@Component
public class KafkaListenConsumer {

    /**
     * 处理单个消息（修改配置文件将type: batch 批量消费注释掉）
     * 
     * id属性主要在日志线程上体现
     * groupId：消费组ID，会将配置文件配置的覆盖
     * topics：监听的topic，可监听多个；
     * topicPartitions：可配置更加详细的监听信息，可指定topic、parition、offset监听
     * 监听test的1号分区，同时监听test2的2号分区和test2的1号分区里面offset从2开始的消息
     * @param record
     */
    // @KafkaListener(id = "test-thread", groupId = "felix-group", topicPartitions = {
    //         @TopicPartition(topic = "test", partitions = { "1" }),
    //         @TopicPartition(topic = "test2", partitions = "2", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "2")) })
    // public void onMessage(ConsumerRecord<?, ?> record) {
    //     log.debug("topic:{}，partition:{}，offset：{}，value：{}", record.topic(), record.partition(), record.offset(),
    //             record.value());
    // }

    /**
     * 监听处理批量消息（修改配置文件将type: batch 批量消费注释放开）
     * 1、如果这里监听多个topic，那么就不能用ack手动提交，否则会有问题
     *  - kafka消费数据是非线程安全的，因此ack手动提交时，也不能用异步多线程去提交（会报错）
     *  - 如果监听多个topic，手动提交offset，可能会造成线程安全问题，如其中一个topic的数据还没有真实消费，但已经被手动提交offset了，那么就会造成数据丢失
     * @param consumerRecords
     * @param ack
     */
    @KafkaListener(topics = {"noah.flow_tcp"}, groupId = "111")
    public void flowDnsConsumer(List<ConsumerRecord> consumerRecords, Acknowledgment ack) {
        System.out.println("当前线程名称：" + Thread.currentThread().getName());
        // 是否提交offset，测试使用
        Boolean flag = true;
        for (ConsumerRecord record : consumerRecords) {
            log.debug("topic:{},监听到消息,partition:{},offset:{},value:{}", record.topic(), record.partition(), record.offset(), record.value());
            // 如果有99的数据则不提交offset，下次服务重新启动后能重新获取到99的数据（注意这里是批量消费）
            if ("99".equals(record.value().toString())) {
                flag = false;
            }
        }
        // 手动提交
        if (flag) {
            ack.acknowledge();
        }
    }
}