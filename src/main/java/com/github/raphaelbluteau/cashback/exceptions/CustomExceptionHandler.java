package com.github.raphaelbluteau.cashback.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.raphaelbluteau.cashback.exceptions.data.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@RestControllerAdvice
@RequiredArgsConstructor
@Log
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final Environment env;

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SpotifyAuthException.class)
    public SpotifyAuthError handleSpotifyAuthError(SpotifyAuthException ex) throws IOException {

        return new ObjectMapper().readValue(ex.getBody(), SpotifyAuthError.class);
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(GatewayException.class)
    public ApiError handleGatewayException(GatewayException ex) {

        return ApiError.builder()
                .status(HttpStatus.BAD_GATEWAY.getReasonPhrase())
                .message(env.getProperty("error.message.bad_gateway"))
                .developerMessage(ex.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler(SpotifyException.class)
    public ResponseEntity<ApiError> handleSpotifyException(SpotifyException ex) throws IOException {

        SpotifyError spotifyError = new ObjectMapper().readValue(ex.getBody(), SpotifyError.class);
        HttpStatus httpStatus = HttpStatus.valueOf(spotifyError.getStatus());
        ApiError apiError = ApiError.builder()
                .status(httpStatus.getReasonPhrase())
                .message(spotifyError.getMessage())
                .developerMessage(ex.getLocalizedMessage())
                .build();

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiError handleNotFoundException(ResourceNotFoundException ex) {

        return ApiError.builder()
                .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                .developerMessage(ex.getLocalizedMessage())
                .message(env.getProperty("error.message.not_found"))
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        super.handleNoHandlerFoundException(ex, headers, status, request);

        ApiError apiError = ApiError.builder()
                .status(status.getReasonPhrase())
                .message(env.getProperty("error.message.internal_server_error"))
                .developerMessage(ex.getLocalizedMessage())
                .build();

        return new ResponseEntity<>(apiError, status);
    }
}
