package com.jordanec.store.producer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "Item")
@Table(name = "sto_item", uniqueConstraints = { @UniqueConstraint(columnNames = "name")} )
public class Item
{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;
    private String name;
    private String description;
    private Double unitPrice;
}
