package com.github.raphaelbluteau.cashback.gateway;

import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyAuthException;
import com.github.raphaelbluteau.cashback.gateway.data.response.AuthorizationGatewayResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface AuthRequests {

    @FormUrlEncoded
    @POST("token")
    Call<AuthorizationGatewayResponse> getAuthorization(@HeaderMap Map<String, String> headers,
                                                        @Field("grant_type") String grantType) throws SpotifyAuthException, GatewayException;
}
