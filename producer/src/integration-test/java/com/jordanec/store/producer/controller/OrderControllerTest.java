package com.jordanec.store.producer.controller;

import com.jordanec.store.producer.entity.Item;
import com.jordanec.store.producer.entity.OrderLine;
import com.jordanec.store.producer.producer.OrderProducer;
import com.jordanec.store.producer.service.ItemService;
import com.jordanec.store.producer.H2JpaConfig;
import com.jordanec.store.producer.ProducerApp;
import com.jordanec.store.producer.entity.Order;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { ProducerApp.class,
        H2JpaConfig.class })
@EmbeddedKafka(topics = {OrderProducer.TOPIC_ORDER_EVENTS}, partitions = 3)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}"})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")*/
public class OrderControllerTest
{

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    ItemService itemService;

    private Consumer<String, String> consumer;

    Order inputOrder;

    @Before
    public void setUp() {
        Map<String, Object> configs = new HashMap<>(
                KafkaTestUtils.consumerProps("order-events_shipment", "true", embeddedKafkaBroker));
        consumer = new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new StringDeserializer())
                .createConsumer();
        embeddedKafkaBroker.consumeFromAllEmbeddedTopics(consumer);
    }

    @After
    public void tearDown()
    {
        consumer.close();
    }

//    @Test
    @Timeout(5)
    public void create_success() throws Exception
    {
        createTestData();

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<Order> request = new HttpEntity<>(inputOrder, headers);

        ResponseEntity<Order> responseEntity = restTemplate
                .exchange("/api/order/create", HttpMethod.POST, request, Order.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertNotNull(responseEntity.getBody().getOrderId());
        ConsumerRecord<String, String> consumerRecord = KafkaTestUtils
                .getSingleRecord(consumer, OrderProducer.TOPIC_ORDER_EVENTS);
        Assert.assertNotNull(consumerRecord);
        Assert.assertEquals(inputOrder.getOrderNumber(), consumerRecord.key());
        Assert.assertNotNull(consumerRecord.value());
        Assert.assertEquals(OrderProducer.TOPIC_ORDER_EVENTS, consumerRecord.topic());
    }

    private void createTestData() throws Exception
    {
        List<Item> items = new ArrayList<>();

        Item item1 = new Item();
        item1.setName("Xperia X ii");
        item1.setDescription("Xperia X ii 256 GB");
        item1.setUnitPrice(1000D);
        items.add(item1);

        Item item2 = new Item();
        item2.setName("Mac Pro");
        item2.setDescription("Mac Pro 2020 32 GB");
        item2.setUnitPrice(4000D);
        items.add(item2);
        itemService.create(items);

        inputOrder = new Order();
        inputOrder.setOrderNumber("12931478901243-47");
        inputOrder.setClient("Jordan Espinoza C.");
        inputOrder.setShippingAddress("33122, FL, USA");
        List<OrderLine> orderLines =  new ArrayList<>();
        OrderLine orderLine = new OrderLine();
        orderLine.setItem(item1);
        orderLine.setQuantity(5);
        orderLines.add(orderLine);
        orderLine = new OrderLine();

        orderLine.setItem(item2);
        orderLine.setQuantity(1);
        orderLines.add(orderLine);
        inputOrder.setOrderLines(orderLines);
    }
}
