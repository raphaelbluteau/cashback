package com.github.raphaelbluteau.cashback.usecase.impl;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.ResourceNotFoundException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyException;
import com.github.raphaelbluteau.cashback.gateway.SpotifyGateway;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayItem;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistGatewayItem;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistGatewayResponse;
import com.github.raphaelbluteau.cashback.service.AlbumService;
import com.github.raphaelbluteau.cashback.usecase.AlbumUseCase;
import com.github.raphaelbluteau.cashback.usecase.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class AlbumUseCaseImpl implements AlbumUseCase {

    private final SpotifyGateway spotifyGateway;
    private final AlbumService albumService;
    private final AlbumConverter albumConverter;
    private static final String NOT_FOUND_MESSAGE = "Album for id %s not found";

    @Override
    public List<Album> getAlbumsByGenre(String accessToken, GenreEnum genre, Integer limit) throws SpotifyException, GatewayException {

        List<ArtistAlbumGatewayItem> gatewayAlbums = new ArrayList<>();
        ArtistGatewayResponse artistGatewayResponse = spotifyGateway.getArtistByGenre(accessToken, genre, limit);
        for (ArtistGatewayItem artistGatewayItem : artistGatewayResponse.getItems()) {
            gatewayAlbums.addAll(spotifyGateway
                    .getAlbumsByArtist(accessToken, artistGatewayItem.getId(), 1).getItems());
        }

        List<Album> albums = albumConverter.toUseCase(gatewayAlbums, genre);
        albums.forEach(a -> a.setPrice(randomPrice()));

        return albums;
    }

    @Override
    public Page<Album> getAlbumsByGenre(GenreEnum genre, Pageable pageable) {
        return albumConverter.toUseCasePage(albumService.getAlbumsByGenre(genre, pageable));
    }

    @Override
    public Album findById(Long id) throws ResourceNotFoundException {

        return albumConverter.toUseCase(albumService.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(NOT_FOUND_MESSAGE, String.valueOf(id)))));
    }

    private BigDecimal randomPrice() {

        double leftLimit = 20D;
        double rightLimit = 90D;
        BigDecimal price = BigDecimal.valueOf(leftLimit + new Random().nextDouble() * (rightLimit - leftLimit));

        return price.setScale(2, RoundingMode.CEILING);
    }
}
