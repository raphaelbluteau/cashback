package com.github.raphaelbluteau.cashback.converter;

import com.github.raphaelbluteau.cashback.gateway.repository.entity.SoldItemEntity;
import com.github.raphaelbluteau.cashback.http.data.response.SoldItemHttpResponse;
import com.github.raphaelbluteau.cashback.service.data.SoldItem;

import java.util.List;

public interface SoldItemConverter {

    List<SoldItemEntity> toEntity(List<SoldItem> soldItems);

    List<SoldItem> fromEntity(List<SoldItemEntity> soldItems);

    List<SoldItemHttpResponse> toResponse(List<com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem> soldItemsEntity);

    List<com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem> toUseCase(List<SoldItemEntity> soldItemEntity);

    List<SoldItem> fromUseCase(List<com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem> items);

    List<com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem> toUseCaseList(List<SoldItem> items);
}
