package com.github.raphaelbluteau.cashback.gateway.repository;

import com.github.raphaelbluteau.cashback.gateway.repository.entity.SaleEntity;
import org.springframework.data.repository.CrudRepository;

public interface SaleRepository extends CrudRepository<SaleEntity, Long> {
}
