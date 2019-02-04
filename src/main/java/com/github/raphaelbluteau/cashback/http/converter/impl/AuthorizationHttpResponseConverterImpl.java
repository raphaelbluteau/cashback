package com.github.raphaelbluteau.cashback.http.converter.impl;

import com.github.raphaelbluteau.cashback.http.converter.AuthorizationHttpResponseConverter;
import com.github.raphaelbluteau.cashback.http.data.response.AuthorizationHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.data.response.Authorization;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class AuthorizationHttpResponseConverterImpl implements AuthorizationHttpResponseConverter {

    @Override
    public AuthorizationHttpResponse toResponse(Authorization authorizationResponse) {

        if (isNull(authorizationResponse)) {
            return null;
        }

        return AuthorizationHttpResponse.builder()
                .accessToken(authorizationResponse.getAccessToken())
                .tokenType(authorizationResponse.getTokenType())
                .expiresIn(authorizationResponse.getExpiresIn())
                .build();
    }
}
