package com.github.raphaelbluteau.cashback.converter.impl;

import com.github.raphaelbluteau.cashback.converter.AuthorizationConverter;
import com.github.raphaelbluteau.cashback.gateway.data.response.AuthorizationGatewayResponse;
import com.github.raphaelbluteau.cashback.http.data.response.AuthorizationHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.data.response.Authorization;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class AuthorizationConverterImpl implements AuthorizationConverter {

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

    @Override
    public Authorization toResponse(AuthorizationGatewayResponse gatewayResponse) {

        if (isNull(gatewayResponse)) {
            return null;
        }

        return Authorization.builder()
                .accessToken(gatewayResponse.getAccessToken())
                .tokenType(gatewayResponse.getTokenType())
                .expiresIn(gatewayResponse.getExpiresIn())
                .build();
    }
}
