package com.github.raphaelbluteau.cashback.http.converter;

import com.github.raphaelbluteau.cashback.http.data.response.SaleHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.data.response.Sale;
import org.springframework.data.domain.Page;

public interface SalesHttpResponseConverter {

    SaleHttpResponse toResponse(Sale sale);

    Page<SaleHttpResponse> toResponse(Page<Sale> sales);
}
