package com.github.raphaelbluteau.cashback.usecase.impl;

import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyAuthException;
import com.github.raphaelbluteau.cashback.gateway.SpotifyGateway;
import com.github.raphaelbluteau.cashback.gateway.data.response.AuthorizationGatewayResponse;
import com.github.raphaelbluteau.cashback.gateway.impl.SpotifyGatewayImpl;
import com.github.raphaelbluteau.cashback.usecase.AuthorizationUseCase;
import com.github.raphaelbluteau.cashback.usecase.converter.AuthorizationConverter;
import com.github.raphaelbluteau.cashback.usecase.converter.impl.AuthorizationConverterImpl;
import com.github.raphaelbluteau.cashback.usecase.data.response.Authorization;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AuthorizationUseCaseImplTest {

    SpotifyGateway spotifyGateway;
    AuthorizationUseCase authorizationUseCase;

    @Before
    public void setUp() throws Exception {

        AuthorizationConverter authorizationConverter = new AuthorizationConverterImpl();
        spotifyGateway = Mockito.mock(SpotifyGatewayImpl.class);
        authorizationUseCase = new AuthorizationUseCaseImpl(spotifyGateway, authorizationConverter);
    }

    @Test
    public void getAuthorization() throws GatewayException, SpotifyAuthException {

        Mockito.when(spotifyGateway.getAuthorization())
                .thenReturn(getResponse());

        Authorization result = authorizationUseCase.getAuthorization();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getAccessToken()).isEqualToIgnoringCase("token");
        Assertions.assertThat(result.getExpiresIn()).isEqualTo(3600);
        Assertions.assertThat(result.getTokenType()).isEqualToIgnoringCase("type");
    }

    private AuthorizationGatewayResponse getResponse() {

        return AuthorizationGatewayResponse.builder()
                .accessToken("token")
                .expiresIn(3600)
                .tokenType("type")
                .build();
    }
}