package com.jordanec.store.consumer.repository;

import com.jordanec.store.consumer.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long>
{
    Shipment findFirstByStatusOrderByCreation(String status);

    @Modifying
    @Query("update Shipment s set s.status = :status where s.shipmentId = :shipmentId")
    void updateStatus(@Param(value = "shipmentId") Long shipmentId, @Param(value = "status") String status);

}
