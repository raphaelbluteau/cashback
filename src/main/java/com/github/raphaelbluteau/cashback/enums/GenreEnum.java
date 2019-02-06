package com.github.raphaelbluteau.cashback.enums;

public enum GenreEnum {
    POP,
    MPB,
    CLASSIC,
    ROCK;

    public static GenreEnum getFrom(String value) {
        return GenreEnum.valueOf(value.toUpperCase());
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
