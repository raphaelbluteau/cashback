package com.github.raphaelbluteau.cashback.usecase;

import com.github.raphaelbluteau.cashback.usecase.data.request.AlbumRequest;
import com.github.raphaelbluteau.cashback.usecase.data.response.Sale;

import java.util.List;

public interface SalesUseCase {

    Sale sale(List<AlbumRequest> albumsRequest);
}
