package com.jordanec.store.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jordanec.store.producer.entity.Order;
import com.jordanec.store.producer.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "api")
public class OrderController
{
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/order/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> create(@RequestBody Order order, @RequestParam(required = false) Integer partition,
            @RequestParam(defaultValue = "false") Boolean synchronousCall)
    {
        try
        {
            return new ResponseEntity<>(orderService.create(order, partition, synchronousCall), HttpStatus.OK);
        }
        catch (Throwable throwable)
        {
            log.error("create() -> order: {}, partition: {}", order, partition, throwable);
            return ResponseEntity.badRequest().build();
        }
    }
}
