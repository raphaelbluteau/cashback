package com.github.raphaelbluteau.cashback.gateway.repository.entity;


import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.DayOfWeek;

@Entity
@Table(name = "cashback_parameters")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CashbackParametersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Digits(integer=5, fraction=2)
    @NotNull
    private BigDecimal percentage;

    @Enumerated(EnumType.STRING)
    @NotNull
    private GenreEnum genre;

    @Enumerated(EnumType.STRING)
    @NotNull
    private DayOfWeek dayOfWeek;

}
