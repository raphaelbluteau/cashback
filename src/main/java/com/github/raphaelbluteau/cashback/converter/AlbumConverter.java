package com.github.raphaelbluteau.cashback.converter;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayItem;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.AlbumEntity;
import com.github.raphaelbluteau.cashback.http.data.request.AlbumHttpRequest;
import com.github.raphaelbluteau.cashback.http.data.response.AlbumHttpResponse;
import com.github.raphaelbluteau.cashback.service.data.Album;
import com.github.raphaelbluteau.cashback.usecase.data.request.AlbumRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AlbumConverter {

    AlbumEntity toEntity(Album album);

    List<AlbumEntity> toEntity(List<com.github.raphaelbluteau.cashback.usecase.data.response.Album> albums, GenreEnum genre);

    Page<Album> toPage(Page<AlbumEntity> albumEntities);

    Optional<Album> toAlbum(AlbumEntity albumEntity);

    Page<AlbumHttpResponse> toResponse(Page<com.github.raphaelbluteau.cashback.usecase.data.response.Album> albums);

    AlbumHttpResponse toResponse(com.github.raphaelbluteau.cashback.usecase.data.response.Album album);

    List<com.github.raphaelbluteau.cashback.usecase.data.response.Album> toUseCase(List<ArtistAlbumGatewayItem> albums, GenreEnum genre);

    com.github.raphaelbluteau.cashback.usecase.data.response.Album toUseCase(AlbumEntity albumEntity);

    com.github.raphaelbluteau.cashback.usecase.data.response.Album toUseCase(com.github.raphaelbluteau.cashback.service.data.Album album);

    Page<com.github.raphaelbluteau.cashback.usecase.data.response.Album> toUseCase(Page<AlbumEntity> albumEntity);

    Page<com.github.raphaelbluteau.cashback.usecase.data.response.Album> toUseCasePage(Page<com.github.raphaelbluteau.cashback.service.data.Album> albums);

    List<AlbumRequest> toUseCaseRequest(List<AlbumHttpRequest> request);

    Album fromUseCase(com.github.raphaelbluteau.cashback.usecase.data.response.Album album);
}
