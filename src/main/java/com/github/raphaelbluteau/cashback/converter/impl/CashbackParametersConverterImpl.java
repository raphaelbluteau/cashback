package com.github.raphaelbluteau.cashback.converter.impl;

import com.github.raphaelbluteau.cashback.converter.CashbackParametersConverter;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.CashbackParametersEntity;
import com.github.raphaelbluteau.cashback.service.data.CashbackParameters;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class CashbackParametersConverterImpl implements CashbackParametersConverter {

    @Override
    public CashbackParameters fromEntity(CashbackParametersEntity cashbackParametersEntity) {

        if (isNull(cashbackParametersEntity)) {
            return null;
        }

        return CashbackParameters.builder()
                .id(cashbackParametersEntity.getId())
                .dayOfWeek(cashbackParametersEntity.getDayOfWeek())
                .genre(cashbackParametersEntity.getGenre())
                .percentage(cashbackParametersEntity.getPercentage())
                .build();

    }

    @Override
    public com.github.raphaelbluteau.cashback.usecase.data.response.CashbackParameters toUseCase(CashbackParameters cashbackParameters) {

        if (isNull(cashbackParameters)) {
            return null;
        }

        return com.github.raphaelbluteau.cashback.usecase.data.response.CashbackParameters.builder()
                .id(cashbackParameters.getId())
                .dayOfWeek(cashbackParameters.getDayOfWeek())
                .genre(cashbackParameters.getGenre())
                .percentage(cashbackParameters.getPercentage())
                .build();
    }
}
