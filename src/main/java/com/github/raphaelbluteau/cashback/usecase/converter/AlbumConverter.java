package com.github.raphaelbluteau.cashback.usecase.converter;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayItem;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.AlbumEntity;
import com.github.raphaelbluteau.cashback.http.data.request.AlbumHttpRequest;
import com.github.raphaelbluteau.cashback.usecase.data.request.AlbumRequest;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;

import java.util.List;

public interface AlbumConverter {

    List<Album> toUseCase(List<ArtistAlbumGatewayItem> albums, GenreEnum genre);

    List<AlbumRequest> toUseCaseRequest(List<AlbumHttpRequest> request);

    List<AlbumEntity> toEntity(List<Album> albums, GenreEnum genre);
}
