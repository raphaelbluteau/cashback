package com.github.raphaelbluteau.cashback.http.converter.impl;

import com.github.raphaelbluteau.cashback.http.converter.AlbumHttpResponseConverter;
import com.github.raphaelbluteau.cashback.http.data.response.AlbumHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class AlbumHttpResponseConverterImpl implements AlbumHttpResponseConverter {

    @Override
    public Page<AlbumHttpResponse> toResponse(Page<Album> albums) {

        if (albums.isEmpty()) {
            return Page.empty();
        }

        List<AlbumHttpResponse> collection = albums.stream().map(this::toResponse).collect(Collectors.toList());

        return new PageImpl<>(collection, albums.getPageable(), albums.getTotalElements());
    }



    @Override
    public AlbumHttpResponse toResponse(Album album) {

        if (isNull(album)) {
            return null;
        }

        return AlbumHttpResponse.builder()
                .id(Long.valueOf(album.getId()))
                .genre(album.getGenre())
                .name(album.getName())
                .price(album.getPrice())
                .build();
    }
}
