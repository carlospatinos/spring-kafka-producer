package com.carlospatinos.salesforceconnector;


import com.carlospatinos.salesforceconnector.config.SenderKafkaConfig;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"local"})
@TestPropertySource(locations = "classpath:application.properties")
@ContextConfiguration(classes = SenderKafkaConfig.class)
public class SecondTest {

//    private static final String TOPIC="test-topic";
//    private static final String SOURCE_ID="source_id";
//    private static final String DESTINATION_ID="destination_id";
//    private static final String DEVICE_AGENT="iOS";
//    private static final String URL="http://localhost:%S/message";
//
//    @LocalServerPort
//    private int randomServerPort;
//    @MockBean
//    private ChannelService channelService;
//    @Autowired
//    private KafkaProducerListener listener;
//
//    private KafkaMessageListenerContainer<String, String> container;
//
//    private BlockingQueue<ConsumerRecord<String, String>> records;
//
//
//    @ClassRule
//    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, TOPIC);
//
//    @BeforeClass
//    public static void setup() {
//        System.setProperty("kafka.bootstrap-servers", embeddedKafka.getBrokersAsString());
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        // set up the Kafka consumer properties
//        Map<String, Object> consumerProperties =
//                KafkaTestUtils.consumerProps("sender", "false", embeddedKafka);
//        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, BaseMessageDeserializer.class);
//
//        DefaultKafkaConsumerFactory<String, BaseMessage> consumerFactory =
//                new DefaultKafkaConsumerFactory<>(consumerProperties);
//        ContainerProperties containerProperties = new ContainerProperties(TOPIC);
//
//        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
//        records = new LinkedBlockingQueue<>();
//
//        container.setupMessageListener((MessageListener<String, BaseMessage>) record -> records.add(record));
//        container.start();
//        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
//        when(channelService.findChannelId(anyString())).thenReturn("channel");
//    }
//
//    @After
//    public void tearDown() {
//        // stop the container
//        container.stop();
//    }
//
//    @Test
//    public void shouldSendMessage() throws Exception {
//        final String id = UUID.randomUUID().toString();
//
//        final IMessage message = IMessage.builder()
//                .id(id)
//                .sourceId(SOURCE_ID)
//                .destinationId(DESTINATION_ID)
//                .type(MessageType.TEXT)
//                .body("body").build();
//        when(listener.isInterestedInSuccess()).thenReturn(true);
//        sendMessage(message);
//        ConsumerRecord<String, BaseMessage> received = records.poll(10, TimeUnit.SECONDS);
//
//        assertNotNull(received);
//        assertEquals(id, received.key());
//        message.setDeviceAgent(DEVICE_AGENT);
//        message.setConversationId(((IMessage) received.value()).getConversationId());
//        message.setTimeStamp(((IMessage) received.value()).getTimeStamp());
//        message.setOrderNumber(((IMessage) received.value()).getOrderNumber());
//        assertEquals(message, received.value());
//        verify(listener, timeout(5000)).onSuccess(eq(TOPIC), any(), eq(message.getId()), eq(message), any(RecordMetadata.class));
//    }

}
