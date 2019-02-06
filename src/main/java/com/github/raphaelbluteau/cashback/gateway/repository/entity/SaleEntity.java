package com.github.raphaelbluteau.cashback.gateway.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "sales")
@Data
public class SaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal cashback;

    @OneToMany
    List<SoldItemEntity> items;
}
