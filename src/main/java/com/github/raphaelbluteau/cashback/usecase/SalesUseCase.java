package com.github.raphaelbluteau.cashback.usecase;

import com.github.raphaelbluteau.cashback.exceptions.data.ResourceNotFoundException;
import com.github.raphaelbluteau.cashback.usecase.data.request.AlbumRequest;
import com.github.raphaelbluteau.cashback.usecase.data.response.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface SalesUseCase {

    Sale sale(List<AlbumRequest> albumsRequest) throws ResourceNotFoundException;

    Page<Sale> findByPeriod(Pageable pageable, LocalDateTime begin, LocalDateTime end);

    Sale findById(Long id) throws ResourceNotFoundException;
}
