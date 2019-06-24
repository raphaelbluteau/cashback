package com.github.raphaelbluteau.cashback.converter;

import com.github.raphaelbluteau.cashback.gateway.repository.entity.CashbackParametersEntity;
import com.github.raphaelbluteau.cashback.service.data.CashbackParameters;

public interface CashbackParametersConverter {

    CashbackParameters fromEntity(CashbackParametersEntity cashbackParametersEntity);

    com.github.raphaelbluteau.cashback.usecase.data.response.CashbackParameters toUseCase(CashbackParameters cashbackParameters);
}
