package com.github.raphaelbluteau.cashback.usecase;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.ResourceNotFoundException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyException;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlbumUseCase {

    List<Album> getAlbumsByGenre(String accessToken, GenreEnum genre, Integer limit) throws SpotifyException, GatewayException;

    Page<Album> getAlbumsByGenre(GenreEnum genre, Pageable pageable);

    Album findById(Long id) throws ResourceNotFoundException;
}
