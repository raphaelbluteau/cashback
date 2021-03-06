package com.github.raphaelbluteau.cashback.http.data.response;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumHttpResponse {

    private Long id;

    private String name;

    private BigDecimal price;

    private GenreEnum genre;
}
