package com.github.raphaelbluteau.cashback.service.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoldItem {

    private Long id;

    private Album album;

    private BigDecimal cashback;
}