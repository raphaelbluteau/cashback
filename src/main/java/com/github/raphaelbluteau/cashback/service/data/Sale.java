package com.github.raphaelbluteau.cashback.service.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

    private Long id;

    private LocalDateTime createdAt;

    private BigDecimal cashback;

    List<SoldItem> items;
}
