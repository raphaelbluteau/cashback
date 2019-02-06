package com.github.raphaelbluteau.cashback.http.data.request;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumHttpRequest {

    private Long id;

    private String name;

    private BigDecimal price;

    private GenreEnum genre;
}
