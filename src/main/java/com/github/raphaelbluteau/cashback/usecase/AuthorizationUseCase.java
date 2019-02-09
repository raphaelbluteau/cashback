package com.github.raphaelbluteau.cashback.usecase;

import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyAuthException;
import com.github.raphaelbluteau.cashback.usecase.data.response.Authorization;

public interface AuthorizationUseCase {

    Authorization getAuthorization() throws GatewayException, SpotifyAuthException;
}
