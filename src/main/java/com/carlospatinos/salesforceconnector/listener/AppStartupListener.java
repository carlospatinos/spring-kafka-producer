package com.carlospatinos.salesforceconnector.listener;

import com.carlospatinos.salesforceconnector.connector.BayeuxParameters;
import com.carlospatinos.salesforceconnector.connector.BearerTokenProvider;
import com.carlospatinos.salesforceconnector.connector.CometdConnector;
import com.carlospatinos.salesforceconnector.connector.TopicSubscription;
import org.eclipse.jetty.util.ajax.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static com.carlospatinos.salesforceconnector.connector.LoginHelper.login;

@Component
public class AppStartupListener {

    private static final Logger log = LoggerFactory.getLogger(AppStartupListener.class);

    @Value("${salesforce.username}")
    private String username;

    @Value("${salesforce.password}")
    private String password;

    @Value("${salesforce.channel}")
    private String channel;

    @Value("${salesforce.replayFrom:-1}")
    private Long replayFrom;

    @EventListener(ApplicationReadyEvent.class)
    public void startSalesforceConnection() throws Exception {
        log.info("Connecting to salesforce using: username[{}], channel[{}] and replayFrom[{}]", username, channel, replayFrom);

        BearerTokenProvider tokenProvider = new BearerTokenProvider(() -> {
            try {
                return login(username, password);
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
                throw new RuntimeException(e);
            }
        });

        BayeuxParameters params = tokenProvider.login();

        Consumer<Map<String, Object>> consumer = event -> System.out.println(String.format("Received:\n%s", JSON.toString(event)));

        CometdConnector connector = new CometdConnector(params);

        connector.setBearerTokenProvider(tokenProvider);

        connector.start().get(5, TimeUnit.SECONDS);

        TopicSubscription subscription = connector.subscribe(channel, replayFrom, consumer).get(5, TimeUnit.SECONDS);

        System.out.println(String.format("Subscribed: %s", subscription));
    }
}
