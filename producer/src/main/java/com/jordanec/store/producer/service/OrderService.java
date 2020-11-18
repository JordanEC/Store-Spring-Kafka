package com.jordanec.store.producer.service;

import com.jordanec.store.producer.entity.Item;
import com.jordanec.store.producer.entity.Order;
import com.jordanec.store.producer.entity.OrderLine;
import com.jordanec.store.producer.entity.OrderLineId;
import com.jordanec.store.producer.producer.OrderProducer;
import com.jordanec.store.producer.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OrderService
{

    @Autowired
    OrderProducer orderProducer;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderLineService orderLineService;
    @Autowired
    ItemService itemService;

    @Autowired
    EntityManager em;

    @Transactional
    public Order create(Order order, Integer partition, Boolean synchronousCall) throws Throwable
    {
        validateOrder(order);
        List<OrderLine> orderLines = calculateOrder(order);
        orderRepository.save(order);
        orderLines.forEach(orderLine -> {
            orderLine.setOrder(order);
            orderLine.setOrderLineId(new OrderLineId(order.getOrderId(), orderLine.getItem().getItemId()));
        });
        orderLineService.createOrderLines(orderLines);
        orderLines.forEach(orderLine -> orderLine.setOrder(null));
        order.setOrderLines(orderLines);
        if (synchronousCall)
        {
            orderProducer.sendOrderCreationSynchronous(order, partition);
        }
        else
        {
            orderProducer.sendOrderCreationAsynchronous(order, partition);
        }
        return order;
    }

    public Order findByOrderNumber(String orderNumber, boolean includeOrderLines) {
        Order order;
        if (includeOrderLines) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery query = cb.createQuery();
            Root rootOrder = query.from(Order.class);
            rootOrder.fetch("orderLines", JoinType.LEFT);
            query.select(rootOrder);
            query.where(cb.equal(rootOrder.get("orderNumber"), orderNumber));
            order = (Order) em.createQuery(query).getSingleResult();
        } else {
                order = orderRepository.findByOrderNumber(orderNumber);
        }
        return order;
    }

    private List<OrderLine> calculateOrder(Order order) throws Exception
    {
        double orderTotal = 0;
//order.getOrderLineS().stream().mapToDouble(line -> line.getItem().getUnitPrice() * line.getQuantity()).sum()
        for (OrderLine orderLine: order.getOrderLines())
        {
            orderLine.setItemTotal(orderLine.getItem().getUnitPrice() * orderLine.getQuantity());
            orderTotal += orderLine.getItemTotal();
        }
        order.setTotal(orderTotal);
        List<OrderLine> orderLines = order.getOrderLines();
        order.setOrderLines(null);
        order.setCreation(LocalDateTime.now());
        return orderLines;
    }

    private void validateOrder(Order order) throws Exception
    {
        if (CollectionUtils.isEmpty(order.getOrderLines()))
        {
            throw new Exception("No OrderLines");
        }
        //validate items
        for(OrderLine orderLine : order.getOrderLines())
        {
            if (orderLine.getQuantity() == null || orderLine.getQuantity() < 1)
            {
                throw new Exception("Invalid quantity for order line: " + orderLine);
            }
            if (orderLine.getItem() == null)
            {
                throw new Exception("Item missing for order line: " + orderLine);
            }
            Item item = null;
            if (!StringUtils.isEmpty(orderLine.getItem().getItemId() != null))
            {
                item = itemService.findByItemId(orderLine.getItem().getItemId());
            }
            if (item == null)
            {
                if (!StringUtils.isEmpty(orderLine.getItem().getName() != null))
                {
                    item = itemService.findByName(orderLine.getItem().getName());
                }
                if (item == null)
                {
                    throw new Exception("Item not found: "+ orderLine.getItem());
                }
            }
            orderLine.setItem(item);
        }
        //... TODO
    }
}
