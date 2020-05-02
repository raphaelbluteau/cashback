package com.github.raphaelbluteau.cashback.gateway;

import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyAuthException;

public interface AuthClient {
    String getAccessToken() throws GatewayException, SpotifyAuthException;
}
