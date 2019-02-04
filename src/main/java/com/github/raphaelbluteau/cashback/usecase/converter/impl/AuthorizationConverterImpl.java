package com.github.raphaelbluteau.cashback.usecase.converter.impl;

import com.github.raphaelbluteau.cashback.gateway.data.response.AuthorizationGatewayResponse;
import com.github.raphaelbluteau.cashback.usecase.converter.AuthorizationConverter;
import com.github.raphaelbluteau.cashback.usecase.data.response.Authorization;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class AuthorizationConverterImpl implements AuthorizationConverter {

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
