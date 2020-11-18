package com.jordanec.store.consumer.service;

import com.jordanec.store.consumer.constant.ShipmentStatus;
import com.jordanec.store.consumer.repository.ShipmentRepository;
import com.jordanec.store.consumer.entity.Shipment;
import com.jordanec.store.dtos.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ShipmentService
{
    @Autowired
    ShipmentRepository shipmentRepository;

    @Transactional
    public void registerOrder(OrderDTO order)
    {
        Shipment shipment = new Shipment();
        shipment.setShippingAddress(order.getShippingAddress());
        shipment.setClient(order.getClient());
        shipment.setOrderNumber(order.getOrderNumber());
        shipment.setCreation(LocalDateTime.now());
        shipment.setStatus(ShipmentStatus.PREPARING.toString());
        shipmentRepository.save(shipment);
    }

    public Shipment findFirstByStatusOrderByCreation(ShipmentStatus shipmentStatus)
    {
        return shipmentRepository.findFirstByStatusOrderByCreation(shipmentStatus.toString());
    }

    public void updateStatus(Shipment shipment, ShipmentStatus shipmentStatus) {
        shipmentRepository.updateStatus(shipment.getShipmentId(), shipmentStatus.toString());
    }
}
