package com.jordanec.store.producer.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "orderId",
        scope = Order.class)
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
            fetch = FetchType.LAZY,
            mappedBy = "order",
            cascade = CascadeType.DETACH,
            orphanRemoval = true)
    private List<OrderLine> orderLines;
    @Column
    private Double total;
    @Column
    private String shippingAddress;
}
