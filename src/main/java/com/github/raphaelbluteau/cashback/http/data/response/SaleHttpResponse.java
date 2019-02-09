package com.github.raphaelbluteau.cashback.http.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleHttpResponse {

    private Long id;

    private LocalDateTime createdAt;

    private BigDecimal cashback;

    List<SoldItemHttpResponse> items;



}
