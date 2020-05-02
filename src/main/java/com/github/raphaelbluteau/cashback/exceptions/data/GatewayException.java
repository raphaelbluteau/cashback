package com.github.raphaelbluteau.cashback.exceptions.data;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GatewayException extends Exception {

    private int code = HttpStatus.BAD_GATEWAY.value();

    public GatewayException(String message, Throwable cause) {
        super(message, cause);
    }

    public GatewayException(String message) {
        super(message);
    }
}
