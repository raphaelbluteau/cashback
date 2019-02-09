package com.github.raphaelbluteau.cashback.http.converter.impl;

import com.github.raphaelbluteau.cashback.http.converter.SalesHttpResponseConverter;
import com.github.raphaelbluteau.cashback.http.converter.SoldItemHttpResponseConverter;
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
public class SalesHttpResponseConverterImpl implements SalesHttpResponseConverter {

    private final SoldItemHttpResponseConverter soldItemConverter;

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
}
