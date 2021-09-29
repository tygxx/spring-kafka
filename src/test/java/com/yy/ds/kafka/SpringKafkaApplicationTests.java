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
	void contextLoads() {
        // kafkaProducer.sendMessageGeneral("test", "11");
        kafkaProducer.sendMessageCallback("test2", "22");
	}

}
