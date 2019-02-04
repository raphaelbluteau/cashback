package com.github.raphaelbluteau.cashback.http;

import com.github.raphaelbluteau.cashback.http.converter.AuthorizationHttpResponseConverter;
import com.github.raphaelbluteau.cashback.http.data.response.AuthorizationHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.AuthorizationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthorizationUseCase service;
    private final AuthorizationHttpResponseConverter converter;

    @GetMapping
    public AuthorizationHttpResponse getAccessToken() {

        return converter.toResponse(service.getAuthorization());
    }
}
