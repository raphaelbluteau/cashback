package com.github.raphaelbluteau.cashback.service.converter;

import com.github.raphaelbluteau.cashback.gateway.repository.entity.AlbumEntity;
import com.github.raphaelbluteau.cashback.service.data.Album;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface AlbumServiceConverter {


    Page<Album> toPage(Page<AlbumEntity> albumEntities);

    Optional<Album> toAlbum(AlbumEntity albumEntity);
}
