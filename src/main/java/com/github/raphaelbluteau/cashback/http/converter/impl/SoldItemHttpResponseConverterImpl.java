package com.github.raphaelbluteau.cashback.http.converter.impl;

import com.github.raphaelbluteau.cashback.http.converter.AlbumHttpResponseConverter;
import com.github.raphaelbluteau.cashback.http.converter.SoldItemHttpResponseConverter;
import com.github.raphaelbluteau.cashback.http.data.response.SoldItemHttpResponse;
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
public class SoldItemHttpResponseConverterImpl implements SoldItemHttpResponseConverter {

    private final AlbumHttpResponseConverter albumConverter;

    @Override
    public List<SoldItemHttpResponse> toResponse(List<SoldItem> soldItemsEntity) {
        if (CollectionUtils.isEmpty(soldItemsEntity)) {
            return Collections.emptyList();
        }

        return soldItemsEntity.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private SoldItemHttpResponse toResponse(SoldItem soldItem) {
        if (isNull(soldItem)) {
            return null;
        }

        return SoldItemHttpResponse.builder()
                .id(soldItem.getId())
                .cashback(soldItem.getCashback())
                .album(albumConverter.toResponse(soldItem.getAlbum()))
                .build();
    }
}
