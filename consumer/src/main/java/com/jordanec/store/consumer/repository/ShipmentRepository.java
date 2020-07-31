package com.jordanec.store.consumer.repository;

import com.jordanec.store.consumer.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long>
{
}
