package com.github.raphaelbluteau.cashback.gateway.impl;

import com.github.raphaelbluteau.cashback.config.SpotifyConfigurationProperties;
import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyAuthException;
import com.github.raphaelbluteau.cashback.gateway.AuthClient;
import com.github.raphaelbluteau.cashback.gateway.AuthRequests;
import com.github.raphaelbluteau.cashback.gateway.data.response.AuthorizationGatewayResponse;
import com.github.raphaelbluteau.cashback.gateway.data.response.SpotifyErrorResponse;
import com.google.gson.Gson;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import okhttp3.Credentials;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthClientImpl implements AuthClient {

    private final SpotifyConfigurationProperties properties;
    private final AuthRequests authRequests;

    private static final String SPOTIFY = "spotify";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String GRANT_TYPE = "grant_type";

    @CircuitBreaker(name = SPOTIFY)
    @Bulkhead(name = SPOTIFY)
    @Retry(name = SPOTIFY)
    public String getAccessToken() throws GatewayException, SpotifyAuthException {

        Response<AuthorizationGatewayResponse> response;

        try {
            response = authRequests.getAuthorization(buildHeaders(), properties.getGrantType()).execute();
        } catch (IOException e) {
            throw new GatewayException(e.getMessage(), e);
        }

        if (response.isSuccessful() && response.body() != null) {
            return response.body().getAccessToken();
        } else {
            assert response.errorBody() != null;

            String errorBodyString = null;

            try {
                errorBodyString = response.errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            SpotifyErrorResponse errorResponse = new Gson().fromJson(response.errorBody().charStream(), SpotifyErrorResponse.class);
            String errorMessage = String.format("%s - %s", errorResponse.getError(), errorResponse.getDescription());
            throw new SpotifyAuthException(errorMessage, errorBodyString, response.code());
        }

    }

    private Map<String, String> buildHeaders() {
        Map<String, String> fields = new HashMap<>();
        fields.put("Authorization", Credentials.basic(properties.getClientId(), properties.getClientSecret()));
        return fields;
    }
}
