package com.jordanec.store.producer.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "orderLineId",
        scope = OrderLine.class)
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "OrderLine")
@Table(name = "sto_order_line")
public class OrderLine
{
    @EmbeddedId
    private OrderLineId orderLineId;
    @ManyToOne
    @MapsId("itemId")
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @ToString.Exclude
    private Order order;
    @Column
    private Integer quantity;
    @Column
    private Double itemTotal;
}

