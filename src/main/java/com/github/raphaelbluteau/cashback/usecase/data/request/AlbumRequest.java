package com.github.raphaelbluteau.cashback.usecase.data.request;

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
public class AlbumRequest {

    private Long id;

    private String name;

    private BigDecimal price;

    private GenreEnum genre;
}
