package com.github.raphaelbluteau.cashback.converter.impl;

import com.github.raphaelbluteau.cashback.converter.ArtistConverter;
import com.github.raphaelbluteau.cashback.usecase.data.response.Artist;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class ArtistConverterImpl implements ArtistConverter {

    @Override
    public List<Artist> toUseCase(List<com.github.raphaelbluteau.cashback.service.data.Artist> artists) {

        if (CollectionUtils.isEmpty(artists)) {
            return Collections.emptyList();
        }

        return artists.stream().map(this::toUseCase).collect(Collectors.toList());
    }

    @Override
    public List<com.github.raphaelbluteau.cashback.service.data.Artist> fromUseCase(List<Artist> artists) {

        if (CollectionUtils.isEmpty(artists)) {
            return Collections.emptyList();
        }

        return artists.stream().map(this::fromUseCase).collect(Collectors.toList());
    }

    private com.github.raphaelbluteau.cashback.service.data.Artist fromUseCase(Artist artist) {

        if (isNull(artist)) {
            return null;
        }

        return com.github.raphaelbluteau.cashback.service.data.Artist.builder()
                .id(artist.getId())
                .name(artist.getName())
                .build();
    }

    private Artist toUseCase(com.github.raphaelbluteau.cashback.service.data.Artist artist) {

        if (isNull(artist)) {
            return null;
        }

        return Artist.builder()
                .id(artist.getId())
                .name(artist.getName())
                .build();
    }
}
