package com.github.raphaelbluteau.cashback.service.data;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Album {

    private String id;
    private String name;
    private GenreEnum genre;
    private BigDecimal price;
    private List<Artist> artists;

}
