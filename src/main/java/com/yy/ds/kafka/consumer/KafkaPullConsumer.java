package com.yy.ds.kafka.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

/*
 *@Description: 主动拉取kafka的topic数据，
 *@ClassAuthor: tengYong
 *@Date: 2021-10-09 13:53:16
*/
public class KafkaPullConsumer {

    public static void main(String[] args) {

        Properties props = new Properties();
        // 配置说明参照yml文件
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.91.3.119:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "spring_kafka_test");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 200);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 120000);
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 180000);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // 定义consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // 消费者订阅的topic, 可同时订阅多个
        consumer.subscribe(Arrays.asList("noah.flow_dns"));
        while (true) {

            // 读取数据，读取超时时间为100ms
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records)

                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());

        }

    }
}