package com.jordanec.store.producer.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jordanec.store.producer.entity.Order;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Arrays;

@Component
@Slf4j
public class OrderProducer
{
    public final static String TOPIC_ORDER_EVENTS = "order-events";

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${application.name}")
    private String APPLICATION_NAME;

    public void sendOrderCreationAsynchronous(Order order, Integer partition) throws Throwable
    {
        log.debug("sendOrderCreationAsynchronous() -> Sending message Asynchronously...");
        String key = order.getOrderNumber();
        String value = objectMapper.writeValueAsString(order);

        ProducerRecord<String, String> producerRecord = producerRecordBuilder(key, value, partition);
//        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate
//                .send(TOPIC_ORDER_EVENTS, partition, key, value);
        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(producerRecord);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>()
        {
            @SneakyThrows
            @Override
            public void onFailure(Throwable ex)
            {
                handleFailure(key, value, ex);
            }
            @Override
            public void onSuccess(SendResult<String, String> result) {
                handleSuccess(key, value, result);
            }
        });
        log.debug("sendOrderCreationAsynchronous() -> Message sent asynchronously!");
    }

    public void sendOrderCreationSynchronous(Order order, Integer partition)
            throws Throwable
    {
        log.debug("sendOrderCreationSynchronous() -> Sending message synchronously...");

        String key = order.getOrderNumber();
        String value = objectMapper.writeValueAsString(order);
        try
        {
            ProducerRecord<String, String> producerRecord = producerRecordBuilder(key, value, partition);
            SendResult<String, String> sendResult = kafkaTemplate.send(producerRecord).get();
            log.debug("sendOrderCreationSynchronous() -> Message sent synchronously!");
            handleSuccess(key, value, sendResult);
        }
        catch (Throwable throwable)
        {
            handleFailure(key, value, throwable);
        }
    }

    private ProducerRecord<String, String> producerRecordBuilder(String key, String value, Integer partition)
    {
        return new ProducerRecord<>(TOPIC_ORDER_EVENTS, partition, key,
                value, Arrays.asList(new RecordHeader("APPLICATION_NAME", APPLICATION_NAME.getBytes())));
    }

    private void handleFailure(String key, String value, Throwable throwable) throws Throwable
    {
        log.error("handleFailure() -> Error Sending the Message! Key: {} Value: {}.", key, value, throwable);
        throw throwable;
    }

    private void handleSuccess(String key, String value, SendResult<String, String> result)
    {
        log.info(
                "handleSuccess() -> Message Sent SuccessFully! Key: {} Value: {} RecordMetadata[offset: {}, partition: {}, timestamp: {}]",
                key, value, result.getRecordMetadata().offset(), result.getRecordMetadata().partition(),
                result.getRecordMetadata().timestamp());
    }
}
