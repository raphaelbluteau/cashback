package com.github.raphaelbluteau.cashback.gateway;

import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyException;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayResponse;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistGatewayResponseWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface SpotifyRequests {

    @GET("search")
    Call<ArtistGatewayResponseWrapper> getArtistByGenre(@Header("Authorization") String accessToken,
                                                        @QueryMap Map<String, String> options) throws SpotifyException, GatewayException;

    @GET("artists/{artistId}/albums")
    Call<ArtistAlbumGatewayResponse> getAlbumsByArtist(@Header("Authorization") String accessToken,
                                                 @Path("artistId") String artistId,
                                                 @QueryMap Map<String, String> options) throws SpotifyException;
}
