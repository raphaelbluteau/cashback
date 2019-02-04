package com.github.raphaelbluteau.cashback.usecase.converter;

import com.github.raphaelbluteau.cashback.gateway.data.response.AuthorizationGatewayResponse;
import com.github.raphaelbluteau.cashback.usecase.data.response.Authorization;

public interface AuthorizationConverter {

    Authorization toResponse(AuthorizationGatewayResponse gatewayResponse);

}
