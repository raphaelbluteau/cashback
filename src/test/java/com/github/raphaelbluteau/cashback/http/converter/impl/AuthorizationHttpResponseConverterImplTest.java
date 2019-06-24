package com.github.raphaelbluteau.cashback.http.converter.impl;

import com.github.raphaelbluteau.cashback.converter.AuthorizationConverter;
import com.github.raphaelbluteau.cashback.converter.impl.AuthorizationConverterImpl;
import com.github.raphaelbluteau.cashback.gateway.data.response.AuthorizationGatewayResponse;
import com.github.raphaelbluteau.cashback.http.data.response.AuthorizationHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.data.response.Authorization;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AuthorizationHttpResponseConverterImplTest {

    private AuthorizationConverter authorizationConverter;

    @Before
    public void setUp() {
        authorizationConverter = new AuthorizationConverterImpl();
    }

    @Test
    public void toResponse() {

        Authorization authorization = Authorization.builder()
                .tokenType("tokenType")
                .accessToken("accessToken")
                .expiresIn(3600)
                .build();
        AuthorizationHttpResponse result = authorizationConverter.toResponse(authorization);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getAccessToken()).isEqualToIgnoringCase("accessToken");
        Assertions.assertThat(result.getTokenType()).isEqualToIgnoringCase("tokenType");
        Assertions.assertThat(result.getExpiresIn()).isEqualTo(3600);

        Assertions.assertThat(authorizationConverter.toResponse((AuthorizationGatewayResponse) null)).isNull();
    }
}