package com.carlospatinos.salesforceconnector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SalesforceConnectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesforceConnectorApplication.class, args);
	}

}
