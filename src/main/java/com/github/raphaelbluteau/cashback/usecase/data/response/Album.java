package com.github.raphaelbluteau.cashback.usecase.data.response;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Album {

    private String id;
    private String name;
    private GenreEnum genre;
    private BigDecimal price;
}
