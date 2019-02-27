package com.github.raphaelbluteau.cashback.service.impl;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.repository.AlbumRepository;
import com.github.raphaelbluteau.cashback.service.AlbumService;
import com.github.raphaelbluteau.cashback.service.converter.AlbumServiceConverter;
import com.github.raphaelbluteau.cashback.service.data.Album;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumServiceConverter albumConverter;


    @Override
    public List<Album> getAlbumsByGenre(String accessToken, GenreEnum genre, Integer limit) {

        // TODO call spotifyGateway and convert

        return null;
    }

    @Override
    public Page<Album> getAlbumsByGenre(GenreEnum genre, Pageable pageable) {

        return albumConverter.toPage(albumRepository.findAllByGenreOrderByName(genre, pageable));
    }

    @Override
    public Optional<Album> findById(Long id) {

        return albumConverter.toAlbum(albumRepository.findById(id).orElse(null));
    }
}
