package com.github.raphaelbluteau.cashback.usecase.converter;

import com.github.raphaelbluteau.cashback.usecase.data.response.Artist;

import java.util.List;

public interface ArtistConverter {

    List<Artist> toUseCase(List<com.github.raphaelbluteau.cashback.service.data.Artist> artists);
}
