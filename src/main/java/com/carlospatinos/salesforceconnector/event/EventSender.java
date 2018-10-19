package com.carlospatinos.salesforceconnector.event;

import com.carlospatinos.salesforceconnector.avro.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventSender {
    Logger logger = LoggerFactory.getLogger(EventSender.class);

    @Value("${app.topic.salesforce}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;


    public void send(Transaction transaction) {
        logger.info("sending message='{}' to topic='{}'", transaction, topic);
        kafkaTemplate.send(topic, transaction);
    }
}
