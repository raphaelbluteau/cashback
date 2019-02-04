package com.github.raphaelbluteau.cashback.usecase;

import com.github.raphaelbluteau.cashback.usecase.data.response.Album;

import java.util.List;

public interface AlbumUseCase {

    List<Album> getAlbumsByGenre(String genre, Integer limit) throws Exception;
}
