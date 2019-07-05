package com.github.raphaelbluteau.cashback.service;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.service.data.CashbackParameters;

import java.time.DayOfWeek;

public interface CashbackParametersService {

    CashbackParameters findByDayOfWeekAndGenre(DayOfWeek dayOfWeek, GenreEnum genreEnum);
}
