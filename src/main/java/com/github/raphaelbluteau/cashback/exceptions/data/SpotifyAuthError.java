package com.github.raphaelbluteau.cashback.exceptions.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotifyAuthError {

    private String error;

    @JsonProperty("error_description")
    private String errorDescription;
}
