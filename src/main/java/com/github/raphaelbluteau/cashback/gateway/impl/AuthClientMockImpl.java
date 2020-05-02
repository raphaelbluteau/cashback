package com.github.raphaelbluteau.cashback.gateway.impl;

import com.github.raphaelbluteau.cashback.gateway.AuthClient;

public class AuthClientMockImpl implements AuthClient {

    @Override
    public String getAccessToken() {
        return "Bearer token";
    }
}
