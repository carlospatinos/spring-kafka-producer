package com.carlospatinos.salesforceconnector.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.carlospatinos.salesforceconnector.avro.User;

@Service
public class EventSender {
    Logger logger = LoggerFactory.getLogger(EventSender.class);

    @Value("${app.topic.salesforce-event}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;


    public void send(User user) {
        logger.info("sending message='{}' to topic='{}'", user, topic);
        kafkaTemplate.send(topic, user);
    }
}
