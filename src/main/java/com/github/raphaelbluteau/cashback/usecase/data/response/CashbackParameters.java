package com.github.raphaelbluteau.cashback.usecase.data.response;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.DayOfWeek;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashbackParameters {

    private Long id;

    private BigDecimal percentage;

    private GenreEnum genre;

    private DayOfWeek dayOfWeek;
}
