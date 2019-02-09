package com.github.raphaelbluteau.cashback.usecase.converter.impl;

import com.github.raphaelbluteau.cashback.gateway.repository.entity.SoldItemEntity;
import com.github.raphaelbluteau.cashback.usecase.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.usecase.converter.SoldItemConverter;
import com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class SoldItemConverterImpl implements SoldItemConverter {

    private final AlbumConverter albumConverter;

    @Override
    public List<SoldItem> toUseCase(List<SoldItemEntity> soldItemEntity) {

        if (CollectionUtils.isEmpty(soldItemEntity)) {
            return Collections.emptyList();
        }

        return soldItemEntity.stream().map(this::toUseCase).collect(Collectors.toList());
    }

    private SoldItem toUseCase(SoldItemEntity soldItemEntity) {

        if (isNull(soldItemEntity)) {
            return null;
        }

        return SoldItem.builder()
                .id(soldItemEntity.getId())
                .album(albumConverter.toUseCase(soldItemEntity.getAlbum()))
                .cashback(soldItemEntity.getCashback())
                .build();
    }
}
