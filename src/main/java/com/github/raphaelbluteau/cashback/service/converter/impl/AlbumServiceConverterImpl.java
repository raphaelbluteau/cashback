package com.github.raphaelbluteau.cashback.service.converter.impl;

import com.github.raphaelbluteau.cashback.gateway.repository.entity.AlbumEntity;
import com.github.raphaelbluteau.cashback.service.converter.AlbumServiceConverter;
import com.github.raphaelbluteau.cashback.service.data.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class AlbumServiceConverterImpl implements AlbumServiceConverter {

    @Override
    public Page<Album> toPage(Page<AlbumEntity> albumEntities) {

        if (albumEntities.isEmpty()) {
            return Page.empty();
        }

        List<Optional<Album>> collection = albumEntities.stream().map(this::toAlbum).collect(Collectors.toList());

        return new PageImpl<>(collection.stream().map(Optional::get).collect(Collectors.toList()),
                albumEntities.getPageable(), albumEntities.getTotalElements());
    }

    @Override
    public Optional<Album> toAlbum(AlbumEntity albumEntity) {

        if (isNull(albumEntity)) {
            return Optional.empty();
        }

        return Optional.of(Album.builder()
                .id(String.valueOf(albumEntity.getId()))
                .genre(albumEntity.getGenre())
                .name(albumEntity.getName())
                .price(albumEntity.getPrice())
                .build());
    }
}
