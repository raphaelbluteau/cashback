package com.github.raphaelbluteau.cashback.service;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.service.data.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AlbumService {

    List<Album> getAlbumsByGenre(String accessToken, GenreEnum genre, Integer limit);

    Page<Album> getAlbumsByGenre(GenreEnum genre, Pageable pageable);

    Optional<Album> findById(Long id);
}
