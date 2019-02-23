package com.github.raphaelbluteau.cashback.usecase.converter.impl;

import com.github.raphaelbluteau.cashback.gateway.data.response.AuthorizationGatewayResponse;
import com.github.raphaelbluteau.cashback.usecase.converter.AuthorizationConverter;
import com.github.raphaelbluteau.cashback.usecase.data.response.Authorization;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AuthorizationConverterImplTest {

    private AuthorizationConverter authorizationConverter;

    @Before
    public void setUp() {

        authorizationConverter = new AuthorizationConverterImpl();
    }

    @Test
    public void toResponse() {

        Authorization result = authorizationConverter.toResponse(getGatewayResponse());

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getAccessToken()).isEqualToIgnoringCase("accessToken");
        Assertions.assertThat(result.getTokenType()).isEqualToIgnoringCase("tokenType");
        Assertions.assertThat(result.getExpiresIn()).isEqualTo(3600);
    }

    @Test
    public void testToResponseNull() {

        Assertions.assertThat(authorizationConverter.toResponse(null)).isNull();
    }

    private AuthorizationGatewayResponse getGatewayResponse() {
        return AuthorizationGatewayResponse.builder()
                .accessToken("accessToken")
                .tokenType("tokenType")
                .expiresIn(3600)
                .build();
    }
}