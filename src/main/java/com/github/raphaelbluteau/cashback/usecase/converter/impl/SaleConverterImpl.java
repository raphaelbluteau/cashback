package com.github.raphaelbluteau.cashback.usecase.converter.impl;

import com.github.raphaelbluteau.cashback.gateway.repository.entity.SaleEntity;
import com.github.raphaelbluteau.cashback.usecase.converter.SaleConverter;
import com.github.raphaelbluteau.cashback.usecase.converter.SoldItemConverter;
import com.github.raphaelbluteau.cashback.usecase.data.response.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class SaleConverterImpl implements SaleConverter {

    private final SoldItemConverter soldItemConverter;

    @Override
    public Sale toUseCase(SaleEntity saleEntity) {

        if (isNull(saleEntity)) {
            return null;
        }

        return Sale.builder()
                .id(saleEntity.getId())
                .cashback(saleEntity.getCashback())
                .createdAt(saleEntity.getCreatedAt())
                .items(soldItemConverter.toUseCase(saleEntity.getItems()))
                .build();
    }

    @Override
    public Page<Sale> toUseCase(Page<SaleEntity> saleEntities) {

        if (saleEntities.isEmpty()) {
            return Page.empty();
        }

        List<Sale> collection = saleEntities.get().map(this::toUseCase).collect(Collectors.toList());

        return new PageImpl<>(collection, saleEntities.getPageable(), saleEntities.getTotalElements());
    }
}
