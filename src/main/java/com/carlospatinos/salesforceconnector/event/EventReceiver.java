package com.carlospatinos.salesforceconnector.event;

import java.util.concurrent.CountDownLatch;

import com.carlospatinos.salesforceconnector.avro.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventReceiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "${app.topic.salesforce}")
    public void receive(Transaction transaction) {
        LOGGER.info("received user='{}'", transaction);
        latch.countDown();
    }
}