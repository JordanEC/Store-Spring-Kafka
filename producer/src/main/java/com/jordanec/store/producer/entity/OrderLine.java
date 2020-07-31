package com.jordanec.store.producer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "OrderLine")
@Table(name = "sto_order_line")
public class OrderLine
{
    @EmbeddedId
    private OrderLineId orderLineId;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itemId")
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Order order;
    @Column
    private Integer quantity;
    @Column
    private Double itemTotal;
}

