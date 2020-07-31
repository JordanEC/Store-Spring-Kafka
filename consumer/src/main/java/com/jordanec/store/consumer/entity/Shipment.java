package com.jordanec.store.consumer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity(name = "Shipment")
@Table(name = "sto_shipment")

public class Shipment
{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shipmentId;
    @Column
    private String orderNumber;
    @Column
    private LocalDateTime creation;
    @Column
    private String client;
    @Column
    private String shippingAddress;
    @Column
    private String status;
}
