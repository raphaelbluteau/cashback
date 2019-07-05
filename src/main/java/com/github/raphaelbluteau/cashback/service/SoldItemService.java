package com.github.raphaelbluteau.cashback.service;

import com.github.raphaelbluteau.cashback.service.data.SoldItem;

import java.util.List;

public interface SoldItemService {

    void saveAll(List<SoldItem> soldItems);
}
