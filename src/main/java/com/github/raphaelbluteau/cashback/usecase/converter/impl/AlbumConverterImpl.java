package com.github.raphaelbluteau.cashback.usecase.converter.impl;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayItem;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.AlbumEntity;
import com.github.raphaelbluteau.cashback.http.data.request.AlbumHttpRequest;
import com.github.raphaelbluteau.cashback.usecase.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.usecase.data.request.AlbumRequest;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class AlbumConverterImpl implements AlbumConverter {

    @Override
    public List<Album> toUseCase(List<ArtistAlbumGatewayItem> albums, GenreEnum genre) {

        if (CollectionUtils.isEmpty(albums)) {
            return Collections.emptyList();
        }

        return albums.stream().map(a -> toUseCase(a, genre)).collect(Collectors.toList());
    }

    @Override
    public List<AlbumRequest> toUseCaseRequest(List<AlbumHttpRequest> request) {

        if (CollectionUtils.isEmpty(request)) {
            return Collections.emptyList();
        }

        return request.stream().map(this::toUseCaseRequest).collect(Collectors.toList());
    }

    private AlbumRequest toUseCaseRequest(AlbumHttpRequest request) {

        if (isNull(request)) {
            return null;
        }

        return AlbumRequest.builder()
                .id(request.getId())
                .genre(request.getGenre())
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }

    @Override
    public List<AlbumEntity> toEntity(List<Album> albums, GenreEnum genre) {

        if (CollectionUtils.isEmpty(albums)) {
            return Collections.emptyList();
        }

        return albums.stream().map(a -> toEntity(a, genre)).collect(Collectors.toList());
    }

    private AlbumEntity toEntity(Album album, GenreEnum genre) {

        if (isNull(album)) {
            return null;
        }

        return AlbumEntity.builder()
                .genre(genre)
                .name(album.getName())
                .price(album.getPrice())
                .build();
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
