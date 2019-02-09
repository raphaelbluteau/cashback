package com.github.raphaelbluteau.cashback.gateway;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyAuthException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyException;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayResponse;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistGatewayResponse;
import com.github.raphaelbluteau.cashback.gateway.data.response.AuthorizationGatewayResponse;

public interface SpotifyGateway {

    AuthorizationGatewayResponse getAuthorization() throws SpotifyAuthException, GatewayException;

    ArtistGatewayResponse getArtistByGenre(String accessToken, GenreEnum genre, Integer limit) throws SpotifyException, GatewayException;

    ArtistAlbumGatewayResponse getAlbumsByArtist(String accessToken, String artistId, Integer limit) throws SpotifyException;
}
