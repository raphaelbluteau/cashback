package com.github.raphaelbluteau.cashback.gateway.repository;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.CashbackParametersEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.DayOfWeek;

public interface CashbackParametersRepository extends PagingAndSortingRepository<CashbackParametersEntity, Long> {

    CashbackParametersEntity findByDayOfWeekAndGenre(DayOfWeek dayOfWeek, GenreEnum genreEnum);
}
