package com.github.raphaelbluteau.cashback.gateway;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayResponse;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistGatewayResponse;
import com.github.raphaelbluteau.cashback.gateway.data.response.AuthorizationGatewayResponse;

public interface SpotifyGateway {

    AuthorizationGatewayResponse getAuthorization();

    ArtistGatewayResponse getArtistByGenre(String accessToken, GenreEnum genre, Integer limit) throws Exception;

    ArtistAlbumGatewayResponse getAlbumsByArtist(String accessToken, String artistId, Integer limit);
}
