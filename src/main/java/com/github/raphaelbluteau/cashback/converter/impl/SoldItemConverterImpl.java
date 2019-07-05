package com.github.raphaelbluteau.cashback.converter.impl;

import com.github.raphaelbluteau.cashback.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.converter.SoldItemConverter;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.SoldItemEntity;
import com.github.raphaelbluteau.cashback.http.data.response.SoldItemHttpResponse;
import com.github.raphaelbluteau.cashback.service.data.SoldItem;
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
    public List<SoldItemEntity> toEntity(List<SoldItem> soldItems) {

        if (CollectionUtils.isEmpty(soldItems)) {
            return Collections.emptyList();
        }

        return soldItems.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<SoldItem> fromEntity(List<SoldItemEntity> soldItems) {

        if (CollectionUtils.isEmpty(soldItems)) {
            return Collections.emptyList();
        }

        return soldItems.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    private SoldItem fromEntity(SoldItemEntity soldItem) {

        if (isNull(soldItem)) {
            return null;
        }

        return SoldItem.builder()
                .id(soldItem.getId())
                .album(albumConverter.toAlbum(soldItem.getAlbum()).orElse(null))
                .cashback(soldItem.getCashback())
                .build();
    }

    private SoldItemEntity toEntity(SoldItem soldItem) {
        if (isNull(soldItem)) {
            return null;
        }

        return SoldItemEntity.builder()
                .id(soldItem.getId())
                .album(albumConverter.toEntity(soldItem.getAlbum()))
                .cashback(soldItem.getCashback())
                .build();
    }

    @Override
    public List<SoldItemHttpResponse> toResponse(List<com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem> soldItemsEntity) {
        if (CollectionUtils.isEmpty(soldItemsEntity)) {
            return Collections.emptyList();
        }

        return soldItemsEntity.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private SoldItemHttpResponse toResponse(com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem soldItem) {
        if (isNull(soldItem)) {
            return null;
        }

        return SoldItemHttpResponse.builder()
                .id(soldItem.getId())
                .cashback(soldItem.getCashback())
                .album(albumConverter.toResponse(soldItem.getAlbum()))
                .build();
    }

    @Override
    public List<com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem> toUseCase(List<SoldItemEntity> soldItemEntity) {

        if (CollectionUtils.isEmpty(soldItemEntity)) {
            return Collections.emptyList();
        }

        return soldItemEntity.stream().map(this::toUseCase).collect(Collectors.toList());
    }

    @Override
    public List<SoldItem> fromUseCase(List<com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem> items) {

        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }

        return items.stream().map(this::fromUseCase).collect(Collectors.toList());
    }


    private SoldItem fromUseCase(com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem item) {

        if (isNull(item)) {
            return null;
        }

        return SoldItem.builder()
                .id(item.getId())
                .album(albumConverter.fromUseCase(item.getAlbum()))
                .cashback(item.getCashback())
                .build();
    }

    @Override
    public List<com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem> toUseCaseList(List<SoldItem> items) {

        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }

        return items.stream().map(this::toUseCase).collect(Collectors.toList());
    }

    private com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem toUseCase(SoldItem soldItem) {

        if (isNull(soldItem)) {
            return null;
        }

        return com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem.builder()
                .id(soldItem.getId())
                .album(albumConverter.toUseCase(soldItem.getAlbum()))
                .cashback(soldItem.getCashback())
                .build();
    }

    private com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem toUseCase(SoldItemEntity soldItemEntity) {

        if (isNull(soldItemEntity)) {
            return null;
        }

        return com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem.builder()
                .id(soldItemEntity.getId())
                .album(albumConverter.toUseCase(soldItemEntity.getAlbum()))
                .cashback(soldItemEntity.getCashback())
                .build();
    }
}
