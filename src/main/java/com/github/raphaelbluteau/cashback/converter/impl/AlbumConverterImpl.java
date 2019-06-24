package com.github.raphaelbluteau.cashback.converter.impl;

import com.github.raphaelbluteau.cashback.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.converter.ArtistConverter;
import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayItem;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.AlbumEntity;
import com.github.raphaelbluteau.cashback.http.data.request.AlbumHttpRequest;
import com.github.raphaelbluteau.cashback.http.data.response.AlbumHttpResponse;
import com.github.raphaelbluteau.cashback.service.data.Album;
import com.github.raphaelbluteau.cashback.usecase.data.request.AlbumRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class AlbumConverterImpl implements AlbumConverter {

    private final ArtistConverter artistConverter;

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

    @Override
    public AlbumEntity toEntity(Album album) {

        if (isNull(album)) {
            return null;
        }

        return AlbumEntity.builder()
                .id(Long.valueOf(album.getId()))
                .name(album.getName())
                .genre(album.getGenre())
                .price(album.getPrice())
                .build();
    }

    @Override
    public Page<AlbumHttpResponse> toResponse(Page<com.github.raphaelbluteau.cashback.usecase.data.response.Album> albums) {

        if (albums.isEmpty()) {
            return Page.empty();
        }

        List<AlbumHttpResponse> collection = albums.stream().map(this::toResponse).collect(Collectors.toList());

        return new PageImpl<>(collection, albums.getPageable(), albums.getTotalElements());
    }



    @Override
    public AlbumHttpResponse toResponse(com.github.raphaelbluteau.cashback.usecase.data.response.Album album) {

        if (isNull(album)) {
            return null;
        }

        return AlbumHttpResponse.builder()
                .id(Long.valueOf(album.getId()))
                .genre(album.getGenre())
                .name(album.getName())
                .price(album.getPrice())
                .build();
    }

    @Override
    public List<com.github.raphaelbluteau.cashback.usecase.data.response.Album> toUseCase(List<ArtistAlbumGatewayItem> albums, GenreEnum genre) {

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

    @Override
    public Album fromUseCase(com.github.raphaelbluteau.cashback.usecase.data.response.Album album) {

        if (isNull(album)) {
            return null;
        }

        return Album.builder()
                .id(album.getId())
                .name(album.getName())
                .price(album.getPrice())
                .genre(album.getGenre())
                .artists(artistConverter.fromUseCase(album.getArtists()))
                .build();
    }

    private AlbumRequest toUseCaseRequest(AlbumHttpRequest request) {

        if (isNull(request)) {
            return null;
        }

        return AlbumRequest.builder()
                .id(request.getId())
                .build();
    }

    @Override
    public List<AlbumEntity> toEntity(List<com.github.raphaelbluteau.cashback.usecase.data.response.Album> albums, GenreEnum genre) {

        if (CollectionUtils.isEmpty(albums)) {
            return Collections.emptyList();
        }

        return albums.stream().map(a -> toEntity(a, genre)).collect(Collectors.toList());
    }

    @Override
    public com.github.raphaelbluteau.cashback.usecase.data.response.Album toUseCase(AlbumEntity albumEntity) {
        if (isNull(albumEntity)) {
            return null;
        }

        return com.github.raphaelbluteau.cashback.usecase.data.response.Album.builder()
                .id(String.valueOf(albumEntity.getId()))
                .name(albumEntity.getName())
                .price(albumEntity.getPrice())
                .genre(albumEntity.getGenre())
                .build();
    }

    @Override
    public com.github.raphaelbluteau.cashback.usecase.data.response.Album toUseCase(com.github.raphaelbluteau.cashback.service.data.Album album) {

        if (isNull(album)) {
            return null;
        }

        return com.github.raphaelbluteau.cashback.usecase.data.response.Album.builder()
                .id(album.getId())
                .name(album.getName())
                .genre(album.getGenre())
                .price(album.getPrice())
                .artists(artistConverter.toUseCase(album.getArtists()))
                .build();
    }

    @Override
    public Page<com.github.raphaelbluteau.cashback.usecase.data.response.Album> toUseCase(Page<AlbumEntity> albumEntity) {

        if (albumEntity.isEmpty()) {
            return Page.empty();
        }

        List<com.github.raphaelbluteau.cashback.usecase.data.response.Album> collection = albumEntity.stream().map(this::toUseCase).collect(Collectors.toList());
        return new PageImpl<>(collection, albumEntity.getPageable(), albumEntity.getTotalElements());
    }

    @Override
    public Page<com.github.raphaelbluteau.cashback.usecase.data.response.Album> toUseCasePage(Page<com.github.raphaelbluteau.cashback.service.data.Album> albums) {

        if (albums.isEmpty()) {
            return Page.empty();
        }


        List<com.github.raphaelbluteau.cashback.usecase.data.response.Album> collection = albums.stream().map(this::toUseCase).collect(Collectors.toList());

        return new PageImpl<>(collection, albums.getPageable(), albums.getTotalElements());
    }

    private AlbumEntity toEntity(com.github.raphaelbluteau.cashback.usecase.data.response.Album album, GenreEnum genre) {

        if (isNull(album)) {
            return null;
        }

        return AlbumEntity.builder()
                .genre(genre)
                .name(album.getName())
                .price(album.getPrice())
                .build();
    }

    private com.github.raphaelbluteau.cashback.usecase.data.response.Album toUseCase(ArtistAlbumGatewayItem item, GenreEnum genre) {

        if (isNull(item)) {
            return null;
        }

        return com.github.raphaelbluteau.cashback.usecase.data.response.Album.builder()
                .genre(genre)
                .id(item.getId())
                .name(item.getName())
                .build();
    }
}
