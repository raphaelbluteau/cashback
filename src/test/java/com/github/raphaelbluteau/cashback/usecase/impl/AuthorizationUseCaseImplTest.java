package com.github.raphaelbluteau.cashback.usecase.impl;

import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyAuthException;
import com.github.raphaelbluteau.cashback.gateway.AuthClient;
import com.github.raphaelbluteau.cashback.usecase.AuthorizationUseCase;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AuthorizationUseCaseImplTest {

    @MockBean
    private AuthClient authClient;

    private AuthorizationUseCase authorizationUseCase;

    @Before
    public void setUp() {

        authorizationUseCase = new AuthorizationUseCaseImpl(authClient);
    }

    @Test
    public void testGetAuthorization() throws GatewayException, SpotifyAuthException {

        Mockito.when(authClient.getAccessToken())
                .thenReturn("Bearer token");

        String result = authorizationUseCase.getAuthorization();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualToIgnoringCase("Bearer token");

    }

    @Test
    public void testGetAuthorizationNull() throws GatewayException, SpotifyAuthException {

        Mockito.when(authClient.getAccessToken()).thenReturn(null);

        Assertions.assertThat(authorizationUseCase.getAuthorization()).isNull();
    }

}