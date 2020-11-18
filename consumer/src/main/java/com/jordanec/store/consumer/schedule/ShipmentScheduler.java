package com.jordanec.store.consumer.schedule;

import com.jordanec.store.consumer.constant.ShipmentStatus;
import com.jordanec.store.consumer.entity.Shipment;
import com.jordanec.store.consumer.service.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Slf4j
@Component
public class ShipmentScheduler {
    @Autowired
    ShipmentService shipmentService;

    /**
     * To simulate errors while processing shipments, a random number is generated and if it matches with a given
     * value an exception is thrown. In this way the retryable mechanism comes into picture.
     *
     * @throws Exception
     */
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 60000))
    @Transactional
    @Scheduled(cron = "#{ ${application.scheduler.shipmentDispatcher.enabled} ? '${application.scheduler.shipmentDispatcher.cron}' : '-' }")
    public void shipmentDispatcher() throws Exception {
        log.info("shipmentDispatcherScheduler() -> Started...");
        Random random = new Random();
        if (5 == (random.nextInt(5) + 1))
        {
            log.warn("random number matches!");
            throw new Exception("Simulating an error if a random number is 5");
        }

        Shipment shipment = shipmentService.findFirstByStatusOrderByCreation(ShipmentStatus.PREPARING);
        if (shipment != null) {
            shipmentService.updateStatus(shipment, ShipmentStatus.SHIPPED);
            log.info("shipmentDispatcherScheduler() -> Shipment: {} was shipped!", shipment.getShipmentId());
        } else {
            log.info("shipmentDispatcherScheduler() -> No shipment with status: {} found", ShipmentStatus.PREPARING);
        }
    }

    @Recover
    public void shipmentDispatcherRecover(Exception exception) {
        log.error("shipmentDispatcherScheduler() -> Max attempts done for the method hence executing recovery", exception);
    }
}
