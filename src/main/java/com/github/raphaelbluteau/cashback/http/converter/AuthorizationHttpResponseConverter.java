package com.github.raphaelbluteau.cashback.http.converter;

import com.github.raphaelbluteau.cashback.http.data.response.AuthorizationHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.data.response.Authorization;

public interface AuthorizationHttpResponseConverter {

    AuthorizationHttpResponse toResponse(Authorization authorizationResponse);
}
