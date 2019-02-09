package com.github.raphaelbluteau.cashback.exceptions.data;

import lombok.Getter;

@Getter
public class InternalServerErrorException extends Exception {

    private final int code;

    public InternalServerErrorException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }
}
