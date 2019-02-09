package com.github.raphaelbluteau.cashback.exceptions.data;

import lombok.Getter;

@Getter
public class GatewayException extends Exception {

    private int code;

    public GatewayException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }
}
