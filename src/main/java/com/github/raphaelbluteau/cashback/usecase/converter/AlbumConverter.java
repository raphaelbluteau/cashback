package com.github.raphaelbluteau.cashback.usecase.converter;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayItem;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.AlbumEntity;
import com.github.raphaelbluteau.cashback.http.data.request.AlbumHttpRequest;
import com.github.raphaelbluteau.cashback.usecase.data.request.AlbumRequest;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AlbumConverter {

    List<Album> toUseCase(List<ArtistAlbumGatewayItem> albums, GenreEnum genre);

    Album toUseCase(AlbumEntity albumEntity);

    Album toUseCase(com.github.raphaelbluteau.cashback.service.data.Album album);

    Page<Album> toUseCase(Page<AlbumEntity> albumEntity);

    Page<Album> toUseCasePage(Page<com.github.raphaelbluteau.cashback.service.data.Album> albums);

    List<AlbumRequest> toUseCaseRequest(List<AlbumHttpRequest> request);

    List<AlbumEntity> toEntity(List<Album> albums, GenreEnum genre);
}
