package com.carlospatinos.salesforceconnector.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.carlospatinos.salesforceconnector.event.EventSender;
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
    EventSender sender;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {

        sender.send(name);

        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
}