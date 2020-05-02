package com.github.raphaelbluteau.cashback.exceptions.data;

import lombok.Getter;

@Getter
public class SpotifyAuthException extends Exception {

    private final String body;
    private final int code;

    public SpotifyAuthException(String message, String body, int code) {
        super(message);
        this.body = body;
        this.code = code;
    }
}
