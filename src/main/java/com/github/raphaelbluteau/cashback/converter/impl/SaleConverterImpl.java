package com.github.raphaelbluteau.cashback.converter.impl;

import com.github.raphaelbluteau.cashback.converter.SaleConverter;
import com.github.raphaelbluteau.cashback.converter.SoldItemConverter;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.SaleEntity;
import com.github.raphaelbluteau.cashback.http.data.response.SaleHttpResponse;
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
    public SaleEntity toEntity(com.github.raphaelbluteau.cashback.service.data.Sale sale) {

        if (isNull(sale)) {
            return null;
        }

        return SaleEntity.builder()
                .id(sale.getId())
                .cashback(sale.getCashback())
                .items(soldItemConverter.toEntity(sale.getItems()))
                .build();
    }

    @Override
    public com.github.raphaelbluteau.cashback.service.data.Sale fromEntity(SaleEntity sale) {

        if (isNull(sale)) {
            return null;
        }

        return com.github.raphaelbluteau.cashback.service.data.Sale.builder()
                .id(sale.getId())
                .cashback(sale.getCashback())
                .createdAt(sale.getCreatedAt())
                .items(soldItemConverter.fromEntity(sale.getItems()))
                .build();
    }

    @Override
    public SaleHttpResponse toResponse(Sale sale) {

        if (isNull(sale)) {
            return null;
        }

        return SaleHttpResponse.builder()
                .id(sale.getId())
                .cashback(sale.getCashback())
                .createdAt(sale.getCreatedAt())
                .items(soldItemConverter.toResponse(sale.getItems()))
                .build();
    }

    @Override
    public Page<SaleHttpResponse> toResponse(Page<Sale> sales) {

        if (sales.isEmpty()) {
            return Page.empty();
        }

        List<SaleHttpResponse> collection = sales.get().map(this::toResponse).collect(Collectors.toList());

        return new PageImpl<>(collection, sales.getPageable(), sales.getTotalElements());
    }

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

    @Override
    public com.github.raphaelbluteau.cashback.service.data.Sale fromUseCase(Sale sale) {

        if (isNull(sale)) {
            return null;
        }

        return com.github.raphaelbluteau.cashback.service.data.Sale.builder()
                .id(sale.getId())
                .items(soldItemConverter.fromUseCase(sale.getItems()))
                .createdAt(sale.getCreatedAt())
                .cashback(sale.getCashback())
                .build();
    }

    @Override
    public Sale toUseCase(com.github.raphaelbluteau.cashback.service.data.Sale sale) {

        if (isNull(sale)) {
            return null;
        }

        return Sale.builder()
                .id(sale.getId())
                .cashback(sale.getCashback())
                .createdAt(sale.getCreatedAt())
                .items(soldItemConverter.toUseCaseList(sale.getItems()))
                .build();
    }

    @Override
    public Page<com.github.raphaelbluteau.cashback.service.data.Sale> fromEntity(Page<SaleEntity> sales) {

        if (sales.isEmpty()) {
            return Page.empty();
        }

        List<com.github.raphaelbluteau.cashback.service.data.Sale> collection = sales.get().map(this::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(collection, sales.getPageable(), sales.getTotalElements());
    }

    @Override
    public Page<Sale> toUseCasePage(Page<com.github.raphaelbluteau.cashback.service.data.Sale> sales) {

        if (sales.isEmpty()) {
            return Page.empty();
        }

        List<Sale> collection = sales.get().map(this::toUseCase).collect(Collectors.toList());

        return new PageImpl<>(collection, sales.getPageable(), sales.getTotalElements());
    }

}
