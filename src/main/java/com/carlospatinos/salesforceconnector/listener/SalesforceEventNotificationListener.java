package com.carlospatinos.salesforceconnector.listener;

import com.carlospatinos.salesforceconnector.avro.Transaction;
import com.carlospatinos.salesforceconnector.connector.BayeuxParameters;
import com.carlospatinos.salesforceconnector.connector.BearerTokenProvider;
import com.carlospatinos.salesforceconnector.connector.CometdConnector;
import com.carlospatinos.salesforceconnector.connector.TopicSubscription;
import com.carlospatinos.salesforceconnector.event.EventSender;
import org.eclipse.jetty.util.ajax.JSON;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.slf4j.MDC;

import static com.carlospatinos.salesforceconnector.connector.LoginHelper.login;

@Component
public class SalesforceEventNotificationListener {

    private static final Logger log = LoggerFactory.getLogger(SalesforceEventNotificationListener.class);

    @Value("${salesforce.username}")
    private String username;

    @Value("${salesforce.password}")
    private String password;

    @Value("${salesforce.channel}")
    private String channel;

    @Value("${salesforce.replayFrom:-2}")
    private Long replayFrom;

    @Autowired
    EventSender sender;

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

        Consumer<Map<String, Object>> consumer =
                (
                        event -> {
                            try {

                                JSONObject eventObj = new JSONObject(event);
                                String uniqueId = eventObj.getJSONObject("sobject").getString("Id");
                                String type = eventObj.getJSONObject("event").getString("type");
                                Integer replayId = eventObj.getJSONObject("event").getInt("replayId");
                                // TODO this is the starting point of the action to send events
                                MDC.put("uniqueId", uniqueId);

                                log.info("New event received as: {}", JSON.toString(event));
                                Transaction user = Transaction.newBuilder().setUniqueId(uniqueId).setEventType(type)
                                        .setReplayId(replayId).build();


                                // TODO fix the topic
                                sender.send(user);
                            } catch (ClassCastException e) {
                                log.error(e.getMessage());
                            } finally {
                                MDC.clear();
                            }

                        }

                );

        CometdConnector connector = new CometdConnector(params);

        connector.setBearerTokenProvider(tokenProvider);

        connector.start().get(5, TimeUnit.SECONDS);

        TopicSubscription subscription = connector.subscribe(channel, replayFrom, consumer).get(5, TimeUnit.SECONDS);

        log.info("Subscribed to: {}", subscription);
    }
}
