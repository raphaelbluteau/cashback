package com.github.raphaelbluteau.cashback.gateway.repository;

import com.github.raphaelbluteau.cashback.gateway.repository.entity.SaleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;

public interface SaleRepository extends PagingAndSortingRepository<SaleEntity, Long> {

    Page<SaleEntity> findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualOrderByCreatedAtDesc(LocalDateTime begin, LocalDateTime end, Pageable pageable);
}
