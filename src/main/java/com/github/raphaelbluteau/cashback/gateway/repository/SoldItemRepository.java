package com.github.raphaelbluteau.cashback.gateway.repository;

import com.github.raphaelbluteau.cashback.gateway.repository.entity.SoldItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface SoldItemRepository extends CrudRepository<SoldItemEntity, Long> {
}
