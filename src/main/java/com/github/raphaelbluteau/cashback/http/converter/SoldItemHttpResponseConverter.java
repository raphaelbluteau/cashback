package com.github.raphaelbluteau.cashback.http.converter;

import com.github.raphaelbluteau.cashback.http.data.response.SoldItemHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem;

import java.util.List;

public interface SoldItemHttpResponseConverter {

    List<SoldItemHttpResponse> toResponse(List<SoldItem> soldItemsEntity);
}
