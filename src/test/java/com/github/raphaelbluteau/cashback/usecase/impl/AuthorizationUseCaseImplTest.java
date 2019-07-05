package com.github.raphaelbluteau.cashback.usecase.impl;

import com.github.raphaelbluteau.cashback.converter.AuthorizationConverter;
import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyAuthException;
import com.github.raphaelbluteau.cashback.gateway.SpotifyGateway;
import com.github.raphaelbluteau.cashback.gateway.data.response.AuthorizationGatewayResponse;
import com.github.raphaelbluteau.cashback.usecase.AuthorizationUseCase;
import com.github.raphaelbluteau.cashback.usecase.data.response.Authorization;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class AuthorizationUseCaseImplTest {

    @MockBean
    private SpotifyGateway spotifyGateway;
    @MockBean
    private AuthorizationConverter authorizationConverter;

    private AuthorizationUseCase authorizationUseCase;

    @Before
    public void setUp() {

        authorizationUseCase = new AuthorizationUseCaseImpl(spotifyGateway, authorizationConverter);
    }

    @Test
    public void testGetAuthorization() throws GatewayException, SpotifyAuthException {

        Mockito.when(spotifyGateway.getAuthorization())
                .thenReturn(getResponse());
        Mockito.when(authorizationConverter.toResponse(any(AuthorizationGatewayResponse.class)))
                .thenReturn(getAuthorizationUseCaseResponse());

        Authorization result = authorizationUseCase.getAuthorization();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getAccessToken()).isEqualToIgnoringCase("token");
        Assertions.assertThat(result.getExpiresIn()).isEqualTo(3600);
        Assertions.assertThat(result.getTokenType()).isEqualToIgnoringCase("type");

    }

    @Test
    public void testGetAuthorizationNull() throws GatewayException, SpotifyAuthException {

        Mockito.when(spotifyGateway.getAuthorization()).thenReturn(null);
        Mockito.when(authorizationConverter.toResponse(any(AuthorizationGatewayResponse.class)))
                .thenReturn(null);

        Assertions.assertThat(authorizationUseCase.getAuthorization()).isNull();
    }

    private AuthorizationGatewayResponse getResponse() {

        return AuthorizationGatewayResponse.builder()
                .accessToken("token")
                .expiresIn(3600)
                .tokenType("type")
                .build();
    }

    private Authorization getAuthorizationUseCaseResponse() {

        return Authorization.builder()
                .tokenType("type")
                .accessToken("token")
                .expiresIn(3600)
                .build();
    }
}