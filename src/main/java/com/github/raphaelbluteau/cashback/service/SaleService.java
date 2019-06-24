package com.github.raphaelbluteau.cashback.service;

import com.github.raphaelbluteau.cashback.service.data.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface SaleService {

    Sale makeSale(Sale sale);

    Sale findById(Long id);

    Page<Sale> findByPeriod(Pageable pageable, LocalDateTime begin, LocalDateTime end);
}
