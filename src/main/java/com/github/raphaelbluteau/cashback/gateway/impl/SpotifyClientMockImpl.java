package com.github.raphaelbluteau.cashback.gateway.impl;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.SpotifyClient;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayItem;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayResponse;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistGatewayItem;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistGatewayResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Profile("mock")
public class SpotifyClientMockImpl implements SpotifyClient {

    @Override
    public ArtistGatewayResponse getArtistByGenre(String accessToken, GenreEnum genre, Integer limit) {

        return ArtistGatewayResponse.builder()
                .items(getArtist(limit))
                .build();
    }

    @Override
    public ArtistAlbumGatewayResponse getAlbumsByArtist(String accessToken, String artistId, Integer limit) {

        return ArtistAlbumGatewayResponse.builder()
                .href("http://uri")
                .items(getArtistAlbums(limit))
                .total(limit)
                .build();
    }

    private List<ArtistGatewayItem> getArtist(Integer limit) {
        List<ArtistGatewayItem> artists = new ArrayList<>();
        Random randomGenerator = new Random();
        for (int i = 0; i < limit; i++) {
            int number = randomGenerator.nextInt(1000);
            artists.add(ArtistGatewayItem.builder()
                    .uri(String.format("uri-%d", number))
                    .name(String.format("Artist %d", number))
                    .id(String.valueOf(i))
                    .build());
        }
        return artists;
    }

    private List<ArtistAlbumGatewayItem> getArtistAlbums(int limit) {
        List<ArtistAlbumGatewayItem> artists = new ArrayList<>();
        GenreEnum[] genres = GenreEnum.values();
        Random randomGenerator = new Random();
        for (int i = 0; i < limit; i++) {
            int number = randomGenerator.nextInt(1000);
            String genre = genres[randomGenerator.nextInt(genres.length)].toString();
            artists.add(ArtistAlbumGatewayItem.builder()
                    .uri(String.format("uri-%d", number))
                    .type(genre)
                    .name(String.format("Album %d", number))
                    .id(String.valueOf(number))
                    .build());
        }
        return artists;
    }
}
