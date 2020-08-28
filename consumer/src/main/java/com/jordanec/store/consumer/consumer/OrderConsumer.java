package com.jordanec.store.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jordanec.store.consumer.service.ShipmentService;
import com.jordanec.store.dtos.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class OrderConsumer
{
    public final static String TOPIC_NAME = "order-events";
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ShipmentService shipmentService;

    /**
     * Since partition is not defined here, it will receive all messages for any partition.
     *
     *
     * @param consumerRecord
     * @throws JsonProcessingException
     */
    @KafkaListener(topics = TOPIC_NAME)   //If there's a listener for each partition is not possible to have another one for all partitions
    public void onMessageNoPartitionDefined(ConsumerRecord<String,String> consumerRecord)
            throws JsonProcessingException, InterruptedException
    {
        logRecordConsumption("onMessageNoPartitionDefined", consumerRecord);
        Thread.sleep(1000); //simulates some task
        OrderDTO order = objectMapper.readValue(consumerRecord.value(), OrderDTO.class);
        shipmentService.registerOrder(order);
    }

    /**
     * Since partition is defined here it will receive messages for that partition only.
     *
     * @param consumerRecord
     * @throws JsonProcessingException
     */
    //@KafkaListener(topicPartitions = @TopicPartition(topic = TOPIC_NAME, partitions = {"0"}))
    public void onMessagePartition0(ConsumerRecord<String,String> consumerRecord) throws JsonProcessingException, InterruptedException
    {
        logRecordConsumption("onMessagePartition0", consumerRecord);
        Thread.sleep(1000); //simulates some task
        OrderDTO order = objectMapper.readValue(consumerRecord.value(), OrderDTO.class);
        shipmentService.registerOrder(order);
    }

    /**
     * Since partition is defined here it will receive messages for that partition only.
     *
     * @param consumerRecord
     * @throws JsonProcessingException
     */
    //@KafkaListener(topicPartitions = {@TopicPartition(topic = TOPIC_NAME, partitions = {"1"})})
    public void onMessagePartition1(ConsumerRecord<String,String> consumerRecord) throws JsonProcessingException, InterruptedException
    {

        logRecordConsumption("onMessagePartition1", consumerRecord);
        Thread.sleep(1000); //simulates some task
        OrderDTO order = objectMapper.readValue(consumerRecord.value(), OrderDTO.class);
        shipmentService.registerOrder(order);
    }

    /**
     * Since partition is defined here it will receive messages for that partition only.
     *
     * @param consumerRecord
     * @throws JsonProcessingException
     */
    //@KafkaListener(topicPartitions = {@TopicPartition(topic = TOPIC_NAME, partitions = {"2"})})
    public void onMessagePartition2(ConsumerRecord<String,String> consumerRecord) throws JsonProcessingException, InterruptedException
    {
        logRecordConsumption("onMessagePartition2", consumerRecord);
        Thread.sleep(1000); //simulates some task
        OrderDTO order = objectMapper.readValue(consumerRecord.value(), OrderDTO.class);
        shipmentService.registerOrder(order);
    }

    private void logRecordConsumption(String methodName, ConsumerRecord<String, String> consumerRecord)
    {
        StringBuilder sb = new StringBuilder();
        consumerRecord.headers()
                .forEach(h -> {
                    try
                    {
                        sb.append(h.key()).append("=").append(new String(h.value(), "UTF-8")).append(" ");
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        log.error("logRecordConsumption() -> Error while decoding headers", e);
                    }
                });
        log.info("{}() -> ConsumerRecord: {} Headers: {}", methodName, consumerRecord, sb.toString());
    }
}
