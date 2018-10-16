package com.carlospatinos.salesforceconnector;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import com.carlospatinos.salesforceconnector.event.EventReceiver;
import com.carlospatinos.salesforceconnector.event.EventSender;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.junit4.SpringRunner;
import com.carlospatinos.salesforceconnector.avro.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class SpringKafkaApplicationTest {

    private static final String SALESFORCE_TOPIC = "salesforce-event";

    @Autowired
    private EventSender sender;

    @Autowired
    private EventReceiver receiver;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, SALESFORCE_TOPIC);

    @Before
    public void setUp() throws Exception {
        // wait until the partitions are assigned
        for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
                .getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer,
                    embeddedKafka.getPartitionsPerTopic());
        }
    }

    @Test
    public void testReceiver() throws Exception {
        User user = User.newBuilder().setName("John Doe").setFavoriteColor("green")
                .setFavoriteNumber(null).build();
        sender.send(SALESFORCE_TOPIC, user);

        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertThat(receiver.getLatch().getCount()).isEqualTo(0);
    }
}