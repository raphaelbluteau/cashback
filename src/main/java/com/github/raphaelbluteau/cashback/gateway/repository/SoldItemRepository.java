package com.github.raphaelbluteau.cashback.gateway.repository;

import com.github.raphaelbluteau.cashback.gateway.repository.entity.SoldItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SoldItemRepository extends PagingAndSortingRepository<SoldItemEntity, Long> {
}
