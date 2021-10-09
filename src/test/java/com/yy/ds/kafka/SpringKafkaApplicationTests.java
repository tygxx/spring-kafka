package com.yy.ds.kafka;

import com.yy.ds.kafka.producer.KafkaProducer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringKafkaApplicationTests {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    void sendMsgTest() {
        // kafkaProducer.sendMessageGeneral("test", "11");
        String dns = "{'dns_type':'0','mpls_label':null,'vlan_id':-99,'collect_time_hour':'2021092616','dmac':null,'event_count':'1;0;0;0','smac':null,'dip':'10.110.200.9','dns_code':'-99','collect_time_date':'20210926','dns_addr':null,'vxlan_id':-99,'dport':32113,'log_type':'flow_dns','occur_time':1632644318843,'geo_dip':{'continent_name':'','city_name':'','latitude':0.0,'country_name':'局域网','province_name':null,'longitude':0.0},'sip':'10.111.75.28','gre_key':-99,'geo_sip':{'continent_name':'','city_name':'','latitude':0.0,'country_name':'局域网','province_name':null,'longitude':0.0},'dns_mx':null,'session_id':'0x61502CD03671AE01','msgid':'06f5bff77b6f4a49b608e11eb3dfbcae','serial_num':'154e9c5fbab4b4b38b70d29283e803db6c19d8f3','dev_id':'数据传感器-NDS系列','user_define':null,'dns_cname':null,'systype':'log','comm_direction':'内到内','domain':'g.cn.miaozhen.com','dev_ip':'10.91.3.102','collect_time':1632645829124,'sport':53894,'dns_txt':null}";
        kafkaProducer.sendMessageCallback("noah.flow_dns", dns);
    }

    @Test
    void sendMsgTest2() {
        // kafkaProducer.sendMessageGeneral("test", "11");
        kafkaProducer.sendMessageCallback("test2", "2");
    }

}
