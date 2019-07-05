package com.github.raphaelbluteau.cashback.service.impl;

import com.github.raphaelbluteau.cashback.converter.CashbackParametersConverter;
import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.repository.CashbackParametersRepository;
import com.github.raphaelbluteau.cashback.service.CashbackParametersService;
import com.github.raphaelbluteau.cashback.service.data.CashbackParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
@RequiredArgsConstructor
public class CashbackParametersServiceImpl implements CashbackParametersService {

    private final CashbackParametersRepository repository;
    private final CashbackParametersConverter converter;

    @Override
    public CashbackParameters findByDayOfWeekAndGenre(DayOfWeek dayOfWeek, GenreEnum genreEnum) {

        return converter.fromEntity(repository.findByDayOfWeekAndGenre(dayOfWeek, genreEnum));
    }
}
