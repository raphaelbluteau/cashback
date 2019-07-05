package com.github.raphaelbluteau.cashback.service.impl;

import com.github.raphaelbluteau.cashback.converter.SoldItemConverter;
import com.github.raphaelbluteau.cashback.gateway.repository.SoldItemRepository;
import com.github.raphaelbluteau.cashback.service.SoldItemService;
import com.github.raphaelbluteau.cashback.service.data.SoldItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SoldItemServiceImpl implements SoldItemService {

    private final SoldItemRepository soldItemRepository;
    private final SoldItemConverter soldItemConverter;

    @Override
    public void saveAll(List<SoldItem> soldItems) {
        soldItemRepository.saveAll(soldItemConverter.toEntity(soldItems));
    }
}
