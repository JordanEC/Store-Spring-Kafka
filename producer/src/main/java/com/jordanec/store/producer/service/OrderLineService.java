package com.jordanec.store.producer.service;

import com.jordanec.store.producer.entity.OrderLine;
import com.jordanec.store.producer.repository.OrderLineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineService
{

    @Autowired
    OrderLineRepository orderLineRepository;

    public List<OrderLine> createOrderLines(List<OrderLine> orderLines)
    {
        return orderLineRepository.saveAll(orderLines);
    }
}
