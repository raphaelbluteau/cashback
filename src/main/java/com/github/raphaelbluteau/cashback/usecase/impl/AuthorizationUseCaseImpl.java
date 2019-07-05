package com.github.raphaelbluteau.cashback.usecase.impl;

import com.github.raphaelbluteau.cashback.converter.AuthorizationConverter;
import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyAuthException;
import com.github.raphaelbluteau.cashback.gateway.SpotifyGateway;
import com.github.raphaelbluteau.cashback.usecase.AuthorizationUseCase;
import com.github.raphaelbluteau.cashback.usecase.data.response.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationUseCaseImpl implements AuthorizationUseCase {

    private final SpotifyGateway spotifyGateway;
    private final AuthorizationConverter converter;

    @Override
    public Authorization getAuthorization() throws GatewayException, SpotifyAuthException {

        return converter.toResponse(spotifyGateway.getAuthorization());
    }
}
