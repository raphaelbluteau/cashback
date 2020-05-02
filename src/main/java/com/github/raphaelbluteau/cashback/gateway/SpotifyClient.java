package com.github.raphaelbluteau.cashback.gateway;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayResponse;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistGatewayResponse;

public interface SpotifyClient {

    ArtistGatewayResponse getArtistByGenre(String accessToken, GenreEnum genre, Integer limit);

    ArtistAlbumGatewayResponse getAlbumsByArtist(String accessToken, String artistId, Integer limit);
}
