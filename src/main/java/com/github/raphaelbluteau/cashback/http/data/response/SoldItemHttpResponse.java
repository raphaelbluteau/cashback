package com.github.raphaelbluteau.cashback.http.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoldItemHttpResponse {

    private Long id;

    private AlbumHttpResponse album;

    private BigDecimal cashback;
}
