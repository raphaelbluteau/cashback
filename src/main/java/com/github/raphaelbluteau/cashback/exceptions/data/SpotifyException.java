package com.github.raphaelbluteau.cashback.exceptions.data;

import lombok.Getter;

@Getter
public class SpotifyException extends Exception {

    private final String body;
    private final int code;

    public SpotifyException(String message, Throwable cause, String body, int code) {
        super(message, cause);
        this.body = body;
        this.code = code;
    }
}
