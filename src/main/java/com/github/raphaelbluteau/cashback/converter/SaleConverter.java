package com.github.raphaelbluteau.cashback.converter;

import com.github.raphaelbluteau.cashback.gateway.repository.entity.SaleEntity;
import com.github.raphaelbluteau.cashback.http.data.response.SaleHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.data.response.Sale;
import org.springframework.data.domain.Page;

public interface SaleConverter {

    SaleEntity toEntity(com.github.raphaelbluteau.cashback.service.data.Sale sale);

    com.github.raphaelbluteau.cashback.service.data.Sale fromEntity(SaleEntity sale);

    SaleHttpResponse toResponse(Sale sale);

    Page<SaleHttpResponse> toResponse(Page<Sale> sales);

    Sale toUseCase(SaleEntity saleEntity);

    Page<Sale> toUseCase(Page<SaleEntity> saleEntities);

    com.github.raphaelbluteau.cashback.service.data.Sale fromUseCase(Sale sale);

    Sale toUseCase(com.github.raphaelbluteau.cashback.service.data.Sale sale);

    Page<com.github.raphaelbluteau.cashback.service.data.Sale> fromEntity(Page<SaleEntity> sales);

    Page<Sale> toUseCasePage(Page<com.github.raphaelbluteau.cashback.service.data.Sale> sales);
}
