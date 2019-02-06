package com.github.raphaelbluteau.cashback.usecase;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;

import java.util.List;

public interface AlbumUseCase {

    List<Album> getAlbumsByGenre(String accessToken, GenreEnum genre, Integer limit) throws Exception;
}
