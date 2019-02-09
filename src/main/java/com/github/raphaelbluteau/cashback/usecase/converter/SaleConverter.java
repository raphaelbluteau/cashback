package com.github.raphaelbluteau.cashback.usecase.converter;

import com.github.raphaelbluteau.cashback.gateway.repository.entity.SaleEntity;
import com.github.raphaelbluteau.cashback.usecase.data.response.Sale;
import org.springframework.data.domain.Page;

public interface SaleConverter {

    Sale toUseCase(SaleEntity saleEntity);

    Page<Sale> toUseCase(Page<SaleEntity> saleEntities);
}
