package com.github.raphaelbluteau.cashback.converter;

import com.github.raphaelbluteau.cashback.usecase.data.response.Artist;

import java.util.List;

public interface ArtistConverter {

    List<Artist> toUseCase(List<com.github.raphaelbluteau.cashback.service.data.Artist> artists);

    List<com.github.raphaelbluteau.cashback.service.data.Artist> fromUseCase(List<Artist> artists);
}
