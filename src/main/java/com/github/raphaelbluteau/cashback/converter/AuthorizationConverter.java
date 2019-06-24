package com.github.raphaelbluteau.cashback.converter;

import com.github.raphaelbluteau.cashback.gateway.data.response.AuthorizationGatewayResponse;
import com.github.raphaelbluteau.cashback.http.data.response.AuthorizationHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.data.response.Authorization;

public interface AuthorizationConverter {

    Authorization toResponse(AuthorizationGatewayResponse gatewayResponse);

    AuthorizationHttpResponse toResponse(Authorization authorizationResponse);
}
