package com.carlospatinos.salesforceconnector.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.carlospatinos.salesforceconnector.model.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class GreetingController {

    Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.topic.salesforce-event}")
    private String topic;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        logger.info("sending message='{}' to topic='{}'", name, topic);
        kafkaTemplate.send(topic, name);

        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
}