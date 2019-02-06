package com.github.raphaelbluteau.cashback.usecase.impl;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.SpotifyGateway;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayItem;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistGatewayResponse;
import com.github.raphaelbluteau.cashback.usecase.AlbumUseCase;
import com.github.raphaelbluteau.cashback.usecase.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import lombok.RequiredArgsConstructor;
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
    private final AlbumConverter albumConverter;

    @Override
    public List<Album> getAlbumsByGenre(String accessToken, GenreEnum genre, Integer limit) throws Exception {

        List<ArtistAlbumGatewayItem> gatewayAlbums = new ArrayList<>();
        ArtistGatewayResponse artistGatewayResponse = spotifyGateway.getArtistByGenre(accessToken, genre, limit);
        artistGatewayResponse.getItems().forEach(a -> gatewayAlbums.addAll(spotifyGateway
                .getAlbumsByArtist(accessToken, a.getId(), 1).getItems()));

        List<Album> albums = albumConverter.toUseCase(gatewayAlbums, genre);
        albums.forEach(a -> a.setPrice(randomPrice()));

        return albums;
    }

    private BigDecimal randomPrice() {

        double leftLimit = 20D;
        double rightLimit = 90D;
        BigDecimal price = BigDecimal.valueOf(leftLimit + new Random().nextDouble() * (rightLimit - leftLimit));

        return price.setScale(2, RoundingMode.CEILING);
    }
}
