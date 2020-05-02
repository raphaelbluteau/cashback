package com.github.raphaelbluteau.cashback.usecase.impl;

import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyAuthException;
import com.github.raphaelbluteau.cashback.gateway.AuthClient;
import com.github.raphaelbluteau.cashback.usecase.AuthorizationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationUseCaseImpl implements AuthorizationUseCase {

    private final AuthClient authClient;

    @Override
    public String getAuthorization() throws GatewayException, SpotifyAuthException {

        return authClient.getAccessToken();
    }
}
