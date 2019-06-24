package com.github.raphaelbluteau.cashback.service.impl;

import com.github.raphaelbluteau.cashback.converter.SaleConverter;
import com.github.raphaelbluteau.cashback.gateway.repository.SaleRepository;
import com.github.raphaelbluteau.cashback.service.SaleService;
import com.github.raphaelbluteau.cashback.service.data.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleConverter saleConverter;
    private final SaleRepository saleRepository;

    @Override
    public Sale makeSale(Sale sale) {
        return saleConverter.fromEntity(saleRepository.save(saleConverter.toEntity(sale)));
    }

    @Override
    public Sale findById(Long id) {

        return saleConverter.fromEntity(saleRepository.findById(id).orElse(null));
    }

    @Override
    public Page<Sale> findByPeriod(Pageable pageable, LocalDateTime begin, LocalDateTime end) {

        return saleConverter.fromEntity(
                saleRepository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualOrderByCreatedAtDesc(begin, end, pageable));
    }
}
