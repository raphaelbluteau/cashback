package com.github.raphaelbluteau.cashback.usecase.converter;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayItem;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;

import java.util.List;

public interface AlbumConverter {

    List<Album> toUseCase(List<ArtistAlbumGatewayItem> albums, GenreEnum genre);
}
