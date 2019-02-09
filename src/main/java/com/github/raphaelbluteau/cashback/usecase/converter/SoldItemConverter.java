package com.github.raphaelbluteau.cashback.usecase.converter;

import com.github.raphaelbluteau.cashback.gateway.repository.entity.SoldItemEntity;
import com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem;

import java.util.List;

public interface SoldItemConverter {

    List<SoldItem> toUseCase(List<SoldItemEntity> soldItemEntity);
}
