package com.github.raphaelbluteau.cashback.usecase.converter.impl;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayItem;
import com.github.raphaelbluteau.cashback.usecase.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class AlbumConverterImpl implements AlbumConverter {

    @Override
    public List<Album> toUseCase(List<ArtistAlbumGatewayItem> albums, GenreEnum genre) {

        if (CollectionUtils.isEmpty(albums)) {
            return Collections.emptyList();
        }

        return albums.stream().map(a -> toUseCase(a, genre)).collect(Collectors.toList());
    }

    private Album toUseCase(ArtistAlbumGatewayItem item, GenreEnum genre) {

        if (isNull(item)) {
            return null;
        }

        return Album.builder()
                .genre(genre)
                .id(item.getId())
                .name(item.getName())
                .build();
    }
}
