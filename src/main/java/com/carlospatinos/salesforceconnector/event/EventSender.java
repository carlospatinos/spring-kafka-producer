package com.carlospatinos.salesforceconnector.event;

import com.carlospatinos.salesforceconnector.avro.Transaction;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class EventSender {
    Logger logger = LoggerFactory.getLogger(EventSender.class);

    @Value("${app.topic.salesforce}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    @Autowired
    MeterRegistry registry;


    private Counter counter;

    @PostConstruct
    public void init(){
        logger.debug("Creating a counter in EventSender");
        this.counter = Counter.builder("salesforce.messages.sent").tag("type", "order").register(registry);
    }


    public void send(Transaction transaction) {
        logger.info("sending message='{}' to topic='{}'", transaction, topic);
        kafkaTemplate.send(topic, transaction);
        counter.increment();
    }
}
