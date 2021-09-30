package com.yy.ds.kafka.producer;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

/*
 *@Description: 发送消息时指定了分区（即自定义分区策略），则直接将消息append到指定分区，该Partitioner生效需要配置文件配置
 *@ClassAuthor: tengYong
 *@Date: 2021-09-30 09:02:17
*/
public class CustomizePartitioner implements Partitioner {

    @Override
    public void configure(Map<String, ?> configs) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // 自定义分区规则(这里假设全部发到0号分区)
        return 1;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        
    }

}