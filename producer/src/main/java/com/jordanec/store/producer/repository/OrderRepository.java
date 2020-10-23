package com.jordanec.store.producer.repository;

import com.jordanec.store.producer.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>
{
    Order findByOrderNumber(String orderNumber);
}
