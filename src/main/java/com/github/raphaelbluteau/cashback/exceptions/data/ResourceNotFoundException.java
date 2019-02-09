package com.github.raphaelbluteau.cashback.exceptions.data;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
