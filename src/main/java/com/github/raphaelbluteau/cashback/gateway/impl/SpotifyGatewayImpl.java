package com.github.raphaelbluteau.cashback.gateway.impl;

import com.github.raphaelbluteau.cashback.config.SpotifyConfigurationProperties;
import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyAuthException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyException;
import com.github.raphaelbluteau.cashback.gateway.SpotifyGateway;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayResponse;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistGatewayResponse;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistGatewayResponseWrapper;
import com.github.raphaelbluteau.cashback.gateway.data.response.AuthorizationGatewayResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Component
@Log
@RequiredArgsConstructor
public class SpotifyGatewayImpl implements SpotifyGateway {

    final SpotifyConfigurationProperties properties;

    private final RestTemplate restTemplate;
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String GRANT_TYPE = "grant_type";
    private static final String QUERY = "q";
    private static final String GENRE_FILTER = "genre:%s";
    private static final String TYPE = "type";
    private static final String LIMIT = "limit";
    private static final String BEARER = "Bearer %s";
    private static final String ALBUMS = "albums";
    private static final String MARKET = "market";

    @Override
    public AuthorizationGatewayResponse getAuthorization() throws SpotifyAuthException, GatewayException {

        try {
            ResponseEntity<AuthorizationGatewayResponse> responseEntity = restTemplate.exchange(properties.getAuthUrl(),
                    HttpMethod.POST, buildAuthRequestHttpEntity(), AuthorizationGatewayResponse.class);

            return responseEntity.getBody();
        } catch (HttpClientErrorException response) {
            throw new SpotifyAuthException(response.getLocalizedMessage(), response.getCause(), response.getResponseBodyAsString(), response.getRawStatusCode());
        } catch (HttpServerErrorException response) {
            throw new GatewayException(response.getLocalizedMessage(), response.getCause(), response.getRawStatusCode());
        }
    }

    @Override
    public ArtistGatewayResponse getArtistByGenre(String accessToken, GenreEnum genre, Integer limit) throws SpotifyException, GatewayException {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(properties.getSearchUrl())
                .queryParam(QUERY, String.format(GENRE_FILTER, genre.toString()))
                .queryParam(TYPE, properties.getSearchType())
                .queryParam(LIMIT, limit);
        String uri = uriBuilder.toUriString();
        try {
            ResponseEntity<ArtistGatewayResponseWrapper> responseEntity = restTemplate.exchange(uri, HttpMethod.GET,
                    buildHttpEntity(accessToken), ArtistGatewayResponseWrapper.class);

            return Objects.requireNonNull(responseEntity.getBody()).getArtists();

        } catch (HttpClientErrorException response) {
            throw new SpotifyException(response.getLocalizedMessage(), response.getCause(), response.getResponseBodyAsString(), response.getRawStatusCode());
        } catch (HttpServerErrorException response) {
            throw new GatewayException(response.getLocalizedMessage(), response.getCause(), response.getRawStatusCode());
        }
    }

    @Override
    public ArtistAlbumGatewayResponse getAlbumsByArtist(String accessToken, String artistId, Integer limit) throws SpotifyException {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(properties.getArtistsUrl())
                .path(artistId.concat("/"))
                .path(ALBUMS)
                .queryParam(LIMIT, limit);
        String uri = uriBuilder.toUriString();
        ResponseEntity<ArtistAlbumGatewayResponse> responseEntity;
        try {
            responseEntity = restTemplate.exchange(uri, HttpMethod.GET,
                    buildHttpEntity(accessToken), ArtistAlbumGatewayResponse.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            throw new SpotifyException(e.getLocalizedMessage(), e.getCause(), e.getResponseBodyAsString(), e.getRawStatusCode());
        }
    }

    private MultiValueMap<String, String> buildAuthRequestBody() {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(CLIENT_ID, properties.getClientId());
        map.add(CLIENT_SECRET, properties.getClientSecret());
        map.add(GRANT_TYPE, properties.getGrantType());

        return map;
    }

    private HttpEntity<MultiValueMap<String, String>> buildAuthRequestHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return new HttpEntity<>(buildAuthRequestBody(), headers);
    }

    private HttpEntity<MultiValueMap<String, String>> buildHttpEntity(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", String.format(BEARER, accessToken));

        return new HttpEntity<>(null, headers);
    }

}
