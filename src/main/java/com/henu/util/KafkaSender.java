package com.henu.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/2/5.
 */
@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * ������Ϣ��kafka,����Ϊtest
     */
    public void send(String topic,String message){
        kafkaTemplate.send(topic,message);
    }
}
