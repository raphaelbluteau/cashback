package com.github.raphaelbluteau.cashback.gateway.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "sold_items")
@Data
public class SoldItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private AlbumEntity album;

    private BigDecimal cashback;

}
