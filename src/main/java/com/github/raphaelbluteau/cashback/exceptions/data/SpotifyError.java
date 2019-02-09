package com.github.raphaelbluteau.cashback.exceptions.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpotifyError {

    private int status;

    private String message;

}
