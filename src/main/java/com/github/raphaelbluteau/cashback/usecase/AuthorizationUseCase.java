package com.github.raphaelbluteau.cashback.usecase;

import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyAuthException;

public interface AuthorizationUseCase {

    String getAuthorization() throws GatewayException, SpotifyAuthException;
}
