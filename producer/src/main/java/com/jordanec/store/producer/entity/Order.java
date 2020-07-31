package com.jordanec.store.producer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "Order")
@Table(name = "sto_order", uniqueConstraints = { @UniqueConstraint(columnNames = "orderNumber")})
public class Order
{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private String orderNumber;
    @Column
    private LocalDateTime creation;
    @Column
    private String client;
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.DETACH,
            orphanRemoval = true)
    private List<OrderLine> orderLines;
    @Column
    private Double total;
    @Column
    private String shippingAddress;
}
