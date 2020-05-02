package com.github.raphaelbluteau.cashback.gateway.impl;

import com.github.raphaelbluteau.cashback.config.SpotifyConfigurationProperties;
import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyException;
import com.github.raphaelbluteau.cashback.gateway.SpotifyClient;
import com.github.raphaelbluteau.cashback.gateway.SpotifyRequests;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayResponse;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistGatewayResponse;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistGatewayResponseWrapper;
import com.github.raphaelbluteau.cashback.gateway.data.response.SpotifyErrorResponse;
import com.google.gson.Gson;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import okhttp3.ResponseBody;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Log
@RequiredArgsConstructor
@Profile("live")
public class SpotifyClientImpl implements SpotifyClient {

    private final SpotifyConfigurationProperties properties;
    private final SpotifyRequests spotifyRequests;

    private static final String SPOTIFY = "spotify";
    private static final String QUERY = "q";
    private static final String GENRE_FILTER = "genre:%s";
    private static final String TYPE = "type";
    private static final String LIMIT = "limit";
    private static final String BEARER = "Bearer %s";
    private static final String ALBUMS = "albums";
    private static final String MARKET = "market";

    @SneakyThrows
    @CircuitBreaker(name = SPOTIFY)
    @Bulkhead(name = SPOTIFY)
    @Retry(name = SPOTIFY)
    public ArtistGatewayResponse getArtistByGenre(String accessToken, GenreEnum genre, Integer limit) {

        Map<String, String> options = new HashMap<>();
        options.put(QUERY, String.format(GENRE_FILTER, genre.toString()));
        options.put(TYPE, properties.getSearchType());
        options.put(LIMIT, String.valueOf(limit));

        Response<ArtistGatewayResponseWrapper> response = spotifyRequests.getArtistByGenre(accessToken, options).execute();

        if (response.isSuccessful() && response.body() != null) {

            return response.body().getArtists();

        } else if (response.errorBody() != null) {

            throwCustomException(response.errorBody());

        }

        throw new GatewayException("An exception occurred while trying to get artists by genre");
    }

    @SneakyThrows
    @CircuitBreaker(name = SPOTIFY)
    @Bulkhead(name = SPOTIFY)
    @Retry(name = SPOTIFY)
    public ArtistAlbumGatewayResponse getAlbumsByArtist(String accessToken, String artistId, Integer limit) {

        Map<String, String> options = new HashMap<>();
        options.put(LIMIT, String.valueOf(limit));

        try {

            Response<ArtistAlbumGatewayResponse> response = spotifyRequests.getAlbumsByArtist(accessToken, artistId, options).execute();

            if (response.isSuccessful() && response.body() != null) {

                return response.body();

            } else if (response.errorBody() != null) {

                throwCustomException(response.errorBody());

            }

        } catch (IOException e) {
            throw new GatewayException("An exception occurred while trying to get albums by artist", e);
        }

        throw new GatewayException("An exception occurred while trying to get albums by artist");
    }

    @SneakyThrows
    private void throwCustomException(ResponseBody body) {
        SpotifyErrorResponse errorResponse = new Gson().fromJson(body.charStream(), SpotifyErrorResponse.class);
        String errorMessage = String.format("%s - %s", errorResponse.getError(), errorResponse.getDescription());
        String errorBodyString = body.string();

        throw new SpotifyException(errorMessage, errorBodyString, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
