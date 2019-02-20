package com.github.raphaelbluteau.cashback.http.converter.impl;

import com.github.raphaelbluteau.cashback.http.converter.AuthorizationHttpResponseConverter;
import com.github.raphaelbluteau.cashback.http.data.response.AuthorizationHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.data.response.Authorization;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AuthorizationHttpResponseConverterImplTest {

    private AuthorizationHttpResponseConverter authorizationHttpResponseConverter;

    @Before
    public void setUp() {
        authorizationHttpResponseConverter = new AuthorizationHttpResponseConverterImpl();
    }

    @Test
    public void toResponse() {

        Authorization authorization = Authorization.builder()
                .tokenType("tokenType")
                .accessToken("accessToken")
                .expiresIn(3600)
                .build();
        AuthorizationHttpResponse result = authorizationHttpResponseConverter.toResponse(authorization);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getAccessToken()).isEqualToIgnoringCase("accessToken");
        Assertions.assertThat(result.getTokenType()).isEqualToIgnoringCase("tokenType");
        Assertions.assertThat(result.getExpiresIn()).isEqualTo(3600);

        Assertions.assertThat(authorizationHttpResponseConverter.toResponse(null)).isNull();
    }
}